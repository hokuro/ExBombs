package mod.exbombs.core;

import mod.exbombs.block.BlockCore;
import mod.exbombs.config.MyConfig;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.gui.GuiBlockRadar;
import mod.exbombs.gui.GuiSpawnRadar;
import mod.exbombs.item.ItemCore;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod( ModCommon.MOD_ID)
public class Mod_ExBombs {
	public final static CommonProxy proxy =DistExecutor.<CommonProxy>runForDist(() -> CommonProxy.Client::new, () -> CommonProxy.Server::new);
	// タブ
	public static final ItemGroup tabExBombs = new ItemGroupExBombs("ExBombs");

    public Mod_ExBombs() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // コンフィグ読み込み
    	ModLoadingContext.get().
        registerConfig(
        		net.minecraftforge.fml.config.ModConfig.Type.COMMON,
        		MyConfig.spec);
        // Register ourselves for server and other game events we are interested in
    	// メッセージ登録
    	MessageHandler.register();

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        proxy.registerRenderInfomation();
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> (openContainer) -> {
        		ResourceLocation location = openContainer.getId();
        		if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.MOD_GUI_ID_SPAWNRADAR)){
        			EntityPlayerSP player = Minecraft.getInstance().player;
        			int idx = openContainer.getAdditionalData().readInt();
        			return new GuiSpawnRadar(idx);
        		}else if (location.toString().equals(ModCommon.MOD_ID + ":" + ModCommon.MOD_GUI_ID_BLOCKRADER)){
        			EntityPlayerSP player = Minecraft.getInstance().player;
        			int idx = openContainer.getAdditionalData().readInt();
        			int idx2 = openContainer.getAdditionalData().readInt();
        			return new GuiBlockRadar(idx,idx2);
        		}
        		return null;
        	});

    }

//	@EventHandler
//	public void preInit(FMLPreInitializationEvent event) {
//		// コンフィグ読み込み
//		MyConfig.init(event);
//		// ブロック登録
//		BlockCore.register(event);
//		// アイテム登録
//		ItemCore.register(event);
//		// エンティティ設定
//		EntityCore.register();
//		// レンダー設定
//		proxy.registerRenderInfomation();
//		// メッセージ登録
//		RegisterMessage();
//		// サウンド登録
//		ModSoundManager.RegisterSounds();
//	}

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
        	BlockCore.registerBlock(blockRegistryEvent);
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            BlockCore.registerItemBlock(itemRegistryEvent);
            ItemCore.register(itemRegistryEvent);
        }

        @SubscribeEvent
        public static void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> etRegistryEvent){
        	EntityCore.Inst().register(etRegistryEvent);
        }

        @SubscribeEvent
        public static void onTERegistyr(final RegistryEvent.Register<TileEntityType<?>> teRegistryEvent){
        	EntityCore.Inst().registerTE(teRegistryEvent);
        	//Mod_ExBombs.proxy.registerCompnents(teRegistryEvent);

        }

        @SubscribeEvent
        public static void onSoundRegistyr(final RegistryEvent.Register<SoundEvent> teRegistryEvent){
        	ModSoundManager.RegisterSounds(teRegistryEvent);
        }
    }


}
