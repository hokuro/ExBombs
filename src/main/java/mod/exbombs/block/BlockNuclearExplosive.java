/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import java.util.Random;

import mod.exbombs.entity.EntityNuclearExplosivePrimed;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockNuclearExplosive extends Block {
	public static final BooleanProperty EXPLODE = BooleanProperty.create("explode");

	public BlockNuclearExplosive() {
		super(Properties.create(Material.TNT)
				.hardnessAndResistance(0.0F, 0.0F)
				.sound(SoundType.PLANT));
		this.setDefaultState(this.getDefaultState().with(EXPLODE, Boolean.valueOf(false)));
	}

	@Override
	public void onBlockAdded(IBlockState state,World worldIn, BlockPos pos, IBlockState oldstate) {
		super.onBlockAdded(state, worldIn, pos, oldstate);
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock, BlockPos fromPos){
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public int quantityDropped(IBlockState state, Random random){
		return 1;
	}

	@Override
	public void onExplosionDestroy(World world, BlockPos pos, Explosion explosion) {
		if (world.isRemote) {
			return;
		}
		EntityNuclearExplosivePrimed entitynucexpprimed =
				new EntityNuclearExplosivePrimed(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
		entitynucexpprimed.fuse = (world.rand.nextInt(entitynucexpprimed.getFuse() / 4) + entitynucexpprimed.getFuse() / 8);
		world.spawnEntity(entitynucexpprimed);
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, IBlockState state){
        if (!worldIn.isRemote())
        {
            if (((Boolean)state.getValues().get(EXPLODE)).booleanValue())
            {
            	EntityNuclearExplosivePrimed entitytntprimed = new EntityNuclearExplosivePrimed(worldIn.getWorld(), (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F));
                worldIn.spawnEntity(entitytntprimed);
                worldIn.playSound((EntityPlayer)null, new BlockPos(entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}
		ItemStack heldItem = playerIn.getHeldItem(hand);
		 if (heldItem != null && (heldItem.getItem() == Items.FLINT_AND_STEEL || heldItem.getItem() == Items.FIRE_CHARGE)){
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);

            if (heldItem.getItem() == Items.FLINT_AND_STEEL)
            {
                heldItem.damageItem(1, playerIn);
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
	public void onEntityCollision(IBlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!worldIn.isRemote && entityIn instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)entityIn;

            if (entityarrow.isBurning())
            {
                this.onPlayerDestroy(worldIn, pos, worldIn.getBlockState(pos).with(EXPLODE, Boolean.valueOf(true)));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(EXPLODE);
     }

}
