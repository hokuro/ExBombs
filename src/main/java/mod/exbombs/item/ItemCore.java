package mod.exbombs.item;

import java.util.HashMap;
import java.util.Map;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.util.EnumBombType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent;

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
	public static Item item_Oil;// = new Item(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMOIL);
	public static Item item_Plastic;// = new Item(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_PLASTIC);
	public static Item item_HeavyMatter;// = new Item(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_HEAVYMATTER);;
	public static Item item_Uranium;// = new ItemUranium().setRegistryName(NAME_ITEMURANIUM);
	public static Item item_RocketFuel;// = new ItemRocketFuel().setRegistryName(NAME_ITEMROCKETFUEL);

	// 手榴弾
	public static Item item_Bomb;// = new ItemBomb(EnumBombType.BOMB).setRegistryName(NAME_ITEMBOMB);
	public static Item item_WaterBomb;// = new ItemBomb(EnumBombType.WARTER, Items.BUCKET).setRegistryName(NAME_ITEMWATERBOMB);
	public static Item item_paintBomb;// = new ItemBomb(EnumBombType.PAINT).setRegistryName(NAME_ITEMPAINTBOMB);
	public static Item item_frozenBomb;// = new ItemBomb(EnumBombType.FROZEN).setRegistryName(NAME_ITEMFROZENBOMB);
	public static Item item_icicleBomb;// = new ItemBomb(EnumBombType.ICICLE).setRegistryName(NAME_ITEMICICLEBOMB);
	public static Item item_MC;// = new ItemMolotovCoctail().setRegistryName(NAME_ITEMMC);
	public static Item item_lavaBomb;// = new ItemBomb(EnumBombType.LAVA,null).setRegistryName(NAME_ITEMLAVABOMB);

	// ミサイル
	public static Item item_TntMissile;// = new ItemMissile().setMissileType(0).setRegistryName(NAME_ITEMTNTMISSILE);
	public static Item item_NCMissile;// = new ItemMissile().setMissileType(1).setRegistryName(NAME_ITEMNCMISSILE);
	public static Item item_CEMissile;// = new ItemMissile().setMissileType(2).setRegistryName(NAME_ITEMCEMISSILE);
	public static Item item_MCEMissile;// = new ItemMissile().setMissileType(3).setRegistryName(NAME_ITEMMCEMISSILE);

	// ツール
	public static Item item_Radar;// = new  ItemSpawnerRadar().setRegistryName(NAME_ITEMRADAR);
	public static Item item_defuser;// = new ItemDefuser().setRegistryName(NAME_ITEMDEFUSER);
	public static Item item_blockRadar;// = new BlockItemRadar().setRegistryName(NAME_ITEMBLOCKRADAR);


	private static Map<String,Item> itemMap;

	public static void init(){

		if (itemMap!= null){return;}


		item_Oil = new ItemNewFuel(30000,new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMOIL);
		item_Plastic= new Item(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_PLASTIC);
		item_HeavyMatter = new Item(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_HEAVYMATTER);;
		item_Uranium = new ItemUranium(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMURANIUM);
		item_RocketFuel = new ItemRocketFuel(40000,new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMROCKETFUEL);

		// 手榴弾
		item_Bomb = new ItemBomb(EnumBombType.BOMB, new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMBOMB);
		item_WaterBomb = new ItemBomb(EnumBombType.WARTER, new Item.Properties().group(Mod_ExBombs.tabExBombs).containerItem(Items.BUCKET)).setRegistryName(NAME_ITEMWATERBOMB);
		item_paintBomb = new ItemBomb(EnumBombType.PAINT, new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMPAINTBOMB);
		item_frozenBomb = new ItemBomb(EnumBombType.FROZEN, new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMFROZENBOMB);
		item_icicleBomb = new ItemBomb(EnumBombType.ICICLE, new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMICICLEBOMB);
		item_MC = new ItemMolotovCoctail(new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMMC);
		item_lavaBomb = new ItemBomb(EnumBombType.LAVA, new Item.Properties().group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMLAVABOMB);

		// ミサイル
		item_TntMissile = new ItemMissile(new Item.Properties().maxStackSize(1).group(Mod_ExBombs.tabExBombs)).setMissileType(0).setRegistryName(NAME_ITEMTNTMISSILE);
		item_NCMissile = new ItemMissile(new Item.Properties().maxStackSize(1).group(Mod_ExBombs.tabExBombs)).setMissileType(1).setRegistryName(NAME_ITEMNCMISSILE);
		item_CEMissile = new ItemMissile(new Item.Properties().maxStackSize(1).group(Mod_ExBombs.tabExBombs)).setMissileType(2).setRegistryName(NAME_ITEMCEMISSILE);
		item_MCEMissile = new ItemMissile(new Item.Properties().maxStackSize(1).group(Mod_ExBombs.tabExBombs)).setMissileType(3).setRegistryName(NAME_ITEMMCEMISSILE);

		// ツール
		item_Radar = new  ItemSpawnerRadar(new Item.Properties().maxStackSize(1).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMRADAR);
		item_defuser = new ItemDefuser(new Item.Properties().maxStackSize(1).defaultMaxDamage(255).group(Mod_ExBombs.tabExBombs)).setRegistryName(NAME_ITEMDEFUSER);
		item_blockRadar = new ItemBlockRadar(new Item.Properties().group(Mod_ExBombs.tabExBombs).maxStackSize(1)).setRegistryName(NAME_ITEMBLOCKRADAR);
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
	}

	public static void register(final RegistryEvent.Register<Item> event) {
		for (String key : NAME_LIST){
			if (itemMap.containsKey(key)) {
				event.getRegistry().register(itemMap.get(key));
			}
		}
	}


}
