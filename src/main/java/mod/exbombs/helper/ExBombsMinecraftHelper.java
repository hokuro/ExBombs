package mod.exbombs.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ExBombsMinecraftHelper {
	public ExBombsMinecraftHelper() {
	}

	public static World getWorld() {
		return Minecraft.getInstance().world;
	}

	public static EntityPlayer getPlayer() {
		return Minecraft.getInstance().player;
	}

	public static void addEffect(Particle fx) {
		Minecraft.getInstance().particles.addEffect(fx);
	}

//	public static boolean isKeyDown(int key) {
//		return Keyboard.isKeyDown(key);
//	}
}
