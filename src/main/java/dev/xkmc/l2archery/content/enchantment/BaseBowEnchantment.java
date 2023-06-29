package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2complements.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class BaseBowEnchantment extends UnobtainableEnchantment implements IBowEnchantment {

	protected BaseBowEnchantment(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
		super(pRarity, pCategory, pApplicableSlots);
	}
}
