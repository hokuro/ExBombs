package mod.exbombs.entity;

import mod.exbombs.config.MyConfig;
import mod.exbombs.util.EnumBombType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityPaintBomb extends EntityBomb {
	private IBlockState block;

	public EntityPaintBomb(World world) {
		super(EntityCore.Inst().PAINTBOMB,world,EnumBombType.PAINT);
	}

	public EntityPaintBomb(World world, EntityLivingBase entityliving, IBlockState changeBlock) {
		super(EntityCore.Inst().PAINTBOMB, entityliving,world);
		this.block = changeBlock;
		this.bombType = EnumBombType.PAINT;
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

		if (!this.world.isRemote && !MyConfig.GENERAL.cheat_paint.get()
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
