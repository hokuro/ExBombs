/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.network.MessageBlockRadarUpdate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;;

public class GuiBlockRadar extends GuiScreen {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/radarGUI.png");
	private static final ResourceLocation texOver = new ResourceLocation("exbombs:textures/gui/radarOverGUI.png");
	private static final int[] RADAR_SIZE = new int[]{16,32,64,128,256};

	private Minecraft mc;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private int renderTicks;
	private int alphaTicks;
	private float renderAlpha;
	private List<BlockPos> blockPos;
	private IBlockState targetBlock;
	private ItemStack drawIcon;
	private int index = 0;

	public GuiBlockRadar(Minecraft minecraft, String string, int meta, int size) {
		this.mc = minecraft;
		this.renderTicks = 0;
		this.alphaTicks = 0;
		this.renderAlpha = 1.0F;

		targetBlock = Block.getBlockFromName(string).getStateFromMeta(meta);
		index = size;
		drawIcon = new ItemStack(targetBlock.getBlock(),1,targetBlock.getBlock().getMetaFromState(targetBlock));
		blockPos = new ArrayList<BlockPos>();
	}

	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(101, this.width / 2 - 125, this.imgTop + 175, 20, 20, "△"));
		this.buttonList.add(new GuiButton(102, this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽"));
		this.buttonList.add(new GuiButton(103, this.width / 2 + 75, this.imgTop + 195, 40, 20, "Search"));

		iconx = this.width / 2 + 90;
		icony = this.imgTop + 175;
	}

	public void drawScreen(int i, int j, float f) {
		try{
			drawDefaultBackground();
		}catch(Exception ex){

		}

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(tex);
		drawTexturedModalRect(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);
		GL11.glPopMatrix();

		renderSearch();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		this.mc.renderEngine.bindTexture(tex);
		GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
		GL11.glRotatef(360 - this.renderTicks * 3 % 360, 0.0F, 0.0F, 1.0F);
		drawTexturedModalRect(0, 0, 0, 214, 120, 42);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texOver);
		drawTexturedModalRect(this.imgLeft - 28, this.imgTop - 37, 0, 0, 256, 256);
		GL11.glPopMatrix();

		for (int index = 0; index < this.buttonList.size(); index++) {
			GuiButton guibutton = (GuiButton) this.buttonList.get(index);
			guibutton.drawButton(this.mc, i, j, f);
		}

		FontRenderer font = this.fontRenderer;
		font.drawString(new Integer(RADAR_SIZE[index]).toString() + " × " + new Integer(RADAR_SIZE[index]).toString(), this.width / 2 - 103, this.imgTop + 205, 0xFFFFFF);


		drawCenteredStringWithoutShadow(font, "Block Radar:" +
		drawIcon.getTooltip(Minecraft.getMinecraft().player, ITooltipFlag.TooltipFlags.NORMAL).get(0),
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
			if ((Math.abs(pos.getX() - this.mc.player.posX) <= size) && (Math.abs(pos.getZ() - this.mc.player.posZ) <= size)) {
				int x = (int) (pos.getX() - this.mc.player.posX) / 2;
				int z = (int) (pos.getZ() - this.mc.player.posZ) / 2;

				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				int y = (int) this.mc.player.posY - pos.getY();
				if (Math.abs(y) <= 10){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, renderAlpha);
				}else if (y < 0){
					GL11.glColor4f(0.098039216F, 0.490196078F, 0.862745098F, renderAlpha);
				}else{
					GL11.glColor4f(1.0F, 0.54902F, 0.0F, renderAlpha);
				}
				this.mc.renderEngine.bindTexture(tex);
				GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
				GL11.glRotatef(-((int) this.mc.player.rotationYaw % 360), 0.0F, 0.0F, 1.0F);
				drawTexturedModalRect(-2 - x, -2 - z, 252, 252, 4, 4);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}

	public void actionPerformed(GuiButton guibutton) {
		switch(guibutton.id){
		case 101:
			++index;
			if (index >= RADAR_SIZE.length){
				index = 0;
			}
			break;
		case 102:
			--index;
			if (index < 0){
				index = RADAR_SIZE.length-1;
			}
			break;
		case 103:
			searchBlock();
			break;
		}
	}

	private void searchBlock(){
		blockPos.clear();
		int px = mc.player.getPosition().getX();
		int py = mc.player.getPosition().getY();
		int pz = mc.player.getPosition().getZ();
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
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
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
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z))== targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
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
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
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
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
						blockPos.add(pos);
						yflag = true;
						break;
					}
				}

				// 頭上方向を探索
				for (int y = py; y < 255 && yflag; y++){
					BlockPos pos = new BlockPos(x,y,z);
					if (mc.getMinecraft().world.getBlockState(new BlockPos(x,y,z)) == targetBlock){
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
    public void onGuiClosed()
    {
		// サイズをサーバーに送る
		Mod_ExBombs.INSTANCE.sendToServer(new MessageBlockRadarUpdate(index));
    }

	public boolean doesGuiPauseGame() {
		return false;
	}

    private void drawItemStack2(ItemStack stack, int x, int y, String altText)
    {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        float zLevel = 200.0F;
        RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
        itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, stack, x, y , altText);
        zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
}
