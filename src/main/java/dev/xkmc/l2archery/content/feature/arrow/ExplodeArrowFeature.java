package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.explosion.*;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

public record ExplodeArrowFeature(float radius, boolean hurt, boolean breakBlock) implements OnHitFeature {

	@Override
	public void onHitEntity(GenericArrowEntity arrow, LivingEntity target) {
		explode(arrow, arrow.getX(), arrow.getY(), arrow.getZ());
	}

	@Override
	public void onHitBlock(GenericArrowEntity arrow, BlockHitResult result) {
		explode(arrow, result.getLocation().x, result.getLocation().y, result.getLocation().z);
		arrow.discard();
	}

	@Override
	public void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target) {

	}

	private void explode(GenericArrowEntity arrow, double x, double y, double z) {
		BaseExplosionContext base = new BaseExplosionContext(arrow.level, x, y, z, radius);
		Explosion.BlockInteraction type = breakBlock() ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE;
		VanillaExplosionContext mc = new VanillaExplosionContext(arrow, getSource(arrow), null, false, type);
		ModExplosionContext mod = entity -> onExplosionHurt(arrow, entity);
		ExplosionHandler.explode(new BaseExplosion(base, mc, mod));
	}

	private boolean onExplosionHurt(GenericArrowEntity arrow, Entity target) {
		if (target instanceof LivingEntity le) {
			arrow.doPostHurtEffects(le);
		}
		return hurt;
	}

	@Nullable
	private DamageSource getSource(GenericArrowEntity arrow) {
		Entity ent = arrow.getOwner();
		if (ent instanceof LivingEntity le) {
			return DamageSource.explosion(le);
		}
		return null;
	}

	@Override
	public void addTooltip(List<Component> list) {
		if (hurt && breakBlock) {
			list.add(LangData.FEATURE_EXPLOSION_ALL.get(radius));
		}
		if (hurt && !breakBlock) {
			list.add(LangData.FEATURE_EXPLOSION_HURT.get(radius));
		}
	}
}
