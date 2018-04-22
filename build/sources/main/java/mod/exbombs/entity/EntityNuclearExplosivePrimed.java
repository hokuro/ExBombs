/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import io.netty.buffer.ByteBuf;
import mod.exbombs.block.BlockCore;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityNuclearExplosivePrimed extends Entity implements IEntityAdditionalSpawnData {
	public int fuse;
	public float yOffset;

	public EntityNuclearExplosivePrimed(World world) {
		super(world);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		this.yOffset = (this.height / 2.0F);
	}

	public EntityNuclearExplosivePrimed(World world, double d, double d1, double d2) {
		this(world);
		setPosition(d, d1, d2);
		float f = (float) (Math.random() * 3.1415927410125732D * 2.0D);
		this.motionX = (-MathHelper.sin(f * 3.141593F / 180.0F) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (-MathHelper.cos(f * 3.141593F / 180.0F) * 0.02F);
		this.fuse = 200;
		this.prevPosX = d;
		this.prevPosY = d1;
		this.prevPosZ = d2;
	}

	@Override
	public void entityInit(){

	}

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if (this.world.isRemote) {
			return false;
		}
		ItemStack stack = player.getHeldItem(hand);
		if ((stack != null) && (stack.getItem() == ItemCore.item_defuser) && (hand == EnumHand.MAIN_HAND)) {
			if (!player.capabilities.isCreativeMode){
				ItemDefuser.defuserUse(stack, player);
			}
			this.isDead = true;
			this.dropItem(new ItemStack(BlockCore.block_nuclear).getItem(), 1);
			return true;
		}
		return false;
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
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		move(MoverType.PLAYER, this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}
		if (this.fuse-- <= 0) {
			if (!this.world.isRemote) {
				setDead();
				explode();
			} else {
				setDead();
			}
		} else {
			this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.fuse);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.fuse = additionalData.readInt();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.fuse = tagCompund.getInteger("Fuse");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("Fuse", (byte) this.fuse);
	}

	@Override
    public double getYOffset()
    {
        return this.yOffset;
    }

	public void setFuse(int fuse){
		this.fuse = fuse;
	}

	public int getFuse(){
		return fuse;
	}

	private void explode() {
		UtilExproder.createSuperExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 80.0F);
	}
}
