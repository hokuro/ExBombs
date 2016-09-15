/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import mod.exbombs.core.ExBombs;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.network.MessageRadarUpdate;
import mod.exbombs.network.MessageShowGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;;

public class GuiRadar extends GuiScreen {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/radarGUI.png");
	private static final ResourceLocation texOver = new ResourceLocation("exbombs:textures/gui/radarOverGUI.png");

	private Minecraft mc;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	int renderTicks;
	private List<Class> entityClass;
	private Map<Class,Entity> entityMap;
	private List<BlockPos> spawnerPos;
	private int index;


	public GuiRadar(Minecraft minecraft, int idx) {
		this.mc = minecraft;
		this.renderTicks = 0;
		this.index = idx;
		// IDリストの初期化
		entityMap = new HashMap<Class,Entity>();
		entityClass = new ArrayList();
		entityClass.add(null);			// ALL用
		Iterator<Integer> keyIt = EntityList.idToClassMapping.keySet().iterator();
		while(keyIt.hasNext()){
			Integer key = keyIt.next();
			Class cl = EntityList.idToClassMapping.get(key);
			if (EntityLiving.class.isAssignableFrom(cl) && cl != EntityLiving.class && cl != EntityMob.class){
				entityClass.add(cl);
				try {
					entityMap.put(cl, (Entity)cl.getConstructor(new Class[]{World.class}).newInstance(new Object[]{mc.getMinecraft().theWorld}));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				}
			}
		}
		if (index >= entityClass.size()){
			index = 0;
    		ExBombs.INSTANCE.sendToServer(new MessageShowGui(index));
		}
		searchSpawner();
	}

	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);

		this.buttonList.clear();
		this.buttonList.add(new GuiButton(101, this.width / 2 - 125, this.imgTop + 175, 20, 20, "△"));
		this.buttonList.add(new GuiButton(102, this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽"));
	}

	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(tex);
		drawTexturedModalRect(this.imgLeft, this.imgTop, 0, 0, this.imgWidth, this.imgHeight);
		GL11.glPopMatrix();

		renderSpawner();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(tex);
		GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
		GL11.glRotatef(360 - this.renderTicks * 3 % 360, 0.0F, 0.0F, 1.0F);
		drawTexturedModalRect(0, 0, 0, 214, 120, 42);
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texOver);
		drawTexturedModalRect(this.imgLeft - 28, this.imgTop - 37, 0, 0, 256, 256);
		GL11.glPopMatrix();
		for (int index = 0; index < this.buttonList.size(); index++) {
			GuiButton guibutton = (GuiButton) this.buttonList.get(index);
			guibutton.drawButton(this.mc, i, j);
		}

		FontRenderer font = this.fontRendererObj;
		if (entityClass.get(index) == null){
			font.drawString("ALL", this.width / 2 - 103, this.imgTop + 200, 0xFFFFFF);
		}else{
			Entity entity = this.entityMap.get(entityClass.get(index));
			String dworString = entity!=null?entity.getName():"Unknown";
			font.drawString(dworString, this.width / 2 - 103, this.imgTop + 205, 0xFFFFFF);
		}
		drawCenteredStringWithoutShadow(font, "Radar:", this.width / 2, this.imgTop - 22, 4210752);
	}

	private void renderSpawner() {
		for (int i = 0; i < this.mc.theWorld.loadedEntityList.size(); i++) {
			if ((this.mc.theWorld.loadedEntityList.get(i) instanceof EntityMissile)) {
				Entity missile = (Entity) this.mc.theWorld.loadedEntityList.get(i);
				if ((Math.abs(missile.posX - this.mc.thePlayer.posX) <= 500.0D) && (Math.abs(missile.posZ - this.mc.thePlayer.posZ) <= 500.0D)) {
					int x = (int) (missile.posX - this.mc.thePlayer.posX) / 5;
					int z = (int) (missile.posZ - this.mc.thePlayer.posZ) / 5;

					GL11.glPushMatrix();
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.renderEngine.bindTexture(tex);
					GL11.glTranslatef(this.imgLeft + 100, this.imgTop + 100, 0.0F);
					GL11.glRotatef(-((int) this.mc.thePlayer.rotationYaw % 360), 0.0F, 0.0F, 1.0F);
					drawTexturedModalRect(-2 - x, -2 - z, 252, 252, 4, 4);
					GL11.glPopMatrix();
				}
			}
		}
	}

	public void actionPerformed(GuiButton guibutton) {
		switch(guibutton.id){
		case 101:
			++index;
			if (index >= entityClass.size()){
				index = 0;
			}
			break;
		case 102:
			--index;
			if (index < 0){
				index = entityClass.size()-1;
			}
			break;
		}
		ExBombs.INSTANCE.sendToServer(new MessageShowGui(index));
		searchSpawner();
	}

	private void searchSpawner(){
		spawnerPos = new ArrayList<BlockPos>();
		Class id = entityClass.get(index);
		Iterator<TileEntity> it = mc.getMinecraft().theWorld.loadedTileEntityList.iterator();
		TileEntity et;
		while(it.hasNext()){
			if (((et = it.next()) instanceof TileEntityMobSpawner) &&
					(id == null || id == getEntityId((TileEntityMobSpawner)et))){
				spawnerPos.add(et.getPos());
				if (id == null){System.out.println("## ALL");}else{}
				}else{
				if (et instanceof TileEntityMobSpawner){
				}
			}
		}
	}

	private int convPos(int value){
		if ( value == 0){return value;}
		if ((value%2) == 0){
			return value/2;
		}else{
			return -1 *((value-1)/2);
		}
	}



	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1) {
			// データをサーバーへ送る
			ExBombs.INSTANCE.sendToServer(new MessageRadarUpdate(index));
		}
		try {
			super.keyTyped(par1, par2);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private Class getEntityId(TileEntityMobSpawner spawner){
		int ret = -1;
		NBTTagCompound tag = new NBTTagCompound();
		spawner.writeToNBT(tag);
		NBTTagCompound nbttag = tag.getCompoundTag("SpawnData");
		if (!nbttag.hasKey("id", 8))
        {
			nbttag.setString("id", "Pig");
        }
		return EntityList.stringToClassMapping.get(nbttag.getString("id"));
	}

	private void drawCenteredStringWithoutShadow(FontRenderer fontrenderer, String s, int i, int j, int k) {
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	public void updateScreen() {
		this.renderTicks += 1;
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
