package mod.exbombs.block;

import mod.exbombs.entity.prime.EntityChunkEraserPrimed;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BlockChunkEraserExplosive extends BlockOriginalTnt {

	private EnumEraseType eraseType;

	public BlockChunkEraserExplosive(EnumEraseType type, Block.Properties property) {
		super(property);
		eraseType = type;
	}

	@Override
	protected void spqwnPriveEntity(IWorld world, BlockPos pos, BlockState state, boolean fuse, boolean sound) {
		EntityChunkEraserPrimed entitytntprimed =
				new EntityChunkEraserPrimed(world.getWorld(),
    					pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
    				    eraseType);
    	if (fuse) {
    		entitytntprimed.fuse = (world.getWorld().rand.nextInt(entitytntprimed.getFuse() / 4) + entitytntprimed.getFuse() / 8);
    	}
    	world.addEntity(entitytntprimed);
    	if (sound) {
    		world.playSound((PlayerEntity)null, new BlockPos(entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
    	}
	}

    public static enum EnumEraseType{
    	ERASEALL(0,"erase_all"),
    	ERASEUNMATCH(1,"erase_unmatch");

    	private final int index;
    	private final String name;
    	private static final EnumEraseType[] values = new EnumEraseType[]{ERASEALL,ERASEUNMATCH};
    	private EnumEraseType(int idx, String nm){
    		index = idx;
    		name = nm;
    	}

    	public int getIndex(){return index;}
    	public String getName(){return name;}
    	public static EnumEraseType getType(int index){
    		if (index >= 0 && index < values.length){
    			return values[index];
    		}
    		return null;
    	}
    }

}
