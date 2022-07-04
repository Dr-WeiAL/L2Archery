package dev.xkmc.l2archery.init;

import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public enum NetworkManager {
	BOWS, ARROWS, EFFECT_FEATURES;

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
		//HANDLER.addCachedConfig(MATERIALS.getID(), new ConfigMerger<>(GolemMaterialConfig.class));
	}

}
