package mod.exbombs.core;

import mod.exbombs.block.BlockCore;
import mod.exbombs.config.ConfigValue;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.event.ModEventHandler;
import mod.exbombs.item.ItemCore;
import mod.exbombs.network.MessageBlockRadarUpdate;
import mod.exbombs.network.MessageFuseSetBurn;
import mod.exbombs.network.MessageMissileLaunchClient;
import mod.exbombs.network.MessageMissileLaunchServer;
import mod.exbombs.network.MessageRadarUpdate;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModCommon.MOD_ID, name = ModCommon.MOD_NAME, version = ModCommon.MOD_VERSION)
public class Mod_ExBombs {
	@Mod.Instance(ModCommon.MOD_ID)
	public static Mod_ExBombs instance;
	@SidedProxy(clientSide = ModCommon.MOD_PACKAGE + ModCommon.MOD_CLIENT_SIDE, serverSide = ModCommon.MOD_PACKAGE + ModCommon.MOD_SERVER_SIDE)
	public static CommonProxy proxy;
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModCommon.MOD_CHANEL);
	public static final ModEventHandler mcEvent = new ModEventHandler();

	// タブ
	public static final CreativeTabs tabExBombs = new CreativeTabExBombs("ExBombs");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// コンフィグ読み込み
		ConfigValue.init(event);

		// ブロック登録
		BlockCore.register(event);
		// アイテム登録
		ItemCore.register(event);
		// エンティティ設定
		EntityCore.register();
		// レンダー設定
		proxy.registerRenderInfomation();
		// メッセージ登録
		RegisterMessage();
		// サウンド登録
		ModSoundManager.RegisterSounds();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		FMLEventChannel e = NetworkRegistry.INSTANCE.newEventDrivenChannel(ModCommon.MOD_EVENTCHANEL);
		e.register(mcEvent);
		MinecraftForge.EVENT_BUS.register(mcEvent);

		// レシピ追加
		this.RegisterRecipe();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}



	/**
	 * レシピを追加する
	 */
	public void RegisterRecipe(){

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMOIL),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_Oil, 5),
				new Object[] {
						" C ",
						"CCC",
						" C ",
				'C', Items.COAL });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_PLASTIC),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_Plastic, 8),
				new Object[] {
						"OSO",
						"SOS",
						"OSO",
				'O', ItemCore.item_Oil,
				'S', Items.SLIME_BALL });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_PLASTIC),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_Plastic, 4),
				new Object[] {
						"OOO",
						"OSO",
						"OOO",
				'O', ItemCore.item_Oil,
				'S', Items.SUGAR });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_HEAVYMATTER),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_HeavyMatter, 6),
				new Object[] {
						"OOO",
						"OOO",
						"OOO",
				'O', Blocks.OBSIDIAN });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMROCKETFUEL),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_RocketFuel, 6),
				new Object[] {
						"PGP",
						"GPG",
						"PGP",
				'G', Items.GUNPOWDER,
				'P', ItemCore.item_Plastic });

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMDEFUSER),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_defuser, 1),
				new Object[] {
						"R",
						"I",
						"I",
						'R', Items.REDSTONE,
						'I', Items.IRON_INGOT });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMRADAR),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_Radar, 1),
				new Object[] {
						"IRI",
						"RDR",
						"IEI",
				'E', Items.EGG,
				'I', Items.IRON_INGOT,
				'R', Items.REDSTONE,
				'D', Items.COMPASS });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMBLOCKRADAR),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_blockRadar, 1),
				new Object[] {
						"IRI",
						"RDR",
						"ICI",
				'C', Blocks.CHEST,
				'I', Items.IRON_INGOT,
				'R', Items.REDSTONE,
				'D', Items.COMPASS });

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +BlockCore.NAME_FUSE),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(BlockCore.block_fuse, 32),
				new Object[] {
						"SSS",
						"GGG",
						"SSS",
				'S', Items.STRING,
				'G', Items.GUNPOWDER });

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +BlockCore.NAME_NUCLEAREX),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(BlockCore.block_nuclear, 1),
				new Object[] {
						"UTU",
						"TUT",
						"UTU",
				'T', Blocks.TNT,
				'U', ItemCore.item_Uranium });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +BlockCore.NAME_TUNNELEX),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(BlockCore.block_tunnel, 1),
				new Object[] {
						"CCC",
						"SCS",
						"SSS",
				'S', Blocks.STONE,
				'C', Blocks.TNT });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +BlockCore.NAME_CHUNKERASER),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(BlockCore.block_chunkeraser, 1),
				new Object[] {
						"I I",
						"ITI",
						"IPI",
				'T', BlockCore.block_nuclear,
				'I', Blocks.IRON_BLOCK,
				'P', Items.COMPASS});
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +BlockCore.NAME_MUCHIBLOCKERASER),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(BlockCore.block_muchblockeraser, 1),
				new Object[] {
						"IRI",
						"ITI",
						"IPI",
				'R', Blocks.REDSTONE_TORCH,
				'T', BlockCore.block_nuclear,
				'I', Blocks.IRON_BLOCK,
				'P', Items.COMPASS});

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMTNTMISSILE),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_TntMissile, 1),
				new Object[] {
						"ITI",
						"IRI",
						"IRI",
				'I', Items.IRON_INGOT,
				'T', Blocks.TNT,
				'R', ItemCore.item_RocketFuel });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMNCMISSILE),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_NCMissile, 1),
				new Object[] {
						"INI",
						"IRI",
						"IRI",
				'I', Items.IRON_INGOT,
				'N', BlockCore.block_nuclear,
				'R', ItemCore.item_RocketFuel });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMCEMISSILE),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_CEMissile, 1),
				new Object[] {
						"ITI",
						"IRI",
						"IRI",
				'I', Items.IRON_INGOT,
				'T', BlockCore.block_nuclear,
				'R', ItemCore.item_RocketFuel });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMMCEMISSILE),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_MCEMissile, 1),
				new Object[] {
						"ITI",
						"IRI",
						"IRI",
				'I', Items.IRON_INGOT,
				'T', BlockCore.block_muchblockeraser,
				'R', ItemCore.item_RocketFuel });

		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMMC),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_MC, 4),
				new Object[] {
						"OPO",
						"OGO",
						"OOO",
				'G', Items.GLASS_BOTTLE,
				'O', ItemCore.item_Oil,
				'P', Items.PAPER });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMBOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_Bomb, 6),
				new Object[] {
						"III",
						"ITI",
						"III",
				'I', Items.IRON_INGOT,
				'T', Blocks.TNT});
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMWATERBOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_WaterBomb, 6),
				new Object[] {
						"PPP",
						"PWP",
						"PPP",
				'P', Items.PAPER,
				'W', Items.WATER_BUCKET });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMFROZENBOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_frozenBomb, 6),
				new Object[] {
						"PPP",
						"PIP",
						"PPP",
				'P', Items.SNOWBALL,
				'I', Blocks.SNOW });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMICICLEBOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_icicleBomb, 6),
				new Object[] {
						"III",
						"ITI",
						"III",
				'I', Blocks.ICE,
				'T', Items.WATER_BUCKET });
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMPAINTBOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_paintBomb, 6),
				new Object[] {
						"APB",
						"WTK",
						"YPG",
						'A', new ItemStack(Items.DYE,1,1),
						'B', new ItemStack(Items.DYE,1,4),
						'P', Items.PAPER,
						'W', new ItemStack(Items.DYE,1,15),
						'T', Blocks.TNT,
						'K', new ItemStack(Items.DYE,1,0),
						'Y', new ItemStack(Items.DYE,1,11),
						'G', new ItemStack(Items.DYE,1,2)});
		GameRegistry.addShapedRecipe(
				new ResourceLocation(ModCommon.MOD_ID + ":" +ItemCore.NAME_ITEMLAVABOMB),
				new ResourceLocation(ModCommon.MOD_ID),
				new ItemStack(ItemCore.item_lavaBomb, 6),
				new Object[] {
						"PPP",
						"PWP",
						"PPP",
				'P', Items.PAPER,
				'W', Items.LAVA_BUCKET });

		GameRegistry.addSmelting(ItemCore.item_HeavyMatter, new ItemStack(ItemCore.item_Uranium, 1), 0.7F);
	}

	public void RegisterMessage(){
		this.INSTANCE.registerMessage(MessageFuseSetBurn.class, MessageFuseSetBurn.class, 1, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchClient.class, MessageMissileLaunchClient.class, 2, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchServer.class, MessageMissileLaunchServer.class, 3, net.minecraftforge.fml.relauncher.Side.CLIENT);
		this.INSTANCE.registerMessage(MessageShowGui.class, MessageShowGui.class, 101, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageRadarUpdate.class, MessageRadarUpdate.class, 102, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageBlockRadarUpdate.class, MessageBlockRadarUpdate.class, 103, net.minecraftforge.fml.relauncher.Side.SERVER);
	}


}
