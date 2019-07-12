package mod.exbombs.network;

import mod.exbombs.core.ModCommon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MessageHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	private static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(ModCommon.MOD_ID, ModCommon.MOD_CHANEL))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();

	public static void register()
	{
		int disc = 0;

		HANDLER.registerMessage(disc++, MessageFuseSetBurn.class, MessageFuseSetBurn::encode, MessageFuseSetBurn::decode, MessageFuseSetBurn.Handler::handle);
		HANDLER.registerMessage(disc++, MessageMissileLaunchClient.class, MessageMissileLaunchClient::encode, MessageMissileLaunchClient::decode, MessageMissileLaunchClient.Handler::handle);
		HANDLER.registerMessage(disc++, MessageMissileLaunchServer.class, MessageMissileLaunchServer::encode, MessageMissileLaunchServer::decode, MessageMissileLaunchServer.Handler::handle);
		HANDLER.registerMessage(disc++, MessageShowGui.class, MessageShowGui::encode, MessageShowGui::decode, MessageShowGui.Handler::handle);
		HANDLER.registerMessage(disc++, MessageRadarUpdate.class, MessageRadarUpdate::encode, MessageRadarUpdate::decode, MessageRadarUpdate.Handler::handle);
		HANDLER.registerMessage(disc++, MessageBlockRadarUpdate.class, MessageBlockRadarUpdate::encode, MessageBlockRadarUpdate::decode, MessageBlockRadarUpdate.Handler::handle);

	}

	public static void SendMessage_BlockRadarUpdate(int index){
		HANDLER.sendToServer(new MessageBlockRadarUpdate(index));
	}

	public static void SendMessage_MissileLaunchClient(int missileid, int x, int z){
		HANDLER.sendToServer(new MessageMissileLaunchClient(missileid, x, z));
	}

	public static void SendMessage_RadarUpdate(int index) {
		HANDLER.sendToServer(new MessageRadarUpdate(index));

	}

	public static void SendMessageShowGui(int guiid, Object[] objects) {
		HANDLER.sendToServer(new MessageShowGui(guiid, objects));

	}

	public static void SendMessage_useSetBurn(int x, int y, int z) {
		HANDLER.sendToServer(new MessageFuseSetBurn(x, y, z));
	}

	public static void SendMessage_MissileLaunchServer(int entityID, EntityPlayerMP aplayer) {
		HANDLER.sendTo(new MessageMissileLaunchServer(entityID), aplayer.connection.netManager,NetworkDirection.PLAY_TO_CLIENT);
	}
}
