package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public record ArrowConfig(GenericArrowItem id, boolean is_inf,
						  List<BowArrowFeature> feature) implements IGeneralConfig {

	private double getValue(BowArrowStatType type) {
		var map = BowArrowStatConfig.get().arrow_stats.get(id);
		if (map == null) return type.getDefault();
		return map.getOrDefault(type, type.getDefault());
	}

	public PotionArrowFeature getEffects() {
		return BowArrowStatConfig.get().getArrowEffects(id);
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
		PotionArrowFeature.addTooltip(getEffects().instances(), list);
	}

}
