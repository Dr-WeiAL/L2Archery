package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

@DataGenOnly
public class UpgradeBuilder extends BaseBuilder<UpgradeBuilder, Upgrade, Upgrade, BowArrowStatConfig.ConfigEffect> {

	UpgradeBuilder(BowArrowStatConfig config, SimpleEntry<Upgrade> up) {
		super(config, config.upgrade_effects, up);
	}

	public UpgradeBuilder putEffect(Holder<MobEffect> type, int duration, int amplifier) {
		this.effects.put(type, new BowArrowStatConfig.ConfigEffect(duration, amplifier));
		return this;
	}

}
