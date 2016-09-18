package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.entity.EntityMissile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMissile extends Item {
	private int type;

	public ItemMissile() {
		super();
		this.maxStackSize = 1;
		this.setCreativeTab(Mod_ExBombs.tabExBombs);
	}

	public Item setMissileType(int type) {
		this.type = type;
		return this;
	}

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if (!worldIn.isRemote){
    		EntityMissile missile = new EntityMissile(worldIn, pos);
    		missile.missileType = this.type;
    		missile.rotationPitch += 90F;
    		worldIn.spawnEntityInWorld(missile);
    	}
    	if (!playerIn.capabilities.isCreativeMode){
    		stack.stackSize -= 1;
    	}
        return EnumActionResult.SUCCESS;
    }
}
