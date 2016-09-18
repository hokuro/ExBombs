package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.item.ItemRadar;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModRegisterItem;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRadarUpdate implements IMessageHandler<MessageRadarUpdate, IMessage>, IMessage {

	private int index;

	public MessageRadarUpdate(){
	}

	public MessageRadarUpdate(int id){
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
	public IMessage onMessage(MessageRadarUpdate message, MessageContext ctx) {
		try{
			((ItemRadar)ModRegisterItem.item_Radar).setRadarData(new ItemStack(ModRegisterItem.item_Radar),
					ctx.getServerHandler().playerEntity.worldObj, message.index);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
