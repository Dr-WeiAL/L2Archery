package dev.xkmc.l2archery.content.feature;

import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.types.DefaultShootFeature;
import dev.xkmc.l2archery.content.feature.types.FlightControlFeature;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import dev.xkmc.l2archery.content.feature.types.OnPullFeature;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import net.minecraft.world.effect.MobEffectInstance;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class FeatureList {

	public static boolean canMerge(FeatureList a, FeatureList b) {
		return a.flight == null || b.flight == null;
	}

	@Nullable
	public static FeatureList merge(GenericBowItem.BowConfig a, GenericArrowItem.ArrowConfig b) {
		if (a.feature().flight != null && b.feature().flight != null) {
			return null;
		}
		FeatureList ans = new FeatureList();
		ans.shot.add(DefaultShootFeature.INSTANCE);
		ans.pull.addAll(a.feature().pull);
		ans.pull.addAll(b.feature().pull);
		ans.shot.addAll(a.feature().shot);
		ans.shot.addAll(b.feature().shot);
		ans.hit.addAll(a.feature().hit);
		ans.hit.addAll(b.feature().hit);
		List<MobEffectInstance> bow_eff = a.getEffects();
		if (bow_eff.size() > 0) ans.hit.add(new PotionArrowFeature(bow_eff));
		List<MobEffectInstance> arrow_eff = b.getEffects();
		if (arrow_eff.size() > 0) ans.hit.add(new PotionArrowFeature(arrow_eff));
		ans.flight = a.feature().flight != null ? a.feature().flight : b.feature().flight;
		return ans;
	}

	public List<OnPullFeature> pull = new ArrayList<>();
	public List<OnShootFeature> shot = new ArrayList<>();
	public FlightControlFeature flight = null;
	public List<OnHitFeature> hit = new ArrayList<>();

	public FlightControlFeature getFlightControl() {
		return flight == null ? FlightControlFeature.INSTANCE : flight;
	}

	public FeatureList add(BowArrowFeature feature) {
		if (feature instanceof OnPullFeature f) pull.add(f);
		if (feature instanceof OnShootFeature f) shot.add(f);
		if (feature instanceof FlightControlFeature f) flight = f;
		if (feature instanceof OnHitFeature f) hit.add(f);
		return this;
	}

	public void end() {

	}
}
