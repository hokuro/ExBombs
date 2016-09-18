/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.item.Item;

public class ItemRocketFuel extends Item {
	public ItemRocketFuel() {
		super();
		this.setCreativeTab(Mod_ExBombs.tabExBombs);
	}

	public int getColorFromDamage(int par1, int par2) {
		return 2761250;
	}
}
