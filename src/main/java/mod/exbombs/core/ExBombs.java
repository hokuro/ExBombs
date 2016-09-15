package mod.exbombs.core;

import java.util.Iterator;
import java.util.Random;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.config.ConfigValue;
import mod.exbombs.entity.EntityBomb;
import mod.exbombs.entity.EntityChunkEraserPrimed;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.entity.EntityNuclearExplosivePrimed;
import mod.exbombs.entity.EntityPaintBomb;
import mod.exbombs.entity.EntityTunnelExplosivePrimed;
import mod.exbombs.entity.EntityWaterBomb;
import mod.exbombs.event.ModEventHandler;
import mod.exbombs.network.MessageExplosion;
import mod.exbombs.network.MessageExplosion.EnumExplosionType;
import mod.exbombs.network.MessageFuseSetBurn;
import mod.exbombs.network.MessageMissileLaunchClient;
import mod.exbombs.network.MessageMissileLaunchServer;
import mod.exbombs.network.MessageRadarUpdate;
import mod.exbombs.network.MessageShowGui;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModRegisterItem;
import net.minecraft.world.World;
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
public class ExBombs {
	@Mod.Instance(ModCommon.MOD_ID)
	public static ExBombs instance;
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

	// エンティティを登録する
	public void RegisterEntity(){
		EntityRegistry.registerModEntity(EntityNuclearExplosivePrimed.class, Entity_NCBomb,      201, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityTunnelExplosivePrimed.class,  Entity_TunnelBomb,  202, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityBomb.class,                   Entity_Bomb,        203, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityWaterBomb.class,              Entity_WaterBomb,   204, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityMissile.class,                Entity_Missile,     205, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityChunkEraserPrimed.class,      Entity_ChunkEraser, 206, this, 256, 1, true);
		EntityRegistry.registerModEntity(EntityPaintBomb.class,              Entity_PaintBomb,   207, this, 256, 1, true);

		// タイルエンティティの登録
		proxy.registerCompnents();
	}

