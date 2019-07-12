package mod.exbombs.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class SpawnerRadarData extends WorldSavedData {
	private int entityIndex;

	private boolean init = false;
	public boolean upDate;
	private static final int UPDATETIME = 1000;

	// コンストラクタ
	public SpawnerRadarData(String name)
	{
		super(name);
		entityIndex = 0;
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

	public void setIndex(int index){
		entityIndex = index;
	}
	public int index() {return entityIndex;}

	@Override
	public void read(NBTTagCompound nbt) {
		entityIndex = nbt.getInt("INDEX");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbt) {
		nbt.setInt("INDEX", entityIndex);
		return nbt;
	}
}