package mod.exbombs.entity;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

public class EntityExBombsSmokeFX extends EntitySmokeFX {
	private int light;
	private boolean useLight;

	public EntityExBombsSmokeFX(World par1World, double par2, double par4, double par6, double par8, double par10,
			double par12, float par14) {
		super(par1World, par2, par4, par6, par8, par10, par12, par14);
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

	public void setLife(int life) {
		this.particleMaxAge = life;
	}

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
