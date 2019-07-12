package mod.exbombs.render;

import org.lwjgl.opengl.GL11;

import mod.exbombs.entity.EntityMissile;
import mod.exbombs.model.ModelMissile;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;


public class RenderMissile extends Render {
	private  static final ResourceLocation tex3 = new ResourceLocation("exbombs","textures/entity/unmatcheraserstsmissile.png");
	private  static final ResourceLocation tex2 = new ResourceLocation("exbombs","textures/entity/eraserstsmissile.png");
	private  static final ResourceLocation tex1 = new ResourceLocation("exbombs","textures/entity/nuclearstsmissile.png");
	private  static final ResourceLocation tex0 = new ResourceLocation("exbombs","textures/entity/tntstsmissile.png");;

	ModelMissile modelmissile;

	public RenderMissile(RenderManager renderManager) {
		super(renderManager);
		this.modelmissile = new ModelMissile();
	}

	public void renderArrow(EntityMissile missile, double x, double y, double z, float par8, float par9) {
		GL11.glPushMatrix();

		GL11.glTranslatef((float) x, (float) y + 1.5F, (float) z);
		GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * par9 + 90.0F, 0.0F, 0.0F, 1.0F);

		this.renderManager.textureManager.bindTexture(getEntityTexture(missile));
		this.modelmissile.render(missile, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderArrow((EntityMissile) par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityMissile missile = (EntityMissile) entity;
		ResourceLocation tex = null;
		switch(missile.missileType){
		case 0:
			tex = tex0;
			break;
		case 1:
			tex = tex1;
			break;
		case 2:
			tex = tex2;
			break;
		case 3:
			tex = tex3;
			break;
		}
		return tex;
	}
}
