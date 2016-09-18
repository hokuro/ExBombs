package net.minecraft.item;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.item.ItemBomb;
import mod.exbombs.item.ItemDefuser;
import mod.exbombs.item.ItemMissile;
import mod.exbombs.item.ItemMolotovCoctail;
import mod.exbombs.item.ItemRadar;
import mod.exbombs.item.ItemRocketFuel;
import mod.exbombs.item.ItemUranium;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRegisterItem {
	// 名前
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
	// アイテム
	// 素材
	public static final Item item_Oil = new Item()
			.setUnlocalizedName(NAME_ITEMOIL).setCreativeTab(Mod_ExBombs.tabExBombs);
	public static final Item item_Plastic = new Item()
			.setUnlocalizedName(NAME_PLASTIC).setCreativeTab(Mod_ExBombs.tabExBombs);
	public static final Item item_HeavyMatter = new Item()
			.setUnlocalizedName(NAME_HEAVYMATTER).setCreativeTab(Mod_ExBombs.tabExBombs);
	public static final Item item_Uranium = new ItemUranium()
			.setUnlocalizedName(NAME_ITEMURANIUM);
	public static final Item item_RocketFuel = new ItemRocketFuel()
			.setUnlocalizedName(NAME_ITEMROCKETFUEL);

	// 手榴弾
	public static final Item item_Bomb = new ItemBomb(EnumBombType.BOMB)
			.setUnlocalizedName(NAME_ITEMBOMB);
	public static final Item item_WaterBomb = new ItemBomb(EnumBombType.WARTER)
			.setContainerItem(Items.BUCKET)
			.setUnlocalizedName(NAME_ITEMWATERBOMB);
	public static final Item item_paintBomb = new ItemBomb(EnumBombType.PAINT)
			.setUnlocalizedName(NAME_ITEMPAINTBOMB);
	public static final Item item_frozenBomb = new ItemBomb(EnumBombType.FROZEN)
			.setUnlocalizedName(NAME_ITEMFROZENBOMB);
	public static final Item item_icicleBomb = new ItemBomb(EnumBombType.ICICLE)
			.setUnlocalizedName(NAME_ITEMICICLEBOMB);
	public static final Item item_MC = new ItemMolotovCoctail()
			.setUnlocalizedName(NAME_ITEMMC);

	// ミサイル
	public static final Item item_TntMissile = new ItemMissile()
			.setMissileType(0)
			.setUnlocalizedName(NAME_ITEMTNTMISSILE);
	public static final Item item_NCMissile = new ItemMissile()
			.setMissileType(1)
			.setUnlocalizedName(NAME_ITEMNCMISSILE);
	public static final Item item_CEMissile = new ItemMissile()
			.setMissileType(2)
			.setUnlocalizedName(NAME_ITEMCEMISSILE);
	public static final Item item_MCEMissile = new ItemMissile()
			.setMissileType(3)
			.setUnlocalizedName(NAME_ITEMMCEMISSILE);

	// ツール
	public static final Item item_Radar = new  ItemRadar()
			.setUnlocalizedName(NAME_ITEMRADAR);
	public static final Item item_defuser = new ItemDefuser()
			.setUnlocalizedName(NAME_ITEMDEFUSER);



	public static void RegisterItem(FMLPreInitializationEvent event){

		GameRegistry.register(item_Oil, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMOIL));
		GameRegistry.register(item_Plastic, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_PLASTIC));
		GameRegistry.register(item_HeavyMatter, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_HEAVYMATTER));
		GameRegistry.register(item_Uranium, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMURANIUM));
		GameRegistry.register(item_RocketFuel, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMROCKETFUEL));
		GameRegistry.register(item_Bomb, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMBOMB));
		GameRegistry.register(item_MC, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMC));
		GameRegistry.register(item_WaterBomb, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMWATERBOMB));
		GameRegistry.register(item_paintBomb, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMPAINTBOMB));
		GameRegistry.register(item_frozenBomb, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMFROZENBOMB));
		GameRegistry.register(item_icicleBomb, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMICICLEBOMB));
		GameRegistry.register(item_TntMissile, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMTNTMISSILE));
		GameRegistry.register(item_NCMissile, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMNCMISSILE));
		GameRegistry.register(item_CEMissile, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMCEMISSILE));
		GameRegistry.register(item_MCEMissile, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMCEMISSILE));
		GameRegistry.register(item_Radar, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMRADAR));
		GameRegistry.register(item_defuser, new ResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMDEFUSER));


		if (event.getSide().isClient()){
			ModelLoader.setCustomModelResourceLocation(item_Oil,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMOIL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_Plastic,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_PLASTIC, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_HeavyMatter,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_HEAVYMATTER, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_Uranium,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMURANIUM, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_RocketFuel,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMROCKETFUEL, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_Bomb,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMBOMB, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_WaterBomb,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMWATERBOMB, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_TntMissile,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMTNTMISSILE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_NCMissile,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMNCMISSILE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_Radar,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMRADAR, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_MC,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMC, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_defuser,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMDEFUSER, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_CEMissile,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMCEMISSILE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_MCEMissile,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMMCEMISSILE, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_paintBomb,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMPAINTBOMB, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_frozenBomb,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMFROZENBOMB, "inventory"));
			ModelLoader.setCustomModelResourceLocation(item_icicleBomb,0, new ModelResourceLocation(ModCommon.MOD_ID+":"+NAME_ITEMICICLEBOMB, "inventory"));
		}
	}
}
