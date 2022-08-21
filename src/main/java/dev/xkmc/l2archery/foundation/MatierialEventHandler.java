package dev.xkmc.l2archery.foundation;

import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MatierialEventHandler {

	//TODO
	private static final int ENDER_VOID = 16, PHANTOM_SUN = 200;

	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event.getEntity() instanceof EnderMan ender) {
			if (!ender.getLevel().isClientSide() && ender.isCreepy()) {
				if (ender.getOnPos().getY() < ender.getLevel().getMinBuildHeight() - ENDER_VOID) {
					ender.spawnAtLocation(ArcheryItems.VOID_EYE.asStack());
				}
			}
		}
		if (event.getEntity() instanceof Phantom phantom) {
			Level level = phantom.getLevel();
			if (!level.isClientSide()) {
				if (event.getSource().isProjectile() && phantom.getOnPos().getY() > level.getMaxBuildHeight() + PHANTOM_SUN) {
					if (level.isDay() && level.canSeeSky(phantom.getOnPos()) && phantom.isOnFire()) {
						phantom.spawnAtLocation(ArcheryItems.SUN_MEMBRANE.asStack());
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
		if (event.getEntity() instanceof Ghast ghast) {
			Level level = ghast.getLevel();
			DamageSource source = event.getSource();
			if (!level.isClientSide() && source.isFire() && source.isMagic() && (source instanceof IndirectEntityDamageSource ind)) {
				if (ind.getDirectEntity() == ghast && ind.getEntity() instanceof Player && ghast.hasEffect(ArcheryRegister.FLAME.get()))
					ghast.spawnAtLocation(ArcheryItems.SOUL_FLAME.asStack());
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
	}

}
