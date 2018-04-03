package mod.exbombs.item;

import java.util.HashMap;
import java.util.Map;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemCore {
	public static final String NAME_ITEMOIL = "oil";
	public static final String NAME_PLASTIC = "plastic";
	public static final String NAME_HEAVYMATTER = "heavymatter";
	public static final String NAME_ITEMURANIUM = "uranium";
	public static final String NAME_ITEMROCKETFUEL = "rocketfuel";
	public static final String NAME_ITEMBOMB = "bomb";
	public static final String NAME_ITEMWATERBOMB = "waterbomb";
	public static final String NAME_ITEMTNTMISSILE = "tntmissile";
	public static final String NAME_ITEMNCMISSILE = "nuclearemissile";
	public static final String NAME_ITEMRADAR = "radar";
	public static final String NAME_ITEMMC = "molotovcoctail";
	public static final String NAME_ITEMDEFUSER ="defuser";
	public static final String NAME_ITEMCEMISSILE = "chunkerasermissile";
	public static final String NAME_ITEMMCEMISSILE = "chunkunmatcherasermissile";
	public static final String NAME_ITEMPAINTBOMB = "paintbomb";
	public static final String NAME_ITEMICICLEBOMB = "iciclebomb";
	public static final String NAME_ITEMFROZENBOMB = "frozenbomb";
	public static final String NAME_ITEMLAVABOMB = "lavabomb";
	public static final String NAME_ITEMBLOCKRADAR = "blockradar";

	public static final String[] NAME_LIST = new String[]{
			NAME_ITEMOIL,
			NAME_PLASTIC,
			NAME_HEAVYMATTER,
			NAME_ITEMURANIUM,
			NAME_ITEMROCKETFUEL,
			NAME_ITEMTNTMISSILE,
			NAME_ITEMNCMISSILE,
			NAME_ITEMCEMISSILE,
			NAME_ITEMMCEMISSILE,
			NAME_ITEMRADAR,
			NAME_ITEMBLOCKRADAR,
			NAME_ITEMDEFUSER,
			NAME_ITEMBOMB,
			NAME_ITEMWATERBOMB,
			NAME_ITEMLAVABOMB,
			NAME_ITEMICICLEBOMB,
			NAME_ITEMFROZENBOMB,
			NAME_ITEMPAINTBOMB,
			NAME_ITEMMC
	};

	// 素材
	public static Item item_Oil;
	public static Item item_Plastic;
	public static Item item_HeavyMatter;
	public static Item item_Uranium;
	public static Item item_RocketFuel;

	// 手榴弾
	public static Item item_Bomb;
	public static Item item_WaterBomb;
	public static Item item_paintBomb;
	public static Item item_frozenBomb;
	public static Item item_icicleBomb;
	public static Item item_MC;
	public static Item item_lavaBomb;

	// ミサイル
	public static Item item_TntMissile;
	public static Item item_NCMissile;
	public static Item item_CEMissile;
	public static Item item_MCEMissile;

	// ツール
	public static Item item_Radar;
	public static Item item_defuser;
	public static Item item_blockRadar;


	private static Map<String,Item> itemMap;
	private static Map<String,ModelResourceLocation[]> resourceMap;

	private static void init(){
		// 素材
		item_Oil = new Item()
				.setRegistryName(NAME_ITEMOIL)
				.setUnlocalizedName(NAME_ITEMOIL).setCreativeTab(Mod_ExBombs.tabExBombs);
		item_Plastic = new Item()
				.setRegistryName(NAME_PLASTIC)
				.setUnlocalizedName(NAME_PLASTIC).setCreativeTab(Mod_ExBombs.tabExBombs);
		item_HeavyMatter = new Item()
				.setRegistryName(NAME_HEAVYMATTER)
				.setUnlocalizedName(NAME_HEAVYMATTER).setCreativeTab(Mod_ExBombs.tabExBombs);
		item_Uranium = new ItemUranium()
				.setRegistryName(NAME_ITEMURANIUM)
				.setUnlocalizedName(NAME_ITEMURANIUM);
		item_RocketFuel = new ItemRocketFuel()
				.setRegistryName(NAME_ITEMROCKETFUEL)
				.setUnlocalizedName(NAME_ITEMROCKETFUEL);

		// 手榴弾
		item_Bomb = new ItemBomb(EnumBombType.BOMB)
				.setRegistryName(NAME_ITEMBOMB)
				.setUnlocalizedName(NAME_ITEMBOMB);
		item_WaterBomb = new ItemBomb(EnumBombType.WARTER)
				.setRegistryName(NAME_ITEMWATERBOMB)
				.setContainerItem(Items.BUCKET)
				.setUnlocalizedName(NAME_ITEMWATERBOMB);
		item_paintBomb = new ItemBomb(EnumBombType.PAINT)
				.setRegistryName(NAME_ITEMPAINTBOMB)
				.setUnlocalizedName(NAME_ITEMPAINTBOMB);
		item_frozenBomb = new ItemBomb(EnumBombType.FROZEN)
				.setRegistryName(NAME_ITEMFROZENBOMB)
				.setUnlocalizedName(NAME_ITEMFROZENBOMB);
		item_icicleBomb = new ItemBomb(EnumBombType.ICICLE)
				.setRegistryName(NAME_ITEMICICLEBOMB)
				.setUnlocalizedName(NAME_ITEMICICLEBOMB);
		item_MC = new ItemMolotovCoctail()
				.setRegistryName(NAME_ITEMMC)
				.setUnlocalizedName(NAME_ITEMMC);
		item_lavaBomb = new ItemBomb(EnumBombType.LAVA)
				.setRegistryName(NAME_ITEMLAVABOMB)
				.setUnlocalizedName(NAME_ITEMLAVABOMB);

		// ミサイル
		item_TntMissile = new ItemMissile()
				.setMissileType(0)
				.setRegistryName(NAME_ITEMTNTMISSILE)
				.setUnlocalizedName(NAME_ITEMTNTMISSILE);
		item_NCMissile = new ItemMissile()
				.setMissileType(1)
				.setRegistryName(NAME_ITEMNCMISSILE)
				.setUnlocalizedName(NAME_ITEMNCMISSILE);
		item_CEMissile = new ItemMissile()
				.setMissileType(2)
				.setRegistryName(NAME_ITEMCEMISSILE)
				.setUnlocalizedName(NAME_ITEMCEMISSILE);
		item_MCEMissile = new ItemMissile()
				.setMissileType(3)
				.setRegistryName(NAME_ITEMMCEMISSILE)
				.setUnlocalizedName(NAME_ITEMMCEMISSILE);

		// ツール
		item_Radar = new  ItemSpawnerRadar()
				.setRegistryName(NAME_ITEMRADAR)
				.setUnlocalizedName(NAME_ITEMRADAR);
		item_defuser = new ItemDefuser()
				.setRegistryName(NAME_ITEMDEFUSER)
				.setUnlocalizedName(NAME_ITEMDEFUSER);
		item_blockRadar = new ItemBlockRadar()
				.setRegistryName(NAME_ITEMBLOCKRADAR)
				.setUnlocalizedName(NAME_ITEMBLOCKRADAR);

		itemMap = new HashMap<String,Item>(){
			{put(NAME_ITEMOIL,item_Oil);}
			{put(NAME_PLASTIC,item_Plastic);}
			{put(NAME_HEAVYMATTER,item_HeavyMatter);}
			{put(NAME_ITEMURANIUM,item_Uranium);}
			{put(NAME_ITEMROCKETFUEL,item_RocketFuel);}
			{put(NAME_ITEMBOMB,item_Bomb);}
			{put(NAME_ITEMWATERBOMB,item_WaterBomb);}
			{put(NAME_ITEMTNTMISSILE,item_TntMissile);}
			{put(NAME_ITEMNCMISSILE,item_NCMissile);}
			{put(NAME_ITEMRADAR,item_Radar);}
			{put(NAME_ITEMMC,item_MC);}
			{put(NAME_ITEMDEFUSER,item_defuser);}
			{put(NAME_ITEMCEMISSILE,item_CEMissile);}
			{put(NAME_ITEMMCEMISSILE,item_MCEMissile);}
			{put(NAME_ITEMPAINTBOMB,item_paintBomb);}
			{put(NAME_ITEMICICLEBOMB,item_icicleBomb);}
			{put(NAME_ITEMFROZENBOMB,item_frozenBomb);}
			{put(NAME_ITEMLAVABOMB,item_lavaBomb);}
			{put(NAME_ITEMBLOCKRADAR,item_blockRadar);}
		};

		resourceMap = new HashMap<String,ModelResourceLocation[]>(){
			{put(NAME_ITEMOIL,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMOIL, "inventory")});}
			{put(NAME_PLASTIC,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_PLASTIC, "inventory")});}
			{put(NAME_HEAVYMATTER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_HEAVYMATTER, "inventory")});}
			{put(NAME_ITEMURANIUM,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMURANIUM, "inventory")});}
			{put(NAME_ITEMROCKETFUEL,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMROCKETFUEL, "inventory")});}
			{put(NAME_ITEMBOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMBOMB, "inventory")});}
			{put(NAME_ITEMWATERBOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMWATERBOMB, "inventory")});}
			{put(NAME_ITEMTNTMISSILE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMTNTMISSILE, "inventory")});}
			{put(NAME_ITEMNCMISSILE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMNCMISSILE, "inventory")});}
			{put(NAME_ITEMRADAR,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMRADAR, "inventory")});}
			{put(NAME_ITEMMC,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMC, "inventory")});}
			{put(NAME_ITEMDEFUSER,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMDEFUSER, "inventory")});}
			{put(NAME_ITEMCEMISSILE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMCEMISSILE, "inventory")});}
			{put(NAME_ITEMMCEMISSILE,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMCEMISSILE, "inventory")});}
			{put(NAME_ITEMPAINTBOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMPAINTBOMB, "inventory")});}
			{put(NAME_ITEMFROZENBOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMFROZENBOMB, "inventory")});}
			{put(NAME_ITEMICICLEBOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMICICLEBOMB, "inventory")});}
			{put(NAME_ITEMLAVABOMB,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMLAVABOMB, "inventory")});}
			{put(NAME_ITEMBLOCKRADAR,new ModelResourceLocation[]{new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMRADAR, "inventory")});}
		};
	}

	public static void register(FMLPreInitializationEvent event) {
		init();
		for (String key : NAME_LIST){
			ForgeRegistries.ITEMS.register(itemMap.get(key));
		}

        //テクスチャ・モデル指定JSONファイル名の登録。
        if (event.getSide().isClient()) {
        	for (String key : NAME_LIST){
        		//1IDで複数モデルを登録するなら、上のメソッドで登録した登録名を指定する。
        		int cnt = 0;
        		for (ModelResourceLocation rc : resourceMap.get(key)){
        			ModelLoader.setCustomModelResourceLocation(itemMap.get(key), cnt, rc);
        			cnt++;
        		}
        	}
        }
	}


}
