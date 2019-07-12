/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import mod.exbombs.util.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBomb extends EntityThrowable implements IEntityAdditionalSpawnData{
	public boolean coctail;
	protected EnumBombType bombType = EnumBombType.BOMB;

	public EntityBomb(World world) {
		super(EntityCore.Inst().BOMB, world);
		this.coctail = false;
	}


	public EntityBomb(World world, EnumBombType type){
		this(world);
		bombType = type;
	}

	public EntityBomb(EntityType<?> etype, World world, EnumBombType type){
		super(etype, world);
		bombType = type;
	}

	protected EntityBomb(EntityType<?> type, double d1, double d2, double d3, World world) {
		super(type,d1,d2,d3,world);
	}

	protected EntityBomb(EntityType<?> type, EntityLivingBase entity, World world) {
		super(type,entity,world);
	}

	public EntityBomb(World world, EntityLivingBase entityliving, EnumBombType type) {
		super(EntityCore.Inst().BOMB, entityliving, world);
		bombType = type;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
        if (result.entity != null)
        {
            result.entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
        }

		if (!this.world.isRemote){
			if (!this.coctail){
				UtilExproder.createExplesion(this.world, this, this.posX, this.posY, this.posZ, getBombType().getSize(), false, false, getBombType());
			}else{
				this.world.newExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true, false);
			}
		}

		if (!this.world.isRemote){
			this.remove();
		}
	}

	@Override
	public void writeAdditional(NBTTagCompound compound)
    {
        super.writeAdditional(compound);
        compound.setInt("bombType", bombType.getIndex());
    }

	@Override
	public void readAdditional(NBTTagCompound compound)
    {
        super.readAdditional(compound);
        bombType = EnumBombType.intToBombType(compound.getInt("bombType"));
    }

	public EnumBombType getBombType(){
		return this.bombType;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(this.bombType.getIndex());

	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.bombType = EnumBombType.intToBombType(additionalData.readInt());
	}

	@Override
	public NBTTagCompound serializeNBT() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
