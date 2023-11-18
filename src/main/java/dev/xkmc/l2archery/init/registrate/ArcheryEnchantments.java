package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.enchantment.BaseBowEnchantment;
import dev.xkmc.l2archery.content.enchantment.BowEnchantmentSupplier;
import dev.xkmc.l2archery.content.enchantment.GenericBowEnchantment;
import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.arrow.ExplodeArrowFeature;
import dev.xkmc.l2archery.content.feature.arrow.ExplosionBreakFeature;
import dev.xkmc.l2archery.content.feature.bow.GlowTargetAimFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.L2Archery;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Function;

public class ArcheryEnchantments {

	public static final EnchantmentCategory BOW = EnchantmentCategory.create("l2bows", e -> e instanceof GenericBowItem);

	public static final RegistryEntry<PotionArrowEnchantment> ENCH_GLOW, ENCH_HARM, ENCH_HEAL, ENCH_FLOAT, ENCH_SLOW,
			ENCH_LEVITATE, ENCH_POISON, ENCH_WITHER, ENCH_WEAK, ENCH_CHAOTIC, ENCH_DISTORTION;

	public static final RegistryEntry<GenericBowEnchantment> ENCH_MAGNIFY, ENCH_EXPLODE, ENCH_GLOW_AIM, ENCH_EXPLOSION_BREAK;

	static {
		ENCH_MAGNIFY = regStat("magnify", 3, "Archery - Magnify Upgrade",
				i -> new StatFeature(1 << i, i * 20 - 10, 1, 0, 1),
				"Zoom in when pulling bow. Works only on L2Archery Bows.");
		ENCH_GLOW_AIM = regStat("glow_aim", 1, "Archery - Glow Aiming Upgrade",
				i -> new GlowTargetAimFeature(128),
				"Aimed entity will appear glowing. Works only on L2Archery Bows.");
		ENCH_EXPLODE = regStat("explode", 3, "Archery - Explosion Upgrade",
				i -> new ExplodeArrowFeature(i, false, false),
				"Create explosion on hit. It will not break block or hurt entities. Works only on L2Archery Bows.");
		ENCH_EXPLOSION_BREAK = regStat("explosion_break", 1, "Archery - Explosion Breaking",
				i -> ExplosionBreakFeature.INS, "Explosion will break blocks anyway.");

		ENCH_GLOW = regPotion("glow", 1, "Archery - Glow Upgrade", "Make enemy glow on hit.");
		ENCH_HARM = regPotion("harm", 3, "Archery - Instant Damage Upgrade", "Inflict enemy with Instant Damage on hit.");
		ENCH_HEAL = regPotion("heal", 3, "Archery - Instant Heal Upgrade", "Inflict enemy with Instant Heal on hit.");
		ENCH_FLOAT = regPotion("float", 5, "Archery - Feather Falling Upgrade", "Apply Feather Falling to enemy on hit.");
		ENCH_SLOW = regPotion("slow", 5, "Archery - Slow Upgrade", "Apply Slowness to enemy on hit.");
		ENCH_LEVITATE = regPotion("levitate", 5, "Archery - Levitation Upgrade", "Apply Levitation to enemy on hit.");
		ENCH_POISON = regPotion("poison", 3, "Archery - Poison Upgrade", "Inflict enemy with Poison on hit.");
		ENCH_WITHER = regPotion("wither", 3, "Archery - Wither Upgrade", "Inflict enemy with Wither on hit.");
		ENCH_WEAK = regPotion("weak", 5, "Archery - Weak Upgrade", "Inflict enemy with Weakenss on hit.");
		ENCH_CHAOTIC = regPotion("chaotic", 3, "Archery - Chaotic Upgrade", "Inflict enemy with various beneficial and harmful effects.");
		ENCH_DISTORTION = regPotion("distortion", 3, "Archery - Distortion Upgrade", "Inflict enemy with various visual-only effects.");
	}

	public static <T extends BaseBowEnchantment> RegistryEntry<T> reg(String id, int max, String def, BowEnchantmentSupplier<T> sup, String desc) {
		return L2Archery.REGISTRATE.enchantment(id, BOW, (a, b, c) -> sup.get(a, b, c, max), desc).lang(def).register();
	}

	public static RegistryEntry<GenericBowEnchantment> regStat(String id, int max, String def, Function<Integer, BowArrowFeature> func, String desc) {
		return reg(id, max, def, (a, b, c, m) -> new GenericBowEnchantment(a, b, c, m, func), desc);
	}

	public static RegistryEntry<PotionArrowEnchantment> regPotion(String id, int max, String def, String desc) {
		return reg(id, max, def, PotionArrowEnchantment::new, desc + " Works only on L2Archery Bows.");
	}

	public static void register() {
	}

}
