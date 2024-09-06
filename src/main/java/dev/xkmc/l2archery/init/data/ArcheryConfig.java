package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2core.util.ConfigInit;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ArcheryConfig {

	public static class Client extends ConfigInit {

		public final ModConfigSpec.BooleanValue showInfo;
		public final ModConfigSpec.BooleanValue showArrow;

		Client(ModConfigSpec.Builder builder) {
			markL2();
			showInfo = builder.comment("Show combined bow arrow stats and features when holding bow")
					.define("showInfo", true);
			showArrow = builder.comment("Show projectile selection")
					.define("showArrow", true);
		}

	}

	public static final Client CLIENT = L2Archery.REGISTRATE.registerClient(Client::new);

	public static void init() {
	}

}
