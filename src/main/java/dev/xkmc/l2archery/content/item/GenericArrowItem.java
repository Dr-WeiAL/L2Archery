package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.controller.ArrowFeatureController;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.bow.InfinityFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class GenericArrowItem extends ArrowItem {

	private final ArrowConfig config;

	public GenericArrowItem(Properties properties, Function<GenericArrowItem, ArrowConfig> config) {
		super(properties);
		this.config = config.apply(this);
	}

	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity user) {
		ItemStack bow = user.getItemInHand(user.getUsedItemHand());
		BowData bowData = bow.getItem() instanceof GenericBowItem bowItem ?
				BowData.of(bowItem, bow) : BowData.of(ArcheryItems.STARTER_BOW.get(), bow);
		var arrow = ArrowFeatureController.createArrowEntity(
				new ArrowFeatureController.BowArrowUseContext(level, user, true, 1),
				bowData, ArrowData.of(this));
		if (arrow == null) {
			arrow = new Arrow(level, user);
		} else {
			arrow.addTag(GenericArrowEntity.TAG);
		}
		return arrow;
	}

	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
		int infLevel = enchant > 0 ? 1 : 0;
		if (bow.getItem() instanceof GenericBowItem bowItem) {
			infLevel = Math.max(InfinityFeature.getLevel(bowItem.getFeatures(bow)), infLevel);
		}
		if (config.infLevel() == 2) {
			return infLevel >= 1 || super.isInfinite(stack, bow, player);
		} else if (config.infLevel() == 1) {
			return infLevel >= 2 || super.isInfinite(stack, bow, player);
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		config.addTooltip(list);
		getFeatures().addTooltip(list);
	}

	public FeatureList getFeatures() {
		FeatureList list = new FeatureList();
		PotionArrowFeature arrow_eff = config.getEffects();
		if (arrow_eff.instances().size() > 0) {
			list.add(arrow_eff);
		}
		for (BowArrowFeature feature : config.feature()) {
			list.add(feature);
		}
		return list;
	}

	public IGeneralConfig getConfig() {
		return config;
	}

}
