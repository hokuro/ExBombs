/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import mod.exbombs.entity.prime.EntityTunnelExplosivePrimed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BlockTunnelExplosive extends BlockOriginalTnt {

	public static final DirectionProperty FACING = DirectionalBlock.FACING;


	public BlockTunnelExplosive(Block.Properties property) {
		super(property);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(EXPLODE, Boolean.valueOf(false)));
	}

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
    	Direction fc = context.getNearestLookingDirection().getOpposite().getOpposite();
    	Direction fcwrite;
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(FACING);
     }

	@Override
	protected void spqwnPriveEntity(IWorld world, BlockPos pos, BlockState state, boolean fuse, boolean sound) {
    	EntityTunnelExplosivePrimed entitytntprimed =
    			new EntityTunnelExplosivePrimed(world.getWorld(),
    					pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
    					((Direction)state.get(FACING)));
    	if (fuse) {
    		entitytntprimed.fuse = (world.getWorld().rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
    	}
    	world.addEntity(entitytntprimed);
    	if (sound) {
    		world.playSound((PlayerEntity)null, new BlockPos(entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
    	}
	}
}
