package dev.xkmc.l2archery.foundation;

import dev.xkmc.l2archery.init.data.ArcheryConfig;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MaterialEventHandler {

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EnderMan ender) {
			if (!ender.getLevel().isClientSide() && ender.isCreepy() && event.getEntity() instanceof Player player) {
				if (ender.getOnPos().getY() <= ender.getLevel().getMinBuildHeight() - ArcheryConfig.COMMON.belowVoid.get()) {
					ender.spawnAtLocation(ArcheryItems.VOID_EYE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Phantom phantom) {
			Level level = phantom.getLevel();
			if (!level.isClientSide()) {
				if (event.getSource().isProjectile()) {
					if (phantom.getOnPos().getY() >= level.getMaxBuildHeight() + ArcheryConfig.COMMON.phantomHeight.get()) {
						if (level.isDay() && level.canSeeSky(phantom.getOnPos()) && phantom.isOnFire()) {
							phantom.spawnAtLocation(ArcheryItems.SUN_MEMBRANE.asStack());
						}
					}
				}
				if (event.getSource().isExplosion()) {
					phantom.spawnAtLocation(ArcheryItems.STORM_CORE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Drowned drowned) {
			Level level = drowned.getLevel();
			if (!level.isClientSide() && event.getSource() == DamageSource.FREEZE) {
				drowned.spawnAtLocation(ArcheryItems.HARD_ICE.asStack());
			}
		}
		if (event.getEntity() instanceof Guardian guardian) {
			if (!guardian.getLevel().isClientSide() && guardian.hasEffect(ArcheryRegister.STONE_CAGE.get())) {
				guardian.spawnAtLocation(ArcheryItems.BLACKSTONE_CORE.asStack());
			}
		}
		if (event.getEntity() instanceof WitherBoss wither) {
			if (!wither.getLevel().isClientSide() && event.getSource().isProjectile()) {
				wither.spawnAtLocation(ArcheryItems.FORCE_FIELD.asStack());
			}
		}
		if (event.getEntity().fireImmune()) {
			LivingEntity entity = event.getEntity();
			Level level = entity.getLevel();
			DamageSource source = event.getSource();
			if (!level.isClientSide() && source.getMsgId().equals("soul_flame")) {
				entity.spawnAtLocation(ArcheryItems.SOUL_FLAME.asStack());
			}
		}
	}

	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent.EntityInteract event) {
		if (event.getLevel().isClientSide()) return;
		if (event.getItemStack().is(ArcheryItems.WIND_BOTTLE.get()) && event.getTarget() instanceof ShulkerBullet bullet) {
			bullet.hurt(DamageSource.playerAttack(event.getEntity()), 1);
			event.getItemStack().shrink(1);
			event.getEntity().getInventory().placeItemBackInInventory(ArcheryItems.CAPTURED_BULLET.asStack());
		}
	}

}
