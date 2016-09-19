/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.render;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.EntityWaterBomb;
import mod.exbombs.model.ModelCube;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWaterBomb extends Render<EntityWaterBomb> {
	ModelCube model;
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/entity/waterBomb.png");

	public RenderWaterBomb(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
		this.model = new ModelCube();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWaterBomb entity) {
		return tex;
	}

	public void RenderEntityBomb(EntityWaterBomb entitybomb, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.renderManager.renderEngine.bindTexture(tex);
		this.model.render(entitybomb, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	public void doRender(EntityWaterBomb entity, double d, double d1, double d2, float f, float f1) {
		RenderEntityBomb((EntityWaterBomb) entity, d, d1, d2, f, f1);
	}

}
