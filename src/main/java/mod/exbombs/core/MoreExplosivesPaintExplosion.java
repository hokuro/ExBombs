package mod.exbombs.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MoreExplosivesPaintExplosion extends Explosion {
	public IBlockState exChange;
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

	public MoreExplosivesPaintExplosion(World world, Entity entity, double x, double y, double z, float size, IBlockState exchange) {
		super(world, entity, x, y, z, size,true,true);
		this.exChange = exchange;
		this.enableDrops = false;
		this.CanDestroyBlock = false;
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
							if(checkBolock(blockpos)){
								IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
								if (iblockstate.getMaterial() != Material.air &&
										iblockstate.getMaterial() != Material.water && iblockstate.getMaterial() != Material.lava
										&& iblockstate.getBlock() != Blocks.bedrock){
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
	}

	private boolean checkBolock(BlockPos pos){
		if (this.worldObj.getBlockState(pos.add(1,0,0)).getMaterial() != Material.air &&
				this.worldObj.getBlockState(pos.add(-1,0,0)).getMaterial() != Material.air &&
				this.worldObj.getBlockState(pos.add(0,1,0)).getMaterial() != Material.air &&
				this.worldObj.getBlockState(pos.add(0,-1,0)).getMaterial() != Material.air &&
				this.worldObj.getBlockState(pos.add(0,0,1)).getMaterial() != Material.air &&
				this.worldObj.getBlockState(pos.add(0,0,-1)).getMaterial() != Material.air){
			return false;
		}
		return true;
	}

	public void doExplosionB(boolean flag) {
		this.worldObj.playSound((EntityPlayer)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.entity_generic_explode, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D, new int[0]);
		ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
		arraylist.addAll(this.destroyedBlockPositions);

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

			if (exChange != null){
				this.worldObj.setBlockState(blockpos, exChange);
			}
		}
	}
}
