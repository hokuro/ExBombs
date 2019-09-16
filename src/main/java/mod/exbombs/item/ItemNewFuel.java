package mod.exbombs.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemNewFuel extends Item {

	protected int burnTime;
	public ItemNewFuel(int burn, Item.Properties property) {
		super(property);
		burnTime = burn;
	}

	@Override
    public int getBurnTime(ItemStack itemStack){
        return burnTime;
    }
}
