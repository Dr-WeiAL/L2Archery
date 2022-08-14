package dev.xkmc.l2archery.content.feature;

import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public interface BowArrowFeature {

	void addTooltip(List<MutableComponent> list);

	default boolean allowDuplicate() {
		return false;
	}

}
