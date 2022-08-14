package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

public record BowConfig(ResourceLocation id, List<BowArrowFeature> feature) implements IBowConfig {

	private double getValue(BowArrowStatType type) {
		var map = BowArrowStatConfig.get().bow_stats.get(id);
		if (map == null) return type.getDefault();
		return map.getOrDefault(type, type.getDefault());
	}

	public List<MobEffectInstance> getEffects() {
		var map = BowArrowStatConfig.get().bow_effects.get(id);
		if (map == null) return List.of();
		return map.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(), e.getValue().duration(), e.getValue().amplifier())).toList();
	}

	public float damage() {
		return (float) getValue(ArcheryRegister.DAMAGE.get());
	}

	public int punch() {
		return (int) getValue(ArcheryRegister.PUNCH.get());
	}

	public int pull_time() {
		return (int) getValue(ArcheryRegister.PULL_TIME.get());
	}

	public float speed() {
		return (float) getValue(ArcheryRegister.SPEED.get());
	}

	public int fov_time() {
		return (int) getValue(ArcheryRegister.FOV_TIME.get());
	}

	public float fov() {
		return (float) getValue(ArcheryRegister.FOV.get());
	}

	public void addTooltip(List<Component> list) {
		LangData.STAT_DAMAGE.getWithSign(list, damage());
		LangData.STAT_PUNCH.getWithSign(list, punch());
		list.add(LangData.STAT_PULL_TIME.get(pull_time() / 20d));
		list.add(LangData.STAT_SPEED.get(speed() * 20));
		list.add(LangData.STAT_FOV.get(fov()));
		PotionArrowFeature.addTooltip(getEffects(), list);
	}

}
