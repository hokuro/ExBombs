package mod.exbombs.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class BlockRadarData extends WorldSavedData {
	private IBlockState targetBlock;
	private int sizeIndex;
	private boolean init = false;
	public boolean upDate;

	private static final int UPDATETIME = 1000;

	// コンストラクタ
	public BlockRadarData(String name)
	{
		super(name);
		targetBlock = Blocks.AIR.getDefaultState();
		sizeIndex = 0;
	}

	public void onUpdate(World world, EntityPlayer player)
	{
		if(!this.init)
		{
			this.init = true;
		}
		if(world.getDayTime() % UPDATETIME == 0l){
			this.upDate = true;
		}
		if(this.upDate)
		{
			this.markDirty();
			this.upDate = false;
		}
	}

	public void setTarget(IBlockState block){
		targetBlock = block;
	}
	public IBlockState getTarget() {
		if (targetBlock == null){
			targetBlock = Blocks.AIR.getDefaultState();
		}
		return targetBlock;
	}

	public String getTargetName(){
		return targetBlock.getBlock().getRegistryName().toString();
	}

	public int getTargetStateId(){
		return Block.getStateId(targetBlock);
	}

	@Override
	public void read(NBTTagCompound nbt) {
		String name = nbt.getString("Name");
		int meta = nbt.getInt("Meta");
		if (!name.isEmpty()){
			targetBlock = Block.getStateById(meta);
		}
		sizeIndex = nbt.getInt("size");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		nbt.setString("Name", targetBlock.getBlock().getRegistryName().toString());
		nbt.setInt("Meta", Block.getStateId(targetBlock));
		nbt.setInt("size", sizeIndex);
		return nbt;
	}

	public void setsize(int size) {
		this.sizeIndex = size;
	}

	public int getSize(){
		return sizeIndex;
	}
}