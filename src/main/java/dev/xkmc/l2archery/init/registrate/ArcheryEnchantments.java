package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.L2Archery;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ArcheryEnchantments {

	public static final EnchantmentCategory BOW = EnchantmentCategory.create("l2bows", e -> e instanceof GenericBowItem);

	public static final RegistryEntry<PotionArrowEnchantment> ENCH_GLOW, ENCH_HARM, ENCH_HEAL, ENCH_FLOAT, ENCH_SLOW,
			ENCH_LEVITATE, ENCH_POISON, ENCH_WITHER, ENCH_WEAK;

	static {
		ENCH_GLOW = regPotion("glow", 1, "Archery - Glow Upgrade", "Make enemy glow on hit.");
		ENCH_HARM = regPotion("harm", 3, "Archery - Instant Damage Upgrade", "Inflict enemy with Instant Damage on hit.");
		ENCH_HEAL = regPotion("heal", 3, "Archery - Instant Heal Upgrade", "Inflict enemy with Instant Heal on hit.");
		ENCH_FLOAT = regPotion("float", 5, "Archery - Feather Falling Upgrade", "Apply Feather Falling to enemy on hit.");
		ENCH_SLOW = regPotion("slow", 5, "Archery - Slow Upgrade", "Apply Slowness to enemy on hit.");
		ENCH_LEVITATE = regPotion("levitate", 5, "Archery - Levitation Upgrade", "Apply Levitation to enemy on hit.");
		ENCH_POISON = regPotion("poison", 3, "Archery - Poison Upgrade", "Inflict enemy with Poison on hit.");
		ENCH_WITHER = regPotion("wither", 3, "Archery - Wither Upgrade", "Inflict enemy with Wither on hit.");
		ENCH_WEAK = regPotion("weak", 5, "Archery - Weak Upgrade", "Inflict enemy with Weakenss on hit.");
	}

	public static RegistryEntry<PotionArrowEnchantment> regPotion(String id, int max, String def, String desc) {
		L2Archery.REGISTRATE.addRawLang("enchantment." + L2Archery.MODID + "." + id + ".desc", desc + " Works only on L2Archery Bows.");
		return L2Archery.REGISTRATE.enchantment(id, BOW, (a, b, c) -> new PotionArrowEnchantment(a, b, c, max)).lang(def).register();
	}

	public static void register() {
	}

}
