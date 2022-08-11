package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

	public record ArrowConfig(ResourceLocation id, boolean is_inf, FeatureList feature) {

		private double getValue(BowArrowStatType type) {
			var map = BowArrowStatConfig.get().arrow_stats.get(id);
			if (map == null) return type.getDefault();
			return map.getOrDefault(type, type.getDefault());
		}

		public List<MobEffectInstance> getEffects() {
			var map = BowArrowStatConfig.get().arrow_effects.get(id);
			if (map == null) return List.of();
			return map.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(), e.getValue().duration(), e.getValue().amplifier())).toList();
		}

		public float damage() {
			return (float) getValue(ArcheryRegister.DAMAGE.get());
		}

		public int punch() {
			return (int) getValue(ArcheryRegister.PUNCH.get());
		}

		public void addTooltip(List<Component> list) {
			LangData.STAT_DAMAGE.getWithSign(list, damage());
			LangData.STAT_PUNCH.getWithSign(list, punch());
			PotionArrowFeature.addTooltip(getEffects(), list);
			feature.addTooltip(list);
		}

	}

	public final ArrowConfig config;

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
	}

}
