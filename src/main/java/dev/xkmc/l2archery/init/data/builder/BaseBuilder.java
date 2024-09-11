package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2core.util.DataGenOnly;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.LinkedHashMap;

@DataGenOnly
public abstract class BaseBuilder<
		B extends BaseBuilder<B, P, T, I, E, R>,
		P extends BaseStatProvider<I, R>,
		T extends I, I, E, R> {

	protected final P parent;
	protected final Holder<I> holder;
	protected final LinkedHashMap<Holder<MobEffect>, E> effects = new LinkedHashMap<>();

	protected BaseBuilder(P parent, Holder<I> holder) {
		this.parent = parent;
		this.holder = holder;
	}

	public abstract R build();

	public final B getThis() {
		return Wrappers.cast(this);
	}

	public P end() {
		parent.builder().add(holder, build(), false);
		return parent;
	}
}
