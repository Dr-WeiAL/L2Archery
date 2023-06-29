package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Function;

public class GenericBowEnchantment extends BaseBowEnchantment {

	private final BowArrowFeature[] features;

	public GenericBowEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots, int max, Function<Integer, BowArrowFeature> gen) {
		super(pRarity, pCategory, pApplicableSlots, max);
		features = new BowArrowFeature[max];
		for (int i = 0; i < max; i++) {
			features[i] = gen.apply(i + 1);
		}
	}

	@Override
	public BowArrowFeature getFeature(int v) {
		return features[Mth.clamp(v - 1, 0, max - 1)];
	}

}
