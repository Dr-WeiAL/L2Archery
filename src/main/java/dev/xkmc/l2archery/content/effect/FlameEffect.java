package dev.xkmc.l2archery.content.effect;

import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FlameEffect extends InherentEffect implements ForceEffect {

	public FlameEffect(MobEffectCategory type, int color) {
		super(type, color);
	}

	@Override
	public void applyEffectTick(LivingEntity target, int level) {
		if (!target.fireImmune()) {
			target.hurt(DamageSource.indirectMagic(target, target.getLastHurtByMob()), 2 << level);
		}
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return tick % 10 == 0;
	}

}
