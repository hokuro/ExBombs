package mod.exbombs.network;

import java.util.function.Supplier;

import mod.exbombs.entity.missile.EntityMissile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageMissileLaunchClient {

	private int entityID;
	private int posX;
	private int posZ;

	public MessageMissileLaunchClient(){
	}

	public MessageMissileLaunchClient(int id, int x, int z){
		this();
		entityID = id;
		posX = x;
		posZ = z;
	}

	public static void encode(MessageMissileLaunchClient pkt, PacketBuffer buf)
	{
		buf.writeInt(pkt.entityID);
		buf.writeInt(pkt.posX);
		buf.writeInt(pkt.posZ);
	}

	public static MessageMissileLaunchClient decode(PacketBuffer buf)
	{
		int id = buf.readInt();
		int x = buf.readInt();
		int z =buf.readInt();
		return new MessageMissileLaunchClient(id,x,z);
	}

	public static class Handler
	{
		public static void handle(final MessageMissileLaunchClient pkt, Supplier<NetworkEvent.Context> ctx)
		{
		     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
	        //PlayerEntity player = SamplePacketMod.proxy.getPlayerEntityInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //PlayerEntity entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.
			try {
				int entityID = pkt.entityID;
				int x = pkt.posX;
				int z = pkt.posZ;
				Entity entity = ctx.get().getSender().world.getEntityByID(entityID);
				if ((entity == null) || (!(entity instanceof EntityMissile))){
					return;
				}
				EntityMissile missile = (EntityMissile)entity;
				if (missile.flying){
					return;
				}
				missile.launch(x, z);
				for (PlayerEntity aplayer : ctx.get().getSender().world.getPlayers()){
					MessageHandler.SendMessage_MissileLaunchServer(entityID,(ServerPlayerEntity)aplayer);
					//Mod_ExBombs.INSTANCE.sendTo(new MessageMissileLaunchServer(entityID), (ServerPlayerEntity)aplayer);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
