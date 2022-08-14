package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DamageArrowFeature implements OnHitFeature {

	private final Function<GenericArrowEntity, DamageSource> source;
	private final Function<GenericArrowEntity, Float> damage;
	private final Supplier<MutableComponent> comp;

	public DamageArrowFeature(Function<GenericArrowEntity, DamageSource> source,
							  Function<GenericArrowEntity, Float> damage,
							  Supplier<MutableComponent> comp) {
		this.source = source;
		this.damage = damage;
		this.comp = comp;
	}

	@Override
	public void postHurtEntity(GenericArrowEntity arrow, LivingEntity target) {
		DamageSource source = this.source.apply(arrow);
		float damage = this.damage.apply(arrow);
		target.hurt(source, damage);
	}

	@Override
	public void onHitEntity(GenericArrowEntity genericArrow, LivingEntity target) {

	}

	@Override
	public void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result) {

	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(comp.get());
	}

	@Override
	public boolean allowDuplicate() {
		return true;
	}

}
