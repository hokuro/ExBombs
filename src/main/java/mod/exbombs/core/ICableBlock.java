/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.core;

import net.minecraft.block.BlockState;

public abstract interface ICableBlock {
	public abstract boolean shouldConnectTo(BlockState paramAce);

	public abstract void preWorldRender();
}
