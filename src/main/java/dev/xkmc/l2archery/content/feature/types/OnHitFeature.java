package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2library.util.annotation.ServerOnly;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public interface OnHitFeature extends BowArrowFeature {

	@ServerOnly
	void onHitEntity(GenericArrowEntity genericArrow, LivingEntity target, EntityHitResult hit);

	@ServerOnly
	void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result);

	@ServerOnly
	void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target);

}
