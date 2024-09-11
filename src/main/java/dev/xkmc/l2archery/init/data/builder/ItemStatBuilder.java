package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.config.ArcheryEffectEntry;
import dev.xkmc.l2archery.content.config.ArcheryStatConfig;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;

@DataGenOnly
public class ItemStatBuilder<B extends ItemStatBuilder<B, T>, T extends Item> extends
		BaseBuilder<B, ItemStatProvider, T, Item, ArcheryEffectEntry, ArcheryStatConfig> {

	private final LinkedHashMap<BowArrowStatType, Double> stats = new LinkedHashMap<>();

	protected ItemStatBuilder(ItemStatProvider parent, Holder<Item> holder) {
		super(parent, holder);
	}

	public final B putStat(BowArrowStatType type, double val) {
		this.stats.put(type, val);
		return getThis();
	}

	public final B putEffect(Holder<MobEffect> type, int duration, int amplifier) {
		this.effects.put(type, new ArcheryEffectEntry(duration, amplifier));
		return getThis();
	}

	public ArcheryStatConfig build() {
		return new ArcheryStatConfig(stats, effects);
	}

}
