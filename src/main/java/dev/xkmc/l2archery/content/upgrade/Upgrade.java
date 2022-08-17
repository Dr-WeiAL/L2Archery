package dev.xkmc.l2archery.content.upgrade;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.NamedEntry;

public class Upgrade extends NamedEntry<Upgrade> {

	public Upgrade(L2Registrate.RegistryInstance<Upgrade> registry) {
		super(registry);
	}

	public BowArrowFeature getFeature() {
		return null;//TODO
	}
}
