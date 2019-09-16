package mod.exbombs.helper;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.inventory.ContainerBlockRadar;
import mod.exbombs.inventory.ContainerMissile;
import mod.exbombs.inventory.ContainerSpawnRadar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExBombsGuiHelper {
	public ExBombsGuiHelper() {
	}

	public static void displayGuiByID(PlayerEntity player, String GuiID, Object[] param) {
		//GuiScreen gui = null;
		if ( GuiID.equals(ModCommon.MOD_GUI_ID_SPAWNRADAR)){
			NetworkHooks.openGui((ServerPlayerEntity)player,
					new SimpleNamedContainerProvider((id,playerInv,x)-> {
						return new ContainerSpawnRadar(Mod_ExBombs.CONTAINER_SPAWNRADAR, id, new Integer(param[0].toString()).intValue());
					}, new StringTextComponent("container.spawnradar")),
					(buf)->{
						buf.writeInt(new Integer(param[0].toString()).intValue());
					}
					);
		}else if (GuiID.equals(ModCommon.MOD_GUI_ID_BLOCKRADER)){
			if (param.length == 3){
				NetworkHooks.openGui((ServerPlayerEntity)player,
						new SimpleNamedContainerProvider((id,playerInv,x)-> {
							return new ContainerBlockRadar(Mod_ExBombs.CONTAINER_BLOCKRADER, id, param[0].toString(), new Integer(param[2].toString()).intValue());
						}, new StringTextComponent("container.blockradar")),
						(buf)->{
							buf.writeString(param[0].toString());
							buf.writeInt(new Integer(param[2].toString()).intValue());
						}
						);
			}
		}else if (GuiID.equals(ModCommon.MOD_GUI_ID_MISSILE)) {
			NetworkHooks.openGui((ServerPlayerEntity)player,
					new SimpleNamedContainerProvider((id,playerInv,x)-> {
						return new ContainerMissile(Mod_ExBombs.CONTAINER_MISSILE, id);
					}, new StringTextComponent("container.missile")),
					(buf)->{
						buf.writeInt(new Integer(param[0].toString()).intValue());
					}
					);
		}
	}

	public boolean isGuiOpen(Class<? extends Screen> guiClass) {
		if (Minecraft.getInstance().currentScreen == null) {
			return false;
		}
		return Minecraft.getInstance().currentScreen.getClass().equals(guiClass);
	}

	public Screen getCurrentGui() {
		return Minecraft.getInstance().currentScreen;
	}

	public void closeGui() {
		Minecraft.getInstance().displayGuiScreen(null);
	}
}
