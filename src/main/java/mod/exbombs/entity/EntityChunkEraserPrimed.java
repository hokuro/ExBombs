package mod.exbombs.entity;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.block.BlockCore;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityChunkEraserPrimed extends Entity implements IEntityAdditionalSpawnData {

	public int fuse;
	private EnumEraseType eraseType;
	private double yOffset;

	public EntityChunkEraserPrimed(World worldIn) {
		super(EntityCore.Inst().CHUNKERASER, worldIn);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		this.yOffset = (this.height / 2.0F);
	}

	public EntityChunkEraserPrimed(IWorld worldIn, BlockPos pos, EnumEraseType type) {
		this(worldIn.getWorld());
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
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if (this.world.isRemote) {
			return false;
		}
		ItemStack stack = player.getHeldItem(hand);
		if ((stack != null) && (stack.getItem() == ItemCore.item_defuser) && (hand == EnumHand.MAIN_HAND)) {
			if (!player.isCreative()){
				ItemDefuser.defuserUse(stack, player);
			}
			remove();
			switch(eraseType){
			case ERASEALL:
				this.entityDropItem(new ItemStack(BlockCore.block_chunkeraser).getItem(), 1);
				break;
			case ERASEUNMATCH:
				this.entityDropItem(new ItemStack(BlockCore.block_muchblockeraser).getItem(), 1);
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
		return !this.removed;
	}

    @Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		move(MoverType.PLAYER,this.motionX, this.motionY, this.motionZ);
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
				remove();
				explode();
			} else {
				remove();
			}
		} else {
			this.world.spawnParticle(Particles.SMOKE, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
    }

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(this.fuse);
		buffer.writeInt(this.eraseType.getIndex());
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.fuse = additionalData.readInt();
		this.eraseType = EnumEraseType.getType(additionalData.readInt());
	}

	@Override
	protected void readAdditional(NBTTagCompound tagCompund) {
		this.fuse = tagCompund.getInt("Fuse");
		this.eraseType = EnumEraseType.getType(tagCompund.getInt("Type"));
	}

	@Override
	protected void writeAdditional(NBTTagCompound tagCompound) {
		tagCompound.setInt("Fuse", (byte) this.fuse);
		tagCompound.setInt("Type", eraseType.getIndex());
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
		UtilExproder.createEraserExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 0.0F, eraseType);
	}

	public EnumEraseType getEraseType(){
		return eraseType;
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

	@Override
	protected void registerData() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
