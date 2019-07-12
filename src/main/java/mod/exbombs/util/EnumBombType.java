package mod.exbombs.util;

import net.minecraft.util.ResourceLocation;

public enum EnumBombType {
//	BOMB,
//	WARTER,
//	ICICLE,
//	FROZEN,
//	PAINT,
//	PRIME,
//	LAVA;
	BOMB(0,5.0F, new ResourceLocation("exbombs:textures/entity/bomb.png")),
	WARTER(1,1.0F, new ResourceLocation("exbombs:textures/entity/waterbomb.png")),
	ICICLE(2,3.0F, new ResourceLocation("exbombs:textures/entity/iciclebomb.png")),
	FROZEN(3,3.0F, new ResourceLocation("exbombs:textures/entity/frozenbomb.png")),
	PAINT(4,2.0F, new ResourceLocation("exbombs:textures/entity/paintbomb.png")),
	PRIME(5,5.0F),
	LAVA(6,1.0F, new ResourceLocation("exbombs:textures/entity/lavabomb.png"));

	private int index;
	private float explodSize;
	private ResourceLocation texture;
	private static final EnumBombType[] values = new EnumBombType[]{BOMB,WARTER,ICICLE,FROZEN,PAINT,PRIME,LAVA};

	private EnumBombType(){}

	private EnumBombType(int idx, float size){
		try{
			index = idx;
			explodSize = size;
		}catch(Throwable ex){
			System.console().printf("");
		}
	}

	private EnumBombType(int idx, float size, ResourceLocation tex){
		try{
			index = idx;
			explodSize = size;
			texture = tex;
		}catch(Throwable ex){
			System.console().printf("");
		}
	}

	public int getIndex(){return index;}
	public float getSize(){return explodSize;}
	public ResourceLocation getTexture(){return texture;}
	public static EnumBombType intToBombType(int idx){
		if (idx >= 0 && idx < values.length){
			return values[idx];
		}
		return BOMB;
	}

}
