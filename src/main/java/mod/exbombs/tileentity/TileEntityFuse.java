/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.tileentity;

import mod.exbombs.block.BlockCore;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.network.MessageHandler;
import mod.exbombs.util.MoreExplosivesFuse;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TNTBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TileEntityFuse extends TileEntity implements ITickableTileEntity{
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
	public void read(CompoundNBT CompoundNBT) {
		super.read(CompoundNBT);
		this.isBurning = CompoundNBT.getBoolean("isBurning");
		this.burnTime = CompoundNBT.getInt("burnTime");
	}

	@Override
	public CompoundNBT write(CompoundNBT CompoundNBT) {
		super.write(CompoundNBT);
		CompoundNBT.putBoolean("isBurning", this.isBurning);
		CompoundNBT.putInt("burnTime", this.burnTime);
		return CompoundNBT;
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
		BlockState state = this.world.getBlockState(tagetPos);
		if (state.getBlock() == BlockCore.block_fuse) {
			((TileEntityFuse) this.world.getTileEntity(tagetPos)).setBurning();
		}
		if (state.getBlock() == Blocks.TNT) {
			TNTBlock tnt = ((TNTBlock)(this.world.getBlockState(new BlockPos(x, y, z)).getBlock()));
			tnt.onBlockExploded(state, this.world, tagetPos, new Explosion(this.world,null,(double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,Explosion.Mode.BREAK));
		}
		if (state.getBlock() == BlockCore.block_nuclear) {
			BlockCore.block_nuclear.onBlockExploded(state,this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (state.getBlock() == BlockCore.block_tunnel) {
			BlockCore.block_tunnel.onBlockExploded(state, this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (state.getBlock() == BlockCore.block_chunkeraser) {
			BlockCore.block_chunkeraser.onBlockExploded(state, this.world, tagetPos,
					new MoreExplosivesFuse(this.world, null, (double)pos.getX(),(double)pos.getY(),(double)pos.getZ(),1.0F,false,false));
		}
		if (state.getBlock() == BlockCore.block_muchblockeraser) {
			BlockCore.block_muchblockeraser.onBlockExploded(state, this.world, tagetPos,
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
                TileEntityFuse.this.world.addParticle(ParticleTypes.LAVA,
                		TileEntityFuse.this.pos.getX() + 0.5F +  ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getY() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		TileEntityFuse.this.pos.getZ() + 0.5F + ((Math.random() - 0.5D) / 4.0D),
                		0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public CompoundNBT serializeNBT() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
