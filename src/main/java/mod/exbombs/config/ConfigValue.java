package mod.exbombs.config;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigValue {
	private static final ModConfig config = new ModConfig();

	public static void init(FMLPreInitializationEvent event){
		config.init(new Class<?>[]{General.class}, event);
		General.init();
	}

	public static void reloadConfig(EntityPlayer player){
		if (config.reloadConfig()){
			if (player != null){
				player.addChatComponentMessage(new TextComponentString("ExBombs reload Configration!"));
			}
			General.init();
		}
	}

	public static class General{
		// 手榴弾での地形破壊を行うか
		@ConfigProperty(comment="conf.bomb_destory_block")
		public static boolean bomb_destroy_block = false;
		@ConfigProperty(comment="conf.tunnel_depth", max="255", min="1")
		public static int tunnel_depth  = 20;
		@ConfigProperty(comment="conf.tunnel_width", max="16", min="1")
		public static int tunnel_width = 1;
		@ConfigProperty(comment="conf.erase_method", max="1", min="0")
		public static int erase_method = 0;
		@ConfigProperty(comment="conf.erase_mach_block")
		public static String erase_match_block ="dirt,stone,sand,stonesand";

		private static List<BlockAndMetadata> erase_match_block_list;
		protected static void init(){
			erase_match_block_list = idStringToArray(erase_match_block);
		}

		public static List<BlockAndMetadata> getUnEraseBlock(){
			return erase_match_block_list;
		}


		private static List<BlockAndMetadata> idStringToArray(String s){
			List<BlockAndMetadata> list = new ArrayList();
			String[] ss = s.split(",");
			for (String str : ss){
				String metastr = null;
				String[] ss2 = str.split(":",2);
				str = ss2[0];
				if (ss2.length >= 2){
					metastr = ss2[1];
				}
				Object b = null;
				str = str.trim();
				b = Block.getBlockFromName(str);
				if(null != b)
				{
					if(Blocks.air != b){
						Block block = (Block)b;
						BlockAndMetadata bam = new BlockAndMetadata(block, convertMetaString(block,metastr));
						list.add(bam);
					}
				}
			}
			return list;
		}


		private static final Pattern ptnNum = Pattern.compile("^[0-9]+$");
		private static int convertMetaString(Block b, String s) {
			if ((null == b) || (null == s)) {
				return 0;
			}
			s = s.trim();
			if (ptnNum.matcher(s).matches()) {
				try {
					return Integer.parseInt(s, 10);
				} catch (Exception localException1) {
				}
			}
			Class<?> enumCls = null;
			for (Field f : b.getClass().getDeclaredFields()) {
				if ((0 != (f.getModifiers() & 0x1)) && (0 != (f.getModifiers() & 0x8))) {
					if (PropertyEnum.class == f.getType()) {
						try {
							enumCls = ((PropertyEnum) f.get(null)).getValueClass();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (null == enumCls) {
				return 0;
			}
			for (Object o : enumCls.getEnumConstants()) {
				if ((o instanceof IStringSerializable)) {
					String name = ((IStringSerializable) o).getName();
					if (s.equalsIgnoreCase(name)) {
						Class<?> c = o.getClass();
						for (Method m : c.getDeclaredMethods()) {
							if (m.getReturnType() == Integer.TYPE) {
								if (0 == m.getParameterTypes().length) {
									try {
										return ((Integer) m.invoke(o, new Object[0])).intValue();
									} catch (Exception e) {
										FMLLog.warning(e.toString(), new Object[0]);
									}
								}
							}
						}
					}
				}
			}
			return 0;
		}
	}
}
