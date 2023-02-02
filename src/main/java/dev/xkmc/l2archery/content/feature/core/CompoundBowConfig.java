package dev.xkmc.l2archery.content.feature.core;

import dev.xkmc.l2archery.content.item.BowConfig;
import dev.xkmc.l2archery.content.item.IBowConfig;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;

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
	public int pull_time() {
		return config.pull_time() * feature.pull_time();
	}

	@Override
	public int fov_time() {
		return config.fov_time() + feature.fov_time();
	}

	@Override
	public List<MobEffectInstance> getEffects() {
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
