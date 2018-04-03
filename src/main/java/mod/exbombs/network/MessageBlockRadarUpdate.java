package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.item.ItemBlockRadar;
import mod.exbombs.item.ItemCore;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageBlockRadarUpdate implements IMessageHandler<MessageBlockRadarUpdate, IMessage>, IMessage {

	private int index;

	public MessageBlockRadarUpdate(){
	}

	public MessageBlockRadarUpdate(int id){
		this();
		this.index = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(index);
	}

	@Override
	public IMessage onMessage(MessageBlockRadarUpdate message, MessageContext ctx) {
		try{
			((ItemBlockRadar)ItemCore.item_blockRadar).setRadarSize(new ItemStack(ItemCore.item_blockRadar),
					ctx.getServerHandler().player.world, message.index);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
