package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2core.util.DataGenOnly;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.HashMap;
import java.util.function.Supplier;

@DataGenOnly
public class BaseBuilder<B extends BaseBuilder<B, T, I, E>, T extends I, I, E> {

	protected final BowArrowStatConfig config;
	protected final T id;

	protected final HashMap<I, HashMap<Holder<MobEffect>, E>> effmap;
	protected final HashMap<Holder<MobEffect>, E> effects = new HashMap<>();

	protected BaseBuilder(BowArrowStatConfig config, HashMap<I, HashMap<Holder<MobEffect>, E>> effmap, Supplier<T> bow) {
		this.config = config;
		this.effmap = effmap;
		this.id = bow.get();
	}

	public BowArrowStatConfig end() {
		if (!effects.isEmpty())
			effmap.put(id, effects);
		return config;
	}

	public final B getThis() {
		return Wrappers.cast(this);
	}

}
