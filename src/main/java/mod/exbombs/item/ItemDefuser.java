package mod.exbombs.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ItemDefuser extends Item {
	public ItemDefuser(Item.Properties property) {
		super(property);
	}


	public static void defuserUse(ItemStack stack, PlayerEntity player, Hand hand){
		stack.damageItem(1, player, (p_220287_1_) -> {
            p_220287_1_.sendBreakAnimation(hand);
         });
	}
}
