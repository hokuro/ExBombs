package mod.exbombs.helper;

import mod.exbombs.core.ModCommon;
import mod.exbombs.gui.GuiRadar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ExBombsGuiHelper {
	public ExBombsGuiHelper() {
	}

	public void displayGui(EntityPlayer player, Object gui) {
		FMLClientHandler.instance().displayGuiScreen(player, (GuiScreen) gui);
	}

	public void displayGuiByID(EntityPlayer player, int GuiID, Object[] param) {
		GuiScreen gui = null;
		if ( GuiID == ModCommon.MOD_GUI_ID_RADAR){
			gui = new GuiRadar(Minecraft.getMinecraft(), (int)param[0]);
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
