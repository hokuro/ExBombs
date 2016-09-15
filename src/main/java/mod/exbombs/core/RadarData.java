package mod.exbombs.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class RadarData extends WorldSavedData {

	private boolean viewMode;
	private int zoom;
	private int entityIndex;

	private boolean init = false;
	public boolean upDate;
	private static final int UPDATETIME = 1000;

	// コンストラクタ
	public RadarData(String name)
	{
		super(name);
		viewMode = false;
		zoom = 0;
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

	public void setData(RadarData dat){
		viewMode = dat.viewMode();
		zoom = dat.zoom();
		entityIndex = dat.index();
	}
	public void setData(boolean mode, int zm, int index){
		viewMode = mode;
		zoom = zm;
		entityIndex = index;
	}

	public boolean viewMode(){return viewMode;}
	public int zoom(){return zoom;}
	public int index() {return entityIndex;}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		viewMode = nbt.getBoolean("VIEWMODE");
		zoom = nbt.getInteger("ZOOM");
		entityIndex = nbt.getInteger("INDEX");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("VIEWMODE", viewMode);
		nbt.setInteger("ZOOM", zoom);
		nbt.setInteger("INDEX", entityIndex);
	}
}