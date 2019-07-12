/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMissile extends ModelBase {
	ModelRenderer engine1;
	ModelRenderer engine2;
	ModelRenderer engine3;
	ModelRenderer engine4;
	ModelRenderer engine5;
	ModelRenderer body1;
	ModelRenderer body2;
	ModelRenderer body3;
	ModelRenderer body4;
	ModelRenderer cone1;
	ModelRenderer cone2;
	ModelRenderer cone3;
	ModelRenderer cone4;

	public ModelMissile() {
		this.textureWidth = 128;
		this.textureHeight = 64;

		this.engine1 = new ModelRenderer(this, 0, 0);
		this.engine1.addBox(-2.5F, 0.0F, -7.5F, 5, 17, 15);
		this.engine1.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.engine1.setTextureSize(128, 64);
		this.engine1.mirror = true;
		setRotation(this.engine1, 0.0F, 0.0F, 0.0F);
		this.engine2 = new ModelRenderer(this, 0, 0);
		this.engine2.addBox(-7.5F, 0.0F, -2.5F, 15, 17, 5);
		this.engine2.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.engine2.setTextureSize(128, 64);
		this.engine2.mirror = true;
		setRotation(this.engine2, 0.0F, 0.0F, 0.0F);
		this.engine3 = new ModelRenderer(this, 0, 0);
		this.engine3.addBox(-6.5F, 0.0F, -4.5F, 13, 17, 9);
		this.engine3.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.engine3.setTextureSize(128, 64);
		this.engine3.mirror = true;
		setRotation(this.engine3, 0.0F, 0.0F, 0.0F);
		this.engine4 = new ModelRenderer(this, 0, 0);
		this.engine4.addBox(-4.5F, 0.0F, -6.5F, 9, 17, 13);
		this.engine4.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.engine4.setTextureSize(128, 64);
		this.engine4.mirror = true;
		setRotation(this.engine4, 0.0F, 0.0F, 0.0F);
		this.engine5 = new ModelRenderer(this, 0, 0);
		this.engine5.addBox(-5.5F, 0.0F, -5.5F, 11, 17, 11);
		this.engine5.setRotationPoint(0.0F, 7.0F, 0.0F);
		this.engine5.setTextureSize(128, 64);
		this.engine5.mirror = true;
		setRotation(this.engine5, 0.0F, 0.0F, 0.0F);
		this.body1 = new ModelRenderer(this, 0, 0);
		this.body1.addBox(-2.5F, 0.0F, -5.5F, 5, 47, 11);
		this.body1.setRotationPoint(0.0F, -40.0F, 0.0F);
		this.body1.setTextureSize(128, 64);
		this.body1.mirror = true;
		setRotation(this.body1, 0.0F, 0.0F, 0.0F);
		this.body2 = new ModelRenderer(this, 0, 0);
		this.body2.addBox(-5.5F, 0.0F, -2.5F, 11, 47, 5);
		this.body2.setRotationPoint(0.0F, -40.0F, 0.0F);
		this.body2.setTextureSize(128, 64);
		this.body2.mirror = true;
		setRotation(this.body2, 0.0F, 0.0F, 0.0F);
		this.body3 = new ModelRenderer(this, 0, 0);
		this.body3.addBox(-3.5F, 0.0F, -4.5F, 7, 47, 9);
		this.body3.setRotationPoint(0.0F, -40.0F, 0.0F);
		this.body3.setTextureSize(128, 64);
		this.body3.mirror = true;
		setRotation(this.body3, 0.0F, 0.0F, 0.0F);
		this.body4 = new ModelRenderer(this, 0, 0);
		this.body4.addBox(-4.5F, 0.0F, -3.5F, 9, 47, 7);
		this.body4.setRotationPoint(0.0F, -40.0F, 0.0F);
		this.body4.setTextureSize(128, 64);
		this.body4.mirror = true;
		setRotation(this.body4, 0.0F, 0.0F, 0.0F);
		this.cone1 = new ModelRenderer(this, 107, 17);
		this.cone1.addBox(-1.5F, 0.0F, -3.5F, 3, 10, 7);
		this.cone1.setRotationPoint(0.0F, -50.0F, 0.0F);
		this.cone1.setTextureSize(128, 64);
		this.cone1.mirror = true;
		setRotation(this.cone1, 0.0F, 0.0F, 0.0F);
		this.cone2 = new ModelRenderer(this, 79, 0);
		this.cone2.addBox(-3.5F, 0.0F, -1.5F, 7, 10, 3);
		this.cone2.setRotationPoint(0.0F, -50.0F, 0.0F);
		this.cone2.setTextureSize(128, 64);
		this.cone2.mirror = true;
		setRotation(this.cone2, 0.0F, 0.0F, 0.0F);
		this.cone3 = new ModelRenderer(this, 99, 0);
		this.cone3.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5);
		this.cone3.setRotationPoint(0.0F, -50.0F, 0.0F);
		this.cone3.setTextureSize(128, 64);
		this.cone3.mirror = true;
		setRotation(this.cone3, 0.0F, 0.0F, 0.0F);
		this.cone4 = new ModelRenderer(this, 120, 0);
		this.cone4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2);
		this.cone4.setRotationPoint(0.0F, -58.0F, 0.0F);
		this.cone4.setTextureSize(128, 64);
		this.cone4.mirror = true;
		setRotation(this.cone4, 0.0F, 0.0F, 0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.engine1.render(f5);
		this.engine2.render(f5);
		this.engine3.render(f5);
		this.engine4.render(f5);
		this.engine5.render(f5);
		this.body1.render(f5);
		this.body2.render(f5);
		this.body3.render(f5);
		this.body4.render(f5);
		this.cone1.render(f5);
		this.cone2.render(f5);
		this.cone3.render(f5);
		this.cone4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
