package dev.xkmc.l2archery.init.registrate;

import dev.xkmc.l2archery.foundation.enchantment.ImmuneEnchantment;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import static dev.xkmc.l2archery.init.L2Archery.REGISTRATE;

public class ArcheryEnchantments {

	public static final RegistryEntry<ImmuneEnchantment> ENCH_PROJECTILE = REGISTRATE.enchantment(
			"projectile_reject", EnchantmentCategory.ARMOR, ImmuneEnchantment::new).addArmorSlots().defaultLang().register();
	public static final RegistryEntry<ImmuneEnchantment> ENCH_FIRE = REGISTRATE.enchantment(
			"fire_reject", EnchantmentCategory.ARMOR, ImmuneEnchantment::new).addArmorSlots().defaultLang().register();
	public static final RegistryEntry<ImmuneEnchantment> ENCH_ENVIRONMENT = REGISTRATE.enchantment(
			"environment_reject", EnchantmentCategory.ARMOR, ImmuneEnchantment::new).addArmorSlots().defaultLang().register();
	public static final RegistryEntry<ImmuneEnchantment> ENCH_EXPLOSION = REGISTRATE.enchantment(
			"explosion_reject", EnchantmentCategory.ARMOR, ImmuneEnchantment::new).addArmorSlots().defaultLang().register();
	public static final RegistryEntry<ImmuneEnchantment> ENCH_MAGIC = REGISTRATE.enchantment(
			"magic_reject", EnchantmentCategory.ARMOR, ImmuneEnchantment::new).addArmorSlots().defaultLang().register();

	public static void register() {
	}

}
