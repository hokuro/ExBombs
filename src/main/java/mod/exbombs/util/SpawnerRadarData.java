package mod.exbombs.util;

import mod.exbombs.item.ItemSpawnerRadar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class SpawnerRadarData extends WorldSavedData {
	private int entityIndex;

	private boolean init = false;
	public boolean upDate;
	private static final int UPDATETIME = 1000;

	public SpawnerRadarData() {
		this(ItemSpawnerRadar.DATANAME);
	}

	// コンストラクタ
	public SpawnerRadarData(String name)
	{
		super(name);
		entityIndex = 0;
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

	public void setIndex(int index){
		entityIndex = index;
	}
	public int index() {return entityIndex;}

	@Override
	public void read(CompoundNBT nbt) {
		entityIndex = nbt.getInt("INDEX");
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		nbt.putInt("INDEX", entityIndex);
		return nbt;
	}
}
