package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDefuser extends Item {
	public ItemDefuser() {
		super();
		setMaxStackSize(1);
		setMaxDamage(255);
		setCreativeTab(Mod_ExBombs.tabExBombs);
	}


	public static void defuserUse(ItemStack stack, EntityPlayer player){
		stack.damageItem(1, player);
	}
}
