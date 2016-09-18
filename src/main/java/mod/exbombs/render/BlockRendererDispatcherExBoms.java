package mod.exbombs.render;

import mod.exbombs.block.BlockTunnelExplosive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BlockRendererDispatcherExBoms extends BlockRendererDispatcher{

	public BlockRendererDispatcherExBoms(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
		super(p_i46577_1_, p_i46577_2_);
	}

    @Override
    public void renderBlockBrightness(IBlockState state, float brightness)
    {
        EnumBlockRenderType enumblockrendertype = state.getRenderType();
        IBakedModel ibakedmodel = this.getModelForState(state);
        ibakedmodel.getQuads(state, state.getValue(BlockTunnelExplosive.FACING), 0);
        getBlockModelRenderer().renderModelBrightness(ibakedmodel, state, brightness, true);
    }

}
