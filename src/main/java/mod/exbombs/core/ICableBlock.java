/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.core;

import net.minecraft.block.state.IBlockState;

public abstract interface ICableBlock {
	public abstract boolean shouldConnectTo(IBlockState paramAce);

	public abstract void preWorldRender();
}
