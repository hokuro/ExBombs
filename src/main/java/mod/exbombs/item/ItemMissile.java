package mod.exbombs.item;

import mod.exbombs.entity.missile.EntityMissile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ItemMissile extends Item {
	private int type;

	public ItemMissile(Item.Properties property) {
		super(property);
	}

	public Item setMissileType(int type) {
		this.type = type;
		return this;
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
	@Override
    public ActionResultType onItemUse(ItemUseContext context)
    {
    	if (!context.getWorld().isRemote){
    		EntityMissile missile = new EntityMissile(context.getWorld(), context.getPos());
    		missile.missileType = this.type;
    		missile.rotationPitch += 90F;
    		context.getWorld().addEntity(missile);
    	}
    	if (!context.getPlayer().isCreative()){
    		context.getItem().shrink(1);
    	}
        return ActionResultType.SUCCESS;
    }
}
