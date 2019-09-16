package mod.exbombs.core;

public class ModCommon {
	// デバッグモードかどうか
	public static boolean isDebug = false;

	// モッドID
	public static final String MOD_ID = "exbombs";
	// モッド名
	public static final String MOD_NAME = "ExBombs";
	public static final String MOD_PACKAGE = "mod.exbombs";
	public static final String MOD_CLIENT_SIDE = ".client.ClientProxy";
	public static final String MOD_SERVER_SIDE = ".core.CommonProxy";
	public static final String MOD_FACTRY = ".client.config.ExBombsGuiFactory";
    // 前に読み込まれるべき前提MODをバージョン込みで指定
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[1.9-12.16.0.1853,)";
    // 起動出来るMinecraft本体のバージョン。記法はMavenのVersion Range Specificationを検索すること。
    public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.12]";
	// モッドバージョン
	public static final String MOD_VERSION = "1.12.0";
	// コンフィグファイル名
	public static final String MOD_CONFIG_FILE = "";
	// コンフィグ
	public static final String MOD_CONFIG_LANG = "";
	// コンフィグリロード間隔
	public static final long MOD_CONFIG_RELOAD = 500L;

	// コンフィグ カテゴリー general
	public static final String MOD_CONFIG_CAT_GENELAL = "general";
	public static final String MOD_CHANEL ="mod_channel_exbombs";
	public static final String MOD_EVENTCHANEL ="mod_eventchannel_exBombs";

	public static final String MOD_GUI_ID_MISSILE = "gui_missaile";
	public static final String MOD_GUI_ID_SPAWNRADAR = "gui_spawnradar";
	public static final String MOD_GUI_ID_BLOCKRADER = "gui_blockradar";
}
