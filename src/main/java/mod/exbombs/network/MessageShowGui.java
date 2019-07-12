package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.helper.ExBombsGuiHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageShowGui {

	private int id;
	private Object[] parameter;

	public MessageShowGui(){
	}

	public MessageShowGui(int id, Object[] param){
		this();
		this.id = id;
		this.parameter = param;
	}

	public static void encode(MessageShowGui pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.id);
		Object[] parameter = pkt.parameter;
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

	public static MessageShowGui decode(PacketBuffer buf)
	{
		int id = buf.readInt();
		int plength = buf.readInt();
		Object[] ptm = new Object[plength];
		for ( int i = 0; i < plength; i++){
			int type = buf.readInt();
			switch(type){
			case 0:
				ptm[i] = new Boolean(buf.readBoolean());
				break;
			case 1:
				ptm[i] = new Integer(buf.readInt());
				break;
			case 2:
				ptm[i] = new Long(buf.readLong());
				break;
			case 3:
				ptm[i] = new Float(buf.readFloat());
				break;
			case 4:
				ptm[i] = new Double(buf.readDouble());
				break;
			case 5:
				int strlen = buf.readInt();
				byte[] str = new byte[strlen];
				buf.readBytes(str);
				ptm[i] = new String(str);
				break;
			}
		}
		return new MessageShowGui(id, ptm);
	}

	public static class Handler
	{
		public static void handle(final MessageShowGui pkt, Supplier<NetworkEvent.Context> ctx)
		{
		     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
	        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.
			try {
				new ExBombsGuiHelper().displayGuiByID(ctx.get().getSender(), pkt.id, pkt.parameter);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
