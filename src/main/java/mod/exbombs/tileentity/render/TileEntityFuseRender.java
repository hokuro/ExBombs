package mod.exbombs.tileentity.render;

import mod.exbombs.block.BlockCore;
import mod.exbombs.block.BlockFuse;
import mod.exbombs.model.ModelCube;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityFuseRender extends TileEntityRenderer<TileEntityFuse> {
	private static final ResourceLocation tex = new ResourceLocation("exbombs:textures/entity/fuse.png");
	private ModelCube mainmodel = new ModelCube();
	private ModelCube submodel1 = new ModelCube();
	private ModelCube submodel2 = new ModelCube();

	public TileEntityFuseRender() {
	}

	public void renderFuse(TileEntityFuse te, double posX, double posY, double posZ, float per8, int per9) {
		double sx,sy,sz = 0.0D;
		double tx,ty,tz = 0.0D;
		sx = sy = sz = 0.03125D;
		tx = ty = tz = 0.5D;

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) posX + 0.5F , (float) posY + 0.5F, (float) posZ + 0.5F);
		GlStateManager.scaled(0.03125D, 0.03125D, 0.03125D);
		GlStateManager.enableCull();
		GlStateManager.enableRescaleNormal();
		this.bindTexture(tex);
		this.mainmodel.render(1.0F);
		GlStateManager.popMatrix();


		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(1,0,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.875F , (float) posY + 0.5F, (float) posZ + 0.5F);
			GlStateManager.scaled(0.015625D, 0.03125D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(-1,0,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.125F , (float) posY + 0.5F, (float) posZ + 0.5F);
			GlStateManager.scaled(0.015625D, 0.03125D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}

		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,1,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.5F , (float) posY + 0.875F, (float) posZ + 0.5F);
			GlStateManager.scaled(0.03125D, 0.015625D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,-1,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.5F , (float) posY + 0.125F, (float) posZ + 0.5F);
			GlStateManager.scaled(0.03125D, 0.015625D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}

		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,0,1)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.5F , (float) posY + 0.5F, (float) posZ + 0.875F);
			GlStateManager.scaled(0.03125D, 0.03125D, 0.015625D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)BlockCore.block_fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,0,-1)))){
			GlStateManager.pushMatrix();
			GlStateManager.translatef((float) posX + 0.5F , (float) posY + 0.5F, (float) posZ + 0.125F);
			GlStateManager.scaled(0.03125D, 0.03125D, 0.015625D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void render(TileEntityFuse te, double x, double y, double z, float partialTicks, int destroyStage) {
		renderFuse((TileEntityFuse)te, x, y, z, partialTicks, destroyStage);
	}
}
