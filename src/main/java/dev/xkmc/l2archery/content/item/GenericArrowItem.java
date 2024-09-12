package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.controller.ArrowFeatureController;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.bow.InfinityFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2core.init.reg.ench.EnchHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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

	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity user, @Nullable ItemStack bow) {
		if (bow == null) bow = user.getItemInHand(user.getUsedItemHand());
		BowData bowData = bow.getItem() instanceof GenericBowItem bowItem ?
				BowData.of(bowItem, bow) : BowData.of(ArcheryItems.STARTER_BOW.get(), bow);
		var arrow = ArrowFeatureController.createArrowEntity(
				new ArrowFeatureController.BowArrowUseContext(level, user),
				bowData, ArrowData.of(this), stack.copyWithCount(1), bow);
		if (arrow == null) {
			arrow = new Arrow(level, user, stack.copyWithCount(1), bow);
		}
		return arrow;
	}

	@Override
	public boolean isInfinite(ItemStack ammo, ItemStack bow, LivingEntity player) {
		int enchant = EnchHelper.getLv(bow, Enchantments.INFINITY);
		int infLevel = enchant > 0 ? 1 : 0;
		if (bow.getItem() instanceof GenericBowItem bowItem) {
			infLevel = Math.max(InfinityFeature.getLevel(bowItem.getFeatures(bow)), infLevel);
		}
		if (config.infLevel() > 0) {
			return infLevel + config.infLevel() >= 3 || super.isInfinite(ammo, bow, player);
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
		config.addTooltip(list);
		getFeatures().addTooltip(list);
	}

	public FeatureList getFeatures() {
		FeatureList list = new FeatureList();
		PotionArrowFeature arrow_eff = config.getEffects();
		if (!arrow_eff.instances().isEmpty()) {
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
