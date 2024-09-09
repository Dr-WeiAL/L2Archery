package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.network.chat.Component;

import java.util.List;

import static net.minecraft.world.item.component.ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT;


public interface IBowConfig extends IGeneralConfig {

	float speed();

	float fov();

	int pull_time();

	int fov_time();

	PotionArrowFeature getEffects();

	default void addStatTooltip(List<Component> list) {
		LangData.STAT_DAMAGE.getWithSign(list, damage());
		LangData.STAT_PUNCH.getWithSign(list, punch());
		list.add(LangData.STAT_PULL_TIME.get(pull_time() / 20d));
		list.add(LangData.STAT_SPEED.get(speed() * 20));
		list.add(LangData.STAT_FOV.get(ATTRIBUTE_MODIFIER_FORMAT.format(1 / (1 - fov()))));
	}

}
