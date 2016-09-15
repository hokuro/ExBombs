package mod.exbombs.item;

import mod.exbombs.core.ExBombs;
import mod.exbombs.entity.EntityPaintBomb;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemPaintBomb extends Item {

	public ItemPaintBomb() {
		super();
		this.maxStackSize = 64;
		this.setCreativeTab(ExBombs.tabExBombs);
	}

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	ItemStack stack;
    	if ((stack =playerIn.getHeldItem(EnumHand.OFF_HAND)) == null){return new ActionResult(EnumActionResult.PASS, itemStackIn);}
    	if (Block.getBlockFromItem(stack.getItem()) == null){return new ActionResult(EnumActionResult.PASS, itemStackIn);}

    	if (!playerIn.capabilities.isCreativeMode){
    		--itemStackIn.stackSize;
    	}
    	worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.entity_snowball_throw, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

    	if (!worldIn.isRemote){
    		IBlockState state =  stack.getItem().getHasSubtypes()? Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage()):
    			Block.getBlockFromItem(stack.getItem()).getDefaultState();
    		EntityPaintBomb bomb = new EntityPaintBomb(worldIn, playerIn, state);
    		bomb.func_184538_a(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
    		worldIn.spawnEntityInWorld(bomb);
    	}
    	playerIn.addStat(StatList.func_188057_b(this));
        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }
}