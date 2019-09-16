package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.entity.missile.EntityMissile;
import mod.exbombs.gui.GuiMissile;
import mod.exbombs.helper.ExBombsGuiHelper;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageMissileLaunchServer {

	private int entityID;

	public MessageMissileLaunchServer(){
	}

	public MessageMissileLaunchServer(int id){
		this();
		this.entityID = id;
	}

	public static void encode(MessageMissileLaunchServer pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.entityID);
	}

	public static MessageMissileLaunchServer decode(PacketBuffer buf)
	{
		int id= buf.readInt();
		return new MessageMissileLaunchServer(id);
	}

	public static class Handler
	{
		public static void handle(final MessageMissileLaunchServer pkt, Supplier<NetworkEvent.Context> ctx)
		{
		     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
	        //PlayerEntity player = SamplePacketMod.proxy.getPlayerEntityInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //PlayerEntity entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.
			try {
				int entityID;
				entityID = pkt.entityID;
				Entity entity = ExBombsMinecraftHelper.getWorld().getEntityByID(entityID);
				if (entity == null) {
					return;
				}
				((EntityMissile) entity).flying = true;
				if (!new ExBombsGuiHelper().isGuiOpen(GuiMissile.class)) {
					return;
				}
				if (((GuiMissile) new ExBombsGuiHelper().getCurrentGui()).missile.getEntityId() == entityID) {
					new ExBombsGuiHelper().closeGui();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
