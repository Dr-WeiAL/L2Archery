package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.controller.ArrowFeatureController;
import dev.xkmc.l2archery.content.controller.BowFeatureController;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.bow.IGlowFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.ClientRegister;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.code.GenericItemStack;
import dev.xkmc.l2library.util.raytrace.FastItem;
import dev.xkmc.l2library.util.raytrace.IGlowingTarget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GenericBowItem extends BowItem implements FastItem, IGlowingTarget {

	public record BowConfig(ResourceLocation id, FeatureList feature) {

		private double getValue(BowArrowStatType type) {
			var map = BowArrowStatConfig.get().bow_stats.get(id);
			if (map == null) return type.getDefault();
			return map.getOrDefault(type, type.getDefault());
		}

		public List<MobEffectInstance> getEffects() {
			var map = BowArrowStatConfig.get().bow_effects.get(id);
			if (map == null) return List.of();
			return map.entrySet().stream().map(e -> new MobEffectInstance(e.getKey(), e.getValue().duration(), e.getValue().amplifier())).toList();
		}

		public float damage() {
			return (float) getValue(ArcheryRegister.DAMAGE.get());
		}

		public int punch() {
			return (int) getValue(ArcheryRegister.PUNCH.get());
		}

		public int pull_time() {
			return (int) getValue(ArcheryRegister.PULL_TIME.get());
		}

		public float speed() {
			return (float) getValue(ArcheryRegister.SPEED.get());
		}

		public int fov_time() {
			return (int) getValue(ArcheryRegister.FOV_TIME.get());
		}

		public float fov() {
			return (float) getValue(ArcheryRegister.FOV.get());
		}

		public void addTooltip(List<Component> list) {
			list.add(LangData.STAT_DAMAGE.getWithSign(damage()));
			list.add(LangData.STAT_PUNCH.getWithSign(punch()));
			list.add(LangData.STAT_PULL_TIME.get(pull_time()));
			list.add(LangData.STAT_SPEED.get(speed()));
			list.add(LangData.STAT_FOV.get(fov()));
			PotionArrowFeature.addTooltip(getEffects(), list);
			feature.addTooltip(list);
		}

	}

	public final BowConfig config;

	public GenericBowItem(Properties properties, BowConfig config) {
		super(properties);
		this.config = config;
		ClientRegister.BOW_LIKE.add(this);
	}

	/**
	 * on release bow
	 */
	public void releaseUsing(ItemStack bow, Level level, LivingEntity user, int remaining_pull_time) {
		if (user instanceof Player player) {
			BowFeatureController.stopUsing(player, new GenericItemStack<>(this, bow));
			boolean has_inf = player.getAbilities().instabuild || bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
			ItemStack arrow = player.getProjectile(bow);
			int pull_time = this.getUseDuration(bow) - remaining_pull_time;
			pull_time = ForgeEventFactory.onArrowLoose(bow, level, player, pull_time, !arrow.isEmpty() || has_inf);
			if (pull_time < 0) return;
			if (arrow.isEmpty() && !has_inf) {
				return;
			}
			if (arrow.isEmpty()) { // no arrow: use default arrow
				arrow = new ItemStack(Items.ARROW);
			}
			float power = getPowerForTime(user, pull_time);
			if (((double) power < 0.1D)) { // not enough power: cancel
				return;
			}
			boolean no_consume = player.getAbilities().instabuild || (arrow.getItem() instanceof ArrowItem && ((ArrowItem) arrow.getItem()).isInfinite(arrow, bow, player));
			if (!level.isClientSide) {
				if (!shootArrowOnServer(player, level, bow, arrow, power, no_consume))
					return;
			}

			float pitch = 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F;
			level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, pitch);
			if (!no_consume && !player.getAbilities().instabuild) {
				arrow.shrink(1);
				if (arrow.isEmpty()) {
					player.getInventory().removeItem(arrow);
				}
			}
			player.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	/**
	 * create arrow entity and add to world
	 */
	private boolean shootArrowOnServer(Player player, Level level, ItemStack bow, ItemStack arrow, float power, boolean no_consume) {
		AbstractArrow abstractarrow;
		if (arrow.getItem() instanceof GenericArrowItem genericArrow) {
			abstractarrow = ArrowFeatureController.createArrowEntity(
					new ArrowFeatureController.BowArrowUseContext(level, player, no_consume, power),
					new GenericItemStack<>(this, bow),
					new GenericItemStack<>(genericArrow, arrow));
		} else {
			ArrowItem arrowitem = (ArrowItem) (arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
			abstractarrow = arrowitem.createArrow(level, arrow, player);
			abstractarrow = customArrow(abstractarrow);
			abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, power * 3f, 1.0F);
			if (power == 1.0F) {
				abstractarrow.setCritArrow(true);
			}

			int j = bow.getEnchantmentLevel(Enchantments.POWER_ARROWS);
			if (j > 0) {
				abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double) j * 0.5D + 0.5D);
			}

			int k = bow.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
			if (k > 0) {
				abstractarrow.setKnockback(k);
			}

			if (bow.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
				abstractarrow.setSecondsOnFire(100);
			}

			if (no_consume || player.getAbilities().instabuild && (arrow.is(Items.SPECTRAL_ARROW) || arrow.is(Items.TIPPED_ARROW))) {
				abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
		}
		if (abstractarrow == null) {
			return false;
		}
		bow.hurtAndBreak(1, player, (pl) -> pl.broadcastBreakEvent(player.getUsedItemHand()));
		level.addFreshEntity(abstractarrow);
		return true;
	}

	public float getPullForTime(LivingEntity entity, float time) {
		float f = time / config.pull_time();
		MobEffectInstance ins = entity.getEffect(ArcheryRegister.QUICK_PULL.get());
		if (ins != null) {
			f *= (1.5 + 0.5 * ins.getAmplifier());
		}
		return Math.min(1, f);
	}

	/**
	 * power of arrow, range 0~1
	 * Formula: (t*(t+2))/3
	 * Full in 1 second
	 */
	public float getPowerForTime(LivingEntity entity, float time) {
		float f = getPullForTime(entity, time);
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}
		return Math.min(1, f);
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public void onUsingTick(ItemStack stack, LivingEntity user, int count) {
		if (user instanceof Player player)
			BowFeatureController.usingTick(player, new GenericItemStack<>(this, stack));
	}

	/**
	 * On start pulling
	 */
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		boolean flag = !player.getProjectile(itemstack).isEmpty();

		InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, level, player, hand, flag);
		if (ret != null) return ret;

		if (!player.getAbilities().instabuild && !flag) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			player.startUsingItem(hand);
			BowFeatureController.startUsing(player, new GenericItemStack<>(this, itemstack));
			return InteractionResultHolder.consume(itemstack);
		}
	}

	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return (stack) -> {
			if (stack.is(ItemTags.ARROWS)) {
				return true;
			}
			if (stack.getItem() instanceof GenericArrowItem arrow) {
				return ArrowFeatureController.canBowUseArrow(this, new GenericItemStack<>(arrow, stack));
			}
			return false;
		};
	}

	/**
	 * return custom arrow entity
	 */
	public AbstractArrow customArrow(AbstractArrow arrow) {
		return arrow;
	}

	/**
	 * For mobs
	 */
	public int getDefaultProjectileRange() {
		return 15;
	}

	@Override
	public boolean isFast(ItemStack stack) {
		if (Proxy.getPlayer().hasEffect(ArcheryRegister.RUN_BOW.get()))
			return true;
		return config.feature().pull.stream().anyMatch(e -> e instanceof WindBowFeature);
	}

	@Override
	public int getDistance(ItemStack itemStack) {
		for (BowArrowFeature feature : config.feature().pull) {
			if (feature instanceof IGlowFeature glow) {
				return glow.range();
			}
		}
		return 0;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		config.addTooltip(list);
	}

}
