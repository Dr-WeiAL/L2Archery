package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.controller.ArrowFeatureController;
import dev.xkmc.l2archery.content.controller.BowFeatureController;
import dev.xkmc.l2archery.content.enchantment.IBowEnchantment;
import dev.xkmc.l2archery.content.energy.IFluxItem;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.bow.FluxFeature;
import dev.xkmc.l2archery.content.feature.bow.IGlowFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.feature.core.CompoundBowConfig;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.upgrade.BowUpgrade;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2core.init.reg.ench.EnchHelper;
import dev.xkmc.l2core.init.reg.ench.LegacyEnchantment;
import dev.xkmc.l2library.content.raytrace.FastItem;
import dev.xkmc.l2library.content.raytrace.IGlowingTarget;
import dev.xkmc.l2library.util.GenericItemStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericBowItem extends BowItem implements FastItem, IGlowingTarget, IFluxItem {

	public static List<Upgrade> getUpgrades(ItemStack stack) {
		return ArcheryItems.BOW_UPGRADE.getOrDefault(stack, BowUpgrade.DEF).list();
	}

	public static void addUpgrade(ItemStack result, Upgrade upgrade) {
		var current = ArcheryItems.BOW_UPGRADE.getOrDefault(result, BowUpgrade.DEF);
		ArcheryItems.BOW_UPGRADE.set(result, current.add(upgrade));
	}

	public final BowConfig config;

	public GenericBowItem(Properties properties, Function<GenericBowItem, BowConfig> config) {
		super(properties);
		this.config = config.apply(this);
		ArcheryItems.BOW_LIKE.add(this);
	}

	@Override
	protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
		super.shootProjectile(shooter, projectile, index, velocity * config.speed() / 3, 0, angle, target);
	}

	@Override
	protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack arrow, boolean isCrit) {
		ArrowData data = parseArrow(arrow);
		Projectile ans;
		if (data != null) {
			ans = ArrowFeatureController.createArrowEntity(
					new ArrowFeatureController.BowArrowUseContext(level, shooter),
					BowData.of(this, weapon), data, arrow, weapon);
			if (ans != null) {
				return ans;
			}
		}
		return super.createProjectile(level, shooter, weapon, arrow, isCrit);
	}

	public float getPullForTime(LivingEntity entity, float time) {
		float f = time / config.pullTime();
		MobEffectInstance ins = entity.getEffect(ArcheryEffects.QUICK_PULL);
		if (ins != null) {
			f *= (1.5f + 0.5f * ins.getAmplifier());
		}
		return Math.min(1, f);
	}

	/**
	 * TODO custom power
	 */
	public float getPowerForTime(LivingEntity entity, float time) {
		float f = getPullForTime(entity, time);
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		return Math.min(1, f);
	}

	@Override
	public void onUseTick(Level level, LivingEntity user, ItemStack stack, int count) {
		if (user instanceof Player player)
			BowFeatureController.usingTick(player, new GenericItemStack<>(this, stack));
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		var ans = super.use(level, player, hand);
		if (player.isUsingItem()) {
			BowFeatureController.startUsing(player, new GenericItemStack<>(this, player.getItemInHand(hand)));
		}
		return ans;
	}

	/**
	 * Overwrite
	 */
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player player) {
			ItemStack itemstack = player.getProjectile(stack);
			if (!itemstack.isEmpty()) {
				int i = this.getUseDuration(stack, entityLiving) - timeLeft;
				i = net.neoforged.neoforge.event.EventHooks.onArrowLoose(stack, level, player, i, !itemstack.isEmpty());
				if (i < 0) return;
				// add user parameter
				float f = getPowerForTime(player, i);
				if (!((double) f < 0.1)) {
					List<ItemStack> list = draw(stack, itemstack, player);
					if (level instanceof ServerLevel serverlevel && !list.isEmpty()) {
						this.shoot(serverlevel, player, player.getUsedItemHand(), stack, list, f * 3.0F, 1.0F, f == 1.0F, null);
					}

					level.playSound(
							null,
							player.getX(),
							player.getY(),
							player.getZ(),
							SoundEvents.ARROW_SHOOT,
							SoundSource.PLAYERS,
							1.0F,
							1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F
					);
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return (stack) -> {
			if (stack.is(Items.ARROW) || stack.is(Items.SPECTRAL_ARROW) || stack.is(Items.TIPPED_ARROW)) {
				return true;
			}
			if (stack.getItem() instanceof GenericArrowItem arrow) {
				return ArrowFeatureController.canBowUseArrow(this, new GenericItemStack<>(arrow, stack));
			}
			return false;
		};
	}

	@Nullable
	public ArrowData parseArrow(ItemStack arrow) {
		ArrowData data = null;
		if (arrow.getItem() instanceof GenericArrowItem gen) {
			data = ArrowData.of(gen);
		} else if (arrow.is(Items.ARROW)) {
			data = ArrowData.of(arrow.getItem());
		} else if (arrow.is(Items.SPECTRAL_ARROW)) {
			data = ArrowData.of(arrow.getItem());
		} else if (arrow.is(Items.TIPPED_ARROW)) {
			data = ArrowData.of(arrow.getItem(), arrow.get(DataComponents.POTION_CONTENTS));
		}
		return data;
	}

	public int getDefaultProjectileRange() {
		return 30;
	}

	@Override
	public boolean isFast(ItemStack stack) {
		var player = Minecraft.getInstance().player;
		if (player != null && player.hasEffect(ArcheryEffects.RUN_BOW))
			return true;
		return config.feature().stream().anyMatch(e -> e instanceof WindBowFeature);
	}

	@Override
	public int getDistance(ItemStack itemStack) {
		for (BowArrowFeature feature : getFeatures(itemStack).all()) {
			if (feature instanceof IGlowFeature glow) {
				return glow.range();
			}
		}
		return 0;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext ctx, List<Component> list, TooltipFlag flag) {
		List<Upgrade> ups = getUpgrades(stack);
		IBowConfig config = this.config;
		for (Upgrade up : ups) {
			if (up.getFeature() instanceof StatFeature f) {
				config = new CompoundBowConfig(config, f);
			}
		}
		config.addStatTooltip(list);
		for (Upgrade up : ups) {
			list.add(Component.translatable(up.getDescriptionId()).withStyle(ChatFormatting.GOLD));
		}
		FeatureList f = getFeatures(stack);
		f.addEffectsTooltip(list);
		f.addTooltip(list);
		tooltipDelegate(stack, list);
		list.add(LangData.REMAIN_UPGRADE.get(getUpgradeSlot(stack)));
	}

	public FeatureList getFeatures(@Nullable ItemStack stack) {
		FeatureList ans = new FeatureList();
		PotionArrowFeature bow_eff = config.getEffects();
		if (!bow_eff.instances().isEmpty()) ans.add(bow_eff);
		for (BowArrowFeature feature : config.feature()) {
			ans.add(feature);
		}
		if (stack != null) {
			ans.stage = FeatureList.Stage.UPGRADE;
			for (Upgrade up : getUpgrades(stack)) {
				BowArrowFeature f = up.getFeature();
				if (f instanceof StatFeature) continue;
				ans.add(f);
			}
			ans.stage = FeatureList.Stage.ENCHANT;
			for (var e : LegacyEnchantment.findAll(stack, IBowEnchantment.class, true)) {
				BowArrowFeature f = e.val().getFeature(e.lv());
				if (!(f instanceof StatFeature)) {
					ans.add(f);
				}
			}
		}
		return ans;
	}

	@Override
	public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
		if (enchantment.is(Enchantments.BINDING_CURSE)) return true;
		return super.isPrimaryItemFor(stack, enchantment);
	}

	public int getUpgradeSlot(ItemStack stack) {
		var upgrades = ArcheryItems.BOW_UPGRADE.getOrDefault(stack, BowUpgrade.DEF);
		return config.rank() + EnchHelper.getLv(stack, Enchantments.BINDING_CURSE) + upgrades.additional() - getUpgrades(stack).size();
	}

	public static void remakeEnergy(ItemStack stack) {
		stack.remove(ArcheryItems.ENERGY);
	}

	@Nullable
	public FluxFeature getFluxFeature(ItemStack stack) {
		for (var upgrade : getUpgrades(stack)) {
			if (upgrade.getFeature() instanceof FluxFeature ff) {
				return ff;
			}
		}
		return null;
	}

	@Override
	public int getStorageRank(ItemStack stack) {
		return config.rank();
	}

	@Override
	public int getConsumptionRank(ItemStack stack) {
		return config.rank() + Math.min(4, getUpgrades(stack).size());
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getFluxFeature(stack) != null || super.isBarVisible(stack);
	}

}
