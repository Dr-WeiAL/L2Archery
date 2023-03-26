package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Consumer;

public record FireArrowFeature(int time) implements OnShootFeature, OnHitFeature {

	@Override
	public boolean onShoot(LivingEntity player, Consumer<Consumer<GenericArrowEntity>> consumer) {
		consumer.accept((e) -> e.setRemainingFireTicks(time));
		return true;
	}

	@Override
	public void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target) {
		target.setRemainingFireTicks(time);
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_FIRE.get(time / 20d));
	}

}
