package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class BowEnchantment extends Enchantment {

	protected BowEnchantment(Rarity rarity) {
		super(rarity, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
	}

	public BowArrowFeature getFeature(int v) {
		return null;//TODO
	}
}
