package dev.xkmc.l2archery.events;

import dev.xkmc.l2archery.content.explosion.BaseExplosion;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("unused")
public class GenericEventHandler {

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void fov(ComputeFovModifierEvent event) {
		Player player = Proxy.getClientPlayer();
		if (player == null)
			return;
		if (player.getMainHandItem().getItem() instanceof GenericBowItem bow) {
			float f = event.getFovModifier();
			float i = player.getTicksUsingItem();
			MobEffectInstance ins = player.getEffect(ArcheryRegister.QUICK_PULL.get());
			if (ins != null) {
				i *= 1.5 + 0.5 * ins.getAmplifier();
			}
			float p = bow.getConfig().fov_time();
			event.setNewFovModifier(f * (1 - Math.min(1, i / p) * bow.getConfig().fov()));
		}
	}


	@SubscribeEvent
	public static void onDetonate(ExplosionEvent.Detonate event) {
		if (event.getExplosion() instanceof BaseExplosion exp) {
			event.getAffectedEntities().removeIf(e -> !exp.hurtEntity(e));
		}
	}

	@SubscribeEvent
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		if (left.getItem() instanceof GenericBowItem bow && right.getItem() instanceof UpgradeItem) {
			Upgrade upgrade = UpgradeItem.getUpgrade(right);
			FeatureList list = bow.getFeatures(left);
			if (upgrade != null && list.allow(upgrade.getFeature())) {
				int count = GenericBowItem.getUpgrades(left).size();
				int max = bow.getBaseUpgradeCount(left) + bow.getEnchantmentLevel(left, Enchantments.BINDING_CURSE);
				if (count < max) {
					ItemStack result = left.copy();
					GenericBowItem.addUpgrade(result, upgrade);
					event.setOutput(result);
					event.setMaterialCost(1);
					event.setCost(4 << (count * 2));
				}
			}
		}
	}


}
