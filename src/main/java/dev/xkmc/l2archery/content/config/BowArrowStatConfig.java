package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

import java.util.HashMap;

@SerialClass
public class BowArrowStatConfig extends BaseConfig {

	@SerialClass.SerialField
	public HashMap<Item, HashMap<BowArrowStatType, Double>> bow_stats;

	@SerialClass.SerialField
	public HashMap<Item, HashMap<BowArrowStatType, Double>> arrow_stats;

	@SerialClass.SerialField
	public HashMap<Item, HashMap<MobEffect, Effect>> bow_effects;

	@SerialClass.SerialField
	public HashMap<Item, HashMap<MobEffect, Effect>> arrow_effects;

	public record Effect(int amplifier, int duration) {
	}

}
