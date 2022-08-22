package dev.xkmc.l2archery.init.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ArcheryConfig {

	public static class Client {

		public final ForgeConfigSpec.BooleanValue showInfo;

		Client(ForgeConfigSpec.Builder builder) {
			showInfo = builder.comment("Show combined bow arrow stats and features when holding bow")
					.define("showInfo", true);
		}

	}

	public static class Common {

		public final ForgeConfigSpec.IntValue belowVoid;
		public final ForgeConfigSpec.IntValue phantomHeight;
		public final ForgeConfigSpec.IntValue explosionDamage;
		public final ForgeConfigSpec.IntValue spaceDamage;

		Common(ForgeConfigSpec.Builder builder) {
			belowVoid = builder.comment("Requirement for void eye drop")
					.defineInRange("belowVoid", 16, 0, 128);
			phantomHeight = builder.comment("Requirement for sun membrane drop")
					.defineInRange("phantomHeight", 200, 0, 10000);
			explosionDamage = builder.comment("Requirement for explosion shard drop")
					.defineInRange("explosionDamage", 80, 1, 10000);
			spaceDamage = builder.comment("Requirement for space shard drop")
					.defineInRange("spaceDamage", 1000, 1, 10000);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Client, ForgeConfigSpec> client = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();

		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ArcheryConfig.CLIENT_SPEC);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ArcheryConfig.COMMON_SPEC);
	}


}
