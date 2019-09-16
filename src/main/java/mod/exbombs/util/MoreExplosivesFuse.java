package mod.exbombs.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class MoreExplosivesFuse  extends Explosion {

	public MoreExplosivesFuse(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking) {
		super(worldIn, entityIn, x, y, z, size, flaming, Explosion.Mode.NONE);
	}

}
