package dev.xkmc.l2archery.content.feature.bow;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public record InfinityFeature(int level) implements BowArrowFeature {

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_INFINITY_ADV_BOW.get());
	}

}
