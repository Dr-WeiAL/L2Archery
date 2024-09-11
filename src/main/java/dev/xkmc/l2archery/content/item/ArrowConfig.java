package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.util.Proxy;
import net.minecraft.network.chat.Component;

import java.util.List;

public record ArrowConfig(GenericArrowItem id, int infLevel,
						  List<BowArrowFeature> feature) implements IGeneralConfig {

	private double getValue(BowArrowStatType type) {
		var reg = Proxy.getRegistryAccess();
		if (reg != null) {
			var ans = ArcheryRegister.ITEM_STAT.get(reg, id.builtInRegistryHolder());
			if (ans != null) return ans.stats().getOrDefault(type, type.getDefault());
		}
		return type.getDefault();
	}

	public PotionArrowFeature getEffects() {
		var reg = Proxy.getRegistryAccess();
		if (reg != null) {
			var ans = ArcheryRegister.ITEM_STAT.get(reg, id.builtInRegistryHolder());
			if (ans != null) return ans.getEffects();
		}
		return PotionArrowFeature.NULL;
	}

	public float damage() {
		return (float) getValue(ArcheryRegister.DAMAGE.get());
	}

	public int punch() {
		return (int) getValue(ArcheryRegister.PUNCH.get());
	}

	public void addTooltip(List<Component> list) {
		LangData.STAT_DAMAGE.getWithSign(list, damage());
		LangData.STAT_PUNCH.getWithSign(list, punch());
		if (infLevel() == 2) {
			list.add(LangData.FEATURE_INFINITY.get());
		} else if (infLevel() == 1) {
			list.add(LangData.FEATURE_INFINITY_ADV.get());
		}
		PotionArrowFeature.addTooltip(getEffects().instances(), list);
	}

}
