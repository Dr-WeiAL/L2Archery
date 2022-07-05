package dev.xkmc.l2archery.content.stats;

import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.base.NamedEntry;

public class BowArrowStatType extends NamedEntry<BowArrowStatType> {

	public final double defaultValue;

	public BowArrowStatType(double defaultValue) {
		super(ArcheryRegister.STAT_TYPE);
		this.defaultValue = defaultValue;
	}

	public double getDefault() {
		return defaultValue;
	}
}
