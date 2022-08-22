package dev.xkmc.l2archery.foundation.event;

import dev.xkmc.l2archery.init.registrate.ArcheryEnchantments;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchantmentEventHandler {

	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if (event.getEntity() instanceof Player player && (player.getAbilities().instabuild || event.getSource().isBypassInvul())) {
			if (player.getInventory().hasAnyMatching(e -> e.is(ArcheryItems.VOID_ARROW.get()))) {
				event.setCanceled(true);
				return;
			}
			if (player.getProjectile(ArcheryItems.STARTER_BOW.asStack()).is(ArcheryItems.VOID_ARROW.get())) {
				event.setCanceled(true);
				return;
			}
		}
		if (event.getSource().isBypassMagic() || event.getSource().isBypassInvul())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_MAGIC.get(), event.getEntity()) > 0) {
			if (event.getSource().isMagic()) event.setCanceled(true);
		}
		if (event.getSource().isBypassEnchantments())
			return;
		if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_PROJECTILE.get(), event.getEntity()) > 0) {
			if (event.getSource().isProjectile()) event.setCanceled(true);
		} else if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_FIRE.get(), event.getEntity()) > 0) {
			if (event.getSource().isFire()) event.setCanceled(true);
		} else if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_EXPLOSION.get(), event.getEntity()) > 0) {
			if (event.getSource().isExplosion()) event.setCanceled(true);
		} else if (EnchantmentHelper.getEnchantmentLevel(ArcheryEnchantments.ENCH_ENVIRONMENT.get(), event.getEntity()) > 0) {
			if (event.getSource().getEntity() == null) event.setCanceled(true);
		}
	}

}
