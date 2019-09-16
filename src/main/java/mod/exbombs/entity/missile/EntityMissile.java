/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity.missile;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.helper.ExBombsGuiHelper;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityMissile extends Entity implements IEntityAdditionalSpawnData {
	public int missileType;
	public boolean flying;
	private ParticleHelper helper;

	public EntityMissile(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().MISSILE, world);
	}

	public EntityMissile(EntityType<?> etype, World world) {
		super(EntityCore.Inst().MISSILE, world);
		this.setRenderDistanceWeight(50.0D);
		this.ignoreFrustumCheck = true;
		if (world.isRemote) {
			this.helper = new ParticleHelper();
		}
	}

	public EntityMissile(World worldIn, BlockPos pos) {
		this(EntityCore.Inst().MISSILE, worldIn);
		this.setPosition(pos.getX(),pos.getY()+1,pos.getZ());
		this.setMotion(this.getMotion().getX(), 5.0D, this.getMotion().getZ());
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.flying) {
			return;
		}
		if (!this.world.isRemote) {
			double mx = this.getMotion().getX();
			double my = this.getMotion().getY();
			double mz = this.getMotion().getZ();

			if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
				float f = MathHelper.sqrt(mx * mx + mz * mz);
				this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(mx, mz) * 180.0D / 3.141592653589793D));
				this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(my, f) * 180.0D / 3.141592653589793D));
			}
			Vec3d vec3d = new Vec3d(this.posX, this.posY+1, this.posZ);
			Vec3d vec3d1 = new Vec3d(this.posX + mx, this.posY + my, this.posZ + mz);
			RayTraceResult  movingobjectposition = this.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (movingobjectposition.getType() != RayTraceResult.Type.MISS) {
				remove();
				if (this.missileType == 0) {
					UtilExproder.createExplesion(this.world, null, this.posX, this.posY, this.posZ, 10.0F, false, false, EnumBombType.PRIME);
				} else if (this.missileType == 1) {
					UtilExproder.createSuperExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 70.0F);
				}else if (this.missileType == 2) {
					UtilExproder.createEraserExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 0.0F, EnumEraseType.ERASEALL);
				}else if (this.missileType == 3) {
					UtilExproder.createEraserExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 0.0F, EnumEraseType.ERASEUNMATCH);
				}
			}
			this.posX += mx;
			this.posY += my;
			this.posZ += mz;
			float f3 = MathHelper.sqrt(mx *mx + mz * mz);
			this.rotationYaw = ((float) (Math.atan2(mx, my) * 180.0D / 3.141592653589793D));
			for (this.rotationPitch = ((float) (Math.atan2(my, f3) * 180.0D / 3.141592653589793D)); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			}
			this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
			this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
			float f4 = 0.99F;
			float f6 = 0.015F;

			mx *= f4;
			my *= f4;
			mz *= f4;
			my -= f6;
			setMotion(mx,my,mz);
			setPosition(this.posX, this.posY, this.posZ);
		} else {
			this.helper.spawn();
		}
	}

	@Override
	public boolean processInitialInteract(PlayerEntity player, Hand hand){
		if (hand == Hand.OFF_HAND){
			// オフハンドは処理をしない
			return super.processInitialInteract(player, hand);
		}
		ItemStack stack = player.getHeldItem(hand);
		ItemStack offHand = player.getHeldItem(Hand.OFF_HAND);
		// メインハンドにDefuserをし装備している場合または
		// メインハンドに何も持っておらず、オフハンドにDefuserを装備している場合信管を外す
		if (((!stack.isEmpty()) && (stack.getItem() == ItemCore.item_defuser)) ||
			((stack.isEmpty()) &&(!offHand.isEmpty() && offHand.getItem() == ItemCore.item_defuser))) {
			if ((!this.world.isRemote) && (!this.removed)) {
				if (!player.isCreative()) {
					if (stack.isEmpty()) {
						ItemDefuser.defuserUse(stack, player,Hand.OFF_HAND);
					}else {
						ItemDefuser.defuserUse(stack, player,Hand.MAIN_HAND);
					}
				}
				this.remove();
				if (this.missileType == 0) {
					this.entityDropItem(ItemCore.item_TntMissile, 1);
				}
				if (this.missileType == 1) {
					this.entityDropItem(ItemCore.item_NCMissile, 1);
				}
				if (this.missileType == 2) {
					this.entityDropItem(ItemCore.item_CEMissile, 1);
				}
				if (this.missileType == 3) {
					this.entityDropItem(ItemCore.item_MCEMissile, 1);
				}
				return true;
			}
		} else if (!this.world.isRemote) {
			ExBombsGuiHelper.displayGuiByID(player, ModCommon.MOD_GUI_ID_MISSILE, new Object[] {this.getEntityId()});
			return true;
		}
		return super.processInitialInteract(player, hand);
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
	public void writeSpawnData(PacketBuffer buffer) {
		buffer.writeInt(this.missileType);
		buffer.writeBoolean(this.flying);
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		this.missileType = additionalData.readInt();
		this.flying = additionalData.readBoolean();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompund) {
		this.missileType = tagCompund.getInt("missileType");
		this.flying = tagCompund.getBoolean("flying");
	}

	@Override
	protected void writeAdditional(CompoundNBT tagCompound) {
		tagCompound.putInt("missileType", this.missileType);
		tagCompound.putBoolean("flying", this.flying);
	}

	public void launch(int x, int z) {
		double XCoord = x - this.posX;
		double YCoord = z - this.posZ;
		if ((Math.abs(XCoord) > 500.0D) || (Math.abs(YCoord) > 500.0D) || (this.flying)) {
			return;
		}
		setMotion( (0.0105D * XCoord),this.getMotion().getY(),(0.0105D * YCoord));

		this.flying = true;
	}

	private class ParticleHelper {
		private ParticleHelper() {
		}

		public void spawn() {
			for (int iterator = 0; iterator < 50; iterator++) {
				world.addParticle(Mod_ExBombs.RegistryEvents.PARTICLE_LARGESMOKEEX,
						EntityMissile.this.posX, EntityMissile.this.posY, EntityMissile.this.posZ,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D);
			}
		}
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
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
