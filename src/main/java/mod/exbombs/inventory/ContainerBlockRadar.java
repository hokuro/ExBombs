package mod.exbombs.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;

public class ContainerBlockRadar extends Container{

	private String blockID;
	private int areaSize;

	public ContainerBlockRadar(ContainerType<?> type, int id) {
		super(type, id);
	}

	public  ContainerBlockRadar(ContainerType<?> type, int id, String bid, int size) {
		this(type,id);
		blockID = bid;
		areaSize = size;
	}

	public void setBlockID(String id) {
		blockID = id;
	}

	public void setAreaSize(int size) {
		areaSize = size;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	public ResourceLocation getBlockID() {
		return new ResourceLocation(blockID);
	}

	public int getSize() {
		// TODO 自動生成されたメソッド・スタブ
		return areaSize;
	}
}
