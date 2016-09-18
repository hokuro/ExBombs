package mod.exbombs.core;

import mod.exbombs.config.ConfigValue;
import mod.exbombs.entity.EntityBomb;
import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.entity.EntityFrozenBomb;
import mod.exbombs.entity.EntityIcicleBomb;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.entity.EntityNuclearExplosivePrimed;
import mod.exbombs.entity.EntityPaintBomb;
import mod.exbombs.entity.EntityTunnelExplosivePrimed;
import mod.exbombs.entity.EntityWaterBomb;
import mod.exbombs.event.ModEventHandler;
import mod.exbombs.network.MessageFuseSetBurn;
import mod.exbombs.network.MessageMissileLaunchClient;
import mod.exbombs.network.MessageMissileLaunchServer;
import mod.exbombs.network.MessageRadarUpdate;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModRegisterItem;
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
import net.minecraftforge.fml.common.registry.EntityRegistry;
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

		// レンダー設定
		proxy.registerRenderInfomation();

		// エンティティ設定
		RegisterEntity();

		// メッセージ登録
		RegisterMessage();

		// ブロック登録
		ModRegisterBlock.registerBlock(event);
		// アイテム登録
		ModRegisterItem.RegisterItem(event);

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

	public static final String Entity_NCBomb = "EntityNuclearExplosivePrimed";
	public static final String Entity_TunnelBomb = "EntityTunnemExprimed";
	public static final String Entity_Bomb = "EntityBomb";
	public static final String Entity_WaterBomb = "EntityWaterBomb";
	public static final String Entity_Missile = "EntityMissile";
	public static final String TileEntity_Fuse = "EntityFuse";
	public static final String Entity_ChunkEraser = "EntityChunkEraser";
	public static final String Entity_PaintBomb = "EntityPaintBomb";
	public static final String Entity_FrozenBomb = "EntityFrozenBomb";
	public static final String Entity_IcicleBomb = "EntityIcicleBomb";

	// エンティティを登録する
	public void RegisterEntity(){

		EntityRegistry.registerModEntity(EntityBomb.class,                   Entity_Bomb,        101, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityWaterBomb.class,              Entity_WaterBomb,   102, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityFrozenBomb.class,             Entity_FrozenBomb,  103, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityIcicleBomb.class,             Entity_IcicleBomb,  104, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityPaintBomb.class,              Entity_PaintBomb,   105, this, 256, 1, true);

		EntityRegistry.registerModEntity(EntityNuclearExplosivePrimed.class, Entity_NCBomb,      201, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityTunnelExplosivePrimed.class,  Entity_TunnelBomb,  202, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityChunkEraserPrimed.class,      Entity_ChunkEraser, 203, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityMissile.class,                Entity_Missile,     200, this, 256, 1, true);
		// タイルエンティティの登録
		proxy.registerCompnents();
	}

	/**
	 * レシピを追加する
	 */
	public void RegisterRecipe(){

		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Oil, 5),
				new Object[] { " C ", "CCC", " C ",
				Character.valueOf('C'), Items.coal });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Plastic, 8),
				new Object[] { "OSO", "SOS", "OSO",
				Character.valueOf('O'), ModRegisterItem.item_Oil,
				Character.valueOf('S'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Plastic, 4),
				new Object[] { "OOO", "OSO", "OOO",
				Character.valueOf('O'), ModRegisterItem.item_Oil,
				Character.valueOf('S'), Items.sugar });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_HeavyMatter, 6),
				new Object[] { "OOO", "OOO", "OOO",
				Character.valueOf('O'), Blocks.obsidian });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_RocketFuel, 6),
				new Object[] { "PGP", "GPG", "PGP",
				Character.valueOf('G'), Items.gunpowder,
				Character.valueOf('P'), ModRegisterItem.item_Plastic });

		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_defuser, 1),
				new Object[] { "R", "I", "I",
						Character.valueOf('R'), Items.redstone,
						Character.valueOf('I'), Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Radar, 1),
				new Object[] { "IRI", "RDR", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('R'), Items.redstone,
				Character.valueOf('D'), Items.compass });

		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_Fuse, 32),
				new Object[] { "SSS", "GGG", "SSS",
				Character.valueOf('S'), Items.string,
				Character.valueOf('G'), Items.gunpowder });

		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_NCBomb, 1),
				new Object[] { "UTU", "TUT", "UTU",
				Character.valueOf('T'), Blocks.tnt,
				Character.valueOf('U'), ModRegisterItem.item_Uranium });
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.bolock_TunnelBomb, 1),
				new Object[] { "CCC", "SCS", "SSS",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('C'), Blocks.tnt });
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_eraser, 1),
				new Object[] { "I I", "ITI", "IPI",
				Character.valueOf('T'), ModRegisterBlock.block_NCBomb,
				Character.valueOf('I'), Blocks.iron_block,
				Character.valueOf('P'), Items.compass});
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_unmach, 1),
				new Object[] { "IRI", "ITI", "IPI",
				Character.valueOf('R'), Blocks.redstone_torch,
				Character.valueOf('T'), ModRegisterBlock.block_NCBomb,
				Character.valueOf('I'), Blocks.iron_block,
				Character.valueOf('P'), Items.compass});

		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_TntMissile, 1),
				new Object[] { "ITI", "IRI", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('T'), Blocks.tnt,
				Character.valueOf('R'), ModRegisterItem.item_RocketFuel });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_NCMissile, 1),
				new Object[] { "INI", "IRI", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('N'), ModRegisterBlock.block_NCBomb,
				Character.valueOf('R'), ModRegisterItem.item_RocketFuel });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_CEMissile, 1),
				new Object[] { "ITI", "IRI", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('T'), ModRegisterBlock.block_eraser,
				Character.valueOf('R'), ModRegisterItem.item_RocketFuel });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_MCEMissile, 1),
				new Object[] { "ITI", "IRI", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('T'), ModRegisterBlock.block_unmach,
				Character.valueOf('R'), ModRegisterItem.item_RocketFuel });

		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_MC, 4),
				new Object[] { "OPO", "OGO", "OOO",
				Character.valueOf('G'), Items.glass_bottle,
				Character.valueOf('O'), ModRegisterItem.item_Oil,
				Character.valueOf('P'), Items.paper });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Bomb, 6),
				new Object[] { "III", "ITI", "III",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('T'), Blocks.tnt});
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_WaterBomb, 6),
				new Object[] { "PPP", "PWP", "PPP",
				Character.valueOf('P'), Items.paper,
				Character.valueOf('W'), Items.water_bucket });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_frozenBomb, 6),
				new Object[] { "PIP", "PIP", "PPP",
				Character.valueOf('P'), Items.paper,
				Character.valueOf('I'), Blocks.ice });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_icicleBomb, 6),
				new Object[] { "III", "ITI", "III",
				Character.valueOf('I'), Blocks.ice,
				Character.valueOf('T'), Items.water_bucket });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_paintBomb, 6),
				new Object[] { "APB", "WTK", "YPG",
						Character.valueOf('A'), new ItemStack(Items.dye,1,1),
						Character.valueOf('B'), new ItemStack(Items.dye,1,4),
						Character.valueOf('P'), Items.paper,
						Character.valueOf('W'), new ItemStack(Items.dye,1,15),
						Character.valueOf('T'), Blocks.tnt,
						Character.valueOf('K'), new ItemStack(Items.dye,1,0),
						Character.valueOf('Y'), new ItemStack(Items.dye,1,11),
						Character.valueOf('G'), new ItemStack(Items.dye,1,2)});
		GameRegistry.addSmelting(ModRegisterItem.item_HeavyMatter, new ItemStack(ModRegisterItem.item_Uranium, 1), 0.7F);
	}

	public void RegisterMessage(){
		this.INSTANCE.registerMessage(MessageFuseSetBurn.class, MessageFuseSetBurn.class, 1, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchClient.class, MessageMissileLaunchClient.class, 2, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchServer.class, MessageMissileLaunchServer.class, 3, net.minecraftforge.fml.relauncher.Side.CLIENT);
		this.INSTANCE.registerMessage(MessageShowGui.class, MessageShowGui.class, 101, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageRadarUpdate.class, MessageRadarUpdate.class, 102, net.minecraftforge.fml.relauncher.Side.SERVER);
	}


}
