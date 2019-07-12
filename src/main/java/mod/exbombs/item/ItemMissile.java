package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.entity.EntityMissile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;

public class ItemMissile extends Item {
	private int type;

	public ItemMissile() {
		super(new Item.Properties()
				.maxStackSize(1)
				.group(Mod_ExBombs.tabExBombs));
	}

	public Item setMissileType(int type) {
		this.type = type;
		return this;
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public EnumActionResult onItemUse(ItemUseContext context)
    {
    	if (!context.getWorld().isRemote){
    		EntityMissile missile = new EntityMissile(context.getWorld(), context.getPos());
    		missile.missileType = this.type;
    		missile.rotationPitch += 90F;
    		context.getWorld().spawnEntity(missile);
    	}
    	if (!context.getPlayer().isCreative()){
    		context.getItem().shrink(1);
    	}
        return EnumActionResult.SUCCESS;
    }
}
