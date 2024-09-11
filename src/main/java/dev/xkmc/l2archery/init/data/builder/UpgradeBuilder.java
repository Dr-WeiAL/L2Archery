package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.config.ArcheryEffectConfig;
import dev.xkmc.l2archery.content.config.ArcheryEffectEntry;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

@DataGenOnly
public class UpgradeBuilder extends BaseBuilder<UpgradeBuilder, UpgradeStatProvider, Upgrade, Upgrade, ArcheryEffectEntry, ArcheryEffectConfig> {

	protected UpgradeBuilder(UpgradeStatProvider parent, Holder<Upgrade> holder) {
		super(parent, holder);
	}

	public UpgradeBuilder putEffect(Holder<MobEffect> type, int duration, int amplifier) {
		this.effects.put(type, new ArcheryEffectEntry(duration, amplifier));
		return this;
	}

	@Override
	public ArcheryEffectConfig build() {
		return new ArcheryEffectConfig(effects);
	}

}
