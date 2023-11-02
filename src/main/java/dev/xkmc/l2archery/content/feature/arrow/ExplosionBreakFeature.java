package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public record ExplosionBreakFeature() implements BowArrowFeature {

	public static ExplosionBreakFeature INS = new ExplosionBreakFeature();

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_EXPLOSION_BREAK.get());
	}

}
