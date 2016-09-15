package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.core.ExBombs;
import mod.exbombs.helper.ExBombsGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageShowGui implements IMessageHandler<MessageShowGui, IMessage>, IMessage {

	private int id;

	public MessageShowGui(){
	}

	public MessageShowGui(int id){
		this();
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
	}

	@Override
	public IMessage onMessage(MessageShowGui message, MessageContext ctx) {
	     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
		try {
			new ExBombsGuiHelper().displayGuiByID((EntityPlayer) ExBombs.proxy.getEntityPlayerInstance(), message.id);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
