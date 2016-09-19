/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.render;

import mod.exbombs.entity.EntityNuclearExplosivePrimed;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderNuclearExplosivePrimed extends Render<EntityNuclearExplosivePrimed> {

	public RenderNuclearExplosivePrimed(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
	}

	public void renderNucExpPrimed(EntityNuclearExplosivePrimed entity, double x, double y, double z, float entityYaw, float partialTicks) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + 0.5F, (float)z);

        if ((float)entity.getFuse() - partialTicks + 1.0F < 10.0F)
        {
            float f = 1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 10.0F;
            f = MathHelper.clamp_float(f, 0.0F, 1.0F);
            f = f * f;
            f = f * f;
            float f1 = 1.0F + f * 0.3F;
            GlStateManager.scale(f1, f1, f1);
        }

        float f2 = (1.0F - ((float)entity.getFuse() - partialTicks + 1.0F) / 100.0F) * 0.8F;
        this.bindEntityTexture(entity);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        blockrendererdispatcher.renderBlockBrightness(ModRegisterBlock.block_NCBomb.getDefaultState(), entity.getBrightness(partialTicks));
        GlStateManager.translate(0.0F, 0.0F, 1.0F);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
            blockrendererdispatcher.renderBlockBrightness(ModRegisterBlock.block_NCBomb.getDefaultState(), 1.0F);
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        else if (entity.getFuse() / 5 % 2 == 0)
        {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
            GlStateManager.color(1.0F, 1.0F, 1.0F, f2);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            blockrendererdispatcher.renderBlockBrightness(ModRegisterBlock.block_NCBomb.getDefaultState(), 1.0F);
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}


	@Override
	public void doRender(EntityNuclearExplosivePrimed entity, double d, double d1, double d2, float f, float f1) {
		renderNucExpPrimed((EntityNuclearExplosivePrimed) entity, d, d1, d2, f, f1);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNuclearExplosivePrimed par1Entity) {
		return func_110808_a(par1Entity);
	}

	protected ResourceLocation func_110808_a(Entity entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
