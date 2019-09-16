package mod.exbombs.util;

import java.util.Iterator;
import java.util.Random;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.config.MyConfig;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.entity.bomb.EntityBomb;
import mod.exbombs.entity.bomb.EntityPaintBomb;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class UtilExproder {
	public static ThrowableEntity createBombEntity(World world, PlayerEntity player, ItemStack itemStackIn, EnumBombType type){
		ThrowableEntity bomb = null;
		switch(type){
		case BOMB:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,EnumBombType.BOMB);
			break;
		case WARTER:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,type);
			break;
		case FROZEN:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,type);
			break;
		case ICICLE:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,type);
			break;
		case PAINT:
			ItemStack heldItem = player.getHeldItemOffhand();
			if (!heldItem.isEmpty() && Block.getBlockFromItem(heldItem.getItem()) != Blocks.AIR){
				BlockState block = Block.getBlockFromItem(heldItem.getItem()).getDefaultState();
				bomb = new EntityPaintBomb(world,player,block);
			}
			break;
		case LAVA:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,type);
			break;
		default:
			bomb = new EntityBomb(EntityCore.Inst().BOMB, world,player,EnumBombType.BOMB);
			break;
		}
		return bomb;
	}



	// 爆発
	public static void createExplesion(World worldObj, Entity entity, double x, double y, double z, float size, boolean isFlaming, boolean enableDrops, EnumBombType type){
		MoreExplosivesBetterExplosion explosion =
				new MoreExplosivesBetterExplosion(worldObj, entity, x, y, z, size, type, enableDrops);
		explosion.isFlaming = isFlaming;
		explosion.CanDestroyBlock = type==EnumBombType.PRIME?true:MyConfig.GENERAL.bomb_destroy_block.get();
		explosion.doExplosionA();
		explosion.doExplosionB(true);
		sendClientFXPacket(worldObj, x, y, z, size, explosion);
	}

	public static void createSuperExplosion(World worldObj, Entity entity, int x, int y, int z, float size) {
		long seed = new Random().nextLong();
		MoreExplosivesSuperExplosion ex;
		(ex=new MoreExplosivesSuperExplosion()).explode(worldObj, size, size, size, x, y, z, seed);
		sendSuperClientFXPacket(worldObj, x, y, z, seed, size,ex);
	}

	public static void createTunnelExplosion(World worldObj, Entity entity, double x, double y, double z, Direction direction) {
		MoreExplosivesTunnelExplosion tunnelexplosion = new MoreExplosivesTunnelExplosion(worldObj, entity, x, y, z, direction);
		tunnelexplosion.doExplosionA();
		tunnelexplosion.doExplosionB(true);
		sendClientFXPacket(worldObj, x, y, z, 0.0F, tunnelexplosion);
	}

	public static void createEraserExplosion(World worldObj, Entity entity, int x, int y, int z, float size, EnumEraseType eraseType) {
		MoreExplosiveEraseExplosion betterexplosion = new MoreExplosiveEraseExplosion(worldObj, entity, eraseType==EnumEraseType.ERASEUNMATCH);
		betterexplosion.explode(worldObj, x, y, z);
		sendEraseClientFXPacket(worldObj, x, y, z,eraseType, size, betterexplosion);
	}

	//通信関係
	private static void sendClientFXPacket(World world, double x, double y, double z, float size, Explosion ex) {
		Iterator players = world.getPlayers().iterator();
		while (players.hasNext()) {
			PlayerEntity player = (PlayerEntity) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				((ServerPlayerEntity)player).connection.sendPacket(new SExplosionPacket((double)x, (double)y, (double)z, size,
						 ex.getAffectedBlockPositions(), (Vec3d)ex.getPlayerKnockbackMap().get(player)));
			}
		}
	}

	private static void sendSuperClientFXPacket(World world, double x, double y, double z, long seed, float size, Explosion ex) {
		Iterator players = world.getPlayers().iterator();
		while (players.hasNext()) {
			PlayerEntity player = (PlayerEntity) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				 ((ServerPlayerEntity)player).connection.sendPacket(new SExplosionPacket((double)x, (double)y, (double)z, size,
						 ex.getAffectedBlockPositions(), (Vec3d)ex.getPlayerKnockbackMap().get(player)));
			}
		}
	}

	private static void sendEraseClientFXPacket(World world, double x, double y, double z, EnumEraseType type, float size, Explosion ex) {
		Iterator players = world.getPlayers().iterator();
		while (players.hasNext()) {
			PlayerEntity player = (PlayerEntity) players.next();
			if (player.getDistanceSq(x, y, z) < 4096.0D) {
				if (type == EnumEraseType.ERASEALL){
					((ServerPlayerEntity)player).connection.sendPacket(new SExplosionPacket((double)x, (double)y, (double)z, size,
							 ex.getAffectedBlockPositions(), (Vec3d)ex.getPlayerKnockbackMap().get(player)));
					//INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.ERASE.getType(),(int) x, (int) y, (int) z, 0L, 0F), (ServerPlayerEntity)player);
				}else{
					((ServerPlayerEntity)player).connection.sendPacket(new SExplosionPacket((double)x, (double)y, (double)z, size,
							 ex.getAffectedBlockPositions(), (Vec3d)ex.getPlayerKnockbackMap().get(player)));
					//INSTANCE.sendTo(new MessageExplosion(EnumExplosionType.MACHING.getType(),(int) x, (int) y, (int) z, 0L, 0F), (ServerPlayerEntity)player);
				}
			}
		}
	}
}
