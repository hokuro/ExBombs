/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBomb extends EntityThrowable {
	public boolean coctail;
	protected EnumBombType bombType = EnumBombType.BOMB;

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

	public EntityBomb(World world, EntityLivingBase entityliving, EnumBombType type) {
		super(world, entityliving);
		bombType = type;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
        }

		if (!this.worldObj.isRemote){
			if (!this.coctail){
				UtilExproder.createExplesion(this.worldObj, this, this.posX, this.posY, this.posZ, getBombType().getSize(), false, false, getBombType());
			}else{
				this.worldObj.newExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true, false);
			}
		}

		if (!this.worldObj.isRemote){
			this.setDead();
		}
	}

	public EnumBombType getBombType(){
		return this.bombType;
	}
}
