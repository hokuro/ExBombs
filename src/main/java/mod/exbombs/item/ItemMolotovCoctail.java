/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.entity.EntityCore;
import mod.exbombs.entity.bomb.EntityBomb;
import mod.exbombs.util.EnumBombType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemMolotovCoctail extends Item {
	public ItemMolotovCoctail(Item.Properties property) {
		super(property);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand)
    {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	if (!playerIn.isCreative()){
    		itemStackIn.shrink(1);
    	}
    	worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (Item.random.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		EntityBomb bomb = new EntityBomb(EntityCore.Inst().BOMB, worldIn, playerIn, EnumBombType.BOMB);
    		bomb.coctail = true;
    		bomb.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.addEntity(bomb);
    	}
    	//playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(ActionResultType.SUCCESS, itemStackIn);
    }
}
