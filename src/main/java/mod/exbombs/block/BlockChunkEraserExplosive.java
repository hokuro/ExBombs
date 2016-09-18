package mod.exbombs.block;

import java.util.Random;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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

public class BlockChunkEraserExplosive extends Block {

	public static final PropertyBool EXPLODE = PropertyBool.create("explode");
	private EnumEraseType eraseType;

	public BlockChunkEraserExplosive(EnumEraseType type) {
		super(Material.tnt);
		this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, Boolean.valueOf(false)));
		setCreativeTab(Mod_ExBombs.tabExBombs);
		eraseType = type;
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
			EntityChunkEraserPrimed entitynucexpprimed =
					new EntityChunkEraserPrimed(world, pos, eraseType);
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
            	EntityChunkEraserPrimed entitytntprimed =
    					new EntityChunkEraserPrimed(worldIn, pos, eraseType);
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
        return this.getDefaultState().withProperty(EXPLODE, Boolean.valueOf((meta & 1) > 0));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((Boolean)state.getValue(EXPLODE)).booleanValue() ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {EXPLODE});
    }


    public static enum EnumEraseType{
    	ERASEALL(0,"erase_all"),
    	ERASEUNMATCH(1,"erase_unmatch");

    	private final int index;
    	private final String name;
    	private static final EnumEraseType[] values = new EnumEraseType[]{ERASEALL,ERASEUNMATCH};
    	private EnumEraseType(int idx, String nm){
    		index = idx;
    		name = nm;
    	}

    	public int getIndex(){return index;}
    	public String getName(){return name;}
    	public static EnumEraseType getType(int index){
    		if (index >= 0 && index < values.length){
    			return values[index];
    		}
    		return null;
    	}
    }
}
