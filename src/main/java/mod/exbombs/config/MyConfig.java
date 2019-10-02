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

		private static List<Block> erase_match_block_list = new ArrayList<Block>();
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
				.define("erase_match_bloc","minecraft:gold_ore,minecraft:iron_ore,minecraft:coal_ore,minecraft:lapis_ore,minecraft:diamond_ore,minecraft:redstone_ore,minecraft:emerald_ore");
			cheat_paint = builder
				.comment("conf.cheat_paint")
				.define("cheat_paint",false);
			builder.pop();
		}

		public List<Block> getUnEraseBlock(){
			String[] blocks = erase_match_block.get().split(",");
			if (blocks.length != erase_match_block_list.size()) {
				for (String blk : blocks) {
					Block addblock = Registry.BLOCK.getOrDefault(new ResourceLocation(blk));
					if (addblock != Blocks.AIR) {
						erase_match_block_list.add(addblock);
					}
				}
			}
			return erase_match_block_list;
		}
	}
}
