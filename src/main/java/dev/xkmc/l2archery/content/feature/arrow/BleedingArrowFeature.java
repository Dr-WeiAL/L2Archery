package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public record BleedingArrowFeature(int dur, int max) implements OnHitFeature {

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_BLEED.get());
	}

	@Override
	public void postHurtEntity(GenericArrowEntity arrow, LivingEntity target) {
		LCEffects.BLEED.get().addTo(target, dur, max, EffectUtil.AddReason.NONE, arrow.getOwner());
	}

}
