package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.config.EnchantmentEffectConfig;
import dev.xkmc.l2archery.content.config.EnchantmentEffectEntry;
import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchVal;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

@DataGenOnly
public class EnchBuilder extends BaseBuilder<EnchBuilder, EnchStatProvider, PotionArrowEnchantment, LegacyEnchantment, EnchantmentEffectEntry, EnchantmentEffectConfig> {

	EnchBuilder(EnchStatProvider config, EnchVal.Legacy<PotionArrowEnchantment> ench) {
		super(config, ench.legacy());
	}

	public EnchBuilder putEffect(Holder<MobEffect> type, int duration, int amplifier, int durBonus, int ampBonus) {
		this.effects.put(type, new EnchantmentEffectEntry(duration, amplifier, durBonus, ampBonus));
		return this;
	}

	@Override
	public EnchantmentEffectConfig build() {
		return new EnchantmentEffectConfig(effects);
	}

}
