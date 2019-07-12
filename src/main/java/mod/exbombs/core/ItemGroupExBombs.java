package mod.exbombs.core;

import mod.exbombs.block.BlockCore;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupExBombs extends ItemGroup {
	public ItemGroupExBombs(String label)
	{
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(BlockCore.block_nuclear);
	}
}