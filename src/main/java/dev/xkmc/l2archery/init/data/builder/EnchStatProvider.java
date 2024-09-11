package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.config.EnchantmentEffectConfig;
import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2core.init.reg.ench.EnchVal;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.neoforged.neoforge.common.data.DataMapProvider;

public record EnchStatProvider(DataMapProvider.Builder<EnchantmentEffectConfig, LegacyEnchantment> builder)
		implements BaseStatProvider<LegacyEnchantment, EnchantmentEffectConfig> {

	public EnchBuilder putEnchantment(EnchVal.Legacy<PotionArrowEnchantment> ench) {
		return new EnchBuilder(this, ench);
	}
}
