package mod.exbombs.render;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.EntityFrozenBomb;
import mod.exbombs.model.ModelCube;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFrozenBomb extends Render<EntityFrozenBomb> {
	ModelCube model;
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/entity/frozenBomb.png");

	public RenderFrozenBomb(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
		this.model = new ModelCube();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFrozenBomb entity) {
		return tex;
	}

	public void RenderEntityBomb(EntityFrozenBomb entitybomb, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.renderManager.renderEngine.bindTexture(tex);
		this.model.render(entitybomb, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	public void doRender(EntityFrozenBomb entity, double d, double d1, double d2, float f, float f1) {
		RenderEntityBomb((EntityFrozenBomb) entity, d, d1, d2, f, f1);
	}

}
