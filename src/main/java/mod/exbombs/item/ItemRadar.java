/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.util.RadarData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRadar extends Item {

	public RadarData data;

	public ItemRadar() {
		super();
		this.maxStackSize = 1;
		this.setCreativeTab(Mod_ExBombs.tabExBombs);
	}

	public void onUpdate(ItemStack item, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(entity instanceof EntityPlayer && isSelected){
			EntityPlayer player = (EntityPlayer) entity;
			if(!world.isRemote)
			{
				this.data = getData(item,world);
				this.data.onUpdate(world, player);
				this.data.markDirty();
			}
		}
	}

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand){
    	if (!worldIn.isRemote) {
    		Mod_ExBombs.INSTANCE.sendToServer(new MessageShowGui(ModCommon.MOD_GUI_ID_RADAR, new Object[]{new Integer(data.index())}));
		}
		return  new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	public static RadarData getRadarData(ItemStack item, World world)
	{
		RadarData data = null;
		if(item != null && item.getItem() instanceof ItemRadar)
		{
			data = ((ItemRadar)item.getItem()).getData(item, world);
		}
		return data;
	}

	public static void setRadarData(ItemStack item, World world, int index){
		ItemRadar radar;
		if (item != null && item.getItem() instanceof ItemRadar){
			radar = (ItemRadar)item.getItem();
			radar.data.setIndex(index);
			radar.data.onUpdate(world, ExBombsMinecraftHelper.getPlayer());
			radar.data.markDirty();
		}
	}

	public RadarData getData(ItemStack item, World world)
	{
		String itemName = this.getRegistryName().toString();
		RadarData data = (RadarData)world.loadItemData(RadarData.class, itemName);

		if (data == null)
		{
			data = new RadarData(itemName);
			data.markDirty();
			world.setItemData(itemName, data);
		}

		return data;
	}
}
