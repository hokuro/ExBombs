package mod.exbombs.util;

import java.util.List;
import java.util.Random;

import mod.exbombs.config.MyConfig;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;

public class MoreExplosiveEraseExplosion extends Explosion {
	private World world;
	private int xPos;
	private int yPos;
	private int zPos;
	private Random random;
	private boolean matching;

	public MoreExplosiveEraseExplosion(World world, Entity exploderIn, boolean match) {
		super(world, exploderIn, 0.0D, 0.0D, 0.0D, 0.0F, true,Explosion.Mode.BREAK);
		matching = match;
	}

	public boolean isMatch(BlockState state, List<Block> match){
		if ((state != null) &&
				(state.getBlock() != Blocks.BEDROCK) &&
				(state.getMaterial() != Material.AIR)){
			for(Block blk : match){
				if (state.getBlock() == blk) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public void explode(World world, int xPos, int yPos, int zPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.world = world;

		this.world.playSound((PlayerEntity)null, this.xPos, this.yPos, this.zPos, ModSoundManager.sound_eraseExplosive, SoundCategory.BLOCKS, 4.0F,
				(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

		int x_start;
		int z_start;
		List<Block> match = MyConfig.GENERAL.getUnEraseBlock();
		if (MyConfig.GENERAL.erase_method.get() == 0){
			IChunk ck = world.getChunk(new BlockPos(xPos,yPos,zPos));//world.getChunkFromBlockCoords(new BlockPos(xPos,yPos,zPos));
			x_start = ck.getPos().getXStart();
			z_start = ck.getPos().getZStart();
		}else{
			x_start = xPos - 8;
			z_start = zPos - 8;
		}
		for (int x = 0; x < 16; x++){
			for ( int z = 0; z < 16; z++){
				for ( int y = 1; y < 255; y++){
					// マッチしたブロックは破壊しない
					if (matching && isMatch(world.getBlockState(new BlockPos(x_start+x,y,z_start+z)), match)){
						continue;
					}
					world.setBlockState(new BlockPos(x_start+x,y,z_start+z),Blocks.AIR.getDefaultState());
				}
			}
		}

		List list = world.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(x_start, 2, z_start, x_start+16, 255, z_start+16));
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index) instanceof LivingEntity){
				  ((Entity)list.get(index)).setPositionAndUpdate(xPos,-10,zPos);
			}else{
				((Entity)list.get(index)).attackEntityFrom(DamageSource.causeExplosionDamage(this), 100.0F);
			}
		}
	}
}
