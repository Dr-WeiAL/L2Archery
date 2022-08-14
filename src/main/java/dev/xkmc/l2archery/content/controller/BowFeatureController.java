package dev.xkmc.l2archery.content.controller;

import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2library.util.code.GenericItemStack;
import net.minecraft.world.entity.player.Player;

public class BowFeatureController {

	public static void startUsing(Player player, GenericItemStack<GenericBowItem> bow) {
		bow.item().getFeatures(bow.stack()).pull().forEach(e -> e.onPull(player, bow));
	}

	public static void usingTick(Player player, GenericItemStack<GenericBowItem> bow) {
		bow.item().getFeatures(bow.stack()).pull().forEach(e -> e.tickAim(player, bow));
	}

	public static void stopUsing(Player player, GenericItemStack<GenericBowItem> bow) {
		bow.item().getFeatures(bow.stack()).pull().forEach(e -> e.stopAim(player, bow));

	}
}
