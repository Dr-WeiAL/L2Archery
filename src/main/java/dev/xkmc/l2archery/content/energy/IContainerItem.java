package dev.xkmc.l2archery.content.energy;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraft.util.Mth.clamp;

/**
 * Marker interface for anything that supports the "Holding" enchantment. Can also be done via the Enchantable capability, but this is way less overhead.
 */
public interface IContainerItem {
	default int getMaxStored(ItemStack container, int amount) {
		Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation("cofh_core", "holding"));
		int holding = container.getEnchantmentLevel(ench);
		return clamp(amount + amount * holding / 2, 0, 2147483647);
	}

}