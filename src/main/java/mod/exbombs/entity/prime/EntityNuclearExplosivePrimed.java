/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.entity.prime;

import mod.exbombs.block.BlockCore;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityNuclearExplosivePrimed extends EntityPrime {
	public EntityNuclearExplosivePrimed(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().NCBOMB, world);
	}

	public EntityNuclearExplosivePrimed(EntityType<?> etype, World world) {
		super(EntityCore.Inst().NCBOMB, world);
		this.fuse = 200;
	}

	public EntityNuclearExplosivePrimed(World world, double x, double y, double z) {
		super(EntityCore.Inst().NCBOMB, world, x, y, z);
		this.fuse = 200;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void explode() {
		UtilExproder.createSuperExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 80.0F);
	}

	@Override
	protected Item getPrimeItem() {
		return new ItemStack(BlockCore.block_nuclear).getItem();
	}

}
