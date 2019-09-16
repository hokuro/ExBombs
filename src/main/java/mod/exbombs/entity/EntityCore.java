package mod.exbombs.entity;

import mod.exbombs.block.BlockCore;
import mod.exbombs.core.ModCommon;
import mod.exbombs.entity.bomb.EntityBomb;
import mod.exbombs.entity.bomb.EntityPaintBomb;
import mod.exbombs.entity.missile.EntityMissile;
import mod.exbombs.entity.prime.EntityChunkEraserPrimed;
import mod.exbombs.entity.prime.EntityNuclearExplosivePrimed;
import mod.exbombs.entity.prime.EntityTunnelExplosivePrimed;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;

public class EntityCore {
	public static final String ENTITY_ID_NCBOMB = "entitynuclearexplosiveprimed";
	public static final String ENTITY_ID_TUNNELBOMB = "entitytunnemexprimed";
	public static final String ENTITY_ID_BOMB = "entitybomb";
	public static final String ENTITY_ID_WATERBOMB = "entitywaterbomb";
	public static final String ENTITY_ID_MISSILE = "entitymissile";
	public static final String ENTITY_ID_CHUNKERASER = "entitychunkeraser";
	public static final String ENTITY_ID_PAINTBOMB = "entitypaintbomb";
	public static final String ENTITY_ID_FROZENBOMB = "entityfrozenbomb";
	public static final String ENTITY_ID_ICICLEBOMB = "entityiciclebomb";
	public static final String ENTITY_ID_LAVABOMB = "entitylavabomb";
	public static final String TILEENTITY_FUSE = "entityfuse";


	public EntityType<EntityBomb> BOMB;
	public EntityType<EntityPaintBomb> PAINTBOMB;

	public EntityType<EntityNuclearExplosivePrimed> NCBOMB;
	public EntityType<EntityTunnelExplosivePrimed> TUNNELBOMB;
	public EntityType<EntityChunkEraserPrimed> CHUNKERASER;
	public EntityType<EntityMissile> MISSILE;

	public TileEntityType<?> FUSE;

	private static final EntityCore instance = new EntityCore();

	public static EntityCore Inst(){
		return instance;
	}

	private EntityCore(){

	}

	public void register(Register<EntityType<?>> event){
		BOMB = EntityType.Builder.<EntityBomb>create(EntityBomb::new,EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F)
    			.setCustomClientFactory(EntityBomb::new)
    			.setCustomClientFactory(EntityBomb::new).build(ModCommon.MOD_ID + ":" + ENTITY_ID_BOMB);
		BOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_BOMB));

		PAINTBOMB = EntityType.Builder.<EntityPaintBomb>create(EntityPaintBomb::new, EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.5F,0.5F)
    			.setCustomClientFactory(EntityPaintBomb::new)
				.build(ModCommon.MOD_ID + ":" + ENTITY_ID_PAINTBOMB);
		PAINTBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_PAINTBOMB));

		NCBOMB = EntityType.Builder.<EntityNuclearExplosivePrimed>create(EntityNuclearExplosivePrimed::new, EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.98F,1.0F)
    			.setCustomClientFactory(EntityNuclearExplosivePrimed::new)
				.build(ModCommon.MOD_ID + ":" + ENTITY_ID_NCBOMB);
		NCBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_NCBOMB));

		TUNNELBOMB = EntityType.Builder.<EntityTunnelExplosivePrimed>create(EntityTunnelExplosivePrimed::new, EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.98F,1.0F)
    			.setCustomClientFactory(EntityTunnelExplosivePrimed::new)
				.build(ModCommon.MOD_ID + ":" + ENTITY_ID_TUNNELBOMB);
		TUNNELBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_TUNNELBOMB));

		CHUNKERASER = EntityType.Builder.<EntityChunkEraserPrimed>create(EntityChunkEraserPrimed::new, EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.98F,1.0F)
    			.setCustomClientFactory(EntityChunkEraserPrimed::new)
				.build(ModCommon.MOD_ID + ":" + ENTITY_ID_CHUNKERASER);
		CHUNKERASER.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_CHUNKERASER));

		MISSILE = EntityType.Builder.<EntityMissile>create(EntityMissile::new, EntityClassification.MISC)
    			.setTrackingRange(256).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(1.0F,3.5F)
    			.setCustomClientFactory(EntityMissile::new)
				.build(ModCommon.MOD_ID + ":" + ENTITY_ID_MISSILE);
		MISSILE.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_MISSILE));

		event.getRegistry().registerAll(BOMB, PAINTBOMB, NCBOMB, TUNNELBOMB, CHUNKERASER, MISSILE);
	}

	public void registerTE(final RegistryEvent.Register<TileEntityType<?>> event) {
		FUSE = TileEntityType.Builder.create(TileEntityFuse::new, BlockCore.block_fuse).build(null);
		FUSE.setRegistryName(ModCommon.MOD_ID, EntityCore.TILEENTITY_FUSE);
		event.getRegistry().register(FUSE);
	}

}
