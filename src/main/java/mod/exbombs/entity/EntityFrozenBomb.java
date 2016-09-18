package mod.exbombs.entity;

import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityFrozenBomb  extends EntityBomb {

	public EntityFrozenBomb(World world) {
		super(world);
		this.bombType = EnumBombType.FROZEN;
	}

	public EntityFrozenBomb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
		this.bombType = EnumBombType.FROZEN;
	}

	public EntityFrozenBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.bombType = EnumBombType.FROZEN;
	}

	public EntityFrozenBomb(World world, EntityLivingBase entityliving, EnumBombType type) {
		super(world, entityliving);
		this.bombType = type;
	}

	@Override
	public EnumBombType getBombType(){
		return EnumBombType.FROZEN;
	}
}
