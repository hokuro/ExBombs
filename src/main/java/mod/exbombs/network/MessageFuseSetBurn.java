package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.helper.ExBombsMinecraftHelper;
import mod.exbombs.tileentity.TileEntityFuse;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageFuseSetBurn {

	private int posX,posY,posZ;

	public MessageFuseSetBurn(){
	}

	public MessageFuseSetBurn(int x, int y, int z){
		this();
		posX = x;
		posY = y;
		posZ = z;
	}

	public static void encode(MessageFuseSetBurn pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.posX);
		buf.writeInt(pkt.posY);
		buf.writeInt(pkt.posZ);
	}

	public static MessageFuseSetBurn decode(PacketBuffer buf)
	{
		int x = buf.readInt();
		int y = buf.readInt();
		int z =buf.readInt();
		return new MessageFuseSetBurn(x,y,z);
	}

	public static class Handler
	{
		public static void handle(final MessageFuseSetBurn pkt, Supplier<NetworkEvent.Context> ctx)
		{
			try {
				((TileEntityFuse) ExBombsMinecraftHelper.getWorld().getTileEntity(new BlockPos(pkt.posX, pkt.posY, pkt.posZ))).isBurning = true;
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
