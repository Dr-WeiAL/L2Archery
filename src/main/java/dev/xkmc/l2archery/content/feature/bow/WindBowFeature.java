package dev.xkmc.l2archery.content.feature.bow;

import dev.xkmc.l2archery.content.feature.types.OnPullFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class WindBowFeature implements OnPullFeature {

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_WIND_BOW.get());
	}
}
