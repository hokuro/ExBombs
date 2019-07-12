package mod.exbombs.block;

import java.util.HashMap;
import java.util.Map;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class BlockCore {
	public static final String NAME_CHUNKERASER = "chunkeraser";
	public static final String NAME_MUCHIBLOCKERASER = "chunkunmacheraser";
	public static final String NAME_FUSE = "fuse";
	public static final String NAME_NUCLEAREX = "nuclearexplosive";
	public static final String NAME_TUNNELEX = "tunnelexplosive";

	public static Block block_chunkeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEALL).setRegistryName(NAME_CHUNKERASER);
	public static Block block_muchblockeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEUNMATCH).setRegistryName(NAME_MUCHIBLOCKERASER);
	public static Block block_fuse = new BlockFuse().setRegistryName(NAME_FUSE);
	public static Block block_nuclear  = new BlockNuclearExplosive().setRegistryName(NAME_NUCLEAREX);
	public static Block block_tunnel  = new BlockTunnelExplosive().setRegistryName(NAME_TUNNELEX);

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
		if (blockMap != null ){return ;}

//		block_chunkeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEALL).setRegistryName(NAME_CHUNKERASER);
//		block_muchblockeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEUNMATCH).setRegistryName(NAME_MUCHIBLOCKERASER);
//		block_fuse = new BlockFuse().setRegistryName(NAME_FUSE);
//		block_nuclear = new BlockNuclearExplosive().setRegistryName(NAME_NUCLEAREX);
//		block_tunnel = new BlockTunnelExplosive().setRegistryName(NAME_TUNNELEX);
		blockMap = new HashMap<String,Block>(){
			{put(NAME_CHUNKERASER,block_chunkeraser);}
			{put(NAME_MUCHIBLOCKERASER,block_muchblockeraser);}
			{put(NAME_FUSE,block_fuse);}
			{put(NAME_NUCLEAREX,block_nuclear);}
			{put(NAME_TUNNELEX,block_tunnel);}
		};

		itemMap = new HashMap<String,Item>(){
			{put(NAME_CHUNKERASER,new ItemBlock(block_chunkeraser, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_CHUNKERASER));}
			{put(NAME_MUCHIBLOCKERASER,new ItemBlock(block_muchblockeraser, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_MUCHIBLOCKERASER));}
			{put(NAME_FUSE,new ItemBlock(block_fuse,(new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_FUSE));}
			{put(NAME_NUCLEAREX,new ItemBlock(block_nuclear, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_NUCLEAREX));}
			{put(NAME_TUNNELEX,new ItemBlock(block_tunnel, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_TUNNELEX));}
		};
	}

	public static void registerBlock(final RegistryEvent.Register<Block> event){
		init();
		for (String name : NAME_LIST){
			event.getRegistry().register(blockMap.get(name));
		}
	}

	public static void registerItemBlock(final RegistryEvent.Register<Item> event){
		init();
		for (String name : NAME_LIST){
			event.getRegistry().register(itemMap.get(name));
		}
	}
}
