package mod.exbombs.core;

import mod.exbombs.block.BlockCore;
import mod.exbombs.config.MyConfig;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.entity.missile.EntityExBombsSmokeFX;
import mod.exbombs.entity.missile.EntityMissile;
import mod.exbombs.gui.GuiBlockRadar;
import mod.exbombs.gui.GuiMissile;
import mod.exbombs.gui.GuiSpawnRadar;
import mod.exbombs.inventory.ContainerBlockRadar;
import mod.exbombs.inventory.ContainerMissile;
import mod.exbombs.inventory.ContainerSpawnRadar;
import mod.exbombs.item.ItemCore;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.sounds.ModSoundManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

@Mod( ModCommon.MOD_ID)
public class Mod_ExBombs {
	public final static CommonProxy proxy =DistExecutor.<CommonProxy>runForDist(() -> CommonProxy.Client::new, () -> CommonProxy.Server::new);
	// タブ
	public static final ItemGroup tabExBombs = new ItemGroupExBombs("ExBombs");

    public Mod_ExBombs() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onParticleFactoryRegister);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::onContainerRegistry);
    	FMLJavaModLoadingContext.get().getModEventBus().register(this);
        // コンフィグ読み込み
    	ModLoadingContext.get().
        registerConfig(
        		net.minecraftforge.fml.config.ModConfig.Type.COMMON,
        		MyConfig.spec);
        // Register ourselves for server and other game events we are interested in
    	// メッセージ登録
    	MessageHandler.register();
    	BlockCore.init();
    	ItemCore.init();

    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_GUI_ID_SPAWNRADAR)
	public static ContainerType<ContainerSpawnRadar> CONTAINER_SPAWNRADAR;

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_GUI_ID_BLOCKRADER)
	public static ContainerType<ContainerBlockRadar> CONTAINER_BLOCKRADER;

	@ObjectHolder(ModCommon.MOD_ID + ":" + ModCommon.MOD_GUI_ID_MISSILE)
	public static ContainerType<ContainerMissile> CONTAINER_MISSILE;

    private void doClientStuff(final FMLClientSetupEvent event) {
        proxy.registerRenderInfomation();
    	guiHandler();
    }

	@OnlyIn(Dist.CLIENT)
	public void guiHandler(){
		ScreenManager.registerFactory(CONTAINER_SPAWNRADAR, GuiSpawnRadar::new);
		ScreenManager.registerFactory(CONTAINER_BLOCKRADER, GuiBlockRadar::new);
		ScreenManager.registerFactory(CONTAINER_MISSILE, GuiMissile::new);
	}

	public void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int index = extraData.readInt();
			return new ContainerSpawnRadar(CONTAINER_SPAWNRADAR,wid,index);
		}).setRegistryName(ModCommon.MOD_GUI_ID_SPAWNRADAR));


		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			String name = extraData.readString();
			int size = extraData.readInt();
			return new ContainerBlockRadar(CONTAINER_BLOCKRADER,wid, name, size);
		}).setRegistryName(ModCommon.MOD_GUI_ID_BLOCKRADER));


		event.getRegistry().register(IForgeContainerType.create((wid, playerInv, extraData)->{
			int id = extraData.readInt();
			Entity ent = playerInv.player.world.getEntityByID(id);
			if (ent instanceof EntityMissile) {
				return new ContainerMissile(CONTAINER_MISSILE,wid, (EntityMissile)ent);
			}
			return null;
		}).setRegistryName(ModCommon.MOD_GUI_ID_MISSILE));
	}

    public void onParticleFactoryRegister(ParticleFactoryRegisterEvent event) {
    	Minecraft.getInstance().particles.registerFactory(RegistryEvents.PARTICLE_LARGESMOKEEX, EntityExBombsSmokeFX.Factory::new);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
        	BlockCore.registerBlock(blockRegistryEvent);
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            BlockCore.registerBlockItem(itemRegistryEvent);
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

        public static BasicParticleType PARTICLE_LARGESMOKEEX;
        public static final String PARTICLE_LARGESMOKEEX_NAME = "particle_largesmokeex";
        @SubscribeEvent
        public static void onParticleTypeRegistyr(final RegistryEvent.Register<ParticleType<?>> event){
        	PARTICLE_LARGESMOKEEX = new BasicParticleType(false);
        	PARTICLE_LARGESMOKEEX.setRegistryName(ModCommon.MOD_ID + ":" + PARTICLE_LARGESMOKEEX_NAME);
        	event.getRegistry().registerAll(PARTICLE_LARGESMOKEEX);
        }


    }


}
