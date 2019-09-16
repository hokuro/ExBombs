package mod.exbombs.config;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class MyConfig {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(BUILDER);
	public static final ForgeConfigSpec spec = BUILDER.build();

	public static class General{
		// 手榴弾での地形破壊を行うか
		public final ForgeConfigSpec.ConfigValue<Boolean> bomb_destroy_block;
		public final ForgeConfigSpec.ConfigValue<Integer> tunnel_depth;
		public final ForgeConfigSpec.ConfigValue<Integer> tunnel_width;
		public final ForgeConfigSpec.ConfigValue<Integer> erase_method;
		public final ForgeConfigSpec.ConfigValue<String> erase_match_block;
		public final ForgeConfigSpec.ConfigValue<Boolean> cheat_paint;

		private static List<Block> erase_match_block_list;
		public General(Builder builder) {
			builder.push("General");
			bomb_destroy_block = builder
				.comment("conf.bomb_destory_block")
				.define("bomb_destroy_block",false);
			tunnel_depth = builder
				.comment("conf.tunnel_depth")
				.defineInRange("tunnel_depth",20,1,255);
			tunnel_width = builder
				.comment("conf.tunnel_width")
				.defineInRange("tunnel_width",1,1,16);
			erase_method = builder
				.comment("conf.erase_method")
				.defineInRange("erase_method",0,0,1);
			erase_match_block = builder
				.comment("conf.erase_mach_block")
				.define("erase_match_bloc","dirt,stone,sand,stonesand");
			cheat_paint = builder
				.comment("conf.cheat_paint")
				.define("cheat_paint",false);
			builder.pop();
		}

		protected void init(){
			erase_match_block_list = idStringToArray(erase_match_block.get());
		}

		public List<Block> getUnEraseBlock(){
			return erase_match_block_list;
		}


		private static List<Block> idStringToArray(String s){
			List<Block> list = new ArrayList<Block>();
			String[] ss = s.split(",");
			for (String str : ss){
				String metastr = null;
				String[] ss2 = str.split(":",2);
				str = ss2[0];
				if (ss2.length >= 2){
					metastr = ss2[1];
				}
				Block b = Blocks.AIR;
				str = str.trim();
				b = Registry.BLOCK.getOrDefault(new ResourceLocation(str));
				if(null != b)
				{
					if (Blocks.AIR != b){
						list.add(b);
					}
				}
			}
			return list;
		}
	}
}