	/**
	 * レシピを追加する
	 */
	public void RegisterRecipe(){
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_NCBomb, 1),
				new Object[] { "UTU", "TUT", "UTU",
				Character.valueOf('T'), Blocks.tnt,
				Character.valueOf('U'), ModRegisterItem.item_Uranium });
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.bolock_TunnelBomb, 1),
				new Object[] { "CCC", "SCS", "SSS",
				Character.valueOf('S'), Blocks.stone,
				Character.valueOf('C'), Blocks.tnt });
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_Fuse, 32),
				new Object[] { "SSS", "GGG", "SSS",
				Character.valueOf('S'), Items.string,
				Character.valueOf('G'), Items.gunpowder });
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
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Bomb, 6),
				new Object[] { "III", "ITI", "III",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('T'), Blocks.tnt});
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_WaterBomb, 6),
				new Object[] { "PPP", "PWP", "PPP",
				Character.valueOf('P'), Items.paper,
				Character.valueOf('W'), Items.water_bucket });
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
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_Radar, 1),
				new Object[] { "IRI", "RDR", "IRI",
				Character.valueOf('I'), Items.iron_ingot,
				Character.valueOf('R'), Items.redstone,
				Character.valueOf('D'), Items.compass });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_MC, 4),
				new Object[] { "OPO", "OGO", "OOO",
				Character.valueOf('G'), Items.glass_bottle,
				Character.valueOf('O'), ModRegisterItem.item_Oil,
				Character.valueOf('P'), Items.paper });
		GameRegistry.addRecipe(new ItemStack(ModRegisterItem.item_defuser, 1),
				new Object[] { "R", "I", "I",
						Character.valueOf('R'), Items.redstone,
						Character.valueOf('I'), Items.iron_ingot });


		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_eraser, 1),
				new Object[] { "IUI", "ITI", "IUI",
				Character.valueOf('T'), Blocks.tnt,
				Character.valueOf('U'), ModRegisterItem.item_Uranium,
				Character.valueOf('I'), Items.iron_ingot });
		GameRegistry.addRecipe(new ItemStack(ModRegisterBlock.block_unmach, 1),
				new Object[] { "ICI", "ITI", "IUI",
						Character.valueOf('C'), Items.compass,
						Character.valueOf('T'), Blocks.tnt,
						Character.valueOf('U'), ModRegisterItem.item_Uranium,
						Character.valueOf('I'), Items.iron_ingot });
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
		this.INSTANCE.registerMessage(MessageExplosion.class, MessageExplosion.class, 0, net.minecraftforge.fml.relauncher.Side.CLIENT);
		this.INSTANCE.registerMessage(MessageFuseSetBurn.class, MessageFuseSetBurn.class, 1, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchClient.class, MessageMissileLaunchClient.class, 2, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageMissileLaunchServer.class, MessageMissileLaunchServer.class, 3, net.minecraftforge.fml.relauncher.Side.CLIENT);
		this.INSTANCE.registerMessage(MessageShowGui.class, MessageShowGui.class, 101, net.minecraftforge.fml.relauncher.Side.SERVER);
		this.INSTANCE.registerMessage(MessageRadarUpdate.class, MessageRadarUpdate.class, 102, net.minecraftforge.fml.relauncher.Side.SERVER);
	}


	// 爆発
	public static void createBetterExplosion(World worldObj, Entity entity, double x, double y, double z, float size, boolean isFlaming, boolean enableDrops, boolean destroy) {
		MoreExplosivesBetterExplosion betterexplosion = new MoreExplosivesBetterExplosion(worldObj, entity, x, y, z, size, null, enableDrops, destroy);
		betterexplosion.isFlaming = isFlaming;
		betterexplosion.doExplosionA();
		betterexplosion.doExplosionB(true);
		sendClientFXPacket(worldObj, x, y, z);
	}

	public static void createBetterExplosionWithWater(World worldObj, Entity entity, double x, double y, double z, float size, boolean isFlaming, boolean enableDrops){
		MoreExplosivesBetterExplosion betterexplosion = new MoreExplosivesBetterExplosion(worldObj, entity, x, y, z, size, Blocks.flowing_water.getDefaultState(), enableDrops, ConfigValue.General.wtbomb_destroy_block);
		betterexplosion.isFlaming = isFlaming;
		betterexplosion.doExplosionA();
		betterexplosion.doExplosionB(true);
		sendClientFXPacket(worldObj, x, y, z);
	}

	public static void createPaintExplosion(World worldObj, Entity entity, double x, double y, double z, float size, IBlockState block){
			MoreExplosivesPaintExplosion betterexplosion = new MoreExplosivesPaintExplosion(worldObj, entity, x, y, z, size, block);
			betterexplosion.doExplosionA();
			betterexplosion.doExplosionB(true);
			sendClientFXPacket(worldObj, x, y, z);
	}

	public static void createSuperExplosion(World worldObj, Entity entity, int x, int y, int z, float size) {
		long seed = new Random().nextLong();
		sendSuperClientFXPacket(worldObj, x, y, z, seed, size);
		new MoreExplosivesSuperExplosion().explode(worldObj, size, size, size, x, y, z, seed);
	}

	public static void createTunnelExplosion(World worldObj, Entity entity, double x, double y, double z, int direction) {
		MoreExplosivesTunnelExplosion tunnelexplosion = new MoreExplosivesTunnelExplosion(worldObj, entity, x, y, z, direction);
		tunnelexplosion.doExplosionA();
		tunnelexplosion.doExplosionB(true);
		sendClientFXPacket(worldObj, x, y, z);
	}

	public static void createEraserExplosion(World worldObj, Entity entity, int x, int y, int z, float size, EnumEraseType eraseType) {
		MoreExplosiveEraseExplosion betterexplosion = new MoreExplosiveEraseExplosion(eraseType==EnumEraseType.ERASEUNMATCH);
		sendEraseClientFXPacket(worldObj, x, y, z,eraseType);
		betterexplosion.explode(worldObj, x, y, z);
	}

	//通信関係
	private static void sendClientFXPacket(World world, double x, double y, double z) {
		Iterator players = world.playerEntities.iterator();
		while (players.hasNext()) {
			EntityPlayer player = (EntityPlayer) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.NORMAL.getType(), (int)x, (int)y, (int)z, 0L, 0F), (EntityPlayerMP)player);
			}
		}
	}

	private static void sendSuperClientFXPacket(World world, double x, double y, double z, long seed, float size) {
		Iterator players = world.playerEntities.iterator();
		while (players.hasNext()) {
			EntityPlayer player = (EntityPlayer) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.SUPER.getType(),(int) x, (int) y, (int) z, seed, size), (EntityPlayerMP)player);
			}
		}
	}

	private static void sendEraseClientFXPacket(World world, double x, double y, double z, EnumEraseType type) {
		Iterator players = world.playerEntities.iterator();
		while (players.hasNext()) {
			EntityPlayer player = (EntityPlayer) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				if (type == EnumEraseType.ERASEALL){
					INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.ERASE.getType(),(int) x, (int) y, (int) z, 0L, 0F), (EntityPlayerMP)player);
				}else{
					INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.MACHING.getType(),(int) x, (int) y, (int) z, 0L, 0F), (EntityPlayerMP)player);
				}
			}
		}
	}
}
