package mod.exbombs.render;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.EntityBomb;
import mod.exbombs.model.ModelCube;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBomb extends Render<EntityBomb> {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/entity/bomb.png");
	ModelCube model;

	public RenderBomb(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
		this.model = new ModelCube();
	}


	public void doRender(EntityBomb entity, double d, double d1, double d2, float f, float f1) {
		RenderEntityBomb((EntityBomb) entity, d, d1, d2, f, f1);
	}

	public void RenderEntityBomb(EntityBomb entitybomb, double d, double d1, double d2, float f, float f1) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glScalef(0.5F, 0.5F, 0.5F);

		this.renderManager.renderEngine.bindTexture(getEntityTexture(entitybomb));

		this.model.render(entitybomb, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBomb entity) {
		return entity.getBombType().getTexture();
	}
}
