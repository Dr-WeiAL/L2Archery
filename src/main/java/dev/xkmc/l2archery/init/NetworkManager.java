package dev.xkmc.l2archery.init;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.ConfigMerger;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public enum NetworkManager {
	STATS;

	public String getID() {
		return name().toLowerCase(Locale.ROOT);
	}

	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(L2Archery.MODID, "main"), 1, "archery_config"
	);

	public <T extends BaseConfig> T getMerged() {
		return HANDLER.getCachedConfig(getID());
	}

	public static void register() {
		HANDLER.addCachedConfig(STATS.getID(), new ConfigMerger<>(BowArrowStatConfig.class));
	}

}
