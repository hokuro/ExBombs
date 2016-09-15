/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import mod.exbombs.core.RadarData;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.item.ItemRadar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModRegisterItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;;

public class GuiRadar extends GuiScreen {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/gui/radarGUI.png");
	private static final ResourceLocation texOver = new ResourceLocation("exbombs:textures/gui/radarOverGUI.png");

	private static final int  MAX_ZOOM = 4;
	private static final int MIN_ZOOM = 0;
	private static final int[] ZOOM = {128,256,512,1024,2048};

	private Minecraft mc;
	private int imgWidth;
	private int imgHeight;
	private int imgLeft;
	private int imgTop;
	int renderTicks;
	private List<String> entities;
	private RadarData radar;

	private GuiButton ViewMode;
	private List<BlockPos> spawner;


	public GuiRadar(Minecraft minecraft) {
		this.mc = minecraft;
		this.renderTicks = 0;
		radar = ((ItemRadar)ModRegisterItem.item_Radar).getRadarData(new ItemStack(ModRegisterItem.item_Radar), ExBombsMinecraftHelper.getWorld());
		entities = new ArrayList();
		Iterator<String> keyIt = EntityList.stringToClassMapping.keySet().iterator();
		while(keyIt.hasNext()){
			String key = keyIt.next();
			Class cl = EntityList.stringToClassMapping.get(key);
			if (EntityLivingBase.class.isAssignableFrom(cl)){
				entities.add(key);
			}
		}
		if (radar.index() >= entities.size()){
			radar.setData(radar.viewMode(),radar.zoom(),0);
			((ItemRadar)ModRegisterItem.item_Radar).setRadarData(new ItemStack(ModRegisterItem.item_Radar), ExBombsMinecraftHelper.getWorld(), radar);
		}
		searchSpawner();
	}

	public void initGui() {
		this.imgWidth = 205;
		this.imgHeight = 200;
		this.imgLeft = ((this.width - this.imgWidth) / 2);
		this.imgTop = ((this.height - this.imgHeight) / 2);


		this.buttonList.clear();
//		this.buttonList.add(new GuiButton(0, this.width / 2 + 100, this.imgTop + 195, 20, 20, "+"));
//		this.buttonList.add(new GuiButton(1, this.width / 2 + 50, this.imgTop + 195, 20, 20, "-"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 125, this.imgTop + 175, 20, 20, "△"));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 125, this.imgTop + 195, 20, 20, "▽"));
//		this.buttonList.add((ViewMode =new GuiButton(4, this.width / 2 + 70, this.imgTop-15, 50, 20, radar.viewMode()?I18n.translateToLocal("AbsoluteView"):I18n.translateToLocal("MapView"))));

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
		drawCenteredStringWithoutShadow(font, "X:"+Integer.toString(radar.zoom()+1), this.width / 2 + 87, this.imgTop + 200, 0xFFFFFF);
		drawCenteredStringWithoutShadow(font, entities.get(radar.index()), this.width / 2 - 80, this.imgTop + 200, 0xFFFFFF);
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
		int zoom = radar.zoom();
		int index = radar.index();
		boolean mode = radar.viewMode();
		switch(guibutton.id){
		case 0:
			// 縮小
			zoom++;
			if (zoom > MAX_ZOOM){zoom = MIN_ZOOM;}
			break;
		case 1:
			// 拡大
			zoom--;
			if (zoom < MIN_ZOOM){zoom = MAX_ZOOM;}
			break;
		case 2:
			++index;
			if (index >= entities.size()){
				index = 0;
			}
			break;
		case 3:
			--index;
			if (index < 0){
				index = entities.size()-1;
			}
			break;
		case 4:
			mode = !mode;
			ViewMode.displayString = mode?I18n.translateToLocal("AbsoluteView"):I18n.translateToLocal("MapView");
			break;
		}

		searchSpawner();
		radar.setData(mode,zoom,index);
		((ItemRadar)ModRegisterItem.item_Radar).setRadarData(new ItemStack(ModRegisterItem.item_Radar), ExBombsMinecraftHelper.getWorld(), radar);
	}

	private void searchSpawner(){
		spawner = new ArrayList<BlockPos>();
		BlockPos pos = ExBombsMinecraftHelper.getPlayer().getPosition();

		for (int x = 0; x < ZOOM[radar.zoom()]; x++){
			int xx = convPos(x);
			for (int z = 0; z < ZOOM[radar.zoom()]; z++){
				int zz = convPos(z);
				for (int y = 0; y < ZOOM[radar.zoom()]; y++){
					int yy = convPos(y);
					if ( Math.abs(Math.sqrt(x*x+y*y+z*z)) < (ZOOM[radar.zoom()]/2)){
						IBlockState state = ExBombsMinecraftHelper.getWorld().getBlockState(pos.add(xx, yy, zz));
						if (state.getBlock() == Blocks.mob_spawner){
							spawner.add(pos.add(x, y, z));
						}
					}
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
