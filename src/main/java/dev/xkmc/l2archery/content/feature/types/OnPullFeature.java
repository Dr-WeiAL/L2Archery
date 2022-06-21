package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2library.util.code.GenericItemStack;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import net.minecraft.world.entity.player.Player;

public interface OnPullFeature extends BowArrowFeature {

	void onPull(Player player, GenericItemStack<GenericBowItem> bow);

	void tickAim(Player player, GenericItemStack<GenericBowItem> bow);

	void stopAim(Player player, GenericItemStack<GenericBowItem> bow);

}
