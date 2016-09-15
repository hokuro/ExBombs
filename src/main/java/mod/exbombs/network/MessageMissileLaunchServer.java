package mod.exbombs.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import mod.exbombs.entity.EntityMissile;
import mod.exbombs.gui.GuiMissile;
import mod.exbombs.helper.ExBombsGuiHelper;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMissileLaunchServer implements IMessageHandler<MessageMissileLaunchServer, IMessage>, IMessage {

	private int entityID;

	public MessageMissileLaunchServer(){
	}

	public MessageMissileLaunchServer(int id){
		this();
		this.entityID = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}

	@Override
	public IMessage onMessage(MessageMissileLaunchServer message, MessageContext ctx) {
	     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
		try {
			int entityID;
			entityID = message.entityID;
			Entity entity = getEntityByID(entityID, ExBombsMinecraftHelper.getWorld());
			if (entity == null) {
				return null;
			}
			((EntityMissile) entity).flying = true;
			if (!new ExBombsGuiHelper().isGuiOpen(GuiMissile.class)) {
				return null;
			}
			if (((GuiMissile) new ExBombsGuiHelper().getCurrentGui()).missile.getEntityId() == entityID) {
				new ExBombsGuiHelper().closeGui();
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
