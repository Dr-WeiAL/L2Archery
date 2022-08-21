package dev.xkmc.l2archery.foundation.effect;

import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2library.base.effects.api.ForceEffect;
import dev.xkmc.l2library.base.effects.api.InherentEffect;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class IceEffect extends InherentEffect implements ForceEffect {

	private static final UUID ID = MathHelper.getUUIDFromString(L2Archery.MODID + ":ice");

	public IceEffect(MobEffectCategory type, int color) {
		super(type, color);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, ID.toString(), -0.6F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public void applyEffectTick(LivingEntity target, int level) {
		target.setIsInPowderSnow(true);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int level) {
		return true;
	}

}
