/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import java.util.Random;

import mod.exbombs.entity.EntityTunnelExplosivePrimed;
import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockTunnelExplosive extends Block {

	public static final BooleanProperty EXPLODE = BooleanProperty.create("explode");
	public static final DirectionProperty FACING = BlockDirectional.FACING;


	public BlockTunnelExplosive() {
		super(Properties.create(Material.TNT)
				.hardnessAndResistance(0.0F,2000)
				.sound(SoundType.PLANT));
		this.setDefaultState(this.getDefaultState().with(FACING, EnumFacing.NORTH).with(EXPLODE, Boolean.valueOf(false)));

	}
	@Override
	   public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState) {
		super.onBlockAdded(state, worldIn, pos, oldState);
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
		if (explosion instanceof MoreExplosivesFuse){
			EntityTunnelExplosivePrimed entitynucexpprimed =
					new EntityTunnelExplosivePrimed(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
			entitynucexpprimed.fuse = (world.rand.nextInt(entitynucexpprimed.getFuse() / 4) + entitynucexpprimed.getFuse() / 8);
			world.spawnEntity(entitynucexpprimed);
		}
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, IBlockState state){
        if (!worldIn.isRemote())
        {
            if (((Boolean)state.getValues().get(EXPLODE)).booleanValue())
            {
            	EntityTunnelExplosivePrimed entitytntprimed =
            			new EntityTunnelExplosivePrimed(worldIn.getWorld(),
            					(double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F),
            					((EnumFacing)state.getValues().get(FACING)));
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
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
    	EnumFacing fc = context.getNearestLookingDirection().getOpposite().getOpposite();
    	EnumFacing fcwrite;
//    	if ((fc.getIndex() % 2) == 0){
//    		fcwrite = EnumFacing.byIndex(fc.getIndex()+1);
//    	}else{
//    		fcwrite = EnumFacing.byIndex(fc.getIndex()-1);
//    	}

        return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(EXPLODE);
        builder.add(FACING);
     }
}
