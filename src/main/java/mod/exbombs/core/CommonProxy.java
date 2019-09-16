package mod.exbombs.core;

import mod.exbombs.entity.bomb.EntityBomb;
import mod.exbombs.entity.missile.EntityMissile;
import mod.exbombs.entity.prime.EntityChunkEraserPrimed;
import mod.exbombs.entity.prime.EntityNuclearExplosivePrimed;
import mod.exbombs.entity.prime.EntityTunnelExplosivePrimed;
import mod.exbombs.render.RenderBomb;
import mod.exbombs.render.RenderChunkEraserEsplosivePrived;
import mod.exbombs.render.RenderMissile;
import mod.exbombs.render.RenderNuclearExplosivePrimed;
import mod.exbombs.render.RenderTunnelExplosivePrimed;
import mod.exbombs.tileentity.TileEntityFuse;
import mod.exbombs.tileentity.render.TileEntityFuseRender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public abstract class CommonProxy {

	public CommonProxy(){

	}

	public abstract void registerRenderInfomation();
	public abstract void registerCompnents(Register<TileEntityType<?>> event);
    public abstract PlayerEntity getPlayerEntityInstance();

	static class Server extends CommonProxy{
		public Server(){

		}

		public void registerRenderInfomation(){

		}
		public void registerCompnents(Register<TileEntityType<?>> event){

			//event.getRegistry().register(EntityCore.Inst().FUSE);
		}

	    public PlayerEntity getPlayerEntityInstance() {return null;}
	}

	static class Client extends CommonProxy{
		public Client(){

		}

		@Override
		public void registerRenderInfomation(){
			RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class, RenderBomb::new);

			RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class, RenderMissile::new);

			RenderingRegistry.registerEntityRenderingHandler(EntityNuclearExplosivePrimed.class, RenderNuclearExplosivePrimed::new);

			RenderingRegistry.registerEntityRenderingHandler(EntityTunnelExplosivePrimed.class, RenderTunnelExplosivePrimed::new);

			RenderingRegistry.registerEntityRenderingHandler(EntityChunkEraserPrimed.class, RenderChunkEraserEsplosivePrived::new);

			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuse.class, new TileEntityFuseRender());
		}

	    @Override
	    public PlayerEntity getPlayerEntityInstance() {
	        return Minecraft.getInstance().player;
	    }

		@Override
		public void registerCompnents(Register<TileEntityType<?>> event){
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuse.class, new TileEntityFuseRender());
		}
	}
}
