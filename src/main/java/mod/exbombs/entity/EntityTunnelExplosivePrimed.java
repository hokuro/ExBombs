/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import io.netty.buffer.ByteBuf;
import mod.exbombs.block.BlockCore;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityTunnelExplosivePrimed extends Entity implements IEntityAdditionalSpawnData {
	public int fuse;
	public int metaData;
	public float yOffset;

	public EntityTunnelExplosivePrimed(World world) {
		super(world);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		this.yOffset = (this.height / 2.0F);
	}

	public EntityTunnelExplosivePrimed(World world, double motionX, double motionY, double motionZ) {
		this(world);
		setPosition(motionX, motionY, motionZ);
		this.fuse = 100;
		this.prevPosX = motionX;
		this.prevPosY = motionY;
		this.prevPosZ = motionZ;
	}

	public EntityTunnelExplosivePrimed(World world, double motionX, double motionY, double motionZ, int meta) {
		this(world, motionX, motionY, motionZ);
		setPosition(motionX, motionY, motionZ);
		this.metaData = meta;
	}

	protected void entityInit() {
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if (this.world.isRemote) {
			return false;
		}
		ItemStack stack = player.getHeldItem(hand);
		if ((stack != null)  && (EnumHand.MAIN_HAND == hand) && (stack.getItem() == ItemCore.item_defuser)) {
			if(!player.capabilities.isCreativeMode){
				ItemDefuser.defuserUse(stack, player);
			}
			this.isDead = true;
			this.dropItem(new ItemStack(BlockCore.block_tunnel).getItem(), 1);
		}
		return super.processInitialInteract(player,hand);
	}

    @Override
	protected boolean canTriggerWalking() {
		return false;
	}

    @Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onUpdate() {
		if (this.fuse-- <= 0) {
			if (!this.world.isRemote) {
				setDead();
				explode();
			} else {
				setDead();
			}
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeByte(this.fuse);
		buffer.writeByte(this.metaData);
		buffer.writeDouble(this.posY);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.fuse = additionalData.readByte();
		this.metaData = additionalData.readByte();
		this.posY = additionalData.readDouble();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.fuse = tagCompund.getInteger("Fuse");
		this.metaData = tagCompund.getInteger("MetaData");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("Fuse", (byte) this.fuse);
		tagCompound.setInteger("MetaData", (byte) this.metaData);
	}

	@Override
    public double getYOffset()
    {
        return this.yOffset;
    }

	public int getFuse(){
		return this.fuse;
	}

	private void explode() {
		int meta = (metaData & 0x0E) >> 1;
		UtilExproder.createTunnelExplosion(this.world, null, this.posX, this.posY, this.posZ, meta);
	}


}
