/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity.prime;

import mod.exbombs.block.BlockCore;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class EntityTunnelExplosivePrimed extends EntityPrime{
	public Direction face;

	public EntityTunnelExplosivePrimed(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().TUNNELBOMB, world);
	}

	public EntityTunnelExplosivePrimed(EntityType etype, World world) {
		super(EntityCore.Inst().TUNNELBOMB, world);
	}

	public EntityTunnelExplosivePrimed(World world, double x, double y, double z) {
		super(EntityCore.Inst().TUNNELBOMB, world, x, y, z);
	}

	public EntityTunnelExplosivePrimed(World world, double x, double y, double z, Direction meta) {
		this(world, x, y, z);
		this.face = meta;
	}

	@Override
	public void tick() {
		if (this.fuse-- <= 0) {
			if (!this.world.isRemote) {
				remove();
				explode();
			} else {
				remove();
			}
		}
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		super.writeSpawnData(buffer);
		buffer.writeInt(this.face.getIndex());
		buffer.writeDouble(this.posY);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		super.readSpawnData(additionalData);
		this.face = Direction.byIndex(additionalData.readInt());
		this.posY = additionalData.readDouble();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompund) {
		super.readAdditional(tagCompund);
		this.face = Direction.byIndex(tagCompund.getInt("MetaData"));
	}

	@Override
	protected void writeAdditional(CompoundNBT tagCompound) {
		super.writeAdditional(tagCompound);
		tagCompound.putInt("MetaData", this.face.getIndex());
	}


	@Override
	protected void explode() {
		UtilExproder.createTunnelExplosion(this.world, null, this.posX, this.posY, this.posZ, face);
	}

	@Override
	protected Item getPrimeItem() {
		return new ItemStack(BlockCore.block_tunnel).getItem();
	}


}
