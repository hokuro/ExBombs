package mod.exbombs.helper;

import mod.exbombs.core.ModCommon;
import mod.exbombs.gui.GuiBlockRadar;
import mod.exbombs.gui.GuiSpawnRadar;
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
		if ( GuiID == ModCommon.MOD_GUI_ID_SPAWNRADAR){
			gui = new GuiSpawnRadar(Minecraft.getMinecraft(), new Integer(param[0].toString()).intValue());
		}else if (GuiID == ModCommon.MOD_GUI_ID_BLOCKRADER){
			if (param.length == 3){
				gui = new GuiBlockRadar(Minecraft.getMinecraft(), param[0].toString(),
						new Integer(param[1].toString()).intValue(),
						new Integer(param[2].toString()).intValue());
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
