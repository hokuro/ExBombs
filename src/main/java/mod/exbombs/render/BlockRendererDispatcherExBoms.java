package mod.exbombs.render;

import java.util.Random;

import mod.exbombs.block.BlockTunnelExplosive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlockRendererDispatcherExBoms extends BlockRendererDispatcher{

	public BlockRendererDispatcherExBoms(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
		super(p_i46577_1_, p_i46577_2_);
	}

    @Override
    public void renderBlockBrightness(IBlockState state, float brightness)
    {
        EnumBlockRenderType enumblockrendertype = state.getRenderType();
        IBakedModel ibakedmodel = this.getModelForState(state);
        ibakedmodel.getQuads(state, state.get(BlockTunnelExplosive.FACING),new Random(0));
        getBlockModelRenderer().renderModelBrightness(ibakedmodel, state, brightness, true);
    }

}
