package dev.xkmc.l2archery.foundation;

import dev.xkmc.l2archery.init.data.ArcheryConfig;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2library.init.events.attack.AttackCache;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MaterialDamageListener implements AttackListener {

	@Override
	public void onDamageFinalized(AttackCache cache, ItemStack weapon) {
		LivingDamageEvent event = cache.getLivingDamageEvent();
		if (event == null) return;
		if (cache.getAttackTarget() instanceof Player player) {
			float damage = cache.getDamageOriginal();
			if (event.getSource().isExplosion() && damage >= ArcheryConfig.COMMON.explosionDamage.get()) {
				if (cache.getDamageDealt() < player.getHealth()) {
					player.getInventory().placeItemBackInInventory(ArcheryItems.EXPLOSION_SHARD.asStack());
				}
			}
		}
		if (cache.getAttackTarget() instanceof Chicken chicken) {
			if (event.getSource().getMsgId().equals("sonic_boom")) {
				if (cache.getDamageDealt() < chicken.getHealth()) {
					chicken.spawnAtLocation(ArcheryItems.RESONANT_FEATHER.asStack());
				}
			}
		}
		if (event.getSource().isProjectile() && event.getSource().getEntity() instanceof Player player) {
			if (cache.getDamageOriginal() >= ArcheryConfig.COMMON.spaceDamage.get()) {
				cache.getAttackTarget().spawnAtLocation(ArcheryItems.SPACE_SHARD.asStack());
			}
		}

	}
}
