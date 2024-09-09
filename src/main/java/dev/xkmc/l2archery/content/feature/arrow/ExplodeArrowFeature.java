package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.content.explosion.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;
import java.util.List;

public record ExplodeArrowFeature(float radius, boolean hurt, boolean breakBlock) implements OnHitFeature {

	@Override
	public void onHitLivingEntity(GenericArrowEntity arrow, LivingEntity target, EntityHitResult hit) {
		explode(arrow, hit.getLocation().x(), hit.getLocation().y(), hit.getLocation().z());
		arrow.discard();
	}

	@Override
	public void onHitBlock(GenericArrowEntity arrow, BlockHitResult result) {
		explode(arrow, result.getLocation().x, result.getLocation().y, result.getLocation().z);
		arrow.discard();
	}

	private void explode(GenericArrowEntity arrow, double x, double y, double z) {
		boolean breaking = breakBlock();
		for (var e : arrow.features.all())
			if (e == ExplosionBreakFeature.INS) {
				breaking = true;
				break;
			}
		BaseExplosionContext base = new BaseExplosionContext(arrow.level(), x, y, z, radius);
		Explosion.BlockInteraction type = breaking ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.KEEP;
		VanillaExplosionContext mc = new VanillaExplosionContext(arrow, getSource(arrow), null, false, type);
		ModExplosionContext mod = entity -> onExplosionHurt(arrow, entity);
		ParticleExplosionContext particle = new ParticleExplosionContext(
				radius <= 2 ? ParticleTypes.EXPLOSION : ParticleTypes.EXPLOSION_EMITTER,
				ParticleTypes.EXPLOSION_EMITTER,
				SoundEvents.GENERIC_EXPLODE
		);
		ExplosionHandler.explode(new BaseExplosion(base, mc, mod, particle));
	}

	private boolean onExplosionHurt(GenericArrowEntity arrow, Entity target) {
		if (target instanceof LivingEntity le) {
			if (arrow.getOwner() instanceof Player pl)
				le.setLastHurtByPlayer(pl);
			arrow.doPostHurtEffects(le);
			return hurt;
		}
		if (target instanceof ItemEntity)
			return false;
		if (target instanceof ExperienceOrb)
			return false;
		if (target instanceof HangingEntity)
			return false;
		if (target instanceof Boat)
			return false;
		if (target instanceof AbstractMinecart)
			return false;
		return hurt;
	}

	@Nullable
	private DamageSource getSource(GenericArrowEntity arrow) {
		Entity ent = arrow.getOwner();
		if (ent instanceof LivingEntity le) {
			return le.level().damageSources().explosion(arrow, ent);
		}
		return null;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		if (hurt && breakBlock) {
			list.add(LangData.FEATURE_EXPLOSION_ALL.get(radius));
		}
		if (hurt && !breakBlock) {
			list.add(LangData.FEATURE_EXPLOSION_HURT.get(radius));
		}
		if (!hurt && !breakBlock) {
			list.add(LangData.FEATURE_EXPLOSION_NONE.get(radius));
		}
	}
}
