/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity;

import io.netty.buffer.ByteBuf;
import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.gui.GuiMissile;
import mod.exbombs.helper.ExBombsGuiHelper;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityMissile extends Entity implements IEntityAdditionalSpawnData {
	public int missileType;
	public boolean flying;
	private ParticleHelper helper;

	public EntityMissile(World world) {
		super(world);
		setSize(1.0F, 3.5F);
		this.setRenderDistanceWeight(50.0D);
		this.ignoreFrustumCheck = true;
		if (world.isRemote) {
			this.helper = new ParticleHelper();
		}
	}

	public EntityMissile(World worldIn, BlockPos pos) {
		this(worldIn);
		this.setPosition(pos.getX(),pos.getY()+1,pos.getZ());
		this.motionY = 5.0D;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (!this.flying) {
			return;
		}
		if (!this.world.isRemote) {
			for (int motionX = -5; motionX < 5; motionX++) {
				for (int motionZ = -5; motionZ < 5; motionZ++) {
					this.world.getChunkProvider().getLoadedChunk(this.chunkCoordX + motionX, this.chunkCoordZ + motionZ);
				}
			}
			if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F)) {
				float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.prevRotationYaw = (this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
				this.prevRotationPitch = (this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / 3.141592653589793D));
			}
			Vec3d vec3d = new Vec3d(this.posX, this.posY+1, this.posZ);
			Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult  movingobjectposition = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
			if (movingobjectposition != null) {
				setDead();
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
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f3 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = ((float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
			for (this.rotationPitch = ((float) (Math.atan2(this.motionY, f3) * 180.0D / 3.141592653589793D)); this.rotationPitch
					- this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			}
			this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
			this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
			float f4 = 0.99F;
			float f6 = 0.015F;

			this.motionX *= f4;
			this.motionY *= f4;
			this.motionZ *= f4;
			this.motionY -= f6;
			setPosition(this.posX, this.posY, this.posZ);
		} else {
			this.helper.spawn();
		}
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if (hand == EnumHand.OFF_HAND){
			// オフハンドは処理をしない
			return super.processInitialInteract(player, hand);
		}
		ItemStack stack = player.getHeldItem(hand);
		ItemStack offHand = player.getHeldItem(EnumHand.OFF_HAND);
		// メインハンドにDefuserをし装備している場合または
		// メインハンドに何も持っておらず、オフハンドにDefuserを装備している場合信管を外す
		if (((stack != null) && (stack.getItem() == ItemCore.item_defuser)) ||
			((stack == null) &&(offHand != null && offHand.getItem() == ItemCore.item_defuser))) {
			if ((!this.world.isRemote) && (!this.isDead)) {
				if (!player.capabilities.isCreativeMode) {
					ItemDefuser.defuserUse(stack, player);
				}
				this.setDead();
				if (this.missileType == 0) {
					this.dropItem(ItemCore.item_TntMissile, 1);
				}
				if (this.missileType == 1) {
					this.dropItem(ItemCore.item_NCMissile, 1);
				}
				if (this.missileType == 2) {
					this.dropItem(ItemCore.item_CEMissile, 1);
				}
				if (this.missileType == 3) {
					this.dropItem(ItemCore.item_MCEMissile, 1);
				}
				return true;
			}
		} else if (this.world.isRemote) {
			new ExBombsGuiHelper().displayGui(player, new GuiMissile(this.world, Minecraft.getMinecraft(), this));
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
		return !this.isDead;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.missileType);
		buffer.writeBoolean(this.flying);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.missileType = additionalData.readInt();
		this.flying = additionalData.readBoolean();
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {
		this.missileType = tagCompund.getInteger("missileType");
		this.flying = tagCompund.getBoolean("flying");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("missileType", this.missileType);
		tagCompound.setBoolean("flying", this.flying);
	}

	public void launch(int x, int z) {
		double XCoord = x - this.posX;
		double YCoord = z - this.posZ;
		if ((Math.abs(XCoord) > 500.0D) || (Math.abs(YCoord) > 500.0D) || (this.flying)) {
			return;
		}
		this.motionX = (0.0105D * XCoord);
		this.motionZ = (0.0105D * YCoord);

		this.flying = true;
	}

	private class ParticleHelper {
		private ParticleHelper() {
		}

		public void spawn() {
			for (int iterator = 0; iterator < 50; iterator++) {
				EntityExBombsSmokeFX fx = new EntityExBombsSmokeFX(EntityMissile.this.world,
						EntityMissile.this.posX, EntityMissile.this.posY, EntityMissile.this.posZ,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D,
						(EntityMissile.this.rand.nextInt(200) - 100.0F) / 600.0D);
				fx.setLife(120);
				fx.interpPosY=8.0D;
				fx.setAll((float) (Math.random() * 0.30000001192092896D));
				ExBombsMinecraftHelper.addEffect(fx);
			}
		}
	}
}
