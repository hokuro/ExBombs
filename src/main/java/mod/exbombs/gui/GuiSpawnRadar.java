/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import mod.exbombs.core.ModCommon;
import mod.exbombs.network.MessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;;

public class GuiSpawnRadar extends GuiScreen {
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

	public GuiSpawnRadar(int idx) {
		this.renderTicks = 0;
		this.alphaTicks = 0;
		this.renderAlpha = 1.0F;
		this.index = idx;
		// IDリストの初期化
		entityMap = new HashMap<ResourceLocation,Entity>();
		entityList = new ArrayList<ResourceLocation>();
		entityList.add(null); 			// ALL指定フラグ


		IRegistry.field_212629_r.forEach((et)->{
			if (et.getEntityClass() != null && (EntityLiving.class.isAssignableFrom(et.getEntityClass()) && et.getEntityClass() != EntityLiving.class && et.getEntityClass() != EntityMob.class)){
				entityList.add( et.getRegistryName());
				entityMap.put(et.getRegistryName(), et.create(Minecraft.getInstance().world));
			}
		});
//		for (ResourceLocation name : IRegistry.field_212629_r.){
//			if (name == null){continue;}
//			EntityType etype = IRegistry.field_212629_r.func_212608_b(name);
//			Class cl = etype.getEntityClass();
//			if (cl != null && (EntityLiving.class.isAssignableFrom(cl) && cl != EntityLiving.class && cl != EntityMob.class)){
//				try {
//					entityMap.put(name, (Entity)cl.getConstructor(new Class[]{World.class}).newInstance(new Object[]{Minecraft.getInstance().world}));
//					entityList.add(name);
//				} catch (InstantiationException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (NoSuchMethodException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				} catch (SecurityException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				}
//			}
//		}
		if (index >= entityList.size() || index < 0){
			index = 0;
			SendMessage();
		}
		searchSpawner();
	}


	@Override
	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		GuiButton b1 = new GuiButton(101, this.width / 2 - 125, this.imgTop + 175, 20, 20, "△"){
			public void onClick(double mouseX, double mouseY){
				++index;
				if (index >= entityList.size()){
					index = 0;
				}
				SendMessage();
				searchSpawner();
			}
		};
		GuiButton b2 = new GuiButton(102, this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽"){
			public void onClick(double mouseX, double mouseY){
				--index;
				if (index < 0){
					index = entityList.size()-1;
				}
				SendMessage();
				searchSpawner();
			}
		};

		this.buttons.clear();
		this.buttons.add(b1);
		this.buttons.add(b2);
		this.children.add(b1);
		this.children.add(b2);
	}

	@Override
	public void render(int i, int j, float f) {
		drawDefaultBackground();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		drawTexturedModalRect(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);
		GL11.glPopMatrix();

		renderSpawner();

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		Minecraft.getInstance().getTextureManager().bindTexture(tex);
		GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
		GL11.glRotatef(360 - this.renderTicks * 3 % 360, 0.0F, 0.0F, 1.0F);
		drawTexturedModalRect(0, 0, 0, 214, 120, 42);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getInstance().getTextureManager().bindTexture(texOver);
		drawTexturedModalRect(this.imgLeft - 28, this.imgTop - 37, 0, 0, 256, 256);
		GL11.glPopMatrix();

		for (int index = 0; index < this.buttons.size(); index++) {
			GuiButton guibutton = (GuiButton) this.buttons.get(index);
			guibutton.render(i, j, f);
		}

		FontRenderer font = this.fontRenderer;
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
			if ((Math.abs(pos.getX() - this.mc.player.posX) <= 200.0D) && (Math.abs(pos.getZ() - this.mc.player.posZ) <= 200.0D)) {
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
				Minecraft.getInstance().getTextureManager().bindTexture(tex);
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
			if (index >= entityList.size()){
				index = 0;
			}
			break;
		case 102:
			--index;
			if (index < 0){
				index = entityList.size()-1;
			}
			break;
		}
		SendMessage();
		searchSpawner();
	}

	private void searchSpawner(){
		spawnerPos = new ArrayList<BlockPos>();
		ResourceLocation id = entityList.get(index);
		Iterator<TileEntity> it = Minecraft.getInstance().world.loadedTileEntityList.iterator();
		TileEntity et;
		while(it.hasNext()){
			if (((et = it.next()) instanceof TileEntityMobSpawner) &&
					(id == null || id.toString().equals(getEntityId((TileEntityMobSpawner)et).toString()))){
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
//		try {
			return super.keyPressed(par1, par2,par3);
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		return false;
	}

	private ResourceLocation getEntityId(TileEntityMobSpawner spawner){
		int ret = -1;
		NBTTagCompound tag = new NBTTagCompound();
		spawner.write(tag);
		NBTTagCompound nbttag = tag.getCompound("SpawnData");
		if (!nbttag.hasKey("id"))
        {
			nbttag.setString("id", "Pig");
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
		//Mod_ExBombs.INSTANCE.sendToServer(new MessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(index)}));

		MessageHandler.SendMessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(index)});
	}
}
