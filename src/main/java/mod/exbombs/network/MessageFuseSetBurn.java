package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFuseSetBurn implements IMessageHandler<MessageFuseSetBurn, IMessage>, IMessage {

	private int posX,posY,posZ;

	public MessageFuseSetBurn(){
	}

	public MessageFuseSetBurn(int x, int y, int z){
		this();
		posX = x;
		posY = y;
		posZ = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		posX = buf.readInt();
		posY = buf.readInt();
		posZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

	@Override
	public IMessage onMessage(MessageFuseSetBurn message, MessageContext ctx) {
		try {
			((TileEntityFuse) ExBombsMinecraftHelper.getWorld().getTileEntity(new BlockPos(message.posX, message.posY, message.posZ))).isBurning = true;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
