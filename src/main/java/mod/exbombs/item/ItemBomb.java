package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.util.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBomb extends Item {

	private EnumBombType bombType;

	public ItemBomb(EnumBombType type) {
		super(new Item.Properties().group(Mod_ExBombs.tabExBombs));
		bombType = type;
	}

	public ItemBomb(EnumBombType type, Item container) {
		super(new Item.Properties().group(Mod_ExBombs.tabExBombs).containerItem(container));
		bombType = type;
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	EntityThrowable bomb = null;
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	if (!worldIn.isRemote){
    		bomb = UtilExproder.createBombEntity(worldIn, playerIn, itemStackIn, bombType);
        	if (bomb == null){return new ActionResult(EnumActionResult.PASS, itemStackIn);}
    	}
    	if (!playerIn.isCreative()){
    		itemStackIn.shrink(1);
    	}
    	worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		bomb.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.spawnEntity(bomb);
    	}
    	//playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}
