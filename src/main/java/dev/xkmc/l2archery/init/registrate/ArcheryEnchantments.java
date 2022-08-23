package dev.xkmc.l2archery.init.registrate;

import dev.xkmc.l2archery.foundation.enchantment.ImmuneEnchantment;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static dev.xkmc.l2archery.init.L2Archery.REGISTRATE;

public class ArcheryEnchantments {

	public static final RegistryEntry<ImmuneEnchantment> ENCH_PROJECTILE = reg("projectile_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_FIRE = reg("fire_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_ENVIRONMENT = reg("environment_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_EXPLOSION = reg("explosion_reject");
	public static final RegistryEntry<ImmuneEnchantment> ENCH_MAGIC = reg("magic_reject");

	private static RegistryEntry<ImmuneEnchantment> reg(String id) {
		return REGISTRATE.enchantment(id, EnchantmentCategory.ARMOR, ImmuneEnchantment::new)
				.addArmorSlots().rarity(Enchantment.Rarity.VERY_RARE).defaultLang().register();
	}

	public static void register() {
	}

}
