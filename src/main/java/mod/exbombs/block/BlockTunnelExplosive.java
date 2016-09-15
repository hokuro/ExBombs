/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import java.util.Random;

import mod.exbombs.core.ExBombs;
import mod.exbombs.core.MoreExplosivesFuse;
import mod.exbombs.entity.EntityTunnelExplosivePrimed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTunnelExplosive extends Block {

	public static final PropertyBool EXPLODE = PropertyBool.create("explode");
	public static final PropertyDirection FACING = BlockDirectional.FACING;


	public BlockTunnelExplosive() {
		super(Material.tnt);
		this.setResistance(2000);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXPLODE, Boolean.valueOf(false)));
		setCreativeTab(ExBombs.tabExBombs);
	}
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		if (worldIn.isBlockPowered(pos)) {
			onBlockDestroyedByPlayer(worldIn, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (worldIn.isBlockPowered(pos)) {
			onBlockDestroyedByPlayer(worldIn, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random){
		return 1;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (world.isRemote) {
			return;
		}
		if (explosion instanceof MoreExplosivesFuse){
			EntityTunnelExplosivePrimed entitynucexpprimed =
					new EntityTunnelExplosivePrimed(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
			entitynucexpprimed.fuse = (world.rand.nextInt(entitynucexpprimed.getFuse() / 4) + entitynucexpprimed.getFuse() / 8);
			world.spawnEntityInWorld(entitynucexpprimed);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote)
        {
            if (((Boolean)state.getValue(EXPLODE)).booleanValue())
            {
            	EntityTunnelExplosivePrimed entitytntprimed = new EntityTunnelExplosivePrimed(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), state.getBlock().getMetaFromState(state));
                worldIn.spawnEntityInWorld(entitytntprimed);
                worldIn.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.entity_tnt_primed, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (worldIn.isRemote) {
			return true;
		}
		 if (heldItem != null && (heldItem.getItem() == Items.flint_and_steel || heldItem.getItem() == Items.fire_charge)){
			onBlockDestroyedByPlayer(worldIn, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 11);

            if (heldItem.getItem() == Items.flint_and_steel)
            {
                heldItem.damageItem(1, playerIn);
            }
            else if (!playerIn.capabilities.isCreativeMode)
            {
                --heldItem.stackSize;
            }
			return true;
		}
		return false;
	}

	@Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote && entityIn instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)entityIn;

            if (entityarrow.isBurning())
            {
                this.onBlockDestroyedByPlayer(worldIn, pos, worldIn.getBlockState(pos).withProperty(EXPLODE, Boolean.valueOf(true)));
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	IBlockState state = this.getDefaultState();
    	return state.withProperty(EXPLODE, ((meta&0x01)==1)).withProperty(FACING,  EnumFacing.getFront(((meta & 0x0E) >> 1)));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
    	int meta = state.getValue(FACING).getIndex();
    	meta = (meta << 1) + (((Boolean)state.getValue(EXPLODE)).booleanValue() ? 1 : 0);
        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {EXPLODE, FACING});
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    	EnumFacing fc = BlockPistonBase.getFacingFromEntity(pos, placer);
    	EnumFacing fcwrite;
    	if ((fc.getIndex() % 2) == 0){
    		fcwrite = fc.getFront(fc.getIndex()+1);
    	}else{
    		fcwrite = fc.getFront(fc.getIndex()-1);
    	}

        return this.getDefaultState().withProperty(FACING, fcwrite);
    }
}
