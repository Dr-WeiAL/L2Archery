package dev.xkmc.l2archery.content.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;

public class MobShootHelper {

	public static void shootAimHelper(LivingEntity target, Projectile arrow, double v, double g) {
		double dx = target.getX() - arrow.getX();
		double dy = target.getY(0.5) - arrow.getY();
		double dz = target.getZ() - arrow.getZ();

		double c = dx * dx + dz * dz + dy * dy;
		boolean completed = false;
		if (target instanceof Slime) {
			var clip = target.level().clip(new ClipContext(target.position(), target.position().add(0, -3, 0),
					ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, target));
			if (clip.getType() == HitResult.Type.BLOCK) {
				dy += clip.getLocation().y() - target.getY();
				completed = true;
			}
		}
		if (!completed) {
			double rt = Math.sqrt(c) / v;
			var tv = target.getDeltaMovement();
			dx += tv.x * rt;
			dy += tv.y * rt;
			dz += tv.z * rt;
		}

		c = dx * dx + dz * dz + dy * dy;

		if (g > 0 && c > v * v * 4) {
			double a = g * g / 4;
			double b = dy * g - v * v;

			double delta = b * b - 4 * a * c;
			if (delta > 0) {
				double t21 = (-b + Math.sqrt(delta)) / (2 * a);
				double t22 = (-b - Math.sqrt(delta)) / (2 * a);
				if (t21 > 0 || t22 > 0) {
					double t2 = t21 > 0 ? t22 > 0 ? Math.min(t21, t22) : t21 : t22;
					arrow.shoot(dx, dy + g * t2 / 2, dz, (float) v, 0);
					return;
				}
			}
		}
		arrow.shoot(dx, dy, dz, (float) v, 0);
	}

}
