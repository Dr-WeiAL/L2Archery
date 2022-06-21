package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2library.util.annotation.ServerOnly;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public interface OnShootFeature extends BowArrowFeature {

	@ServerOnly
	boolean onShoot(Player player, Consumer<Consumer<GenericArrowEntity>> entity);

	default void onClientShoot(GenericArrowEntity entity) {
	}
}
