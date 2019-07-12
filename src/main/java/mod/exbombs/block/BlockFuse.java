/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.block;

import mod.exbombs.core.ICableBlock;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockFuse extends BlockContainer implements ICableBlock {

	protected VoxelShape bBox = Block.makeCuboidShape(0.25D,    0.25D, 0.25D,    0.75D,    0.75D, 0.75D);
	protected static final VoxelShape[] field_185730_f = new VoxelShape[] {
			 Block.makeCuboidShape( 4D,  4D,  4D, 12D, 12D, 12D),    // center 0
			 Block.makeCuboidShape( 4D, 12D,  4D, 12D, 16D, 12D),    // down   1
			 Block.makeCuboidShape( 4D,  0D,  0D, 12D, 12D, 12D),    // up     2
			 Block.makeCuboidShape( 4D,  4D, 12D, 12D, 12D, 16D),    // north  3
			 Block.makeCuboidShape( 4D,  4D,  0D, 12D, 12D,  4D),    // south  4
			 Block.makeCuboidShape( 0D,  4D,  4D, 12D,  4D, 12D),    // west   5
			 Block.makeCuboidShape(12D,  4D,  4D, 16D, 12D, 12D)     // east   6
	};

	public BlockFuse() {
		super(Properties.create(Material.TNT)
				.hardnessAndResistance(0.0F, 0.0F)
				.sound(SoundType.PLANT));
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos)
	//public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
	//public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB p_185477_4_, List<AxisAlignedBB> p_185477_5_, @Nullable Entity p_185477_6_,boolean isActualState)
	{
		VoxelShape sh1 = field_185730_f[0];
		VoxelShape sh2 = Block.makeCuboidShape(0, 0, 0, 0, 0, 0);
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,1,0)))){
	    	// UP
	    	sh1 = VoxelShapes.or(sh1,field_185730_f[2]);
	    }
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,-1,0)))){
	    	// DOWN
	    	sh1 = VoxelShapes.or(sh1,field_185730_f[1]);
	    }
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,0,1)))){
	    	// NORTH
	    	sh1 =VoxelShapes.or(sh1,field_185730_f[3]);
	    }
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,0,-1)))){
	    	// SOUTH
	    	sh1 = VoxelShapes.or(sh1,field_185730_f[4]);
	    }
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(-1,0,0)))){
	    	// WEST
	    	sh1 = VoxelShapes.or(sh1,field_185730_f[5]);
	    }
	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(1,0,0)))){
	    	// EASHT
	    	sh2 = VoxelShapes.or(sh1,field_185730_f[6]);
	    }
	    return sh1;

	}

//	@Override
//	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
//	{
//		AxisAlignedBB result = new AxisAlignedBB(bBox.minX,bBox.minY,bBox.minZ, bBox.maxX,bBox.maxY,bBox.maxZ);
//		World worldIn = ExBombsMinecraftHelper.getWorld();
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,1,0)))){
//	    	// UP
//	    	result = result.union(new AxisAlignedBB(0.25D, 0.25D ,0.25D, 0.75D, 1.0D, 0.75D));
//	    }
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,-1,0)))){
//	    	// DOWN
//	    	result = result.union(new AxisAlignedBB(0.25D, 0.00D ,0.25D, 0.75D, 0.75D, 0.75D));
//	    }
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,0,1)))){
//	    	// NORTH
//	    	result = result.union(new AxisAlignedBB(0.25D, 0.25D ,0.25D, 0.75D, 0.75D, 1.00D));
//	    }
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(0,0,-1)))){
//	    	// SOUTH
//	    	result = result.union(new AxisAlignedBB(0.25D, 0.25D ,0.00D, 0.75D, 0.75D, 0.75D));
//	    }
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(-1,0,0)))){
//	    	// WEST
//	    	result = result.union(new AxisAlignedBB(0.00, 0.25D ,0.25D, 0.75D, 0.75D, 0.75D));
//	    }
//	    if (this.shouldConnectTo(worldIn.getBlockState(pos.add(1,0,0)))){
//	    	// EASHT
//	    	result = result.union(new AxisAlignedBB(0.25D, 0.25D ,0.25D, 1.00D, 0.75D, 0.75D));
//	    }
//	    return result;
//	}

	@Override
	public void onBlockAdded(IBlockState state, World world, BlockPos pos, IBlockState oldState) {
	//public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		int i,j,k;
		i = pos.getX();
		j = pos.getY();
		k = pos.getZ();

		super.onBlockAdded(state,world, pos, oldState );
		if (world.isBlockPowered(pos)) {
			ignite(world, i, j, k);
			return;
		}
		if (world.getBlockState(pos.add(1,0,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(-1,0,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,1,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,-1,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,0,1)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,0,-1)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
	}

	@Override
	public void onExplosionDestroy(World world, BlockPos pos, Explosion explosion) {
		super.onExplosionDestroy(world, pos, explosion);
		ignite(world, pos.getX(), pos.getY(), pos.getZ());

	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos fromPos){
		int i,j,k;
		i = pos.getX();
		j = pos.getY();
		k = pos.getZ();
		if (world.isBlockPowered(pos)) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(1,0,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(-1,0,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,1,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,-1,0)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,0,1)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
		if (world.getBlockState(pos.add(0,0,-1)).getBlock() == Blocks.FIRE) {
			ignite(world, i, j, k);
		}
	}

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
	public void preWorldRender() {

	}

	@Override
	public boolean shouldConnectTo(IBlockState state) {

		if (this == state.getBlock()) {
			return true;
		}
		if (Blocks.TNT == state.getBlock()) {
			return true;
		}
		if (BlockCore.block_nuclear == state.getBlock()) {
			return true;
		}
		if (BlockCore.block_tunnel == state.getBlock()) {
			return true;
		}
		if (BlockCore.block_chunkeraser == state.getBlock()) {
			return true;
		}
		if (BlockCore.block_muchblockeraser == state.getBlock()) {
			return true;
		}
		return false;
	}


	private void ignite(World world, int posX, int posY, int posZ) {
		if (world.isRemote) {
			return;
		}
		TileEntity tileentity = world.getTileEntity(new BlockPos(posX, posY, posZ));
		if ((tileentity != null) && ((tileentity instanceof TileEntityFuse))) {
			((TileEntityFuse) tileentity).setBurning();
		} else {
			tryIgnite(world, posX, posY, posZ + 1);
			tryIgnite(world, posX, posY, posZ - 1);
			tryIgnite(world, posX, posY + 1, posZ);
			tryIgnite(world, posX, posY - 1, posZ);
			tryIgnite(world, posX + 1, posY, posZ);
			tryIgnite(world, posX - 1, posY, posZ);
		}
	}

	public void tryIgnite(World world, int x, int y, int z) {
		if (!world.isRemote) {
			return;
		}
		if (world.getBlockState(new BlockPos(x, y, z)).getBlock() == this) {
			((TileEntityFuse) world.getTileEntity(new BlockPos(x, y, z))).setBurning();
		}
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new TileEntityFuse();
	}
}
