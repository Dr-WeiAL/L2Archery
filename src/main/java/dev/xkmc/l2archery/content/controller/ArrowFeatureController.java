package dev.xkmc.l2archery.content.controller;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2library.util.code.GenericItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ArrowFeatureController {

	public record BowArrowUseContext(Level level, Player user, boolean no_consume, float power) {

	}

	public static boolean canBowUseArrow(GenericBowItem bow, GenericItemStack<GenericArrowItem> arrow) {
		return FeatureList.canMerge(bow.config.feature(), arrow.item().config.feature().get());
	}

	@Nullable
	public static AbstractArrow createArrowEntity(BowArrowUseContext ctx,
												  GenericItemStack<GenericBowItem> bow,
												  GenericItemStack<GenericArrowItem> arrow) {
		FeatureList features = Objects.requireNonNull(FeatureList.merge(bow.item().config.feature(), arrow.item().config.feature().get()));
		List<Consumer<GenericArrowEntity>> list = new ArrayList<>();
		for (OnShootFeature e : features.shot)
			if (!e.onShoot(ctx.user, list::add))
				return null;
		GenericArrowEntity ans = new GenericArrowEntity(ctx.level(), ctx.user(),
				new GenericArrowEntity.ArrowEntityData(bow, arrow, ctx.no_consume, ctx.power), features);
		list.forEach(e -> e.accept(ans));
		return ans;
	}

}
