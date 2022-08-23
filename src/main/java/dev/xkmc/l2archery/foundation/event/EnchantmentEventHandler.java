package dev.xkmc.l2archery.foundation.event;

import dev.xkmc.l2archery.init.registrate.ArcheryEnchantments;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchantmentEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (event.getSource().isBypassMagic() || event.getSource().isBypassInvul())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_MAGIC.get(), event.getEntity()) > 0) {
			if (event.getSource().isMagic()) event.setCanceled(true);
		}
		if (event.getSource().isBypassEnchantments())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_PROJECTILE.get(), event.getEntity()) > 0) {
			if (event.getSource().isProjectile()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_FIRE.get(), event.getEntity()) > 0) {
			if (event.getSource().isFire()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_EXPLOSION.get(), event.getEntity()) > 0) {
			if (event.getSource().isExplosion()) event.setCanceled(true);
		}
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_ENVIRONMENT.get(), event.getEntity()) > 0) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
	}

}
