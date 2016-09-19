package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemBomb extends Item {

	private EnumBombType bombType;

	public ItemBomb(EnumBombType type) {
		super();
		this.maxStackSize = 64;
		this.setCreativeTab(Mod_ExBombs.tabExBombs);
		bombType = type;
	}

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	EntityThrowable bomb = null;
    	if (!worldIn.isRemote){
    		bomb = UtilExproder.createBombEntity(worldIn, playerIn, itemStackIn, bombType);
        	if (bomb == null){return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);}
    	}
    	if (!playerIn.capabilities.isCreativeMode){
    		--itemStackIn.stackSize;
    	}
    	worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		bomb.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.spawnEntityInWorld(bomb);
    	}
    	playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }
}
