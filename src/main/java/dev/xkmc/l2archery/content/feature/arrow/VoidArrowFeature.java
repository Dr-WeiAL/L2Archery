package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class VoidArrowFeature implements OnHitFeature {

	@Override
	public void onHitEntity(GenericArrowEntity genericArrow, Entity target, EntityHitResult hit) {
		target.hurt(DamageSource.OUT_OF_WORLD, (float) (genericArrow.getBaseDamage() * genericArrow.getDeltaMovement().length()));
		genericArrow.discard();
	}

	@Override
	public void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target) {
		target.hurt(DamageSource.OUT_OF_WORLD, (float) (genericArrow.getBaseDamage() * genericArrow.getDeltaMovement().length()));
	}

	@Override
	public void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result) {
		genericArrow.getLevel().setBlockAndUpdate(result.getBlockPos(), Blocks.AIR.defaultBlockState());
		genericArrow.discard();
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {

	}

}
