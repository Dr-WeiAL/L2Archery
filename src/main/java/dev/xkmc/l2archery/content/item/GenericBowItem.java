package dev.xkmc.l2archery.content.item;


import dev.xkmc.l2archery.content.controller.ArrowFeatureController;
import dev.xkmc.l2archery.content.controller.BowFeatureController;
import dev.xkmc.l2archery.content.enchantment.BowEnchantment;
import dev.xkmc.l2archery.content.energy.IFluxItem;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.bow.IGlowFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.feature.core.CompoundBowConfig;
import dev.xkmc.l2archery.content.feature.bow.FluxFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.init.L2ArcheryClient;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.util.Proxy;
import dev.xkmc.l2library.util.code.GenericItemStack;
import dev.xkmc.l2library.util.nbt.ItemCompoundTag;
import dev.xkmc.l2library.util.raytrace.FastItem;
import dev.xkmc.l2library.util.raytrace.IGlowingTarget;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static dev.xkmc.l2archery.init.data.TagGen.TAG_ENERGY;


public class GenericBowItem extends BowItem implements FastItem, IGlowingTarget, IFluxItem {

	public static final String KEY = "upgrades";


	public static List<Upgrade> getUpgrades(ItemStack stack) {
		List<Upgrade> ans = new ArrayList<>();
		ListTag list = ItemCompoundTag.of(stack).getSubList(KEY, Tag.TAG_STRING).getOrCreate();
		for (int i = 0; i < list.size(); i++) {
			Upgrade up = ArcheryRegister.UPGRADE.get().getValue(new ResourceLocation(list.getString(i)));
			if (up != null) {
				ans.add(up);
			}
		}
		return ans;
	}

	public static void addUpgrade(ItemStack result, Upgrade upgrade) {
		List<Upgrade> list = getUpgrades(result);
		list.add(upgrade);
		ListTag tag = new ListTag();
		for (Upgrade up : list) {
			tag.add(StringTag.valueOf(up.getID()));
		}
		ItemCompoundTag.of(result).getSubList(KEY, Tag.TAG_STRING).setTag(tag);
	}

	public final BowConfig config;

	public GenericBowItem(Properties properties, BowConfig config) {
		super(properties);
		this.config = config;
		L2ArcheryClient.BOW_LIKE.add(this);
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
				if (!level.isClientSide) {
					arrow.shrink(1);
					if (arrow.isEmpty()) {
						player.getInventory().removeItem(arrow);
					}
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
		ArrowData data = parseArrow(arrow);
		if (data != null) {
			abstractarrow = ArrowFeatureController.createArrowEntity(
					new ArrowFeatureController.BowArrowUseContext(level, player, no_consume, power),
					BowData.of(this, bow), data);
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
		MobEffectInstance ins = entity.getEffect(ArcheryEffects.QUICK_PULL.get());
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
			data = ArrowData.of(arrow.getItem(), arrow);
		}
		return data;
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
		if (Proxy.getPlayer().hasEffect(ArcheryEffects.RUN_BOW.get()))
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
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
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
		List<MobEffectInstance> bow_eff = config.getEffects();
		if (bow_eff.size() > 0) ans.add(new PotionArrowFeature(bow_eff));
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
			stack.getAllEnchantments().forEach((k, v) -> {
				if (k instanceof BowEnchantment b) {
					ans.add(b.getFeature(v));
				}
			});
		}
		return ans;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == Enchantments.BINDING_CURSE) return true;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public Rarity getRarity(ItemStack stack) {
		return Rarity.values()[config.rank()];
	}

	public int getUpgradeSlot(ItemStack stack) {
		return config.rank() + getEnchantmentLevel(stack, Enchantments.BINDING_CURSE) - getUpgrades(stack).size();
	}

	public static void remakeEnergy(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt(TAG_ENERGY, 0);
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
		//TODO flux
		return super.isBarVisible(stack);
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		//TODO flux
		return super.getBarWidth(stack);
	}

	@Override
	public int getBarColor(ItemStack stack) {
		//TODO flux
		return super.getBarColor(stack);
	}
}
