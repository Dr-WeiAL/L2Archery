package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
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

public class GenericArrowItem extends ArrowItem {

	private final ArrowConfig config;

	public GenericArrowItem(Properties properties, ArrowConfig config) {
		super(properties);
		this.config = config;
	}

	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity user) {
		Arrow arrow = new Arrow(level, user);
		arrow.setEffectsFromItem(stack);
		return arrow;
	}

	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS);
		return enchant > 0 && config.is_inf();
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		config.addTooltip(list);
		getFeatures().addTooltip(list);
	}

	public FeatureList getFeatures() {
		FeatureList list = new FeatureList();
		List<MobEffectInstance> arrow_eff = config.getEffects();
		if (arrow_eff.size() > 0) {
			list.add(new PotionArrowFeature(arrow_eff));
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
