package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.item.ItemBlockRadar;
import mod.exbombs.item.ItemCore;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageBlockRadarUpdate{

	private int index;

	public MessageBlockRadarUpdate(){
	}

	public MessageBlockRadarUpdate(int id){
		this();
		this.index = id;
	}

	public static void encode(MessageBlockRadarUpdate pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.index);
	}

	public static MessageBlockRadarUpdate decode(PacketBuffer buf)
	{
		int index = buf.readInt();
		return new MessageBlockRadarUpdate(index);
	}

	public static class Handler
	{
		public static void handle(final MessageBlockRadarUpdate pkt, Supplier<NetworkEvent.Context> ctx)
		{
			try{
				ItemBlockRadar.setRadarSize(new ItemStack(ItemCore.item_blockRadar),
						ctx.get().getSender().world, pkt.index);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}
