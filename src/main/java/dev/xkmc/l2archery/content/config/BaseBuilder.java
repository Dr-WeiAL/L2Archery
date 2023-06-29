package dev.xkmc.l2archery.content.config;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.world.effect.MobEffect;

import java.util.HashMap;

@DataGenOnly
public class BaseBuilder<B extends BaseBuilder<B, T, I, E>, T extends I, I, E> {

	protected final BowArrowStatConfig config;
	protected final T id;

	protected final HashMap<I, HashMap<MobEffect, E>> effmap;
	protected final HashMap<MobEffect, E> effects = new HashMap<>();

	protected BaseBuilder(BowArrowStatConfig config, HashMap<I, HashMap<MobEffect, E>> effmap, RegistryEntry<T> bow) {
		this.config = config;
		this.effmap = effmap;
		this.id = bow.get();
	}

	public BowArrowStatConfig end() {
		if (effects.size() > 0)
			effmap.put(id, effects);
		return config;
	}

	public final B getThis() {
		return Wrappers.cast(this);
	}

}
