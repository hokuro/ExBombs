/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.helper;

import mod.exbombs.core.ModCommon;
import mod.exbombs.gui.GuiRadar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ExBombsGuiHelper {
	public ExBombsGuiHelper() {
	}

	public void displayGui(EntityPlayer player, Object gui) {
		FMLClientHandler.instance().displayGuiScreen(player, (GuiScreen) gui);
	}

	public void displayGuiByID(EntityPlayer player, int ID) {
		GuiScreen gui = null;
		if (ID == ModCommon.MOD_GUI_ID_RADAR) {
			if ((ExBombsMinecraftHelper.isKeyDown(42)) || (ExBombsMinecraftHelper.isKeyDown(54))) {
				RayTraceResult  pos = player.rayTrace(100000.0D, 1.0F);
				if (pos == null) {
					player.addChatComponentMessage(new TextComponentString("Target out of range!"));
				} else {
					player.addChatComponentMessage(new TextComponentString("X: " + pos.hitVec.xCoord + " Z: " + pos.hitVec.zCoord));
				}
			} else {
				gui = new GuiRadar(Minecraft.getMinecraft());
			}
		}

		if (gui != null) {
			displayGui(player, gui);
		}
	}

	public boolean isGuiOpen(Class<? extends GuiScreen> guiClass) {
		if (Minecraft.getMinecraft().currentScreen == null) {
			return false;
		}
		return Minecraft.getMinecraft().currentScreen.getClass().equals(guiClass);
	}

	public GuiScreen getCurrentGui() {
		return Minecraft.getMinecraft().currentScreen;
	}

	public void closeGui() {
		Minecraft.getMinecraft().displayGuiScreen(null);
		Minecraft.getMinecraft().setIngameFocus();
	}
}
