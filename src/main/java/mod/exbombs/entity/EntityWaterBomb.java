/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import mod.exbombs.core.ExBombs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWaterBomb extends EntityThrowable {
	public EntityWaterBomb(World world) {
		super(world);
	}

	public EntityWaterBomb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	public EntityWaterBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
        }

		if (!this.worldObj.isRemote){
			ExBombs.createBetterExplosionWithWater(this.worldObj, null, this.posX, this.posY, this.posZ, 1.0F, false, false);
			this.setDead();
		}
	}
}
