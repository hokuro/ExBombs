package mod.exbombs.network;

import java.util.List;
import java.util.function.Supplier;

import mod.exbombs.entity.EntityMissile;
import mod.exbombs.gui.GuiMissile;
import mod.exbombs.helper.ExBombsGuiHelper;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
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
	        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.
			try {
				int entityID;
				entityID = pkt.entityID;
				Entity entity = getEntityByID(entityID, ExBombsMinecraftHelper.getWorld());
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
