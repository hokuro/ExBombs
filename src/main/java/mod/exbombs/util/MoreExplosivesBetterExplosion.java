package mod.exbombs.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.exbombs.entity.bomb.EntityPaintBomb;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;

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
		super(world, entity, x, y, z, size,true, Explosion.Mode.BREAK);
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
							BlockState iblockstate = this.worldObj.getBlockState(blockpos);
							if (bombType == EnumBombType.PAINT){
								if ((iblockstate.getBlock() != Blocks.BEDROCK && !ignoreMaterial(iblockstate.getMaterial())) ||
										(iblockstate.getBlock() == Blocks.GRASS)){
									if(checkBolock(blockpos)){
										this.destroyedBlockPositions.add(blockpos);
									}
								}
							}else if (bombType == EnumBombType.LAVA){
								if (MathHelper.floor(eY) == MathHelper.floor(explosionY)-1){
									if ((iblockstate.getBlock() != Blocks.BEDROCK) && (iblockstate.getBlock() != Blocks.AIR)){
										this.destroyedBlockPositions.add(blockpos);
									}
								}
							} else if (bombType == EnumBombType.WARTER){
									if (MathHelper.floor(eY) == MathHelper.floor(explosionY)-1){
										if ((iblockstate.getBlock() != Blocks.BEDROCK) && (iblockstate.getBlock() != Blocks.AIR)){
											this.destroyedBlockPositions.add(blockpos);
										}
									}
							} else if (bombType == EnumBombType.FROZEN || bombType == EnumBombType.ICICLE ||  bombType == EnumBombType.WARTER){
								if ((esize1 > 0.0F) || (iblockstate.getBlock() != Blocks.BEDROCK)){
									this.destroyedBlockPositions.add(blockpos);
								}
							}else{
								if (iblockstate.getMaterial() != Material.AIR){
				                    IFluidState ifluidstate = this.worldObj.getFluidState(blockpos);

				                    float f3 = Math.max(iblockstate.getExplosionResistance(worldObj, blockpos, exploder, this), ifluidstate.getExplosionResistance(worldObj, blockpos, exploder, this));
			                        if (this.exploder != null) {
			                        	f3 = this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate, ifluidstate, f3);
			                        }
			                        esize1 -= (f3 + 0.3F) * f2;
								}
								if ((esize1 > 0.0F) || (iblockstate.getBlock() != Blocks.BEDROCK)){
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
				new AxisAlignedBB(MathHelper.floor(this.explosionX - this.explosionSize - 1.0D),
									MathHelper.floor(this.explosionY - this.explosionSize - 1.0D),
									MathHelper.floor(this.explosionZ - this.explosionSize - 1.0D),
									MathHelper.floor(this.explosionX + this.explosionSize + 1.0D),
									MathHelper.floor(this.explosionY + this.explosionSize + 1.0D),
									MathHelper.floor(this.explosionZ + this.explosionSize + 1.0D)));
		Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
		for (int k2 = 0; k2 < list.size(); k2++) {
			Entity entity = (Entity) list.get(k2);
			double d4 = entity.getDistanceSq(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;
			if (d4 <= 1.0D) {
				double d6 = entity.posX - this.explosionX;
				double d8 = entity.posY - this.explosionY;
				double d10 = entity.posZ - this.explosionZ;
				double d11 = MathHelper.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
				d6 /= d11;
				d8 /= d11;
				d10 /= d11;
				double d12 = (double)func_222259_a(vec3d, entity);
				double d13 = (1.0D - d4) * d12;
				if (bombType == EnumBombType.FROZEN && entity instanceof LivingEntity){
					((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 10));
				}else{
					if (bombType == EnumBombType.ICICLE && entity instanceof LivingEntity){
						((LivingEntity)entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 200, 10));
					}
					entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (int) ((d13 * d13 + d13) / 2.0D * 8.0D * this.explosionSize + 1.0D));
				}
				double d14 = d13;
				entity.setMotion(
					entity.getMotion().getX() + d6 * d14,
					entity.getMotion().getY() + d8 * d14,
					entity.getMotion().getZ() + d10 * d14);
			}
		}
		this.explosionSize = esize;
	}

	public void doExplosionB(boolean flag) {
		if (bombType == EnumBombType.PAINT){
			this.worldObj.playSound((PlayerEntity)null, this.explosionX, this.explosionY, this.explosionZ, ModSoundManager.sound_paintBombInpackt, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}else{
			this.worldObj.playSound((PlayerEntity)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		}

		this.worldObj.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
		arraylist.addAll(this.destroyedBlockPositions);
		Set posSet = new HashSet();
		for (BlockPos blockpos : arraylist)
		{
			BlockState iblockstate = this.worldObj.getBlockState(blockpos);
			Block block = iblockstate.getBlock();

			if (flag)
			{
				double d0 = (double)((float)blockpos.getX() + this.worldObj.rand.nextFloat());
				double d1 = (double)((float)blockpos.getY() + this.worldObj.rand.nextFloat());
				double d2 = (double)((float)blockpos.getZ() + this.worldObj.rand.nextFloat());
				double d3 = d0 - this.explosionX;
				double d4 = d1 - this.explosionY;
				double d5 = d2 - this.explosionZ;
				double d6 = (double)MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				d3 = d3 / d6;
				d4 = d4 / d6;
				d5 = d5 / d6;
				double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
				d7 = d7 * (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
				d3 = d3 * d7;
				d4 = d4 * d7;
				d5 = d5 * d7;
				this.worldObj.addParticle(ParticleTypes.EXPLOSION, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5);
				this.worldObj.addParticle(ParticleTypes.SMOKE, d0, d1, d2, d3, d4, d5);

			}

			if (bombType == EnumBombType.WARTER ){
				// 爆発半径全部を水源に変える爆弾から、着弾地点に深さ1の水たまりを作る爆弾に変更
				if (iblockstate.getMaterial() != Material.AIR || iblockstate.getMaterial() == Material.WATER || iblockstate.getMaterial() == Material.LAVA) {
					this.worldObj.setBlockState(blockpos,  Blocks.WATER.getDefaultState());
				}
//				if (this.CanDestroyBlock){
//					if (this.enableDrops) {
//						iblockstate.getBlock().dropBlockAsItemWithChance(this.worldObj, blockpos, iblockstate, 0.3F, 0);
//					}
//					this.worldObj.setBlockState(blockpos, Blocks.FLOWING_WATER.getDefaultState());
//					if (iblockstate.getMaterial() != Material.AIR) {
//						iblockstate.getBlock().onBlockExploded(this.worldObj, blockpos, this);
//					}
//				}else{
//					if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.WATER || iblockstate.getMaterial() == Material.LAVA) {
//						this.worldObj.setBlockState(blockpos,  Blocks.FLOWING_WATER.getDefaultState());
//					}
//				}
			}else if (bombType == EnumBombType.LAVA){
				if (iblockstate.getMaterial() != Material.AIR || iblockstate.getMaterial() == Material.WATER || iblockstate.getMaterial() == Material.LAVA) {
					this.worldObj.setBlockState(blockpos,  Blocks.LAVA.getDefaultState());
				}
			}else if (bombType == EnumBombType.ICICLE){
				if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.WATER || iblockstate.getMaterial() == Material.LAVA) {
					if ((ExplosionRNG.nextInt(10000)%20) == 0){
						this.worldObj.setBlockState(blockpos,  Blocks.FROSTED_ICE.getDefaultState());
					}else{
						this.worldObj.setBlockState(blockpos,  Blocks.ICE.getDefaultState());
					}
				}
			}else if (bombType == EnumBombType.FROZEN){
				if (iblockstate.getMaterial() == Material.WATER){
					if ((ExplosionRNG.nextInt(10000)%20) == 0){
						this.worldObj.setBlockState(blockpos,  Blocks.FROSTED_ICE.getDefaultState());
					}else{
						this.worldObj.setBlockState(blockpos,  Blocks.ICE.getDefaultState());
					}
				}else if (iblockstate.getMaterial() == Material.LAVA){
					if (iblockstate.getBlock() == Blocks.LAVA){
						this.worldObj.setBlockState(blockpos,  Blocks.STONE.getDefaultState());
					}else if (iblockstate.getBlock() == Blocks.LAVA){
						this.worldObj.setBlockState(blockpos,  Blocks.COBBLESTONE.getDefaultState());
					}else{
						if ((ExplosionRNG.nextInt(10000)%2) == 0){
							this.worldObj.setBlockState(blockpos,  Blocks.STONE.getDefaultState());
						}else{
							this.worldObj.setBlockState(blockpos,  Blocks.COBBLESTONE.getDefaultState());
						}
					}
				}else if (iblockstate.getMaterial() == Material.AIR){
					if ((ExplosionRNG.nextInt(10000)%3) == 0){
						BlockPos underPos = blockpos.add(0, -1, 0);
						BlockState w = this.worldObj.getBlockState(underPos);
						if (w.getMaterial() != Material.AIR && w.getMaterial() != Material.OCEAN_PLANT &&
							w.getMaterial() != Material.PLANTS && w.getMaterial() != Material.TALL_PLANTS &&
							w.getMaterial() != Material.SNOW){
							this.worldObj.setBlockState(blockpos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, worldObj.rand.nextInt(8)+1));
							posSet.add(blockpos);
						}
					}
				}
			}else if (bombType == EnumBombType.PAINT && exploder instanceof EntityPaintBomb){
				BlockState next = ((EntityPaintBomb)exploder).checkNextBlock();
				BlockState chgBlock = this.worldObj.getBlockState(blockpos);
				if (next.getBlock() != Blocks.AIR && next.getBlock() != chgBlock.getBlock()){
					((EntityPaintBomb)exploder).getBlockState();
					this.worldObj.setBlockState(blockpos, next);
				}
			}else{
				if (this.CanDestroyBlock){
	                if (iblockstate.getMaterial() != Material.AIR)
	                {
	                    if (this.enableDrops)
	                    {
	                        if (this.worldObj instanceof ServerWorld && iblockstate.canDropFromExplosion(this.worldObj, blockpos, this)) {
	                            TileEntity tileentity = iblockstate.hasTileEntity() ? this.worldObj.getTileEntity(blockpos) : null;
	                            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.worldObj)).withRandom(this.worldObj.rand).withParameter(LootParameters.POSITION, blockpos).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, tileentity);
                                lootcontext$builder.withParameter(LootParameters.EXPLOSION_RADIUS, explosionSize);


	                            Block.spawnDrops(iblockstate, lootcontext$builder);
	                         }
	                    }
	                    iblockstate.onBlockExploded(this.worldObj, blockpos, this);

	                }
				}
			}
		}
		//posSet.clear();

		if (this.isFlaming) {
			for (BlockPos blockpos1 : this.destroyedBlockPositions)
			{
				if (this.worldObj.getBlockState(blockpos1).getMaterial() == Material.AIR && this.worldObj.getBlockState(blockpos1.down()).isOpaqueCube(this.worldObj, blockpos1.down()) && this.ExplosionRNG.nextInt(3) == 0)
				{
					this.worldObj.setBlockState(blockpos1, Blocks.FIRE.getDefaultState());
				}
			}
		}
	}


	private boolean checkBolock(BlockPos pos){
		if ((ignoreMaterial(this.worldObj.getBlockState(pos.add(1,0,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(1,0,0)).getBlock() != Blocks.GRASS) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(-1,0,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(-1,0,0)).getBlock() != Blocks.GRASS) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,1,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,1,0)).getBlock() != Blocks.GRASS) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,-1,0)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,-1,0)).getBlock() != Blocks.GRASS) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,0,1)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,0,1)).getBlock() != Blocks.GRASS) ||
			(ignoreMaterial(this.worldObj.getBlockState(pos.add(0,0,-1)).getMaterial()) && this.worldObj.getBlockState(pos.add(0,0,-1)).getBlock() != Blocks.GRASS)){
			return true;
		}
		return false;
	}

	private boolean ignoreMaterial(Material mat){
		if (mat != Material.AIR &&
			mat != Material.CACTUS &&
			mat != Material.BARRIER &&
			mat != Material.CAKE &&
			mat != Material.CARPET &&
			mat != Material.MISCELLANEOUS &&
			mat != Material.DRAGON_EGG &&
			mat != Material.FIRE &&
			mat != Material.PLANTS &&
			mat != Material.PORTAL &&
			mat != Material.LEAVES &&
			mat != Material.WEB &&
			mat != Material.TALL_PLANTS &&
			mat != Material.PLANTS &&
			mat != Material.OCEAN_PLANT &&
			mat != Material.BAMBOO &&
			mat != Material.BAMBOO_SAPLING &&
			mat != Material.WATER &&
			mat != Material.LAVA){
			return false;
		}
		return true;
	}

