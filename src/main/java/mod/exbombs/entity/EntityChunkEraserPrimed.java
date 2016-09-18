package mod.exbombs.entity;

import io.netty.buffer.ByteBuf;
import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.UtilExproder;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModRegisterItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityChunkEraserPrimed extends Entity implements IEntityAdditionalSpawnData {

	public int fuse;
	private EnumEraseType eraseType;
	private double yOffset;

	public EntityChunkEraserPrimed(World worldIn) {
		super(worldIn);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		this.yOffset = (this.height / 2.0F);
	}

	public EntityChunkEraserPrimed(World worldIn, BlockPos pos, EnumEraseType type) {
		this(worldIn);
		eraseType = type;

		setPosition(pos.getX(), pos.getY(), pos.getZ());
		float f = (float) (Math.random() * 3.1415927410125732D * 2.0D);
		this.motionX = (-MathHelper.sin(f * 3.141593F / 180.0F) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (-MathHelper.cos(f * 3.141593F / 180.0F) * 0.02F);
		this.fuse = 100;
		this.prevPosX = pos.getX();
		this.prevPosY = pos.getY();
		this.prevPosZ = pos.getZ();
	}

	@Override
	public void entityInit(){

	}

    @Override
    public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand){
		if (this.worldObj.isRemote) {
			return false;
		}
		if ((stack != null) && (stack.getItem() == ModRegisterItem.item_defuser) && (hand == EnumHand.MAIN_HAND)) {
			if (!player.capabilities.isCreativeMode){
				ItemDefuser.onItemUsed(stack, player);
			}
			this.isDead = true;
			switch(eraseType){
			case ERASEALL:
				this.dropItem(new ItemStack(ModRegisterBlock.block_eraser).getItem(), 1);
				break;
			case ERASEUNMATCH:
				this.dropItem(new ItemStack(ModRegisterBlock.block_unmach).getItem(), 1);
				break;
			}
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
		moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}
		if (this.fuse-- <= 0) {
			if (!this.worldObj.isRemote) {
				setDead();
				explode();
			} else {
				setDead();
			}
		} else {
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
    }

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.fuse);
		buffer.writeInt(this.eraseType.getIndex());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.fuse = additionalData.readInt();
		this.eraseType = EnumEraseType.getType(additionalData.readInt());
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.fuse = tagCompund.getInteger("Fuse");
		this.eraseType = EnumEraseType.getType(tagCompund.getInteger("Type"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("Fuse", (byte) this.fuse);
		tagCompound.setInteger("Type", eraseType.getIndex());
	}

	@Override
    public double getYOffset()
    {
        return this.yOffset;
    }


	public int getFuse(){
		return fuse;
	}

	private void explode() {
		UtilExproder.createEraserExplosion(this.worldObj, null, (int) this.posX, (int) this.posY, (int) this.posZ, 0.0F, eraseType);
	}

	public EnumEraseType getType(){
		return eraseType;
	}

}
