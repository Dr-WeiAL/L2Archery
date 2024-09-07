package dev.xkmc.l2archery.content.upgrade;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.init.reg.registrate.NamedEntry;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.function.Function;
import java.util.function.Supplier;

public class Upgrade extends NamedEntry<Upgrade> {

	private final Supplier<BowArrowFeature> feature;

	public Upgrade(Supplier<BowArrowFeature> feature) {
		super(ArcheryRegister.UPGRADE);
		this.feature = Lazy.of(feature);
	}

	public Upgrade(Function<Upgrade, BowArrowFeature> feature) {
		super(ArcheryRegister.UPGRADE);
		this.feature = () -> feature.apply(this);
	}

	public BowArrowFeature getFeature() {
		return feature.get();
	}

	public boolean allow(GenericBowItem bow) {
		return feature.get().allow(bow.config);
	}
}
