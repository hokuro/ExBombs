package mod.exbombs.inventory;

import mod.exbombs.entity.missile.EntityMissile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

public class ContainerMissile extends Container{
	private EntityMissile missile;
	public ContainerMissile(ContainerType<?> type, int id) {
		super(type, id);
	}

	public ContainerMissile(ContainerType<ContainerMissile> type, int id, EntityMissile ent) {
		this(type, id);
		missile = ent;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	public EntityMissile getMissile() {
		return missile;
	}

}
