/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.util;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MoreExplosivesSuperExplosion extends Explosion {
	private World world;
	private int xPos;
	private int yPos;
	private int zPos;
	private Random random;

	public MoreExplosivesSuperExplosion() {
		super(null, null, 0.0D, 0.0D, 0.0D, 0.0F, true,true);
	}

	public void explode(World world, double radiusX, double radiusY, double radiusZ, int xPos, int yPos, int zPos, long randomSeed){
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.world = world;
		this.random = new Random(randomSeed);

		this.world.playSound((EntityPlayer)null, this.xPos, this.yPos, this.zPos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
				(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

		radiusX += 0.5D;
		radiusY += 0.5D;
		radiusZ += 0.5D;

		double invRadiusX = 1.0D / radiusX;
		double invRadiusY = 1.0D / radiusY;
		double invRadiusZ = 1.0D / radiusZ;

		int ceilRadiusX = (int)Math.ceil(radiusX);
		int ceilRadiusY = (int)Math.ceil(radiusY);
		int ceilRadiusZ = (int)Math.ceil(radiusZ);

		double nextXn = 0.0D;
		boolean flag = true;
		labelfin:{
			for (int x = 0; x <= ceilRadiusX; x++){
				labelSuper:{
				double xn = nextXn;
				nextXn = (x + 1) * invRadiusX;
				double nextYn = 0.0D;
				for (int y = 0; y <= ceilRadiusY; y++)
				{
					double yn = nextYn;
					nextYn = (y + 1) * invRadiusY;
					double nextZn = 0.0D;
					for (int z = 0; z <= ceilRadiusZ; z++)
					{
						double zn = nextZn;
						nextZn = (z + 1) * invRadiusZ;

						double distanceSq = lengthSq(xn, yn, zn);
						if (distanceSq > 1.0D)
						{
							if (z != 0) {
								break;
							}
							if (y != 0) {
								break labelSuper;
							}
							break labelfin;
						}

						//計測したい処理を記述

						if (((lengthSq(nextXn, yn, zn) <= 1.0D) && (lengthSq(xn, nextYn, zn) <= 1.0D) && (lengthSq(xn, yn, nextZn) <= 1.0D)) || (!this.random.nextBoolean()))
						{
							setBlock(x, y, z);
							setBlock(-x, y, z);
							setBlock(x, -y, z);
							setBlock(x, y, -z);
							setBlock(-x, -y, z);
							setBlock(x, -y, -z);
							setBlock(-x, y, -z);
							setBlock(-x, -y, -z);
						}
					}
				}
			}
			}
		}
		List list = world.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(xPos - radiusX, yPos - radiusY, zPos - radiusY, xPos + radiusX, yPos + radiusY, zPos + radiusY));
		for (int index = 0; index < list.size(); index++) {
		  ((Entity)list.get(index)).attackEntityFrom(DamageSource.causeExplosionDamage(this), 100.0F);
		}
	}
	private final double lengthSq(double x, double y, double z) {
		return x * x + y * y + z * z;
	}

	private final void setBlock(double x, double y, double z) {
		IBlockState state = this.world.getBlockState(new BlockPos((int) x + this.xPos, (int) y + this.yPos, (int) z + this.zPos));
		if ((state.getMaterial() != Material.AIR) && (state.getBlock() != Blocks.BEDROCK)) {
			this.world.setBlockToAir(new BlockPos((int) x + this.xPos, (int) y + this.yPos, (int) z + this.zPos));
		}
	}
}
