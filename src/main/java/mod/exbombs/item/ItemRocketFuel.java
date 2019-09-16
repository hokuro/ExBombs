/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import net.minecraft.item.Item;

public class ItemRocketFuel extends ItemNewFuel {
	public ItemRocketFuel(int burnTime, Item.Properties property) {
		super(burnTime,property);
	}

	public int getColorFromDamage(int par1, int par2) {
		return 2761250;
	}
}
