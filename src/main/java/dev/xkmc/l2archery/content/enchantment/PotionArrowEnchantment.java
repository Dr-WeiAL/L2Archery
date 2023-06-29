package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class PotionArrowEnchantment extends BaseBowEnchantment {

	private final int max;

	public PotionArrowEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, int max) {
		super(pRarity, pCategory, pApplicableSlots);
		this.max = max;
	}

	@Override
	public BowArrowFeature getFeature(int v) {
		return BowArrowStatConfig.get().getEnchEffects(this, v);
	}

	@Override
	public int getMaxLevel() {
		return max;
	}
}
