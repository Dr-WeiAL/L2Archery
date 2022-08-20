package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.enchantment.BowEnchantment;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.core.CompoundBowConfig;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record BowData(Item item, ArrayList<Upgrade> upgrade, HashMap<Enchantment, Integer> ench) {

	public static BowData of(GenericBowItem item) {
		return new BowData(item, new ArrayList<>(0), new HashMap<>(0));
	}

	public static BowData of(GenericBowItem item, List<Upgrade> upgrade, Map<Enchantment, Integer> ench) {
		return new BowData(item, new ArrayList<>(upgrade), new HashMap<>(ench));
	}

	public static BowData of(GenericBowItem item, ItemStack stack) {
		List<Upgrade> upgrade = GenericBowItem.getUpgrades(stack);
		return of(item, upgrade, stack.getAllEnchantments());
	}

	public FeatureList getFeatures() {
		FeatureList ans = getItem().getFeatures(null);
		ans.stage = FeatureList.Stage.UPGRADE;
		upgrade.forEach(e -> ans.add(e.getFeature()));
		ans.stage = FeatureList.Stage.ENCHANT;
		ench.forEach((k, v) -> {
			if (k instanceof BowEnchantment b) {
				ans.add(b.getFeature(v));
			}
		});
		return ans;
	}

	public GenericBowItem getItem() {
		return (GenericBowItem) item;
	}

	public IBowConfig getConfig() {
		StatFeature stat = null;
		for (Upgrade up : upgrade) {
			if (up.getFeature() instanceof StatFeature f) {
				stat = f;
			}
		}
		if (stat == null) {
			return getItem().config;
		} else {
			return new CompoundBowConfig(getItem().config, stat);
		}
	}

}
