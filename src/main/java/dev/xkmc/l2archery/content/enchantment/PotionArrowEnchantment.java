package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;

public class PotionArrowEnchantment extends BaseBowEnchantment {

	@Override
	public BowArrowFeature getFeature(int v) {
		return BowArrowStatConfig.get().getEnchEffects(this, v);
	}

}
