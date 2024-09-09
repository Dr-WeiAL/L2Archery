package dev.xkmc.l2archery.content.feature.core;

import dev.xkmc.l2archery.content.item.IBowConfig;

public record CompoundBowConfig(IBowConfig config, StatFeature feature) implements IBowConfig {

	@Override
	public float speed() {
		return config.speed() * feature.speed();
	}

	@Override
	public float fov() {
		float old = 1 / (1 - config.fov());
		float next = old * feature.fov();
		next = Math.min(10, next);
		return 1 - 1 / next;
	}

	@Override
	public int pullTime() {
		return config.pullTime() * feature.pullTime();
	}

	@Override
	public int fovTime() {
		return config.fovTime() + feature.fovTime();
	}

	@Override
	public PotionArrowFeature getEffects() {
		return config.getEffects();
	}

	@Override
	public float damage() {
		return config.damage() * feature.damage();
	}

	@Override
	public int punch() {
		return config.punch() + feature.punch();
	}

}
