package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.LinkedHashMap;

public record ArcheryStatConfig(
		LinkedHashMap<BowArrowStatType, Double> stats,
		LinkedHashMap<Holder<MobEffect>, ArcheryEffectEntry> effects
) {

	public PotionArrowFeature getEffects() {
		return effects != null && !effects().isEmpty() ? new PotionArrowFeature(
				effects.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(),
						e.getValue().duration(), e.getValue().amplifier())).toList()) :
				PotionArrowFeature.NULL;

	}


}
