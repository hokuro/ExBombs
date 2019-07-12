/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.tileentity;

import mod.exbombs.block.BlockCore;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.BlockTNT;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TileEntityFuse extends TileEntity implements ITickable{
	public boolean isBurning;
	public int burnTime;
	private ParticleHelper helper;

	public TileEntityFuse(){
		this(EntityCore.Inst().FUSE);
	}

	public TileEntityFuse(TileEntityType tetype){
		super(tetype);

		this.helper = new ParticleHelper();
	}

	public TileEntityFuse(World world) {
		this();
	}

	@Override
	public void read(NBTTagCompound nbttagcompound) {
		super.read(nbttagcompound);
		this.isBurning = nbttagcompound.getBoolean("isBurning");
		this.burnTime = nbttagcompound.getInt("burnTime");
	}

	@Override
	public NBTTagCompound write(NBTTagCompound nbttagcompound) {
		super.write(nbttagcompound);
		nbttagcompound.setBoolean("isBurning", this.isBurning);
		nbttagcompound.setInt("burnTime", this.burnTime);
		return nbttagcompound;
	}

	@Override
	public void tick() {
		if (this.isBurning) {
			this.burnTime += 1;
			if ((this.burnTime > 10) && (!this.world.isRemote)) {
				tryIgnite(this.pos.getX(),     this.pos.getY(),     this.pos.getZ() + 1);
				tryIgnite(this.pos.getX(),     this.pos.getY(),     this.pos.getZ() - 1);
				tryIgnite(this.pos.getX(),     this.pos.getY() + 1, this.pos.getZ());
				tryIgnite(this.pos.getX(),     this.pos.getY() - 1, this.pos.getZ());
				tryIgnite(this.pos.getX() + 1, this.pos.getY(),     this.pos.getZ());
				tryIgnite(this.pos.getX() - 1, this.pos.getY(),     this.pos.getZ());
				this.world.setBlockState(this.getPos(), Blocks.AIR.getDefaultState());
			}
			if (this.world.isRemote) {
				this.helper.spawn();
			}
		}
	}

	public void tryIgnite(int x, int y, int z) {
		BlockPos tagetPos = new BlockPos(x,y,z);
		if (this.world.getBlockState(tagetPos).getBlock() == BlockCore.block_fuse) {
			((TileEntityFuse) this.world.getTileEntity(tagetPos)).setBurning();
		}
		if (this.world.getBlockState(tagetPos).getBlock() == Blocks.TNT) {
			BlockTNT tnt = ((BlockTNT)(this.world.getBlockState(new BlockPos(x, y, z)).getBlock()));
			tnt.onBlockExploded(Blocks.TNT.getDefaultState(), this.world, tagetPos, new Explosion(this.world,null,(double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.world.getBlockState(tagetPos).getBlock() == BlockCore.block_nuclear) {
			BlockCore.block_nuclear.onBlockExploded(BlockCore.block_nuclear.getDefaultState(),this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.world.getBlockState(tagetPos).getBlock() == BlockCore.block_tunnel) {
			BlockCore.block_tunnel.onBlockExploded(BlockCore.block_tunnel.getDefaultState(), this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.world.getBlockState(tagetPos).getBlock() == BlockCore.block_chunkeraser) {
			BlockCore.block_chunkeraser.onBlockExploded(BlockCore.block_chunkeraser.getDefaultState(), this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (this.world.getBlockState(tagetPos).getBlock() == BlockCore.block_muchblockeraser) {
			BlockCore.block_muchblockeraser.onBlockExploded(BlockCore.block_muchblockeraser.getDefaultState(), this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
	}

	public void setBurning() {
		this.isBurning = true;
		MessageHandler.SendMessage_useSetBurn(this.pos.getX(), this.pos.getY(), this.pos.getZ());
		//Mod_ExBombs.INSTANCE.sendToServer(new MessageFuseSetBurn(this.pos.getX(), this.pos.getY(), this.pos.getZ()));
	}

	private class ParticleHelper {
		private ParticleHelper() {
		}

		public void spawn() {
			for (int iterator = 0; iterator < 3; iterator++) {
                TileEntityFuse.this.world.spawnParticle(Particles.LAVA,
                		TileEntityFuse.this.pos.getX() + 0.5F +  ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getY() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getZ() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
