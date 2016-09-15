package mod.exbombs.network;

import io.netty.buffer.ByteBuf;
import mod.exbombs.core.MoreExplosiveEraseExplosion;
import mod.exbombs.core.MoreExplosivesSuperExplosion;
import mod.exbombs.helper.ExBombsMinecraftHelper;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExplosion implements IMessageHandler<MessageExplosion, IMessage>, IMessage {

	private int explosionType;
	private int posX,posY,posZ;
	private long seed;
	private float size;

	public MessageExplosion(){
	}

	public MessageExplosion(int type, int x, int y, int z, long seed, float size){
		this();
		explosionType = type;
		posX = x;
		posY = y;
		posZ = z;
		this.seed = seed;
		this.size = size;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		explosionType = buf.readInt();
		posX = buf.readInt();
		posY = buf.readInt();
		posZ = buf.readInt();
		this.seed = buf.readLong();
		this.size = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(explosionType);
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeLong(this.seed);
		buf.writeFloat(this.size);
	}

	@Override
	public IMessage onMessage(MessageExplosion message, MessageContext ctx) {
	     //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
        //Do something.
		try {
			EnumExplosionType type = EnumExplosionType.getType(message.explosionType);
			int x = message.posX;
			int y = message.posY;
			int z = message.posZ;
			Explosion explosion;
			World world = ExBombsMinecraftHelper.getWorld();
			switch (type){
			case NORMAL:
				explosion = new Explosion(world, null, x, y, z, 0.0F, false, false);
				explosion.doExplosionB(true);
				break;
			case SUPER:
				long seed = message.seed;
				float size = message.size;
				explosion = new Explosion(world, null, x, y, z, 0.0F, false, false);
				explosion.doExplosionB(true);
				new MoreExplosivesSuperExplosion().explode(world, size, size, size, x, y, z, seed);
				break;
			case ERASE:
				explosion = new Explosion(world, null, x, y, z, 0.0F, false, false);
				explosion.doExplosionB(true);
				new MoreExplosiveEraseExplosion(false).explode(world, x, y, z);
				break;
			case MACHING:
				explosion = new Explosion(world, null, x, y, z, 0.0F, false, false);
				explosion.doExplosionB(true);
				new MoreExplosiveEraseExplosion(true).explode(world, x, y, z);
				break;
			case WATER:
				break;
				default:
					return null;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static enum EnumExplosionType implements IStringSerializable
	{
	    NORMAL(0, "normal"),
	    SUPER(1, "super"),
	    ERASE(2, "erase"),
	    MACHING(3, "maching"),
	    WATER(4, "water");

	    private final int index;
	    private final String name;
	    private final static EnumExplosionType[] values = new EnumExplosionType[]{NORMAL,SUPER,ERASE,MACHING,WATER};

	    private EnumExplosionType(int indexIn, String nameIn)
	    {
	        this.index = indexIn;
	        this.name = nameIn;
	    }

	    /**
	     * Get the Index of this Facing (0-5). The order is D-U-N-S-W-E
	     */
	    public int getType()
	    {
	        return this.index;
	    }

	    /**
	     * Same as getName, but does not override the method from Enum.
	     */
	    public String getName2()
	    {
	        return this.name;
	    }

	    public String toString()
	    {
	        return this.name;
	    }

	    public String getName()
	    {
	        return this.name;
	    }

	    public static EnumExplosionType getType(int type){
	    	return values[type];
	    }
	}
}
