package dev.xkmc.l2archery.content.feature.materials;

import dev.xkmc.l2archery.content.feature.types.FlightControlFeature;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class PoseiditeArrowFeature extends FlightControlFeature {

	public PoseiditeArrowFeature() {
		this.water_inertia = inertia;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
	}

}
