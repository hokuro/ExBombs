package mod.exbombs.entity;

import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class EntityPaintBomb extends EntityBomb {
	private IBlockState block;

	public EntityPaintBomb(World world) {
		super(world);
		this.bombType = EnumBombType.PAINT;
	}

	public EntityPaintBomb(World world, EntityLivingBase entityliving, IBlockState changeBlock) {
		super(world, entityliving);
		this.block = changeBlock;
	}

	@Override
	public EnumBombType getBombType(){
		return EnumBombType.PAINT;
	}

	public IBlockState getBlockState() {
		if (block == null){
			return Blocks.air.getDefaultState();
		}
		return block;
	}
}
