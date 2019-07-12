package mod.exbombs.network;

import java.util.List;
import java.util.function.Supplier;

import mod.exbombs.entity.EntityMissile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
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
	        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.
			try {
				int entityID = pkt.entityID;
				int x = pkt.posX;
				int z = pkt.posZ;
				Entity entity = getEntityByID(entityID, ctx.get().getSender().world);
				if ((entity == null) || (!(entity instanceof EntityMissile))){
					return;
				}
				EntityMissile missile = (EntityMissile)entity;
				if (missile.flying){
					return;
				}
				missile.launch(x, z);
				for (EntityPlayer aplayer : ctx.get().getSender().world.playerEntities){
					MessageHandler.SendMessage_MissileLaunchServer(entityID,(EntityPlayerMP)aplayer);
					//Mod_ExBombs.INSTANCE.sendTo(new MessageMissileLaunchServer(entityID), (EntityPlayerMP)aplayer);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		public static Entity getEntityByID(int ID, World world) {
			List<Entity> entities = world.loadedEntityList;
			for (Entity entity : entities) {
				if (entity.getEntityId() == ID) {
					return entity;
				}
			}
			return null;
		}
	}
}
