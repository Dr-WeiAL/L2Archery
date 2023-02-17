package dev.xkmc.l2archery.content.feature.core;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public record FluxFeature(int maxEnergy, int extract, int receive, int perUsed) implements BowArrowFeature {
	public static FluxFeature DEFAULT = new FluxFeature(100000, 10000, 10000, 500);

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_FLUX_UP.get());
	}
}
