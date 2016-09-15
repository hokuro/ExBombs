package mod.exbombs.tileentity.render;

import mod.exbombs.block.BlockFuse;
import mod.exbombs.model.ModelCube;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityFuseRender extends TileEntitySpecialRenderer {
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
		GlStateManager.translate((float) posX + 0.5F , (float) posY + 0.5F, (float) posZ + 0.5F);
		GlStateManager.scale(0.03125D, 0.03125D, 0.03125D);
		GlStateManager.enableCull();
		GlStateManager.enableRescaleNormal();
		this.bindTexture(tex);
		this.mainmodel.render(1.0F);
		GlStateManager.popMatrix();


		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(1,0,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.875D , (float) posY + 0.5, (float) posZ + 0.5);
			GlStateManager.scale(0.015625D, 0.03125D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(-1,0,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.125D , (float) posY + 0.5, (float) posZ + 0.5);
			GlStateManager.scale(0.015625D, 0.03125D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}

		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,1,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.5D , (float) posY + 0.875D, (float) posZ + 0.5);
			GlStateManager.scale(0.03125D, 0.015625D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,-1,0)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.5D , (float) posY + 0.125D, (float) posZ + 0.5);
			GlStateManager.scale(0.03125D, 0.015625D, 0.03125D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}

		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,0,1)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.5D , (float) posY + 0.5D, (float) posZ + 0.875D);
			GlStateManager.scale(0.03125D, 0.03125D, 0.015625D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
		if (((BlockFuse)ModRegisterBlock.block_Fuse).shouldConnectTo(te.getWorld().getBlockState(te.getPos().add(0,0,-1)))){
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) posX + 0.5D , (float) posY + 0.5D, (float) posZ + 0.125D);
			GlStateManager.scale(0.03125D, 0.03125D, 0.015625D);
			GlStateManager.enableCull();
			GlStateManager.enableRescaleNormal();
			this.bindTexture(tex);
			this.mainmodel.render(1.0F);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posZ, double posY, float rot_rot, int p_180535_9_) {
		renderFuse((TileEntityFuse)te, posX, posZ, posY, rot_rot, p_180535_9_);
	}
}
