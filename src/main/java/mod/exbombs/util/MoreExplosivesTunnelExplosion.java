/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import mod.exbombs.config.MyConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MoreExplosivesTunnelExplosion extends Explosion {
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Direction direction;
	public Entity exploder;
	public Set destroyedBlockPositions;

	public MoreExplosivesTunnelExplosion(World world, Entity entity, double i, double j, double k, Direction direction2) {
		super(world, entity, i, j, k, 0.0F,true, Explosion.Mode.DESTROY);

		this.destroyedBlockPositions = new HashSet();
		this.worldObj = world;
		this.exploder = entity;
		this.explosionX = i;
		this.explosionY = j;
		this.explosionZ = k;
		this.direction = direction2;
	}

	public void doExplosionA() {
		int explosionX2 = MathHelper.floor(this.explosionX);
		int explosionY2 = MathHelper.floor(this.explosionY);
		int explosionZ2 = MathHelper.floor(this.explosionZ);
		int width = MyConfig.GENERAL.tunnel_width.get();
		int height = MyConfig.GENERAL.tunnel_width.get();
		int depth = MyConfig.GENERAL.tunnel_depth.get();

		if (this.direction == Direction.DOWN) {

			for (int i = explosionX2 - width; i <= explosionX2 + width; i++) {
				for (int j = explosionY2 - depth; j <= explosionY2 + 0; j++) {
					for (int k = explosionZ2 - height; k <= explosionZ2 + height; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		if (this.direction == Direction.UP) {
			for (int i = explosionX2 - width; i <= explosionX2 + width; i++) {
				for (int j = explosionY2 - 0; j <= explosionY2 + depth; j++) {
					for (int k = explosionZ2 - height; k <= explosionZ2 + height; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		if (this.direction == Direction.NORTH) {
			for (int i = explosionX2 - width; i <= explosionX2 + width; i++) {
				for (int j = explosionY2 - height; j <= explosionY2 + height; j++) {
					for (int k = explosionZ2 - depth; k <= explosionZ2 + 0; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		if (this.direction == Direction.SOUTH) {
			for (int i = explosionX2 - width; i <= explosionX2 + width; i++) {
				for (int j = explosionY2 - height; j <= explosionY2 + height; j++) {
					for (int k = explosionZ2 - 0; k <= explosionZ2 + depth; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		if (this.direction == Direction.WEST) {
			for (int i = explosionX2 - depth; i <= explosionX2 + 0; i++) {
				for (int j = explosionY2 - width; j <= explosionY2 + width; j++) {
					for (int k = explosionZ2 - width; k <= explosionZ2 + width; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		if (this.direction == Direction.EAST) {
			for (int i = explosionX2 - 0; i <= explosionX2 + depth; i++) {
				for (int j = explosionY2 - width; j <= explosionY2 + width; j++) {
					for (int k = explosionZ2 - height; k <= explosionZ2 + height; k++) {
						addPosition(i, j, k);
					}
				}
			}
		}
		ArrayList arraylist = new ArrayList();
		arraylist.addAll(this.destroyedBlockPositions);
	}

	private void addPosition(int i, int j, int k) {
		float f1 = 20.0F;
		BlockState state = this.worldObj.getBlockState(new BlockPos(i,j,k));
		if (state != null){
			f1 -= (state.getExplosionResistance(worldObj,new BlockPos(i,j,k),this.exploder,this) + 0.3F) * 0.3F;
		}
		if (f1 > 0.0F) {
			this.destroyedBlockPositions.add(new BlockPos(i, j, k));
		}
	}

	public void doExplosionB(boolean flag) {
		this.worldObj.playSound((PlayerEntity)null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		this.worldObj.addParticle(ParticleTypes.EXPLOSION, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
		ArrayList arraylist = new ArrayList();
		arraylist.addAll(this.destroyedBlockPositions);
		for (int i = arraylist.size() - 1; i >= 0; i--) {
			BlockPos pos = (BlockPos)arraylist.get(i);
			BlockState state = this.worldObj.getBlockState(pos);
			if (flag && ((i%arraylist.size()) == 0)){
				double d0 = pos.getX() + this.worldObj.rand.nextFloat();
				double d1 = pos.getY() + this.worldObj.rand.nextFloat();
				double d2 = pos.getZ() + this.worldObj.rand.nextFloat();
				double d3 = d0 - this.explosionX;
				double d4 = d1 - this.explosionY;
				double d5 = d2 - this.explosionZ;
				double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
				d3 /= d6;
				d4 /= d6;
				d5 /= d6;
				double d7 = 0.5D / (d6 / 5.1D);
				d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
				d3 *= d7;
				d4 *= d7;
				d5 *= d7;
				this.worldObj.addParticle(ParticleTypes.EXPLOSION, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5);
                this.worldObj.addParticle(ParticleTypes.SMOKE, d0, d1, d2, d3, d4, d5);
			}
            if (state.getMaterial() != Material.AIR)
            {
                if (state.getBlock().canDropFromExplosion(this)){

                	ItemStack stack = new ItemStack(state.getBlock(),1);
                	worldObj.addEntity(new ItemEntity(worldObj, pos.getX(), pos.getY(), pos.getZ(), stack));
                }
                state.onBlockExploded(this.worldObj, pos, this);
            }
		}
	}
}
