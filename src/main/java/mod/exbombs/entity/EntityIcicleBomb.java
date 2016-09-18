package mod.exbombs.entity;

import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityIcicleBomb extends EntityBomb {

	public EntityIcicleBomb(World world) {
		super(world);
		this.bombType = EnumBombType.ICICLE;
	}

	public EntityIcicleBomb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
		this.bombType = EnumBombType.ICICLE;
	}

	public EntityIcicleBomb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
		this.bombType = EnumBombType.ICICLE;
	}

	public EntityIcicleBomb(World world, EntityLivingBase entityliving, EnumBombType type) {
		super(world, entityliving);
		this.bombType = type;
	}

	@Override
	public EnumBombType getBombType(){
		return EnumBombType.ICICLE;
	}
}