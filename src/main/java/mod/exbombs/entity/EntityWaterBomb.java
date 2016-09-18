/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityWaterBomb extends EntityBomb {
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
	public EnumBombType getBombType(){
		return EnumBombType.WARTER;
	}

//	@Override
//	protected void onImpact(RayTraceResult result) {
//        if (result.entityHit != null)
//        {
//            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
//        }
//
//		if (!this.worldObj.isRemote){
//			Mod_ExBombs.createExplesion(this.worldObj, null, this.posX, this.posY, this.posZ, 1.0F, false, false, EnumBombType.WARTER);
//			this.setDead();
//		}
//	}
}
