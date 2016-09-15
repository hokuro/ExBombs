package mod.exbombs.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import mod.exbombs.core.ExBombs;
import mod.exbombs.entity.EntityMissile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMissileLaunchClient implements IMessageHandler<MessageMissileLaunchClient, IMessage>, IMessage {

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

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		posX = buf.readInt();
		posZ = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(posX);
		buf.writeInt(posZ);
	}

	@Override
	public IMessage onMessage(MessageMissileLaunchClient message, MessageContext ctx) {
	     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
		try {
			int entityID = message.entityID;
			int x = message.posX;
			int z = message.posZ;
			Entity entity = getEntityByID(entityID, ctx.getServerHandler().playerEntity.worldObj);
			if ((entity == null) || (!(entity instanceof EntityMissile))){
				return null;
			}
			EntityMissile missile = (EntityMissile)entity;
			if (missile.flying){
				return null;
			}
			missile.launch(x, z);
			for (EntityPlayer aplayer : ctx.getServerHandler().playerEntity.worldObj.playerEntities){
				ExBombs.INSTANCE.sendTo(new MessageMissileLaunchServer(entityID), (EntityPlayerMP)aplayer);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public Entity getEntityByID(int ID, World world) {
		List<Entity> entities = world.loadedEntityList;
		for (Entity entity : entities) {
			if (entity.getEntityId() == ID) {
				return entity;
			}
		}
		return null;
	}
}
