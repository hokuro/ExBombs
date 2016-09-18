package mod.exbombs.sounds;

import mod.exbombs.core.ModCommon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModSoundManager {
	public static String SOUND_ERASEEXPLOSIVE = "items.eraseexplosive";
	public static String SOUND_PAINTBOMB = "items.paintbombinpackt";

	public static SoundEvent sound_eraseExplosive = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_ERASEEXPLOSIVE))
			.setRegistryName(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_ERASEEXPLOSIVE));

	public static SoundEvent sound_paintBombInpackt = new SoundEvent(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_PAINTBOMB))
			.setRegistryName(new ResourceLocation(ModCommon.MOD_ID+":" + SOUND_PAINTBOMB));

	public static void RegisterSounds(){
		GameRegistry.register(sound_eraseExplosive);
		GameRegistry.register(sound_paintBombInpackt);
	}
}
