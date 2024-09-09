package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.enchantment.IBowEnchantment;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.core.CompoundBowConfig;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.ArrayList;
import java.util.List;

public record BowData(Item item, ArrayList<Upgrade> upgrade, ItemEnchantments ench) {

	public static BowData of(GenericBowItem item) {
		return new BowData(item, new ArrayList<>(0), ItemEnchantments.EMPTY);
	}

	public static BowData of(GenericBowItem item, List<Upgrade> upgrade, ItemEnchantments ench) {
		return new BowData(item, new ArrayList<>(upgrade), ench);
	}

	public static BowData of(GenericBowItem item, ItemStack stack) {
		List<Upgrade> upgrade = GenericBowItem.getUpgrades(stack);
		var reg = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
		return of(item, upgrade, reg == null ? ItemEnchantments.EMPTY : stack.getAllEnchantments(reg));
	}

	public FeatureList getFeatures() {
		FeatureList ans = getItem().getFeatures(null);
		ans.stage = FeatureList.Stage.UPGRADE;
		upgrade.forEach(e -> ans.add(e.getFeature()));
		ans.stage = FeatureList.Stage.ENCHANT;
		for (var e : ench.entrySet()) {
			var legacy = LegacyEnchantment.firstOf(e.getKey(), IBowEnchantment.class);
			if (legacy != null) {
				ans.add(legacy.getFeature(e.getIntValue()));
			}
		}
		return ans;
	}

	public GenericBowItem getItem() {
		return (GenericBowItem) item;
	}

	public IBowConfig getConfig() {
		IBowConfig ans = getItem().config;
		for (Upgrade up : upgrade) {
			if (up.getFeature() instanceof StatFeature f) {
				ans = new CompoundBowConfig(ans, f);
			}
		}
		for (var e : ench.entrySet()) {
			var legacy = LegacyEnchantment.firstOf(e.getKey(), IBowEnchantment.class);
			if (legacy != null) {
				var f = legacy.getFeature(e.getIntValue());
				if (f instanceof StatFeature sf) {
					ans = new CompoundBowConfig(ans, sf);
				}
			}
		}
		return ans;
	}

}
