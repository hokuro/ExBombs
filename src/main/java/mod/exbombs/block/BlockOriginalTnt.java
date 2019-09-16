package mod.exbombs.block;

import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class BlockOriginalTnt extends Block{

	public static final BooleanProperty EXPLODE = BooleanProperty.create("explode");

	public BlockOriginalTnt(Block.Properties property) {
		super(property);
		this.setDefaultState(this.getDefaultState().with(EXPLODE, Boolean.valueOf(false)));
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		if (world.isRemote) {
			return;
		}
		if (explosion instanceof MoreExplosivesFuse){
        	spqwnPriveEntity(world, pos, state.with(EXPLODE, true), true, false);
		}
		super.onBlockExploded(state, world, pos, explosion);
	}

	@Override
	public void onExplosionDestroy(World world, BlockPos pos, Explosion explosion){

	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if (!worldIn.isRemote())
        {
            if (((Boolean)state.getValues().get(EXPLODE)).booleanValue())
            {
            	spqwnPriveEntity(worldIn, pos, state, false, true);
            }
        }
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return true;
		}
		ItemStack heldItem = playerIn.getHeldItem(hand);
		 if (heldItem != null && (heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE)){
			 onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

            if (heldItem.getItem() == Items.FLINT_AND_STEEL)
            {
                heldItem.damageItem(1, playerIn, (p_220287_1_) -> {
                    p_220287_1_.sendBreakAnimation(hand);
                 });
            }
            else if (!playerIn.isCreative())
            {
                heldItem.shrink(1);
            }
			return true;
		}
		return false;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!worldIn.isRemote && entityIn instanceof ArrowEntity)
        {
        	ArrowEntity entityarrow = (ArrowEntity)entityIn;

            if (entityarrow.isBurning())
            {
                this.onPlayerDestroy(worldIn, pos, worldIn.getBlockState(pos).with(EXPLODE, Boolean.valueOf(true)));
                worldIn.setBlockState(pos,Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(EXPLODE);
     }




	protected abstract void spqwnPriveEntity(IWorld world, BlockPos pos, BlockState state, boolean fuse, boolean sound);

}
