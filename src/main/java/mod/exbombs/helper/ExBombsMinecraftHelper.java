package mod.exbombs.helper;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ExBombsMinecraftHelper {
	public ExBombsMinecraftHelper() {
	}

	public static World getWorld() {
		return Minecraft.getMinecraft().theWorld;
	}

	public static EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

	public static void addEffect(EntityFX fx) {
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}

	public static boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}
}
