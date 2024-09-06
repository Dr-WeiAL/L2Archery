package dev.xkmc.l2archery.content.feature.core;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.*;

public class PotionAggregator {

	private final Map<Holder<MobEffect>, TreeMap<Integer, MobEffectInstance>> map = new LinkedHashMap<>();

	public void add(MobEffectInstance ins) {
		TreeMap<Integer, MobEffectInstance> sub = map.computeIfAbsent(ins.getEffect(), e -> new TreeMap<>());
		MobEffectInstance old = sub.get(ins.getAmplifier());
		if (old != null) {
			if (old.getDuration() < ins.getDuration()) {
				sub.put(ins.getAmplifier(), ins);
			}
		} else {
			sub.put(ins.getAmplifier(), ins);
		}
	}

	public List<MobEffectInstance> build() {
		List<MobEffectInstance> ans = new ArrayList<>();
		for (var ent : map.entrySet()) {
			TreeMap<Integer, MobEffectInstance> sub = ent.getValue();
			int duration = 0;
			for (var ins : sub.descendingMap().entrySet()) {
				if (ins.getValue().getDuration() > duration) {
					ans.add(ins.getValue());
					duration = ins.getValue().getDuration();
				}
			}
		}
		return ans;
	}

	public void addAll(List<MobEffectInstance> instances) {
		for (MobEffectInstance ins : instances) {
			add(ins);
		}
	}
}
