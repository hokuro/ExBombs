/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import io.netty.buffer.ByteBuf;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBomb extends EntityThrowable implements IEntityAdditionalSpawnData{
	public boolean coctail;
	protected EnumBombType bombType = EnumBombType.BOMB;

	public EntityBomb(World world) {
		super(world);
		this.coctail = false;
	}

	public EntityBomb(World world, EnumBombType type){
		this(world);
		bombType = type;
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

		if (!this.world.isRemote){
			if (!this.coctail){
				UtilExproder.createExplesion(this.world, this, this.posX, this.posY, this.posZ, getBombType().getSize(), false, false, getBombType());
			}else{
				this.world.newExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true, false);
			}
		}

		if (!this.world.isRemote){
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("bombType", bombType.getIndex());
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        bombType = EnumBombType.intToBombType(compound.getInteger("bombType"));
    }

	public EnumBombType getBombType(){
		return this.bombType;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.bombType.getIndex());

	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.bombType = EnumBombType.intToBombType(additionalData.readInt());
	}
}
