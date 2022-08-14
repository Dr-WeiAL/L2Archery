package dev.xkmc.l2archery.content.feature;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2archery.content.feature.types.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureList {

	public static boolean canMerge(FeatureList bow, FeatureList arrow) {
		if (bow.flight != null && arrow.flight != null) {
			return false;
		}
		Map<Class<?>, BowArrowFeature> map = new HashMap<>();
		List<List<BowArrowFeature>> lists = List.of(bow.all, arrow.all);
		for (var list : lists) {
			for (BowArrowFeature feature : list) {
				Class<?> cls = feature.getClass();
				if (map.containsKey(cls) && !map.get(cls).allowDuplicate()) {
					return false;
				}
				map.put(cls, feature);
			}
		}
		return true;
	}

	public static FeatureList merge(FeatureList bow, FeatureList arrow) {
		FeatureList ans = new FeatureList();
		ans.shot.add(DefaultShootFeature.INSTANCE);
		for (BowArrowFeature f : bow.inherent) ans.add(f);
		for (BowArrowFeature f : arrow.inherent) ans.add(f);
		ans.stage = Stage.UPGRADE;
		for (BowArrowFeature f : bow.upgrade) ans.add(f);
		ans.stage = Stage.ENCHANT;
		for (BowArrowFeature f : bow.enchant) ans.add(f);
		return ans;
	}

	public Stage stage = Stage.INHERENT;

	private final List<BowArrowFeature> all = new ArrayList<>();
	private final Map<Class<?>, BowArrowFeature> map = new HashMap<>();

	private final List<BowArrowFeature> inherent = new ArrayList<>();
	private final List<BowArrowFeature> upgrade = new ArrayList<>();
	private final List<BowArrowFeature> enchant = new ArrayList<>();

	private final List<OnPullFeature> pull = new ArrayList<>();
	private final List<OnShootFeature> shot = new ArrayList<>();
	private final List<OnHitFeature> hit = new ArrayList<>();
	private FlightControlFeature flight = null;

	public FeatureList add(BowArrowFeature feature) {
		Class<?> cls = feature.getClass();
		if (map.containsKey(cls) && !map.get(cls).allowDuplicate()) {
			return this;
		}
		map.put(cls, feature);

		all.add(feature);

		var list = switch (stage) {
			case INHERENT -> inherent;
			case UPGRADE -> upgrade;
			case ENCHANT -> enchant;
		};

		list.add(feature);

		if (feature instanceof OnPullFeature f) pull.add(f);
		if (feature instanceof OnShootFeature f) shot.add(f);
		if (flight != null && feature instanceof FlightControlFeature f) flight = f;
		if (feature instanceof OnHitFeature f) hit.add(f);
		return this;
	}

	public void addTooltip(List<Component> list) {
		List<Pair<List<BowArrowFeature>, ChatFormatting>> lists = List.of(
				Pair.of(inherent, ChatFormatting.GREEN),
				Pair.of(upgrade, ChatFormatting.GOLD),
				Pair.of(enchant, ChatFormatting.LIGHT_PURPLE));
		for (var l : lists) {
			for (var f : l.getFirst()) {
				List<MutableComponent> temp = new ArrayList<>();
				f.addTooltip(temp);
				for (MutableComponent c : temp) {
					list.add(c.withStyle(l.getSecond()));
				}
			}
		}
	}

	public List<OnPullFeature> pull() {
		return pull;
	}

	public List<OnShootFeature> shot() {
		return shot;
	}

	public FlightControlFeature flight() {
		return flight == null ? FlightControlFeature.INSTANCE : flight;
	}

	public List<OnHitFeature> hit() {
		return hit;
	}

	public enum Stage {
		INHERENT, UPGRADE, ENCHANT
	}

}
