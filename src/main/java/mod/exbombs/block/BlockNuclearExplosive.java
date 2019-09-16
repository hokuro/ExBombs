/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import mod.exbombs.entity.prime.EntityNuclearExplosivePrimed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BlockNuclearExplosive extends BlockOriginalTnt {

	public BlockNuclearExplosive(Block.Properties property) {
		super(property);
	}

	@Override
	protected void spqwnPriveEntity(IWorld world, BlockPos pos, BlockState state, boolean fuse, boolean sound) {
		EntityNuclearExplosivePrimed entitytntprimed =
				new EntityNuclearExplosivePrimed(world.getWorld(),
    					pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
    	if (fuse) {
    		entitytntprimed.fuse = (world.getWorld().rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
    	}
    	world.addEntity(entitytntprimed);
    	if (sound) {
    		world.playSound((PlayerEntity)null, new BlockPos(entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
    	}
	}
}
