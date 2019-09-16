/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.util.SpawnerRadarData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemSpawnerRadar extends Item {

	public SpawnerRadarData data;

	public ItemSpawnerRadar(Item.Properties property) {
		super(property);
	}

	@Override
	public void inventoryTick(ItemStack item, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(entity instanceof PlayerEntity && isSelected){
			PlayerEntity player = (PlayerEntity) entity;
			if(!world.isRemote)
			{
				this.data = getData(item,world);
				this.data.onUpdate(world, player);
				//this.data.markDirty();
			}
		}
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand){
    	if (!worldIn.isRemote) {
//    		NetworkHooks.openGui((ServerPlayerEntity)playerIn,
//    				new InteractionObjectSpawnRadar(),
//    				(buf)->{
//    					buf.writeInt(data.index());
//    				}
//    				);


    		//new ExBombsGuiHelper().disp\layGui(playerIn, new GuiIngameMenu());

    		//new ExBombsGuiHelper().displayGui(playerIn, new GuiSpawnRadar(data.index()));
    		MessageHandler.SendMessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(data.index())});
    		//new ExBombsGuiHelper().displayGuiByID((PlayerEntity) Mod_ExBombs.proxy.getPlayerEntityInstance(), ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(data.index())});
    		//Mod_ExBombs.INSTANCE.sendToServer(new MessageShowGui(ModCommon.MOD_GUI_ID_SPAWNRADAR, new Object[]{new Integer(data.index())}));
		}
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
		return  new ActionResult(ActionResultType.PASS, itemStackIn);
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

	public static final String DATANAME = ModCommon.MOD_ID + ":" + ItemCore.NAME_ITEMRADAR;
	public SpawnerRadarData getData(ItemStack item, World world)
	{
		ServerWorld sWorld = (ServerWorld)world;
		//SpawnerRadarData data = null;//(SpawnerRadarData)world.loadData(SpawnerRadarData.class, itemName);
		SpawnerRadarData data = sWorld.getSavedData().getOrCreate(SpawnerRadarData::new, DATANAME);

		if (data == null)
		{
			data = new SpawnerRadarData(DATANAME);
			data.markDirty();
			sWorld.getSavedData().set(data);
			//world.func_212409_a(world.dimension.getType(), data.getName(), data);
			//world.setData(itemName, data);
		}
		return data;
	}
}
