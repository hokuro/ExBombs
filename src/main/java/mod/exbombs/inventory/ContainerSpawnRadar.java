package mod.exbombs.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public class ContainerSpawnRadar extends Container{

	private int index;

	public ContainerSpawnRadar(ContainerType<?> type, int id) {
		super(type, id);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public ContainerSpawnRadar(ContainerType<?> type, int id, int idx) {
		this(type, id);
		index = idx;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	public void setIndex(int idx) {
		index = idx;
	}

	public int getIndex() {
		return index;
	}
}
