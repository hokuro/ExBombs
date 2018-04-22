package mod.exbombs.block;

import java.util.HashMap;
import java.util.Map;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.core.ModCommon;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockCore {
	public static final String NAME_CHUNKERASER = "chunkeraser";
	public static final String NAME_MUCHIBLOCKERASER = "chunkunmacheraser";
	public static final String NAME_FUSE = "fuse";
	public static final String NAME_NUCLEAREX = "nuclearexplosive";
	public static final String NAME_TUNNELEX = "tunnelexplosive";

	public static Block block_chunkeraser;
	public static Block block_muchblockeraser;
	public static Block block_fuse;
	public static Block block_nuclear;
	public static Block block_tunnel;

	public static final String[] NAME_LIST = {
			NAME_NUCLEAREX,
			NAME_CHUNKERASER,
			NAME_MUCHIBLOCKERASER,
			NAME_TUNNELEX,
			NAME_FUSE
	};

	private static Map<String,Block> blockMap;
	private static Map<String,Item> itemMap;
	private static Map<String,ResourceLocation[]> resourceMap;


	protected static void init(){
		block_chunkeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEALL).setRegistryName(NAME_CHUNKERASER).setUnlocalizedName(NAME_CHUNKERASER);
		block_muchblockeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEUNMATCH).setRegistryName(NAME_MUCHIBLOCKERASER).setUnlocalizedName(NAME_MUCHIBLOCKERASER);
		block_fuse = new BlockFuse().setRegistryName(NAME_FUSE).setUnlocalizedName(NAME_FUSE);
		block_nuclear = new BlockNuclearExplosive().setRegistryName(NAME_NUCLEAREX).setUnlocalizedName(NAME_NUCLEAREX);
		block_tunnel = new BlockTunnelExplosive().setRegistryName(NAME_TUNNELEX).setUnlocalizedName(NAME_TUNNELEX);
		blockMap = new HashMap<String,Block>(){
			{put(NAME_CHUNKERASER,block_chunkeraser);}
			{put(NAME_MUCHIBLOCKERASER,block_muchblockeraser);}
			{put(NAME_FUSE,block_fuse);}
			{put(NAME_NUCLEAREX,block_nuclear);}
			{put(NAME_TUNNELEX,block_tunnel);}
		};

		itemMap = new HashMap<String,Item>(){
			{put(NAME_CHUNKERASER,new ItemBlock(block_chunkeraser).setRegistryName(NAME_CHUNKERASER));}
			{put(NAME_MUCHIBLOCKERASER,new ItemBlock(block_muchblockeraser).setRegistryName(NAME_MUCHIBLOCKERASER));}
			{put(NAME_FUSE,new ItemBlock(block_fuse).setRegistryName(NAME_FUSE));}
			{put(NAME_NUCLEAREX,new ItemBlock(block_nuclear).setRegistryName(NAME_NUCLEAREX));}
			{put(NAME_TUNNELEX,new ItemBlock(block_tunnel).setRegistryName(NAME_TUNNELEX));}
		};

		resourceMap = new HashMap<String,ResourceLocation[]>(){
			{put(NAME_CHUNKERASER,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_CHUNKERASER)});}
			{put(NAME_MUCHIBLOCKERASER,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_MUCHIBLOCKERASER)});}
			{put(NAME_FUSE,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_FUSE)});}
			{put(NAME_NUCLEAREX,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_NUCLEAREX)});}
			{put(NAME_TUNNELEX,new ResourceLocation[]{new ResourceLocation(ModCommon.MOD_ID + ":" + NAME_TUNNELEX)});}
		};

	}

	public static void register(FMLPreInitializationEvent event){
		init();
		for (String key: NAME_LIST){
			ForgeRegistries.BLOCKS.register(blockMap.get(key));
			ForgeRegistries.ITEMS.register(itemMap.get(key));
		}

		if (event.getSide().isClient()){
			for (String key : NAME_LIST){
				Item witem = itemMap.get(key);
				ResourceLocation[] wresource = resourceMap.get(key);
				if (wresource.length > 1){
					ModelLoader.registerItemVariants(witem, wresource);
				}
				for (int i = 0; i < wresource.length; i++){
					ModelLoader.setCustomModelResourceLocation(witem, i,
							new ModelResourceLocation(wresource[i], "inventory"));
				}
			}
		}
	}

}
