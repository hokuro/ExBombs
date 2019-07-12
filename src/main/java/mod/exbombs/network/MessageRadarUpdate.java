package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.item.ItemCore;
import mod.exbombs.item.ItemSpawnerRadar;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRadarUpdate {

	private int index;

	public MessageRadarUpdate(){
	}

	public MessageRadarUpdate(int id){
		this();
		this.index = id;
	}

	public static void encode(MessageRadarUpdate pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.index);
	}

	public static MessageRadarUpdate decode(PacketBuffer buf)
	{
		int idx = buf.readInt();
		return new MessageRadarUpdate(idx);
	}

	public static class Handler
	{
		public static void handle(final MessageRadarUpdate pkt, Supplier<NetworkEvent.Context> ctx)
		{
			try{
				ItemSpawnerRadar.setRadarData(new ItemStack(ItemCore.item_Radar),
						ctx.get().getSender().world, pkt.index);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

}
