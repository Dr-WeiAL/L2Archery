package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchVal;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

@DataGenOnly
public class EnchBuilder extends BaseBuilder<EnchBuilder, PotionArrowEnchantment, LegacyEnchantment, BowArrowStatConfig.EnchantmentConfigEffect> {

	EnchBuilder(BowArrowStatConfig config, EnchVal.Legacy<PotionArrowEnchantment> ench) {
		super(config, config.enchantment_effects, ench.legacy());
	}

	public EnchBuilder putEffect(Holder<MobEffect> type, int duration, int amplifier, int durBonus, int ampBonus) {
		this.effects.put(type, new BowArrowStatConfig.EnchantmentConfigEffect(duration, amplifier, durBonus, ampBonus));
		return this;
	}

}
