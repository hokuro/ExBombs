/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.entity.EntityBomb;
import mod.exbombs.util.EnumBombType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemMolotovCoctail extends Item {
	public ItemMolotovCoctail() {
		super(new Item.Properties()
				.group(Mod_ExBombs.tabExBombs));
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	if (!playerIn.isCreative()){
    		itemStackIn.shrink(1);
    	}
    	worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (Item.random.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		EntityBomb bomb = new EntityBomb(worldIn, playerIn, EnumBombType.BOMB);
    		bomb.coctail = true;
    		bomb.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.spawnEntity(bomb);
    	}
    	//playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}
