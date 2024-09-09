package dev.xkmc.l2archery.content.feature.core;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.item.IBowConfig;
import dev.xkmc.l2archery.content.upgrade.StatHolder;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.List;
import java.util.Set;

public record StatFeature(float fov, int fovTime, float damage, int punch,
						  float speed) implements BowArrowFeature, IBowConfig {

	public static final StatFeature NOOP = new StatFeature(1, 0, 1, 0, 1);

	/**
	 * cannot be implemented due to pulling not having access to ItemStack
	 */
	@Override
	public int pullTime() {
		return 1;
	}

	@Override
	public PotionArrowFeature getEffects() {
		return PotionArrowFeature.NULL;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		if (damage() != NOOP.damage()) list.add(LangData.STAT_DAMAGE.get("x" + damage()));
		if (punch() != NOOP.punch()) list.add(LangData.STAT_PUNCH.get("+" + punch()));
		if (fov() != NOOP.fov()) list.add(LangData.STAT_FOV.get("x" + fov()));
		if (speed() != NOOP.speed()) list.add(LangData.STAT_SPEED.get("x" + speed()));
	}

	@Override
	public boolean allow(IBowConfig config) {
		if (damage > 1f && config.damage() == 0f) return false;
		return !(fov() > 1f) || !(1 / (1 - config.fov()) >= 9.9f);
	}

	public boolean addStatHolder(Set<StatHolder> set) {
		boolean success = true;
		if (damage() != NOOP.damage()) success &= set.add(StatHolder.DAMAGE);
		if (punch() != NOOP.punch()) success &= set.add(StatHolder.PUNCH);
		if (fov() != NOOP.fov()) success &= set.add(StatHolder.FOV);
		if (speed() != NOOP.speed()) success &= set.add(StatHolder.SPEED);
		return success;
	}

}
