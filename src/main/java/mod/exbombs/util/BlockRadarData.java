package mod.exbombs.util;

import mod.exbombs.item.ItemBlockRadar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class BlockRadarData extends WorldSavedData {
	private BlockState targetBlock;
	private int sizeIndex;
	private boolean init = false;
	public boolean upDate;

	private static final int UPDATETIME = 1000;

	public BlockRadarData() {
		this(ItemBlockRadar.DATANAME);
	}

	// コンストラクタ
	public BlockRadarData(String name)
	{
		super(name);
		targetBlock = Blocks.AIR.getDefaultState();
		sizeIndex = 0;
	}

	public void onUpdate(World world, PlayerEntity player)
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

	public void setTarget(BlockState block){
		targetBlock = block;
	}
	public BlockState getTarget() {
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
	public void read(CompoundNBT nbt) {
		String name = nbt.getString("Name");
		int meta = nbt.getInt("Meta");
		if (!name.isEmpty()){
			targetBlock = Block.getStateById(meta);
		}
		sizeIndex = nbt.getInt("size");
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.putString("Name", targetBlock.getBlock().getRegistryName().toString());
		nbt.putInt("Meta", Block.getStateId(targetBlock));
		nbt.putInt("size", sizeIndex);
		return nbt;
	}

	public void setsize(int size) {
		this.sizeIndex = size;
	}

	public int getSize(){
		return sizeIndex;
	}
}
