package mod.exbombs.entity.bomb;

import mod.exbombs.config.MyConfig;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.util.EnumBombType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityPaintBomb extends EntityBomb {
	private BlockState block;

	public EntityPaintBomb(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().PAINTBOMB, world);
	}

	public EntityPaintBomb(EntityType<? extends ThrowableEntity> etype, World world) {
		super(etype, world);
		this.bombType = EnumBombType.PAINT;
	}

	public EntityPaintBomb(World world, LivingEntity entityliving, BlockState changeBlock) {
		super(EntityCore.Inst().PAINTBOMB, world,  entityliving, EnumBombType.PAINT);
		this.block = changeBlock;
		this.bombType = EnumBombType.PAINT;
	}

	@Override
	public EnumBombType getBombType(){
		return EnumBombType.PAINT;
	}

	public BlockState getBlockState() {
		if (block == Blocks.AIR.getDefaultState()){
			return Blocks.AIR.getDefaultState();
		}

		BlockState retBlock = block;

		if (!this.world.isRemote && !MyConfig.GENERAL.cheat_paint.get()
			&& ((this.owner instanceof PlayerEntity) && !((PlayerEntity)owner).isCreative())){
			this.owner.getHeldItem(Hand.OFF_HAND).shrink(1);
			if (this.owner.getHeldItem(Hand.OFF_HAND).isEmpty()){
				this.block = Blocks.AIR.getDefaultState();
			}
		}

		return retBlock;
	}

	public BlockState checkNextBlock() {
		if (block == null){
			return Blocks.AIR.getDefaultState();
		}
		return block;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
