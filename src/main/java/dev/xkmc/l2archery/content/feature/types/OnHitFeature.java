package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2library.util.annotation.ServerOnly;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;

public interface OnHitFeature extends BowArrowFeature {

	@ServerOnly
	void onHitEntity(GenericArrowEntity genericArrow, LivingEntity target);

	@ServerOnly
	void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result);

}
