package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.entity.PartEntity;

import java.util.List;

public class VoidArrowFeature implements OnHitFeature {

	@Override
	public void onHitEntity(GenericArrowEntity genericArrow, Entity target, EntityHitResult hit) {
		if (target instanceof LivingEntity living) {
			onHitLivingEntity(genericArrow, living, hit);
			return;
		}
		if (target instanceof PartEntity<?>) {
			Entity parent = target;
			while (parent instanceof PartEntity<?> part) {
				parent = part.getParent();
				if (part.getParent() == part)
					break;
			}
			if (parent instanceof LivingEntity living) {
				onHitLivingEntity(genericArrow, living, hit);
				return;
			}
		}
		genericArrow.discard();
		target.discard();
	}

	@Override
	public void onHitLivingEntity(GenericArrowEntity genericArrow, LivingEntity target, EntityHitResult hit) {
		if (target instanceof Player player) {
			if (player.getInventory().hasAnyMatching(e -> e.is(ArcheryItems.VOID_ARROW.get()))) {
				return;
			}
			if (player.getProjectile(ArcheryItems.STARTER_BOW.asStack()).is(ArcheryItems.VOID_ARROW.get())) {
				return;
			}
		}
		if (target instanceof EnderDragon dragon) {
			dragon.hurt(DamageSource.arrow(genericArrow, genericArrow.getOwner()).bypassArmor().bypassMagic().bypassInvul(), 2048);
			return;
		}
		target.kill();
		genericArrow.discard();
		if (target.isAlive()) target.discard();
	}

	@Override
	public void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target) {
		if (target instanceof Player player) {
			if (player.getInventory().hasAnyMatching(e -> e.is(ArcheryItems.VOID_ARROW.get()))) {
				return;
			}
			if (player.getProjectile(ArcheryItems.STARTER_BOW.asStack()).is(ArcheryItems.VOID_ARROW.get())) {
				return;
			}
		}
		target.kill();
		if (target.isAlive()) target.discard();
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {

	}

}