//	public static enum EnumBombType{
//		BOMB(0,5.0F,"exbombs:textures/entity/bomb.png"),
//		WARTER(1,1.0F,"exbombs:textures/entity/waterBomb.png"),
//		ICICLE(2,3.0F,"exbombs:textures/entity/icicleBomb.png"),
//		FROZEN(3,3.0F,"exbombs:textures/entity/frozenBomb.png"),
//		PAINT(4,2.0F,"exbombs:textures/entity/paintBomb.png"),
//		PRIME(5,5.0F),
//		LAVA(6,1.0F,"exbombs:textures/entity/lavabomb.png");
//
//		private int index;
//		private float explodSize;
//		private ResourceLocation texture;
//		private static final EnumBombType[] values = new EnumBombType[]{BOMB,WARTER,ICICLE,FROZEN,PAINT,PRIME,LAVA};
//
//		private EnumBombType(int idx, float size){
//			try{
//				index = idx;
//				explodSize = size;
//			}catch(Exception ex){
//				System.console().printf("");
//			}
//		}
//
//		private EnumBombType(int idx, float size, String tex){
//			try{
//				index = idx;
//				explodSize = size;
//				texture = new ResourceLocation(tex);
//			}catch(Exception ex){
//				System.console().printf("");
//			}
//		}
//
//		public int getIndex(){return index;}
//		public float getSize(){return explodSize;}
//		public ResourceLocation getTexture(){return texture;}
//		public static EnumBombType intToBombType(int idx){
//			if (idx >= 0 && idx < values.length){
//				return values[idx];
//			}
//			return BOMB;
//		}
//
//	}
}
