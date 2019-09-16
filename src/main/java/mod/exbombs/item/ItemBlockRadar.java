/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.util.BlockRadarData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ItemBlockRadar extends Item {

	public BlockRadarData data;

	public ItemBlockRadar(Item.Properties property) {
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
				this.data.markDirty();
			}
		}
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand){
    	if (!worldIn.isRemote){
    		if (playerIn.isSneaking()){
        		ItemStack target = playerIn.getHeldItem(Hand.OFF_HAND);
    	 		if (!target.isEmpty() &&  Block.getBlockFromItem(target.getItem()) != Blocks.AIR){
    	 			 setRadarTarget(new ItemStack(this),Minecraft.getInstance().world,Block.getBlockFromItem(target.getItem()).getDefaultState());
    	 		}
    		}else{
        		if (data.getTarget() != Blocks.AIR.getDefaultState()){
    			MessageHandler.SendMessageShowGui(ModCommon.MOD_GUI_ID_BLOCKRADER,
    					new Object[]{
    							data.getTargetName(),
    							new Integer(data.getTargetStateId()),
    							new Integer(data.getSize())
    							});
        		}
    		}
    	}
//
//    		if (playerIn.isSneaking()){
//                if (Minecraft.getInstance().objectMouseOver != null &&
//                		Minecraft.getInstance().objectMouseOver.type == RayTraceResult.Type.BLOCK &&
//                				Minecraft.getInstance().objectMouseOver.getBlockPos() != null)
//                {
//                    BlockPos blockpos1 = Minecraft.getInstance().objectMouseOver.getBlockPos();
//                    BlockState block = Minecraft.getInstance().world.getBlockState(blockpos1);
//                    setRadarTarget(new ItemStack(this),Minecraft.getInstance().world,block);
//
//                }
//    		}else{
//        		if (data.getTarget() != Blocks.AIR){
//        			MessageHandler.SendMessageShowGui(ModCommon.MOD_GUI_ID_BLOCKRADER,
//        					new Object[]{
//        							data.getTargetName(),
//        							new Integer(data.getTargetMeta()),
//        							new Integer(data.getSize())
//        							});
//        		}
//    		}
//		}
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
		return  new ActionResult(ActionResultType.SUCCESS, itemStackIn);
	}



	public static BlockRadarData getRadarData(ItemStack item, World world)
	{
		BlockRadarData data = null;
		if(item != null && item.getItem() instanceof ItemBlockRadar)
		{
			data = ((ItemBlockRadar)item.getItem()).getData(item, world);
		}
		return data;
	}

	private void setRadarTarget(ItemStack item, ClientWorld world, BlockState block) {
		ItemBlockRadar radar;
		if (item != null && item.getItem() instanceof ItemBlockRadar){
			radar = (ItemBlockRadar)item.getItem();
			radar.data.setTarget(block);
			radar.data.onUpdate(world, ExBombsMinecraftHelper.getPlayer());
			radar.data.markDirty();
		}
	}

	public static void setRadarSize(ItemStack item, World world, int size){
		ItemBlockRadar radar;
		if (item != null && item.getItem() instanceof ItemBlockRadar){
			radar = (ItemBlockRadar)item.getItem();
			radar.data.setsize(size);
			radar.data.onUpdate(world, ExBombsMinecraftHelper.getPlayer());
			radar.data.markDirty();
		}
	}


	public static final String DATANAME = ModCommon.MOD_ID + ":" + ItemCore.NAME_ITEMBLOCKRADAR;
	public BlockRadarData getData(ItemStack item, World world)
	{
		ServerWorld sWorld = (ServerWorld)world;
		//SpawnerRadarData data = null;//(SpawnerRadarData)world.loadData(SpawnerRadarData.class, itemName);
		BlockRadarData data = sWorld.getSavedData().getOrCreate(BlockRadarData::new, DATANAME);

		if (data == null)
		{
			data = new BlockRadarData(DATANAME);
			data.markDirty();
			sWorld.getSavedData().set(data);
			//world.func_212409_a(world.dimension.getType(), data.getName(), data);
			//world.setData(itemName, data);
		}
		return data;
	}
}
