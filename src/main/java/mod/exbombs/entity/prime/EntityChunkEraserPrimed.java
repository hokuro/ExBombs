package mod.exbombs.entity.prime;

import mod.exbombs.block.BlockChunkEraserExplosive.EnumEraseType;
import mod.exbombs.block.BlockCore;
import mod.exbombs.entity.EntityCore;
import mod.exbombs.util.UtilExproder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class EntityChunkEraserPrimed extends EntityPrime{

	private EnumEraseType eraseType;

	public EntityChunkEraserPrimed(FMLPlayMessages.SpawnEntity packet, World world) {
		this(EntityCore.Inst().CHUNKERASER, world);
	}

	public EntityChunkEraserPrimed(EntityType<?> etype, World worldIn) {
		super(EntityCore.Inst().CHUNKERASER, worldIn);
	}

	public EntityChunkEraserPrimed(IWorld worldIn, double x, double y, double z, EnumEraseType type) {
		super(EntityCore.Inst().CHUNKERASER, worldIn.getWorld(), x, y ,z);
		eraseType = type;
	}

	@Override
	public void writeSpawnData(PacketBuffer buffer) {
		super.writeSpawnData(buffer);
		buffer.writeInt(this.eraseType.getIndex());
	}

	@Override
	public void readSpawnData(PacketBuffer additionalData) {
		super.readSpawnData(additionalData);
		this.eraseType = EnumEraseType.getType(additionalData.readInt());
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompund) {
		super.readAdditional(tagCompund);
		this.eraseType = EnumEraseType.getType(tagCompund.getInt("Type"));
	}

	@Override
	protected void writeAdditional(CompoundNBT tagCompound) {
		super.writeAdditional(tagCompound);
		tagCompound.putInt("Type", eraseType.getIndex());
	}

	public EnumEraseType getEraseType(){
		return eraseType;
	}

	protected void explode() {
		UtilExproder.createEraserExplosion(this.world, null, (int) this.posX, (int) this.posY, (int) this.posZ, 0.0F, eraseType);
	}

	@Override
	protected Item getPrimeItem() {
		Item ret = ItemStack.EMPTY.getItem();
		switch(eraseType){
		case ERASEALL:
			ret = new ItemStack(BlockCore.block_chunkeraser).getItem();
			break;
		case ERASEUNMATCH:
			ret = new ItemStack(BlockCore.block_muchblockeraser).getItem();
			break;
		}
		return ret;
	}
}
