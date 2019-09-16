/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.exbombs.inventory.ContainerBlockRadar;
import mod.exbombs.network.MessageHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;;

public class GuiBlockRadar extends ContainerScreen<ContainerBlockRadar> {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/radargui.png");
	private static final ResourceLocation texOver = new ResourceLocation("exbombs:textures/gui/radarovergui.png");
	private static final int[] RADAR_SIZE = new int[]{16,32,64,128,256};

//	private Minecraft mc;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private int renderTicks;
	private int alphaTicks;
	private float renderAlpha;
	private List<BlockPos> blockPos;
	private BlockState targetBlock;
	private ItemStack drawIcon;
	private int index = 0;

	private World world;
	private PlayerEntity player;

	public GuiBlockRadar(ContainerBlockRadar container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);

		this.renderTicks = 0;
		this.alphaTicks = 0;
		this.renderAlpha = 1.0F;

		targetBlock = Registry.BLOCK.getOrDefault(container.getBlockID()).getDefaultState();
		index = container.getSize();
		drawIcon = new ItemStack(targetBlock.getBlock(),1);
		blockPos = new ArrayList<BlockPos>();

		world = Minecraft.getInstance().world;
		player = Minecraft.getInstance().player;
	}

	@Override
	public void init() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttons.clear();
		this.addButton(new Button(this.width / 2 - 125, this.imgTop + 175, 20, 20, "△", (bt)-> {
			++index;
				if (index >= RADAR_SIZE.length){
					index = 0;
				}
			}
		));

		this.addButton(new Button(this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽", (bt)-> {
				--index;
				if (index < 0){
					index = RADAR_SIZE.length-1;
				}
			}
		));

		this.addButton(new Button(this.width / 2 + 75, this.imgTop + 195, 40, 20, "Search", (bt)-> {
				searchBlock();
			}
		));
		iconx = this.width / 2 + 90;
		icony = this.imgTop + 175;
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		try{
			super.renderBackground();
		}catch(Exception ex){

		}

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		blit(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);
		GL11.glPopMatrix();

		renderSearch();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
		GL11.glRotatef(360 - this.renderTicks * 3 % 360, 0.0F, 0.0F, 1.0F);
		blit(0, 0, 0, 214, 120, 42);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texOver);
		blit(this.imgLeft - 28, this.imgTop - 37, 0, 0, 256, 256);
		GL11.glPopMatrix();

		for (int index = 0; index < this.buttons.size(); index++) {
			Widget guibutton = this.buttons.get(index);
			guibutton.render(mouseX, mouseY, partialTicks);
		}

		FontRenderer font = this.font;
		font.drawString(new Integer(RADAR_SIZE[index]).toString() + " × " + new Integer(RADAR_SIZE[index]).toString(), this.width / 2 - 103, this.imgTop + 205, 0xFFFFFF);


		drawCenteredStringWithoutShadow(font, "Block Radar:",
		//drawIcon.getTooltip(Minecraft.getInstance().player, ITooltipFlag.TooltipFlags.NORMAL).get(0),
		this.width / 2, this.imgTop - 12, 4210752);

		GL11.glPushMatrix();
		drawItemStack2(drawIcon,iconx,icony,"");
		GL11.glPopMatrix();
	}

	private int iconx = 0;
	private int icony = 0;
	private void renderSearch() {
		Iterator<BlockPos> posList = blockPos.iterator();
		int size = RADAR_SIZE[index];
		while(posList.hasNext()){
			BlockPos pos = posList.next();
			if ((Math.abs(pos.getX() -  Minecraft.getInstance().player.posX) <= size) && (Math.abs(pos.getZ() - player.posZ) <= size)) {
				int x = (int) (pos.getX() - player.posX) / 2;
				int z = (int) (pos.getZ() - player.posZ) / 2;

				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				int y = (int) player.posY - pos.getY();
				if (Math.abs(y) <= 10){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, renderAlpha);
				}else if (y < 0){
					GL11.glColor4f(0.098039216F, 0.490196078F, 0.862745098F, renderAlpha);
				}else{
					GL11.glColor4f(1.0F, 0.54902F, 0.0F, renderAlpha);
				}
				Minecraft.getInstance().getTextureManager().bindTexture(tex);
				GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
				GL11.glRotatef(-((int) player.rotationYaw % 360), 0.0F, 0.0F, 1.0F);
				blit(-2 - x, -2 - z, 252, 252, 4, 4);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}

	private void searchBlock(){
		blockPos.clear();
		int px = player.getPosition().getX();
		int py = player.getPosition().getY();
		int pz = player.getPosition().getZ();
		long t1 = System.currentTimeMillis();
		long t2;
		int size = RADAR_SIZE[index];
		// x+方向
		for (int x = px; x < px+size; x++){
			// z+方向
			for (int z = pz; z < pz+size; z++){
				boolean yflag = false;
				// 足元下頭上のうち一番近いブロックのみ記録する
				// 足元方向を探索
				for (int y = py; y > 0; y--){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}
			}

			// z-方向
			for (int z = pz; z > pz-size; z--){
				boolean yflag = false;
				// 足元下頭上のうち一番近いブロックのみ記録する
				// 足元方向を探索
				for (int y = py; y > 0; y--){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z))== targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}
			}
		}

		// x-方向
		for (int x = px; x > px-size; x--){
			// z+方向
			for (int z = pz; z < pz+size; z++){
				boolean yflag = false;
				// 足元下頭上のうち一番近いブロックのみ記録する
				// 足元方向を探索
				for (int y = py; y > 0; y--){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}
			}

			// z-方向
			for (int z = pz; z > pz-size; z--){
				boolean yflag = false;
				// 足元下頭上のうち一番近いブロックのみ記録する
				// 足元方向を探索
				for (int y = py; y > 0; y--){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (Minecraft.getInstance().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}
			}
		}
		t2 = System.currentTimeMillis();
	}

	private void drawCenteredStringWithoutShadow(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	public void updateScreen() {
		this.renderTicks += 1;
		this.alphaTicks += 1;
		if (alphaTicks%2 == 0){
			renderAlpha -= 0.1F;
			if (renderAlpha <= -0.2F){
				alphaTicks = 0;
				renderAlpha = 1.0F;
			}
		}
	}

	@Override
    public void onClose()
    {
		// サイズをサーバーに送る
		MessageHandler.SendMessage_BlockRadarUpdate(index);
    }

	public boolean doesGuiPauseGame() {
		return false;
	}

    private void drawItemStack2(ItemStack stack, int x, int y, String altText)
    {
        GlStateManager.translatef(0.0F, 0.0F, 32.0F);
        float zLevel = 200.0F;
        ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
        itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getInstance().fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y , altText);
        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

}
