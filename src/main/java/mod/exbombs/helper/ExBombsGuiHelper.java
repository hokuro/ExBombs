package mod.exbombs.helper;

import mod.exbombs.core.ModCommon;
import mod.exbombs.inventory.InteractionObjectBlockRadar;
import mod.exbombs.inventory.InteractionObjectSpawnRadar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExBombsGuiHelper {
	public ExBombsGuiHelper() {
	}

	public void displayGui(EntityPlayer player, Object gui) {
        Minecraft.getInstance().displayGuiScreen((GuiScreen)gui);
		//FMLClientHandler.instance().displayGuiScreen(player, (GuiScreen) gui);
	}

	public void displayGuiByID(EntityPlayer player, int GuiID, Object[] param) {
		//GuiScreen gui = null;
		if ( GuiID == ModCommon.MOD_GUI_ID_SPAWNRADAR){
			//gui = new GuiSpawnRadar(new Integer(param[0].toString()).intValue());
			NetworkHooks.openGui((EntityPlayerMP)player,
					new InteractionObjectSpawnRadar(),
					(buf)->{
						buf.writeInt(new Integer(param[0].toString()).intValue());
					}
					);
		}else if (GuiID == ModCommon.MOD_GUI_ID_BLOCKRADER){
			if (param.length == 3){
//				gui = new GuiBlockRadar(
//						new Integer(param[1].toString()).intValue(),
//						new Integer(param[2].toString()).intValue());

				NetworkHooks.openGui((EntityPlayerMP)player,
						new InteractionObjectBlockRadar(),
						(buf)->{
							buf.writeInt(new Integer(param[1].toString()).intValue());
							buf.writeInt(new Integer(param[2].toString()).intValue());
						}
						);
			}
		}
//		if (gui != null) {
//			displayGui(player, gui);
//		}
	}

	public boolean isGuiOpen(Class<? extends GuiScreen> guiClass) {
		if (Minecraft.getInstance().currentScreen == null) {
			return false;
		}
		return Minecraft.getInstance().currentScreen.getClass().equals(guiClass);
	}

	public GuiScreen getCurrentGui() {
		return Minecraft.getInstance().currentScreen;
	}

	public void closeGui() {
		Minecraft.getInstance().displayGuiScreen(null);
		//Minecraft.getInstance().setIngameFocus();
	}
}
