package mod.exbombs.core;

import mod.exbombs.entity.EntityCore;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public CommonProxy(){

	}

	public void registerRenderInfomation(){

	}
	public void registerCompnents(){
		GameRegistry.registerTileEntity(TileEntityFuse.class, EntityCore.TILEENTITY_FUSE);
	}

    public EntityPlayer getEntityPlayerInstance() {return null;}
}