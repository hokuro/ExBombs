/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import mod.exbombs.core.ModCommon;
import mod.exbombs.inventory.ContainerSpawnRadar;
import mod.exbombs.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;;

public class GuiSpawnRadar extends ContainerScreen<ContainerSpawnRadar> {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/radargui.png");
	private static final ResourceLocation texOver = new ResourceLocation("exbombs:textures/gui/radarovergui.png");

	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	private int renderTicks;
	private int alphaTicks;
	private float renderAlpha;
	private Map<ResourceLocation,Entity> entityMap;
	private List<BlockPos> spawnerPos;
	private int index;

	private List<ResourceLocation> entityList;
	private PlayerEntity player;

	public GuiSpawnRadar(ContainerSpawnRadar container, PlayerInventory inv, ITextComponent titleIn) {
		super(container, inv, titleIn);
		this.renderTicks = 0;
		this.alphaTicks = 0;
		this.renderAlpha = 1.0F;

		this.index = container.getIndex();
		// IDリストの初期化
		entityMap = new HashMap<ResourceLocation,Entity>();
		entityList = new ArrayList<ResourceLocation>();
		entityList.add(null); 			// ALL指定フラグ

		Registry.ENTITY_TYPE.forEach((et)->{
			if (et.getClassification() == EntityClassification.MONSTER){
				entityList.add(et.getRegistryName());
				entityMap.put(et.getRegistryName(), et.create(Minecraft.getInstance().world));
			}
		});

		if (index >= entityList.size() || index < 0){
			index = 0;
			SendMessage();
		}
		searchSpawner();

		player = Minecraft.getInstance().player;
	}

	@Override
	public void init() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttons.clear();
		this.addButton(new Button(this.width / 2 - 125, this.imgTop + 175, 20, 20, "△", (bt)->{
				++index;
				if (index >= entityList.size()){
					index = 0;
				}
				SendMessage();
				searchSpawner();
			}
		));
		this.addButton(new Button(this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽", (bt)->{
				--index;
				if (index < 0){
					index = entityList.size()-1;
				}
				SendMessage();
				searchSpawner();
			}
		));
	}

	@Override
	public void render(int i, int j, float f) {
		this.renderBackground();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		blit(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);
		GL11.glPopMatrix();

		renderSpawner();

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
			guibutton.render(i, j, f);
		}

		FontRenderer font = this.font;
		if (entityList.get(index) == null){
			font.drawString("ALL", this.width / 2 - 103, this.imgTop + 205, 0xFFFFFF);
		}else{
			Entity entity = this.entityMap.get(entityList.get(index));
			String dworString = entity!=null?entity.getDisplayName().getFormattedText():"Unknown";
			font.drawString(dworString, this.width / 2 - 103, this.imgTop + 205, 0xFFFFFF);
		}
		drawCenteredStringWithoutShadow(font, "Radar:", this.width / 2, this.imgTop - 22, 4210752);
	}

	private void renderSpawner() {
		Iterator<BlockPos> posList = spawnerPos.iterator();
		while(posList.hasNext()){
			BlockPos pos = posList.next();
			if ((Math.abs(pos.getX() - player.posX) <= 200.0D) && (Math.abs(pos.getZ() - player.posZ) <= 200.0D)) {
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

	private void searchSpawner(){
		spawnerPos = new ArrayList<BlockPos>();
		ResourceLocation id = entityList.get(index);
		Iterator<TileEntity> it = Minecraft.getInstance().world.loadedTileEntityList.iterator();
		TileEntity et;
		while(it.hasNext()){
			if (((et = it.next()) instanceof MobSpawnerTileEntity) &&
					(id == null || id.toString().equals(getEntityId((MobSpawnerTileEntity)et).toString()))){
				spawnerPos.add(et.getPos());
			}
		}
	}

	@Override
	public boolean keyPressed(int par1, int par2, int par3) {
		if (par2 == 1) {
			// データをサーバーへ送る
			MessageHandler.SendMessage_RadarUpdate(index);
		}
		return super.keyPressed(par1, par2, par3);
	}

	private ResourceLocation getEntityId(MobSpawnerTileEntity spawner){
		int ret = -1;
		CompoundNBT tag = new CompoundNBT();
		spawner.write(tag);
		CompoundNBT nbttag = tag.getCompound("SpawnData");
		if (!nbttag.contains("id")) {
			nbttag.putString("id", "Pig");
        }
		return new ResourceLocation(nbttag.getString("id"));
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

	public boolean doesGuiPauseGame() {
		return false;
	}

	private void SendMessage(){
		MessageHandler.SendMessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(index)});
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
}
