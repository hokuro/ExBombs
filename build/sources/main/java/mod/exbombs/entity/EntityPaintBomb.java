package mod.exbombs.entity;

import mod.exbombs.config.ConfigValue;
import mod.exbombs.util.MoreExplosivesBetterExplosion.EnumBombType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
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
		if (block == Blocks.AIR){
			return Blocks.AIR.getDefaultState();
		}

		IBlockState retBlock = block;

		if (!this.world.isRemote && !ConfigValue.General.cheat_paint
			&& ((this.thrower instanceof EntityPlayer) && !((EntityPlayer)thrower).isCreative())){
			this.thrower.getHeldItem(EnumHand.OFF_HAND).shrink(1);
			if (this.thrower.getHeldItem(EnumHand.OFF_HAND).isEmpty()){
				this.block = Blocks.AIR.getDefaultState();
			}
		}

		return retBlock;
	}

	public IBlockState checkNextBlock() {
		if (block == null){
			return Blocks.AIR.getDefaultState();
		}
		return block;
	}
}
