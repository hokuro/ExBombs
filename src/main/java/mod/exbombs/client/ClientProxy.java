package mod.exbombs.client;

import mod.exbombs.core.CommonProxy;
import mod.exbombs.entity.EntityBomb;
import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.entity.EntityCore;
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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public ClientProxy(){

	}

	@Override
	public void registerRenderInfomation(){
		RenderingRegistry.registerEntityRenderingHandler(EntityBomb.class,   new IRenderFactory<EntityBomb>() {
			@Override
			public Render<? super EntityBomb> createRenderFor(RenderManager manager) {
				return new RenderBomb(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityMissile.class,   new IRenderFactory<EntityMissile>() {
			@Override
			public Render<? super EntityMissile> createRenderFor(RenderManager manager) {
				return new RenderMissile(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityNuclearExplosivePrimed.class,   new IRenderFactory<EntityNuclearExplosivePrimed>() {
			@Override
			public Render<? super EntityNuclearExplosivePrimed> createRenderFor(RenderManager manager) {
				return new RenderNuclearExplosivePrimed(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityTunnelExplosivePrimed.class,   new IRenderFactory<EntityTunnelExplosivePrimed>() {
			@Override
			public Render<? super EntityTunnelExplosivePrimed> createRenderFor(RenderManager manager) {
				return new RenderTunnelExplosivePrimed(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityChunkEraserPrimed.class,   new IRenderFactory<EntityChunkEraserPrimed>() {
			@Override
			public Render<? super EntityChunkEraserPrimed> createRenderFor(RenderManager manager) {
				return new RenderChunkEraserEsplosivePrived(manager);
			}
		});
	}

    @Override
    public EntityPlayer getEntityPlayerInstance() {
        return Minecraft.getMinecraft().player;
    }

	@Override
	public void registerCompnents(){
		ClientRegistry.registerTileEntity(TileEntityFuse.class, EntityCore.TILEENTITY_FUSE, new TileEntityFuseRender());
	}
}
