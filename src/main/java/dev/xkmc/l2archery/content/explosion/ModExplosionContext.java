package dev.xkmc.l2archery.content.explosion;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public interface ModExplosionContext {

	/**
	 * return false to cancel damage
	 */
	boolean hurtEntity(Entity entity);

}
