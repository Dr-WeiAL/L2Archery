package dev.xkmc.l2archery.content.config;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.enchantment.PotionArrowEnchantment;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2core.serial.config.CollectType;
import dev.xkmc.l2core.serial.config.ConfigCollect;
import dev.xkmc.l2core.util.DataGenOnly;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.HashMap;

@SerialClass
public class BowArrowStatConfig extends BaseConfig {

	public static BowArrowStatConfig get() {
		return L2Archery.STATS.getMerged();
	}

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Item, HashMap<BowArrowStatType, Double>> bow_stats = new HashMap<>();

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Item, HashMap<BowArrowStatType, Double>> arrow_stats = new HashMap<>();

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Item, HashMap<Holder<MobEffect>, ConfigEffect>> bow_effects = new HashMap<>();

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Item, HashMap<Holder<MobEffect>, ConfigEffect>> arrow_effects = new HashMap<>();

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Enchantment, HashMap<Holder<MobEffect>, EnchantmentConfigEffect>> enchantment_effects = new HashMap<>();

	@ConfigCollect(CollectType.MAP_COLLECT)
	@SerialField
	public HashMap<Upgrade, HashMap<Holder<MobEffect>, ConfigEffect>> upgrade_effects = new HashMap<>();

	private final HashMap<ConfigIdentifier, PotionArrowFeature> potion_cache = new HashMap<>();

	private <T> PotionArrowFeature getEffects(T upgrade, HashMap<T, HashMap<Holder<MobEffect>, ConfigEffect>> config) {
		ConfigIdentifier id = new ConfigIdentifier(upgrade, 0);
		if (potion_cache.containsKey(id)) return potion_cache.get(id);
		var map = config.get(upgrade);
		PotionArrowFeature ans = map != null ? new PotionArrowFeature(map.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(),
				e.getValue().duration(), e.getValue().amplifier())).toList()) : PotionArrowFeature.NULL;
		potion_cache.put(id, ans);
		return ans;
	}

	public PotionArrowFeature getUpgradeEffects(Upgrade upgrade) {
		return getEffects(upgrade, upgrade_effects);
	}

	public PotionArrowFeature getBowEffects(GenericBowItem bow) {
		return getEffects(bow, bow_effects);
	}

	public PotionArrowFeature getArrowEffects(GenericArrowItem arrow) {
		return getEffects(arrow, arrow_effects);
	}

	public PotionArrowFeature getEnchEffects(PotionArrowEnchantment enchantment, int lv) {
		ConfigIdentifier id = new ConfigIdentifier(enchantment, lv);
		if (potion_cache.containsKey(id)) return potion_cache.get(id);
		var map = enchantment_effects.get(enchantment);
		PotionArrowFeature ans = map != null ? new PotionArrowFeature(map.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(),
				e.getValue().duration() + e.getValue().duration_bonus() * (lv - 1),
				e.getValue().amplifier() + e.getValue().amplifier_bonus() * (lv - 1)
		)).toList()) : PotionArrowFeature.NULL;
		potion_cache.put(id, ans);
		return ans;
	}

	public record ConfigEffect(int duration, int amplifier) {
	}

	public record EnchantmentConfigEffect(int duration, int amplifier, int duration_bonus, int amplifier_bonus) {
	}

	public record ConfigIdentifier(Object item, int lv) {

	}

	@DataGenOnly
	public BowBuilder putBow(ItemEntry<GenericBowItem> bow) {
		return new BowBuilder(this, bow);
	}

	@DataGenOnly
	public ArrowBuilder putArrow(ItemEntry<GenericArrowItem> arrow) {
		return new ArrowBuilder(this, arrow);
	}

	@DataGenOnly
	public EnchBuilder putEnchantment(SimpleEntry<PotionArrowEnchantment> arrow) {
		return new EnchBuilder(this, arrow);
	}

	@DataGenOnly
	public UpgradeBuilder putUpgrade(SimpleEntry<Upgrade> arrow) {
		return new UpgradeBuilder(this, arrow);
	}

}
