package mod.exbombs.core;

import mod.exbombs.entity.EntityBomb;
import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.entity.EntityNuclearExplosivePrimed;
import mod.exbombs.entity.EntityTunnelExplosivePrimed;
import mod.exbombs.render.RenderBomb;
import mod.exbombs.render.RenderChunkEraserEsplosivePrived;
import mod.exbombs.render.RenderMissile;
import mod.exbombs.render.RenderNuclearExplosivePrimed;
import mod.exbombs.render.RenderTunnelExplosivePrimed;
import mod.exbombs.tileentity.TileEntityFuse;
import mod.exbombs.tileentity.render.TileEntityFuseRender;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public abstract class CommonProxy {

	public CommonProxy(){

	}

	public abstract void registerRenderInfomation();
	public abstract void registerCompnents(Register<TileEntityType<?>> event);
    public abstract EntityPlayer getEntityPlayerInstance();

	static class Server extends CommonProxy{
		public Server(){

		}

		public void registerRenderInfomation(){

		}
		public void registerCompnents(Register<TileEntityType<?>> event){

			//event.getRegistry().register(EntityCore.Inst().FUSE);
		}

	    public EntityPlayer getEntityPlayerInstance() {return null;}
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
//			RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class,   new IRenderFactory<EntityBomb>() {
//				@Override
//				public Render<? super EntityBomb> createRenderFor(RenderManager manager) {
//					return new RenderBomb(manager);
//				}
//			});
//
//			RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class,   new IRenderFactory<EntityMissile>() {
//				@Override
//				public Render<? super EntityMissile> createRenderFor(RenderManager manager) {
//					return new RenderMissile(manager);
//				}
//			});
//
//			RenderingRegistry.registerEntityRenderingHandler(EntityNuclearExplosivePrimed.class,   new IRenderFactory<EntityNuclearExplosivePrimed>() {
//				@Override
//				public Render<? super EntityNuclearExplosivePrimed> createRenderFor(RenderManager manager) {
//					return new RenderNuclearExplosivePrimed(manager);
//				}
//			});
//
//			RenderingRegistry.registerEntityRenderingHandler(EntityTunnelExplosivePrimed.class,   new IRenderFactory<EntityTunnelExplosivePrimed>() {
//				@Override
//				public Render<? super EntityTunnelExplosivePrimed> createRenderFor(RenderManager manager) {
//					return new RenderTunnelExplosivePrimed(manager);
//				}
//			});
//
//			RenderingRegistry.registerEntityRenderingHandler(EntityChunkEraserPrimed.class,   new IRenderFactory<EntityChunkEraserPrimed>() {
//				@Override
//				public Render<? super EntityChunkEraserPrimed> createRenderFor(RenderManager manager) {
//					return new RenderChunkEraserEsplosivePrived(manager);
//				}
//			});
		}

	    @Override
	    public EntityPlayer getEntityPlayerInstance() {
	        return Minecraft.getInstance().player;
	    }

		@Override
		public void registerCompnents(Register<TileEntityType<?>> event){
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFuse.class, new TileEntityFuseRender());
		}
	}
}