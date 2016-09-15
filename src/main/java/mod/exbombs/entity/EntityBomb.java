/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import mod.exbombs.config.ConfigValue;
import mod.exbombs.core.ExBombs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBomb extends EntityThrowable {
	public boolean coctail;

	public EntityBomb(World world) {
		super(world);
		this.coctail = false;
	}

	public EntityBomb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	public EntityBomb(World world, double d, double d1, double d2) {
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
				ExBombs.createBetterExplosion(this.worldObj, null, this.posX, this.posY, this.posZ, 5.0F, false, false, ConfigValue.General.bomb_destroy_block);
			}else{
				this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true, false);
			}
		}

		if (!this.worldObj.isRemote){
			this.setDead();
		}
	}
}
