package dev.xkmc.l2archery.content.config;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.HashMap;
import java.util.function.Supplier;

@DataGenOnly
public class BaseStatBuilder<B extends BaseStatBuilder<B, T, I>, T extends I, I> extends BaseBuilder<B, T, I, BowArrowStatConfig.ConfigEffect> {

	private final HashMap<I, HashMap<BowArrowStatType, Double>> statmap;
	private final HashMap<BowArrowStatType, Double> stats = new HashMap<>();

	protected BaseStatBuilder(
			BowArrowStatConfig config,
			HashMap<I, HashMap<Holder<MobEffect>, BowArrowStatConfig.ConfigEffect>> map,
			HashMap<I, HashMap<BowArrowStatType, Double>> statmap,
			Supplier<T> bow) {
		super(config, map, bow);
		this.statmap = statmap;
	}

	public final B putStat(BowArrowStatType type, double val) {
		this.stats.put(type, val);
		return getThis();
	}

	public final B putEffect(Holder<MobEffect> type, int duration, int amplifier) {
		this.effects.put(type, new BowArrowStatConfig.ConfigEffect(duration, amplifier));
		return getThis();
	}

	public BowArrowStatConfig end() {
		super.end();
		if (stats.size() > 0)
			statmap.put(id, stats);
		return config;
	}

}
