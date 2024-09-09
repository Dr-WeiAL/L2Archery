package dev.xkmc.l2archery.content.controller;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.content.item.ArrowData;
import dev.xkmc.l2archery.content.item.BowData;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2library.util.GenericItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArrowFeatureController {

	public record BowArrowUseContext(Level level, LivingEntity user) {

	}

	public static boolean canBowUseArrow(GenericBowItem bow, GenericItemStack<GenericArrowItem> arrow) {
		return FeatureList.canMerge(bow.getFeatures(null), arrow.item().getFeatures());
	}

	@Nullable
	public static AbstractArrow createArrowEntity(BowArrowUseContext ctx, BowData bow, ArrowData arrow, ItemStack arrowStack, ItemStack bowStack) {
		FeatureList features = FeatureList.merge(bow.getFeatures(), arrow.getFeatures());
		List<Consumer<GenericArrowEntity>> list = new ArrayList<>();
		for (OnShootFeature e : features.shot())
			if (!e.onShoot(ctx.user, list::add))
				return null;
		GenericArrowEntity ans = new GenericArrowEntity(ctx.level(), ctx.user(),
				new GenericArrowEntity.ArrowEntityData(bow, arrow), features, arrowStack, bowStack);
		list.forEach(e -> e.accept(ans));
		return ans;
	}

}
