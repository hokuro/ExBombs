package mod.exbombs.entity;

import mod.exbombs.core.ModCommon;
import mod.exbombs.tileentity.TileEntityFuse;
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


	public EntityType<EntityBomb> BOMB;// = EntityType.register(ENTITY_ID_BOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	//public static final EntityType<EntityBomb> WATERBOMB = EntityType.register(ENTITY_ID_WATERBOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	//public static final EntityType<EntityBomb> FROZENBOMB = EntityType.register(ENTITY_ID_FROZENBOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	//public static final EntityType<EntityBomb> ICICLEBOMB = EntityType.register(ENTITY_ID_ICICLEBOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	//public static final EntityType<EntityBomb> LAVABOMB = EntityType.register(ENTITY_ID_LAVABOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	public EntityType<EntityPaintBomb> PAINTBOMB;// = EntityType.register(ENTITY_ID_PAINTBOMB, EntityType.Builder.create(EntityPaintBomb.class, EntityPaintBomb::new));

	public EntityType<EntityNuclearExplosivePrimed> NCBOMB;// = EntityType.register(ENTITY_ID_NCBOMB, EntityType.Builder.create(EntityNuclearExplosivePrimed.class, EntityNuclearExplosivePrimed::new));
	public EntityType<EntityTunnelExplosivePrimed> TUNNELBOMB;// = EntityType.register(ENTITY_ID_TUNNELBOMB, EntityType.Builder.create(EntityTunnelExplosivePrimed.class, EntityTunnelExplosivePrimed::new));
	public EntityType<EntityChunkEraserPrimed> CHUNKERASER;// = EntityType.register(ENTITY_ID_CHUNKERASER, EntityType.Builder.create(EntityChunkEraserPrimed.class, EntityChunkEraserPrimed::new));
	//public static final EntityType<EntityBomb> FUSE = EntityType.register(TILEENTITY_FUSE, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
	public EntityType<EntityMissile> MISSILE;// = EntityType.register(ENTITY_ID_MISSILE, EntityType.Builder.create(EntityMissile.class, EntityMissile::new));

	public TileEntityType<?> FUSE;


	private static final EntityCore instance = new EntityCore();

	public static EntityCore Inst(){
		return instance;
	}

	private EntityCore(){

	}

	public void register(Register<EntityType<?>> event){
//		BOMB = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_BOMB, EntityType.Builder.create(EntityBomb.class, EntityBomb::new));
//		PAINTBOMB = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_PAINTBOMB, EntityType.Builder.create(EntityPaintBomb.class, EntityPaintBomb::new));
//		NCBOMB = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_NCBOMB, EntityType.Builder.create(EntityNuclearExplosivePrimed.class, EntityNuclearExplosivePrimed::new));
//		TUNNELBOMB = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_TUNNELBOMB, EntityType.Builder.create(EntityTunnelExplosivePrimed.class, EntityTunnelExplosivePrimed::new));
//		CHUNKERASER = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_CHUNKERASER, EntityType.Builder.create(EntityChunkEraserPrimed.class, EntityChunkEraserPrimed::new));
//		MISSILE = EntityType.register(ModCommon.MOD_ID + ":" +ENTITY_ID_MISSILE, EntityType.Builder.create(EntityMissile.class, EntityMissile::new));

		BOMB = EntityType.Builder.create(EntityBomb.class, EntityBomb::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_BOMB);
		BOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_BOMB));

		PAINTBOMB = EntityType.Builder.create(EntityPaintBomb.class, EntityPaintBomb::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_PAINTBOMB);
		PAINTBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_PAINTBOMB));

		NCBOMB = EntityType.Builder.create(EntityNuclearExplosivePrimed.class, EntityNuclearExplosivePrimed::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_NCBOMB);
		NCBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_NCBOMB));

		TUNNELBOMB = EntityType.Builder.create(EntityTunnelExplosivePrimed.class, EntityTunnelExplosivePrimed::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_TUNNELBOMB);
		TUNNELBOMB.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_TUNNELBOMB));

		CHUNKERASER = EntityType.Builder.create(EntityChunkEraserPrimed.class, EntityChunkEraserPrimed::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_CHUNKERASER);
		CHUNKERASER.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_CHUNKERASER));

		MISSILE = EntityType.Builder.create(EntityMissile.class, EntityMissile::new).tracker(256, 1, true).build(ModCommon.MOD_ID + ":" + ENTITY_ID_MISSILE);
		MISSILE.setRegistryName(new ResourceLocation(ModCommon.MOD_ID,ENTITY_ID_MISSILE));

		event.getRegistry().registerAll(BOMB, PAINTBOMB, NCBOMB, TUNNELBOMB, CHUNKERASER, MISSILE);
	}

	public void registerTE(final RegistryEvent.Register<TileEntityType<?>> event) {
		FUSE = TileEntityType.Builder.create(TileEntityFuse::new).build(null);
		FUSE.setRegistryName(ModCommon.MOD_ID, EntityCore.TILEENTITY_FUSE);
		event.getRegistry().register(FUSE);
	}

}
