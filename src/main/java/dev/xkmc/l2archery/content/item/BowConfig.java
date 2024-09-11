package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.util.Proxy;

import java.util.List;

public record BowConfig(GenericBowItem id, int rank, List<BowArrowFeature> feature) implements IBowConfig {

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

	public int pullTime() {
		return (int) getValue(ArcheryRegister.PULL_TIME.get());
	}

	public float speed() {
		return (float) getValue(ArcheryRegister.SPEED.get());
	}

	public int fovTime() {
		return (int) getValue(ArcheryRegister.FOV_TIME.get());
	}

	public float fov() {
		return (float) getValue(ArcheryRegister.FOV.get());
	}

}
