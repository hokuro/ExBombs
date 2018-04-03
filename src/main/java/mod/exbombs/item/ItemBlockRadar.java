/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.util.BlockRadarData;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemBlockRadar extends Item {

	public BlockRadarData data;

	public ItemBlockRadar() {
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
    	if (!worldIn.isRemote){
    		if (playerIn.isSneaking()){
                if (Minecraft.getMinecraft().objectMouseOver != null &&
                		Minecraft.getMinecraft().objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK &&
                				Minecraft.getMinecraft().objectMouseOver.getBlockPos() != null)
                {
                    BlockPos blockpos1 = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
                    IBlockState block = Minecraft.getMinecraft().world.getBlockState(blockpos1);
                    setRadarTarget(new ItemStack(this),Minecraft.getMinecraft().world,block);

                }
    		}else{
        		if (data.getTarget() != Blocks.AIR){
        			Mod_ExBombs.INSTANCE.sendToServer(new MessageShowGui(ModCommon.MOD_GUI_ID_BLOCKRADER,
        					new Object[]{
        							data.getTargetName(),
        							new Integer(data.getTargetMeta()),
        							new Integer(data.getSize())
        							}));
        		}
    		}
		}
    	ItemStack itemStackIn = playerIn.getHeldItem(hand);
		return  new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
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

	private void setRadarTarget(ItemStack item, WorldClient world, IBlockState block) {
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

	public BlockRadarData getData(ItemStack item, World world)
	{
		String itemName = this.getRegistryName().toString();
		BlockRadarData data = (BlockRadarData)world.loadData(BlockRadarData.class, itemName);

		if (data == null)
		{
			data = new BlockRadarData(itemName);
			data.markDirty();
			world.setData(itemName, data);
		}

		return data;
	}
}
