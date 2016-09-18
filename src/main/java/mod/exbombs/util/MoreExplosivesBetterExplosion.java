package mod.exbombs.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.exbombs.entity.EntityPaintBomb;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MoreExplosivesBetterExplosion extends Explosion {
	public EnumBombType bombType;
	public boolean enableDrops;
	public boolean isFlaming;
	public boolean CanDestroyBlock;
	private Random ExplosionRNG;
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	public Set<BlockPos> destroyedBlockPositions;

	public MoreExplosivesBetterExplosion(World world, Entity entity,
			double x, double y, double z, float size,
			EnumBombType type, boolean enableDrop) {
		super(world, entity, x, y, z, size,true,true);
		this.bombType = type;
		this.enableDrops = enableDrop;
		this.CanDestroyBlock = true;
		this.isFlaming = false;
		this.ExplosionRNG = new Random();
		this.destroyedBlockPositions = new HashSet();
		this.worldObj = world;
		this.exploder = entity;
		this.explosionSize = size;
		this.explosionX = x;
		this.explosionY = y;
		this.explosionZ = z;
	}

	public void doExplosionA() {
		float esize = this.explosionSize;
		int i = 16;
		for (int j = 0; j < i; j++) {
			for (int l = 0; l < i; l++) {
				for (int j1 = 0; j1 < i; j1++) {
					if ((j == 0) || (j == i - 1) || (l == 0) || (l == i - 1) || (j1 == 0) || (j1 == i - 1)) {
						double d = j / (i - 1.0F) * 2.0F - 1.0F;
						double d1 = l / (i - 1.0F) * 2.0F - 1.0F;
						double d2 = j1 / (i - 1.0F) * 2.0F - 1.0F;
						double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
						d /= d3;
						d1 /= d3;
						d2 /= d3;
						float esize1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						double eX = this.explosionX;
						double eY = this.explosionY;
						double eZ = this.explosionZ;
						float f2 = 0.3F;
						while (esize1 > 0.0F) {
							BlockPos blockpos = new BlockPos(eX, eY, eZ);
							IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
							if (bombType == EnumBombType.PAINT){
								if ((iblockstate.getBlock() != Blocks.bedrock && !ignoreMaterial(iblockstate.getMaterial())) ||
										(iblockstate.getBlock() == Blocks.grass)){
									if(checkBolock(blockpos)){
										this.destroyedBlockPositions.add(blockpos);
									}
								}
							} else if (bombType == EnumBombType.FROZEN || bombType == EnumBombType.ICICLE ||  bombType == EnumBombType.WARTER){
								if ((esize1 > 0.0F) || (iblockstate.getBlock() != Blocks.bedrock)){
									this.destroyedBlockPositions.add(blockpos);
								}
							}else{
								if (iblockstate.getMaterial() != Material.air){
									float f3 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(worldObj, blockpos, (Entity)null, this);
									esize1 -= (f3 + 0.3F) * f2;
								}
								if ((esize1 > 0.0F) || (iblockstate.getBlock() != Blocks.bedrock)){
									this.destroyedBlockPositions.add(blockpos);
								}
							}
							eX += d * f2;
							eY += d1 * f2;
							eZ += d2 * f2;
							esize1 -= f2 * 0.75F;
						}
					}
				}
			}
		}
		this.explosionSize *= 2.0F;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder,
				new AxisAlignedBB(MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0D),
									MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0D),
									MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0D),
									MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0D),
									MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0D),
									MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0D)));
		Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
		for (int k2 = 0; k2 < list.size(); k2++) {
			Entity entity = (Entity) list.get(k2);
			double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
			if (d4 <= 1.0D) {
				double d6 = entity.posX - this.explosionX;
				double d8 = entity.posY - this.explosionY;
				double d10 = entity.posZ - this.explosionZ;
				double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);
				d6 /= d11;
				d8 /= d11;
				d10 /= d11;
				double d12 = this.worldObj.getBlockDensity(vec3d, entity.getEntityBoundingBox());
				double d13 = (1.0D - d4) * d12;
				if (bombType == EnumBombType.FROZEN && entity instanceof EntityLivingBase){
					((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.moveSlowdown, 200, 10));
				}else{
					if (bombType == EnumBombType.ICICLE && entity instanceof EntityLivingBase){
						((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.moveSlowdown, 200, 10));
					}
					entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (int) ((d13 * d13 + d13) / 2.0D * 8.0D * this.explosionSize + 1.0D));
				}
				double d14 = d13;
				entity.motionX += d6 * d14;
				entity.motionY += d8 * d14;
				entity.motionZ += d10 * d14;
			}
		}
		this.explosionSize = esize;
	}

	public void doExplosionB(boolean flag) {
		if (bombType == EnumBombType.PAINT){
			this.worldObj.playSound((EntityPlayer)null, this.explosionX, this.explosionY, this.explosionZ, ModSoundManager.sound_paintBombInpackt, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}else{
			this.worldObj.playSound((EntityPlayer)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.entity_generic_explode, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}

		this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
		ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
		arraylist.addAll(this.destroyedBlockPositions);
		Set posSet = new HashSet();
		for (BlockPos blockpos : arraylist)
		{
			IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
			Block block = iblockstate.getBlock();

			if (flag)
			{
				double d0 = (double)((float)blockpos.getX() + this.worldObj.rand.nextFloat());
				double d1 = (double)((float)blockpos.getY() + this.worldObj.rand.nextFloat());
				double d2 = (double)((float)blockpos.getZ() + this.worldObj.rand.nextFloat());
				double d3 = d0 - this.explosionX;
				double d4 = d1 - this.explosionY;
				double d5 = d2 - this.explosionZ;
				double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
				d3 = d3 / d6;
				d4 = d4 / d6;
				d5 = d5 / d6;
				double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
				d7 = d7 * (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
				d3 = d3 * d7;
				d4 = d4 * d7;
				d5 = d5 * d7;
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5, new int[0]);
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);

			}

			if (bombType == EnumBombType.WARTER){
				if (this.CanDestroyBlock){
					if (this.enableDrops) {
						iblockstate.getBlock().dropBlockAsItemWithChance(this.worldObj, blockpos, iblockstate, 0.3F, 0);
					}
					this.worldObj.setBlockState(blockpos, Blocks.flowing_water.getDefaultState());
					if (iblockstate.getMaterial() != Material.air) {
						iblockstate.getBlock().onBlockExploded(this.worldObj, blockpos, this);
					}
				}else{
					if (iblockstate.getMaterial() == Material.air || iblockstate.getMaterial() == Material.water || iblockstate.getMaterial() == Material.lava) {
						this.worldObj.setBlockState(blockpos,  Blocks.flowing_water.getDefaultState());
					}
				}
			}else if (bombType == EnumBombType.ICICLE){
				if (iblockstate.getMaterial() == Material.air || iblockstate.getMaterial() == Material.water || iblockstate.getMaterial() == Material.lava) {
					if ((ExplosionRNG.nextInt(10000)%20) == 0){
						this.worldObj.setBlockState(blockpos,  Blocks.frosted_ice.getDefaultState());
					}else{
						this.worldObj.setBlockState(blockpos,  Blocks.ice.getDefaultState());
					}
				}
			}else if (bombType == EnumBombType.FROZEN){
				if (iblockstate.getMaterial() == Material.water){
					if ((ExplosionRNG.nextInt(10000)%20) == 0){
						this.worldObj.setBlockState(blockpos,  Blocks.frosted_ice.getDefaultState());
					}else{
						this.worldObj.setBlockState(blockpos,  Blocks.ice.getDefaultState());
					}
				}else if (iblockstate.getMaterial() == Material.lava){
					if (iblockstate.getBlock() == Blocks.lava){
						this.worldObj.setBlockState(blockpos,  Blocks.stone.getDefaultState());
					}else if (iblockstate.getBlock() == Blocks.flowing_lava){
						this.worldObj.setBlockState(blockpos,  Blocks.cobblestone.getDefaultState());
					}else{
						if ((ExplosionRNG.nextInt(10000)%2) == 0){
							this.worldObj.setBlockState(blockpos,  Blocks.stone.getDefaultState());
						}else{
							this.worldObj.setBlockState(blockpos,  Blocks.cobblestone.getDefaultState());
						}
					}
				}else if (iblockstate.getMaterial() == Material.air){
					if ((ExplosionRNG.nextInt(10000)%3) == 0){
						BlockPos underPos = blockpos.add(0, -1, 0);
						IBlockState w = this.worldObj.getBlockState(underPos);
						if ((w.getMaterial() == Material.sand || w.getMaterial() == Material.clay || w.getMaterial() == Material.cloth ||
							w.getMaterial() == Material.gourd || w.getMaterial() == Material.ice || w.getMaterial() == Material.iron ||
							w.getMaterial() == Material.packedIce || w.getMaterial() == Material.rock || w.getMaterial() == Material.wood ||
							w.getMaterial() == Material.snow || w.getMaterial() == Material.grass) && w.getBlock() != Blocks.snow_layer &&
							!posSet.contains(underPos)){
							this.worldObj.setBlockState(blockpos, Blocks.snow_layer.getStateFromMeta(ExplosionRNG.nextInt(4)));
							posSet.add(blockpos);
						}
					}
				}
			}else if (bombType == EnumBombType.PAINT && exploder instanceof EntityPaintBomb){
				this.worldObj.setBlockState(blockpos, ((EntityPaintBomb)exploder).getBlockState());
			}else{
				if (this.CanDestroyBlock){
	                if (iblockstate.getMaterial() != Material.air)
	                {
	                    if (this.enableDrops)
	                    {
	                        block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos), 1.0F / this.explosionSize, 0);
	                    }
	                    block.onBlockExploded(this.worldObj, blockpos, this);
	                }
				}
			}
		}
		posSet.clear();

		if (this.isFlaming) {
			for (BlockPos blockpos1 : this.destroyedBlockPositions)
			{
				if (this.worldObj.getBlockState(blockpos1).getMaterial() == Material.air && this.worldObj.getBlockState(blockpos1.down()).isFullBlock() && this.ExplosionRNG.nextInt(3) == 0)
				{
					this.worldObj.setBlockState(blockpos1, Blocks.fire.getDefaultState());
				}
			}
		}
	}


	private boolean checkBolock(BlockPos pos){
		if ((ignoreMaterial(this.worldObj.getBlockState(pos.add(1,0,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(1,0,0)).getBlock() != Blocks.grass) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(-1,0,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(-1,0,0)).getBlock() != Blocks.grass) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,1,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,1,0)).getBlock() != Blocks.grass) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,-1,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,-1,0)).getBlock() != Blocks.grass) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,0,1)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,0,1)).getBlock() != Blocks.grass) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,0,-1)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,0,-1)).getBlock() != Blocks.grass)){
			return true;
		}
		return false;
	}

	private boolean ignoreMaterial(Material mat){
		if (mat != Material.air &&
			mat != Material.cactus &&
			mat != Material.barrier &&
			mat != Material.cake &&
			mat != Material.carpet &&
			mat != Material.circuits &&
			mat != Material.dragonEgg &&
			mat != Material.fire &&
			mat != Material.plants &&
			mat != Material.portal &&
			mat != Material.vine &&
			mat != Material.web &&
			mat != Material.grass &&
			mat != Material.water &&
			mat != Material.lava){
			return false;
		}
		return true;
	}

	public static enum EnumBombType{
		BOMB(0,5.0F),
		WARTER(1,1.0F),
		ICICLE(2,3.0F),
		FROZEN(3,3.0F),
		PAINT(4,2.0F),
		PRIME(5,5.0F);

		private int index;
		private float explodSize;

		private EnumBombType(int idx, float size){
			index = idx;
			explodSize = size;
		}

		public int getIndex(){return index;}
		public float getSize(){return explodSize;}
	}
}
