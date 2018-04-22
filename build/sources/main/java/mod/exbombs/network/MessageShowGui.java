package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.core.Mod_ExBombs;
import mod.exbombs.helper.ExBombsGuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageShowGui implements IMessageHandler<MessageShowGui, IMessage>, IMessage {

	private int id;
	private Object[] parameter;

	public MessageShowGui(){
	}

	public MessageShowGui(int id, Object[] param){
		this();
		this.id = id;
		this.parameter = param;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		int plength = buf.readInt();
		parameter = new Object[plength];
		for ( int i = 0; i < plength; i++){
			int type = buf.readInt();
			switch(type){
			case 0:
				parameter[i] = new Boolean(buf.readBoolean());
				break;
			case 1:
				parameter[i] = new Integer(buf.readInt());
				break;
			case 2:
				parameter[i] = new Long(buf.readLong());
				break;
			case 3:
				parameter[i] = new Float(buf.readFloat());
				break;
			case 4:
				parameter[i] = new Double(buf.readDouble());
				break;
			case 5:
				int strlen = buf.readInt();
				byte[] str = new byte[strlen];
				buf.readBytes(str);
				parameter[i] = new String(str);
				break;
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(parameter.length);
		for (int i = 0; i < parameter.length; i++){
			if (parameter[i] instanceof Boolean){
				buf.writeInt(0);
				buf.writeBoolean(((Boolean)parameter[i]).booleanValue());
			}else if (parameter[i] instanceof Integer){
				buf.writeInt(1);
				buf.writeInt(((Integer)parameter[i]).intValue());
			}else if (parameter[i] instanceof Long){
				buf.writeInt(2);
				buf.writeLong(((Long)parameter[i]).longValue());
			}else if (parameter[i] instanceof Float){
				buf.writeInt(3);
				buf.writeFloat(((Float)parameter[i]).floatValue());
			}else if (parameter[i] instanceof Double){
				buf.writeInt(4);
				buf.writeDouble(((Double)parameter[i]).doubleValue());
			}else if(parameter[i] instanceof String){
				buf.writeInt(5);
				byte[] dat = ((String)parameter[i]).getBytes();
				buf.writeInt(dat.length);
				buf.writeBytes(dat);
			}
		}
	}

	@Override
	public IMessage onMessage(MessageShowGui message, MessageContext ctx) {
	     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
		try {
			new ExBombsGuiHelper().displayGuiByID((EntityPlayer) Mod_ExBombs.proxy.getEntityPlayerInstance(), message.id, message.parameter);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
}
