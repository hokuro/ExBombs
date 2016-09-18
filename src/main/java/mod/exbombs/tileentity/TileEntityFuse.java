/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.tileentity;

import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.network.MessageFuseSetBurn;
import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.ModRegisterBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TileEntityFuse extends TileEntity implements ITickable{
	public boolean isBurning;
	public int burnTime;
	private ParticleHelper helper;

	public TileEntityFuse(World world) {
		if (world.isRemote) {
			this.helper = new ParticleHelper();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.isBurning = nbttagcompound.getBoolean("isBurning");
		this.burnTime = nbttagcompound.getInteger("burnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("isBurning", this.isBurning);
		nbttagcompound.setInteger("burnTime", this.burnTime);
	}

	@Override
	public void update() {
		if (this.isBurning) {
			this.burnTime += 1;
			if ((this.burnTime > 10) && (!this.worldObj.isRemote)) {
				tryIgnite(this.pos.getX(),     this.pos.getY(),     this.pos.getZ() + 1);
				tryIgnite(this.pos.getX(),     this.pos.getY(),     this.pos.getZ() - 1);
				tryIgnite(this.pos.getX(),     this.pos.getY() + 1, this.pos.getZ());
				tryIgnite(this.pos.getX(),     this.pos.getY() - 1, this.pos.getZ());
				tryIgnite(this.pos.getX() + 1, this.pos.getY(),     this.pos.getZ());
				tryIgnite(this.pos.getX() - 1, this.pos.getY(),     this.pos.getZ());
				this.worldObj.setBlockState(this.getPos(), Blocks.air.getDefaultState());
			}
			if (this.worldObj.isRemote) {
				this.helper.spawn();
			}
		}
	}

	public void tryIgnite(int x, int y, int z) {
		BlockPos tagetPos = new BlockPos(x,y,z);
		if (this.worldObj.getBlockState(tagetPos).getBlock() == ModRegisterBlock.block_Fuse) {
			((TileEntityFuse) this.worldObj.getTileEntity(tagetPos)).setBurning();
		}
		if (this.worldObj.getBlockState(tagetPos).getBlock() == Blocks.tnt) {
			BlockTNT tnt = ((BlockTNT)(this.worldObj.getBlockState(new BlockPos(x, y, z)).getBlock()));
			tnt.onBlockExploded(this.worldObj, tagetPos, new Explosion(this.worldObj,null,(double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.worldObj.getBlockState(tagetPos).getBlock() == ModRegisterBlock.block_NCBomb) {
			ModRegisterBlock.block_NCBomb.onBlockExploded(this.worldObj, tagetPos,
					new MoreExplosivesFuse(this.worldObj, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.worldObj.getBlockState(tagetPos).getBlock() == ModRegisterBlock.bolock_TunnelBomb) {
			ModRegisterBlock.bolock_TunnelBomb.onBlockExploded(this.worldObj, tagetPos,
					new MoreExplosivesFuse(this.worldObj, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.worldObj.getBlockState(tagetPos).getBlock() == ModRegisterBlock.block_eraser) {
			ModRegisterBlock.block_eraser.onBlockExploded(this.worldObj, tagetPos,
					new MoreExplosivesFuse(this.worldObj, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.worldObj.getBlockState(tagetPos).getBlock() == ModRegisterBlock.block_unmach) {
			ModRegisterBlock.block_unmach.onBlockExploded(this.worldObj, tagetPos,
					new MoreExplosivesFuse(this.worldObj, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
	}

	public void setBurning() {
		this.isBurning = true;
		Mod_ExBombs.INSTANCE.sendToServer(new MessageFuseSetBurn(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
	}

	private class ParticleHelper {
		private ParticleHelper() {
		}

		public void spawn() {
			for (int iterator = 0; iterator < 3; iterator++) {
                TileEntityFuse.this.worldObj.spawnParticle(EnumParticleTypes.LAVA,
                		TileEntityFuse.this.pos.getX() + 0.5F +  ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getY() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getZ() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

}
