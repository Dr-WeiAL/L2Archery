package dev.xkmc.l2archery.init.data.builder;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.util.DataGenOnly;

@DataGenOnly
public class BowBuilder extends ItemStatBuilder<BowBuilder, GenericBowItem> {

	BowBuilder(ItemStatProvider parent, ItemEntry<GenericBowItem> bow) {
		super(parent, bow);
	}

	public BowBuilder damage(double val) {
		return putStat(ArcheryRegister.DAMAGE.get(), val);
	}

	public BowBuilder punch(double val) {
		return putStat(ArcheryRegister.PUNCH.get(), val);
	}

	public BowBuilder speed(double val) {
		return putStat(ArcheryRegister.SPEED.get(), val);
	}

	public BowBuilder bothTimes(double val) {
		putStat(ArcheryRegister.PULL_TIME.get(), val);
		putStat(ArcheryRegister.FOV_TIME.get(), val);
		return this;
	}

	public BowBuilder fovs(int time, double fov) {
		putStat(ArcheryRegister.FOV_TIME.get(), time);
		putStat(ArcheryRegister.FOV.get(), fov);
		return this;
	}

}
