package mod.exbombs.entity;

import net.minecraft.client.particle.ParticleSmokeLarge;
import net.minecraft.world.World;

public class EntityExBombsSmokeFX extends ParticleSmokeLarge {
	private int light;
	private boolean useLight;

	protected EntityExBombsSmokeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1201_8_, double p_i1201_10_, double p_i1201_12_){
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i1201_8_, p_i1201_10_, p_i1201_12_);
		this.particleRed = (this.particleBlue = this.particleGreen = 0.0F);
	}

	public void setBlue(float blue) {
		this.particleBlue = blue;
	}

	public void setGreen(float green) {
		this.particleGreen = green;
	}

	public void setRed(float red) {
		this.particleRed = red;
	}

	public void setAll(float all) {
		this.particleRed = (this.particleBlue = this.particleGreen = all);
	}

//	public void setLife(int life) {
//		this.particleMaxAge = life;
//	}

	public void setLight(int light) {
		this.light = light;
		this.useLight = true;
	}

	@Override
	public int getBrightnessForRender(float par1) {
		if (!this.useLight) {
			return super.getBrightnessForRender(0.5F);
		}
		return this.light;
	}
}
