package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.LinkedHashMap;

public record EnchantmentEffectConfig(
		LinkedHashMap<Holder<MobEffect>, EnchantmentEffectEntry> effects
) {

	public PotionArrowFeature getEffects(int lv) {
		return effects != null && !effects.isEmpty() ? new PotionArrowFeature(
				effects.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(),
						e.getValue().duration() + e.getValue().duration_bonus() * (lv - 1),
						e.getValue().amplifier() + e.getValue().amplifier_bonus() * (lv - 1)
				)).toList()) : PotionArrowFeature.NULL;
	}


}
