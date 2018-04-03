package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemSpawnerRadar;
import net.minecraft.item.ItemStack;
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
			((ItemSpawnerRadar)ItemCore.item_Radar).setRadarData(new ItemStack(ItemCore.item_Radar),
					ctx.getServerHandler().player.world, message.index);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

}
