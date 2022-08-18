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
					.define("showInfo",true);
		}

	}

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	/**
	 * Registers any relevant listeners for config
	 */
	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ArcheryConfig.CLIENT_SPEC);
	}


}
