package dev.xkmc.l2archery.init.data.builder;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.xkmc.l2archery.content.config.ArcheryStatConfig;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;

public record ItemStatProvider(DataMapProvider.Builder<ArcheryStatConfig, Item> builder)
		implements BaseStatProvider<Item, ArcheryStatConfig> {

	public BowBuilder putBow(ItemEntry<GenericBowItem> item) {
		return new BowBuilder(this, item);
	}

	public ArrowBuilder putArrow(ItemEntry<GenericArrowItem> item) {
		return new ArrowBuilder(this, item);
	}


}
