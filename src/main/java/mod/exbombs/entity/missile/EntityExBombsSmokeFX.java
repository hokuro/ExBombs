package mod.exbombs.entity.missile;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.LargeSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityExBombsSmokeFX extends LargeSmokeParticle {
	private int light;
	private boolean useLight;

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite p_i50554_1_) {
			this.spriteSet = p_i50554_1_;
		}

		public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			EntityExBombsSmokeFX fx = new EntityExBombsSmokeFX(worldIn,
					x, y, z,
					xSpeed, ySpeed, zSpeed,
					this.spriteSet);
			fx.setMaxAge(120);
			fx.interpPosY=8.0D;
			fx.setAll((float) (Math.random() * 0.30000001192092896D));
			return fx;
		}
	}

	protected EntityExBombsSmokeFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1201_8_, double p_i1201_10_, double p_i1201_12_, IAnimatedSprite p_i51024_14_){
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i1201_8_, p_i1201_10_, p_i1201_12_, p_i51024_14_);
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
