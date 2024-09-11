package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.config.ArcheryEffectConfig;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.neoforged.neoforge.common.data.DataMapProvider;

public record UpgradeStatProvider(DataMapProvider.Builder<ArcheryEffectConfig, Upgrade> builder)
		implements BaseStatProvider<Upgrade, ArcheryEffectConfig> {

	public UpgradeBuilder putUpgrade(SimpleEntry<Upgrade> item) {
		return new UpgradeBuilder(this, item);
	}

}
