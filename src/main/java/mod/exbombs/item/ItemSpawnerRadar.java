/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.util.SpawnerRadarData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSpawnerRadar extends Item {

	public SpawnerRadarData data;

	public ItemSpawnerRadar() {
		super();
		this.maxStackSize = 1;
		this.setCreativeTab(Mod_ExBombs.tabExBombs);
	}

	@Override
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

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand){
    	if (!worldIn.isRemote) {
    		Mod_ExBombs.INSTANCE.sendToServer(new MessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(data.index())}));
		}
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
		return  new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	public static SpawnerRadarData getRadarData(ItemStack item, World world)
	{
		SpawnerRadarData data = null;
		if(item != null && item.getItem() instanceof ItemSpawnerRadar)
		{
			data = ((ItemSpawnerRadar)item.getItem()).getData(item, world);
		}
		return data;
	}

	public static void setRadarData(ItemStack item, World world, int index){
		ItemSpawnerRadar radar;
		if (item != null && item.getItem() instanceof ItemSpawnerRadar){
			radar = (ItemSpawnerRadar)item.getItem();
			radar.data.setIndex(index);
			radar.data.onUpdate(world, ExBombsMinecraftHelper.getPlayer());
			radar.data.markDirty();
		}
	}

	public SpawnerRadarData getData(ItemStack item, World world)
	{
		String itemName = this.getRegistryName().toString();
		SpawnerRadarData data = (SpawnerRadarData)world.loadData(SpawnerRadarData.class, itemName);

		if (data == null)
		{
			data = new SpawnerRadarData(itemName);
			data.markDirty();
			world.setData(itemName, data);
		}

		return data;
	}
}
