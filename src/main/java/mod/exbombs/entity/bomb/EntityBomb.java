/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity.bomb;

import mod.exbombs.entity.EntityCore;
import mod.exbombs.util.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityBomb extends ThrowableEntity implements IEntityAdditionalSpawnData{
	public boolean coctail;
	protected EnumBombType bombType = EnumBombType.BOMB;


	public EntityBomb(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().BOMB, world);
	}

	public EntityBomb(EntityType<? extends ThrowableEntity> etype, World world) {
		super(etype, world);
	}

	public EntityBomb(EntityType<? extends ThrowableEntity> etype, World world, LivingEntity entityliving, EnumBombType type) {
		super(etype, entityliving, world);
		bombType = type;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
	      if (!this.world.isRemote) {
		         this.world.setEntityState(this, (byte)3);
		         this.remove();
		      }
        if (result.getType() == RayTraceResult.Type.ENTITY){
        	Entity entity = ((EntityRayTraceResult)result).getEntity();
        	entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.5F);
        }

		if (!this.world.isRemote){
			if (!this.coctail){
				UtilExproder.createExplesion(this.world, this, this.posX, this.posY, this.posZ, getBombType().getSize(), false, false, getBombType());
			}else{
				this.world.createExplosion(this, this.posX, this.posY, this.posZ, 5.0F, true, Explosion.Mode.NONE);
			}
		}

		if (!this.world.isRemote){
			this.remove();
		}
	}

	public EnumBombType getBombType(){
		return this.bombType;
	}

	@Override
	public void writeAdditional(CompoundNBT compound){
        super.writeAdditional(compound);
        compound.putInt("bombType", bombType.getIndex());
    }

	@Override
	public void readAdditional(CompoundNBT compound){
        super.readAdditional(compound);
        bombType = EnumBombType.intToBombType(compound.getInt("bombType"));
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
	public CompoundNBT serializeNBT() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void registerData() {
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
