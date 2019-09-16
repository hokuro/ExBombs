package mod.exbombs.entity.prime;

import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityPrime extends Entity implements IEntityAdditionalSpawnData {

	public int fuse;
	public float yOffset;

	public EntityPrime(EntityType<?> etype, World world) {
		super(etype, world);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		this.yOffset = (this.getHeight() / 2.0F);
	}

	public EntityPrime(EntityType<?> etype, World world, double x, double y, double z) {
		this(etype, world);
		setPosition(x, y, z);
		float f = (float) (Math.random() * 3.1415927410125732D * 2.0D);
		this.setMotion(
			-MathHelper.sin(f * 3.141593F / 180.0F) * 0.02F,
			 0.20000000298023224D,
			 (-MathHelper.cos(f * 3.141593F / 180.0F) * 0.02F));
		this.fuse = 100;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand){
		if (this.world.isRemote) {
			return false;
		}
		ItemStack stack = player.getHeldItem(hand);
		if ((stack != null) && (stack.getItem() == ItemCore.item_defuser) && (hand == Hand.MAIN_HAND)) {
			if (!player.isCreative()){
				ItemDefuser.defuserUse(stack, player, hand);
			}
			this.removed = true;
			this.entityDropItem(getPrimeItem(), 1);
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
		return this.isAlive();
	}

    @Override
	public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.hasNoGravity()) {
           this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
           this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
			if (!this.world.isRemote) {
				remove();
				explode();
			} else {
				remove();
			}
        } else {
           this.handleWaterMovement();
           this.world.addParticle(ParticleTypes.SMOKE, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(this.fuse);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.fuse = additionalData.readInt();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompund) {
		this.fuse = tagCompund.getInt("Fuse");
	}

	@Override
	protected void writeAdditional(CompoundNBT tagCompound) {
		tagCompound.putInt("Fuse", (byte) this.fuse);
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

	@Override
	public CompoundNBT serializeNBT() {return null;}

	@Override
	public void deserializeNBT(CompoundNBT nbt) { }


	@Override
	protected void registerData() {}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
	protected abstract void explode();
	protected abstract Item getPrimeItem();
}
