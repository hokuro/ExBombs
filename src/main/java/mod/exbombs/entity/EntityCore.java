package mod.exbombs.entity;

import mod.exbombs.core.ModCommon;
import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityCore {
	public static final String ENTITY_ID_NCBOMB = "EntityNuclearExplosivePrimed";
	public static final String ENTITY_ID_TUNNELBOMB = "EntityTunnemExprimed";
	public static final String ENTITY_ID_BOMB = "EntityBomb";
	public static final String ENTITY_ID_WATERBOMB = "EntityWaterBomb";
	public static final String ENTITY_ID_MISSILE = "EntityMissile";
	public static final String ENTITY_ID_CHUNKERASER = "EntityChunkEraser";
	public static final String ENTITY_ID_PAINTBOMB = "EntityPaintBomb";
	public static final String ENTITY_ID_FROZENBOMB = "EntityFrozenBomb";
	public static final String ENTITY_ID_ICICLEBOMB = "EntityIcicleBomb";
	public static final String ENTITY_ID_LAVABOMB = "EntityLavaBomb";
	public static final String TILEENTITY_FUSE = "EntityFuse";

	public static void register(){
		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_BOMB),EntityBomb.class,                   ENTITY_ID_BOMB,        101, Mod_ExBombs.instance, 256, 1, true);
//		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_WATERBOMB),EntityWaterBomb.class,              ENTITY_ID_WATERBOMB,   102, Mod_ExBombs.instance, 256, 1, true);
//		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_FROZENBOMB),EntityFrozenBomb.class,             ENTITY_ID_FROZENBOMB,  103, Mod_ExBombs.instance, 256, 1, true);
//		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_ICICLEBOMB),EntityIcicleBomb.class,             ENTITY_ID_ICICLEBOMB,  104, Mod_ExBombs.instance, 256, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_PAINTBOMB),EntityPaintBomb.class,              ENTITY_ID_PAINTBOMB,   105, Mod_ExBombs.instance, 256, 1, true);
//		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_LAVABOMB),EntityLavaBomb.class,              ENTITY_ID_LAVABOMB,   106, Mod_ExBombs.instance, 256, 1, true);

		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_NCBOMB),EntityNuclearExplosivePrimed.class, ENTITY_ID_NCBOMB,      201, Mod_ExBombs.instance, 256, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_TUNNELBOMB),EntityTunnelExplosivePrimed.class,  ENTITY_ID_TUNNELBOMB,  202, Mod_ExBombs.instance, 256, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_CHUNKERASER),EntityChunkEraserPrimed.class,      ENTITY_ID_CHUNKERASER, 203, Mod_ExBombs.instance, 256, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ModCommon.MOD_ID+":"+ENTITY_ID_MISSILE),EntityMissile.class,                ENTITY_ID_MISSILE,     200, Mod_ExBombs.instance, 256, 1, true);
		Mod_ExBombs.proxy.registerCompnents();
	}

}
