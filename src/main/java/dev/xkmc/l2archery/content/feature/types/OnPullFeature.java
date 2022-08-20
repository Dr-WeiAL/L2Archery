package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2library.util.code.GenericItemStack;
import net.minecraft.world.entity.player.Player;

public interface OnPullFeature extends BowArrowFeature {

	default void onPull(Player player, GenericItemStack<GenericBowItem> bow) {
	}

	default void tickAim(Player player, GenericItemStack<GenericBowItem> bow) {
	}

	default void stopAim(Player player, GenericItemStack<GenericBowItem> bow) {
	}

}
