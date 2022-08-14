package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.feature.types.FlightControlFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class NoFallArrowFeature extends FlightControlFeature {

	public NoFallArrowFeature(int life) {
		this.gravity = 0;
		this.inertia = 1;
		this.water_inertia = 1;
		this.life = life;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_NO_FALL.get(life / 20d));
	}

}
