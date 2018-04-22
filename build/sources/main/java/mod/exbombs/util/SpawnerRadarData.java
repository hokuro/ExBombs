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
		if(world.getWorldTime() % UPDATETIME == 0l){
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
	public void readFromNBT(NBTTagCompound nbt) {
		entityIndex = nbt.getInteger("INDEX");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("INDEX", entityIndex);
		return nbt;
	}
}