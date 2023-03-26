package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2library.util.annotation.ServerOnly;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Consumer;

public interface OnShootFeature extends BowArrowFeature {

	@ServerOnly
	boolean onShoot(LivingEntity player, Consumer<Consumer<GenericArrowEntity>> entity);

	default void onClientShoot(GenericArrowEntity entity) {
	}
}
