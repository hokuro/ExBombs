/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.render;

import com.mojang.blaze3d.platform.GlStateManager;

import mod.exbombs.block.BlockCore;
import mod.exbombs.block.BlockTunnelExplosive;
import mod.exbombs.entity.prime.EntityTunnelExplosivePrimed;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderTunnelExplosivePrimed extends EntityRenderer<EntityTunnelExplosivePrimed> {

	public RenderTunnelExplosivePrimed(EntityRendererManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
	}
	public void render(EntityTunnelExplosivePrimed entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        BlockState modelState = BlockCore.block_tunnel.getDefaultState();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y + 0.5F, (float)z);

        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F)
        {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scalef(f1, f1, f1);
        }
        float f2 = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);

        GlStateManager.translatef(-0.5F, -0.5F, 0.5F);

        Direction face = entity.face;
        if (face == Direction.NORTH){
        	face = Direction.EAST;
        }else if (face == Direction.WEST){
        	face = Direction.NORTH;
        }else if (face == Direction.SOUTH){
        	face = Direction.WEST;
        }else if (face == Direction.EAST){
        	face = Direction.SOUTH;
        }
        modelState = modelState.with(BlockTunnelExplosive.FACING, face);
        blockrendererdispatcher.renderBlockBrightness(
        		modelState,
        		entity.getBrightness());
        GlStateManager.translatef(0.0F, 0.0F, 1.0F);
        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
            blockrendererdispatcher.renderBlockBrightness(modelState, 1.0F);
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }
        else if (entity.getFuse() / 5 % 2 == 0)
        {
            GlStateManager.disableTexture();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.polygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcher.renderBlockBrightness(modelState, 1.0F);
            GlStateManager.polygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture();
        }
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityTunnelExplosivePrimed par1Entity) {
		return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
	}

	@Override
	public void doRender(EntityTunnelExplosivePrimed entity, double d, double d1, double d2, float f, float f1) {
		render((EntityTunnelExplosivePrimed) entity, d, d1, d2, f, f1);
	}
}
