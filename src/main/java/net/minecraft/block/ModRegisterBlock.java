package net.minecraft.block;

import mod.exbombs.block.BlockChunkEraserExplosive;
import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.block.BlockFuse;
import mod.exbombs.block.BlockNuclearExplosive;
import mod.exbombs.block.BlockTunnelExplosive;
import mod.exbombs.core.ModCommon;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRegisterBlock {
	// 名前
	public static final String NAME_BLOCKFUSE = "fuse";
	public static final String NAME_BLOCKNCBOMB = "nuclearexplosive";
	public static final String NAME_BLOCKTUNNELBOMB = "tunnelexplosive";
	public static final String NAME_BLOCKCHUNKERASER ="chunkeraser";
	public static final String NAME_BLOCKUNMACHCUNKERASER = "chunkunmacheraser";

	// ブロック
	public static final Block block_Fuse = new BlockFuse()
			.setHardness(0.0F)
			.setSoundType(SoundType.PLANT)
			.setUnlocalizedName(NAME_BLOCKFUSE);
	public static final Block block_NCBomb = new BlockNuclearExplosive()
			.setHardness(0.0F)
			.setSoundType(SoundType.PLANT)
			.setUnlocalizedName(NAME_BLOCKNCBOMB);
	public static final Block bolock_TunnelBomb = new BlockTunnelExplosive()
			.setHardness(0.0F)
			.setSoundType(SoundType.PLANT)
			.setUnlocalizedName(NAME_BLOCKTUNNELBOMB);
	public static final Block block_eraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEALL)
			.setHardness(0.0F)
			.setSoundType(SoundType.PLANT)
			.setUnlocalizedName(NAME_BLOCKCHUNKERASER);
	public static final Block block_unmach = new BlockChunkEraserExplosive(EnumEraseType.ERASEUNMATCH)
			.setHardness(0.0F)
			.setSoundType(SoundType.PLANT)
			.setUnlocalizedName(NAME_BLOCKUNMACHCUNKERASER);

	public static void registerBlock(FMLPreInitializationEvent event){
		GameRegistry.register(block_Fuse, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKFUSE));
		GameRegistry.register(new ItemBlock(block_Fuse),new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKFUSE));

		GameRegistry.register(block_NCBomb,new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKNCBOMB));
		GameRegistry.register(new ItemBlock(block_NCBomb),new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKNCBOMB));

		GameRegistry.register(bolock_TunnelBomb,new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKTUNNELBOMB));
		GameRegistry.register(new ItemBlock(bolock_TunnelBomb),new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKTUNNELBOMB));

		GameRegistry.register(block_eraser,new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKCHUNKERASER));
		GameRegistry.register(new ItemBlock(block_eraser),new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKCHUNKERASER));

		GameRegistry.register(block_unmach,new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKUNMACHCUNKERASER));
		GameRegistry.register(new ItemBlock(block_unmach),new ResourceLocation(ModCommon.MOD_ID+":"+NAME_BLOCKUNMACHCUNKERASER));

		if (event.getSide().isClient()){
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_Fuse), 0,
					new ModelResourceLocation(new ResourceLocation(ModCommon.MOD_ID + ":"+NAME_BLOCKFUSE),"inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_NCBomb), 0,
					new ModelResourceLocation(new ResourceLocation(ModCommon.MOD_ID + ":"+NAME_BLOCKNCBOMB),"inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(bolock_TunnelBomb), 0,
					new ModelResourceLocation(new ResourceLocation(ModCommon.MOD_ID + ":"+NAME_BLOCKTUNNELBOMB),"inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_eraser), 0,
					new ModelResourceLocation(new ResourceLocation(ModCommon.MOD_ID + ":"+NAME_BLOCKCHUNKERASER),"inventory"));

			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block_unmach), 0,
					new ModelResourceLocation(new ResourceLocation(ModCommon.MOD_ID + ":"+NAME_BLOCKUNMACHCUNKERASER),"inventory"));
		}
	}
}
