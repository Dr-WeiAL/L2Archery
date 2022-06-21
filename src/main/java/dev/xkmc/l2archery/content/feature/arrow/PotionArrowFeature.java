package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;

public record PotionArrowFeature(MobEffectInstance... instances) implements OnHitFeature {

	@Override
	public void onHitEntity(GenericArrowEntity arrow, LivingEntity target) {
		for (MobEffectInstance instance : instances) {
			EffectUtil.addEffect(target, instance, EffectUtil.AddReason.PROF, arrow.getOwner());
		}
	}

	@Override
	public void onHitBlock(GenericArrowEntity arrow, BlockHitResult result) {
	}
}
