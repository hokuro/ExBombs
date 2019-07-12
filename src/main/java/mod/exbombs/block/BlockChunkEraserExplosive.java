package mod.exbombs.block;

import java.util.Random;

import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.util.MoreExplosivesFuse;
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

public class BlockChunkEraserExplosive extends Block {

	public static final BooleanProperty EXPLODE = BooleanProperty.create("explode");
	private EnumEraseType eraseType;

	public BlockChunkEraserExplosive(EnumEraseType type) {
		super(Properties.create(Material.TNT)
				.hardnessAndResistance(0.0F, 0.0F)
				.sound(SoundType.PLANT));

		this.setDefaultState(this.getDefaultState().with(EXPLODE, Boolean.valueOf(false)));
		eraseType = type;
	}

	@Override
	public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState){
	//public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(state,worldIn, pos,oldState);
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos,Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock, BlockPos fromPos){
		if (worldIn.isBlockPowered(pos)) {
			onPlayerDestroy(worldIn, pos, state.with(EXPLODE, Boolean.valueOf(true)));
			worldIn.setBlockState(pos,Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public int quantityDropped(IBlockState state, Random random){
		return 1;
	}

	@Override
	public void onExplosionDestroy(World world, BlockPos pos, Explosion explosion){
	//public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
		if (world.isRemote) {
			return;
		}
		if (explosion instanceof MoreExplosivesFuse){
			EntityChunkEraserPrimed entitynucexpprimed =
					new EntityChunkEraserPrimed(world, pos, eraseType);
			entitynucexpprimed.fuse = (world.rand.nextInt(entitynucexpprimed.getFuse() / 4) + entitynucexpprimed.getFuse() / 8);
			world.spawnEntity(entitynucexpprimed);
		}
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, IBlockState state) {
	//public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote())
        {
            if (((Boolean)state.getValues().get(EXPLODE)).booleanValue())
            {
            	EntityChunkEraserPrimed entitytntprimed =
    					new EntityChunkEraserPrimed(worldIn, pos, eraseType);
            	worldIn.spawnEntity(entitytntprimed);
                worldIn.playSound(
                		(EntityPlayer)null,
                		new BlockPos(entitytntprimed.posX,entitytntprimed.posY, entitytntprimed.posZ),
                		SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
	}

	@Override
	public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	//public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ){
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
    //public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote && entityIn instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)entityIn;

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

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(EXPLODE);
     }
}
