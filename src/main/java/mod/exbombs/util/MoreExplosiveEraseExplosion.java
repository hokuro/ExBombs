package mod.exbombs.util;

import java.util.List;
import java.util.Random;

import mod.exbombs.config.BlockAndMetadata;
import mod.exbombs.config.ConfigValue;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class MoreExplosiveEraseExplosion extends Explosion {
	private World world;
	private int xPos;
	private int yPos;
	private int zPos;
	private Random random;
	private boolean matching;

	public MoreExplosiveEraseExplosion(boolean match) {
		super(null, null, 0.0D, 0.0D, 0.0D, 0.0F, true,true);
		matching = match;
	}

	public boolean isMatch(IBlockState state, List<BlockAndMetadata> match){
		if ((state != null) &&
				(state.getBlock() != Blocks.BEDROCK) &&
				(state.getMaterial() != Material.AIR)){
			for (int mcc = 0; mcc < match.size(); mcc++){
				if (match.get(mcc).toString().equals(state.getBlock().getRegistryName().toString())){
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

		this.world.playSound((EntityPlayer)null, this.xPos, this.yPos, this.zPos, ModSoundManager.sound_eraseExplosive, SoundCategory.BLOCKS, 4.0F,
				(1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);


		int x_start;
		int z_start;
		List<BlockAndMetadata> match = ConfigValue.General.getUnEraseBlock();
		if (ConfigValue.General.erase_method == 0){
			Chunk ck = world.getChunkFromBlockCoords(new BlockPos(xPos,yPos,zPos));
			x_start = ck.xPosition* 16;
			z_start = ck.zPosition * 16;
		}else{
			x_start = xPos - 8;
			z_start = zPos - 8;
		}
		for (int x = 0; x < 16; x++){
			for ( int z = 0; z < 16; z++){
				for ( int y = 1; y < 255; y++){
					if (matching &&
							ConfigValue.General.getUnEraseBlock() != null &&
							isMatch(world.getBlockState(new BlockPos(x_start+x,y,z_start+z)), match)){
						continue;
					}
					world.setBlockToAir(new BlockPos(x_start+x,y,z_start+z));
				}
			}
		}

		List<Entity> list = world.getEntitiesWithinAABB(Entity.class,  new AxisAlignedBB(x_start, 2, z_start, x_start+16, 255, z_start+16));
		for (int index = 0; index < list.size(); index++) {
			if (list.get(index) instanceof EntityLivingBase){
				  ((Entity)list.get(index)).setPositionAndUpdate(xPos,-10,zPos);
			}else{
				((Entity)list.get(index)).attackEntityFrom(DamageSource.causeExplosionDamage(this), 100.0F);
			}
		}
	}
}
