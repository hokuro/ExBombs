package mod.exbombs.block;

import java.util.HashMap;
import java.util.Map;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class BlockCore {
	public static final String NAME_CHUNKERASER = "chunkeraser";
	public static final String NAME_MUCHIBLOCKERASER = "chunkunmacheraser";
	public static final String NAME_FUSE = "fuse";
	public static final String NAME_NUCLEAREX = "nuclearexplosive";
	public static final String NAME_TUNNELEX = "tunnelexplosive";

	public static Block block_chunkeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEALL,Properties.create(Material.TNT).hardnessAndResistance(0.0F, 0.0F).sound(SoundType.PLANT)).setRegistryName(NAME_CHUNKERASER);
	public static Block block_muchblockeraser = new BlockChunkEraserExplosive(EnumEraseType.ERASEUNMATCH,Properties.create(Material.TNT).hardnessAndResistance(0.0F, 0.0F).sound(SoundType.PLANT)).setRegistryName(NAME_MUCHIBLOCKERASER);
	public static Block block_fuse = new BlockFuse().setRegistryName(NAME_FUSE);
	public static Block block_nuclear  = new BlockNuclearExplosive(Properties.create(Material.TNT).hardnessAndResistance(0.0F, 0.0F).sound(SoundType.PLANT)).setRegistryName(NAME_NUCLEAREX);
	public static Block block_tunnel  = new BlockTunnelExplosive(Properties.create(Material.TNT).hardnessAndResistance(0.0F, 0.0F).sound(SoundType.PLANT)).setRegistryName(NAME_TUNNELEX);

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


	public static void init(){
		if (blockMap != null ){return ;}
		blockMap = new HashMap<String,Block>(){
			{put(NAME_CHUNKERASER,block_chunkeraser);}
			{put(NAME_MUCHIBLOCKERASER,block_muchblockeraser);}
			{put(NAME_FUSE,block_fuse);}
			{put(NAME_NUCLEAREX,block_nuclear);}
			{put(NAME_TUNNELEX,block_tunnel);}
		};

		itemMap = new HashMap<String,Item>(){
			{put(NAME_CHUNKERASER,new BlockItem(block_chunkeraser, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_CHUNKERASER));}
			{put(NAME_MUCHIBLOCKERASER,new BlockItem(block_muchblockeraser, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_MUCHIBLOCKERASER));}
			{put(NAME_FUSE,new BlockItem(block_fuse,(new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_FUSE));}
			{put(NAME_NUCLEAREX,new BlockItem(block_nuclear, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_NUCLEAREX));}
			{put(NAME_TUNNELEX,new BlockItem(block_tunnel, (new Item.Properties()).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_TUNNELEX));}
		};
	}

	public static void registerBlock(final RegistryEvent.Register<Block> event){
		for (String name : NAME_LIST){
			if (blockMap.containsKey(name)) {
				event.getRegistry().register(blockMap.get(name));
			}
		}
	}

	public static void registerBlockItem(final RegistryEvent.Register<Item> event){
		for (String name : NAME_LIST){
			if(itemMap.containsKey(name)) {
				event.getRegistry().register(itemMap.get(name));
			}
		}
	}
}
