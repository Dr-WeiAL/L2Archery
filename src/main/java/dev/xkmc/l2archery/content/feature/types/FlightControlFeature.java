package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FlightControlFeature implements BowArrowFeature {

	public static final FlightControlFeature INSTANCE = new FlightControlFeature();

	public float gravity = 0.05f;
	public float inertia = 0.99f;
	public float water_inertia = 0.6f;
	public int life = -1;
	public int ground_life = 1200;

	public void tickMotion(GenericArrowEntity entity, Vec3 velocity) {
		float inertia = entity.isInWater() ? water_inertia : this.inertia;
		velocity = velocity.scale(inertia);
		float grav = !entity.isNoGravity() && !entity.isNoPhysics() ? gravity : 0;
		entity.setDeltaMovement(velocity.x, velocity.y - grav, velocity.z);
	}

	@Override
	public void addTooltip(List<Component> list) {
	}

}
