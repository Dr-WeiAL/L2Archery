package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Consumer;

public record FireArrowFeature(int time) implements OnShootFeature, OnHitFeature {

	@Override
	public boolean onShoot(Player player, Consumer<Consumer<GenericArrowEntity>> consumer) {
		consumer.accept((e) -> e.setRemainingFireTicks(time));
		return true;
	}

	@Override
	public void onHitEntity(GenericArrowEntity genericArrow, LivingEntity target) {
		target.setRemainingFireTicks(time);
	}

	@Override
	public void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result) {

	}
}
