package dev.xkmc.l2archery.content.feature.core;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.base.effects.EffectUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public record PotionArrowFeature(List<MobEffectInstance> instances) implements OnHitFeature {

	@Override
	public void onHitLivingEntity(GenericArrowEntity arrow, LivingEntity target, EntityHitResult hit) {
	}

	@Override
	public void onHitBlock(GenericArrowEntity arrow, BlockHitResult result) {
	}

	@Override
	public void postHurtEntity(GenericArrowEntity arrow, LivingEntity target) {
		for (MobEffectInstance instance : instances) {
			EffectUtil.addEffect(target, instance, EffectUtil.AddReason.PROF, arrow.getOwner());
		}
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		//addTooltip(instances, list);
	}

	public static void addTooltip(List<MobEffectInstance> instances, List<Component> list) {
		if (instances.size() > 0) {
			list.add(LangData.STAT_EFFECT.get());
		}
		for (var eff : instances) {
			list.add(getTooltip(eff));
		}
	}

	public static MutableComponent getTooltip(MobEffectInstance eff) {
		MutableComponent comp = Component.translatable(eff.getDescriptionId());
		MobEffect mobeffect = eff.getEffect();
		if (eff.getAmplifier() > 0) {
			comp = Component.translatable("potion.withAmplifier", comp,
					Component.translatable("potion.potency." + eff.getAmplifier()));
		}
		if (eff.getDuration() > 20) {
			comp = Component.translatable("potion.withDuration", comp,
					MobEffectUtil.formatDuration(eff, 1));
		}
		return comp.withStyle(mobeffect.getCategory().getTooltipFormatting());
	}

	@Override
	public boolean allowDuplicate() {
		return true;
	}
}
