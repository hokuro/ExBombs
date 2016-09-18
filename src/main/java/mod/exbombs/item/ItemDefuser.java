package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDefuser extends Item {
	public ItemDefuser() {
		super();
		setMaxStackSize(1);
		setMaxDamage(255);
		setCreativeTab(Mod_ExBombs.tabExBombs);
	}

	public static void onItemUsed(ItemStack itemstack, EntityLivingBase entityliving) {
		itemstack.damageItem(1, entityliving);
	}
}
