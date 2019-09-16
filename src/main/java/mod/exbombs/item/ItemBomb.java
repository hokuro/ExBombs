package mod.exbombs.item;

import mod.exbombs.util.EnumBombType;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemBomb extends Item {

	private EnumBombType bombType;

	public ItemBomb(EnumBombType type, Item.Properties property) {
		super(property);
		bombType = type;
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand)
    {
		ThrowableEntity bomb = null;
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
    	if (!worldIn.isRemote){
    		bomb = UtilExproder.createBombEntity(worldIn, playerIn, itemStackIn, bombType);
        	if (bomb == null){return new ActionResult(ActionResultType.PASS, itemStackIn);}
    	}
    	if (!playerIn.isCreative()){
    		itemStackIn.shrink(1);
    	}
    	worldIn.playSound((PlayerEntity)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (this.random.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		bomb.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.addEntity(bomb);
    	}
    	//playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(ActionResultType.SUCCESS, itemStackIn);
    }
}
