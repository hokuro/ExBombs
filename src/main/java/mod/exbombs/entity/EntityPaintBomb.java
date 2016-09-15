package mod.exbombs.entity;

import mod.exbombs.core.ExBombs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPaintBomb extends EntityThrowable {
	public boolean coctail;
	private IBlockState block;

	public EntityPaintBomb(World world) {
		super(world);
		this.coctail = false;
	}

	public EntityPaintBomb(World world, EntityLivingBase entityliving, IBlockState changeBlock) {
		super(world, entityliving);
		this.block = changeBlock;
	}

	public EntityPaintBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
        }

		if (!this.worldObj.isRemote){
			if (!this.coctail){
				ExBombs.createPaintExplosion(this.worldObj, null, this.posX, this.posY, this.posZ, 2.0F, block);
			}else{
				this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true, false);
			}
		}

		if (!this.worldObj.isRemote){
			this.setDead();
		}
	}
}
