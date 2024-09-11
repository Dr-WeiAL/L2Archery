package dev.xkmc.l2archery.init.data.builder;

import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.util.DataGenOnly;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

@DataGenOnly
public class ArrowBuilder extends ItemStatBuilder<ArrowBuilder, GenericArrowItem> {

	protected ArrowBuilder(ItemStatProvider parent, Holder<Item> holder) {
		super(parent, holder);
	}

	public ArrowBuilder damage(double val) {
		return putStat(ArcheryRegister.DAMAGE.get(), val);
	}

	public ArrowBuilder punch(double val) {
		return putStat(ArcheryRegister.PUNCH.get(), val);
	}

}
