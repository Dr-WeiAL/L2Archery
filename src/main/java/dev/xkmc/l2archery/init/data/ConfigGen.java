package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.serial.network.ConfigDataProvider;
import net.minecraft.data.DataGenerator;

import java.util.Map;

public class ConfigGen extends ConfigDataProvider {

	public ConfigGen(DataGenerator generator) {
		super(generator, "data/l2archery/archery_config/", "Archery Config");
	}

	@Override
	public void add(Map<String, BaseConfig> map) {
		map.put("stats/bows", new BowArrowStatConfig()
				.putBow(ArcheryItems.STARTER_BOW).end()
				.putBow(ArcheryItems.IRON_BOW).damage(3).bothTimes(40).speed(3.9).end()
				.putBow(ArcheryItems.GLOW_AIM_BOW).end()
				.putBow(ArcheryItems.ENDER_AIM_BOW).end()
				.putBow(ArcheryItems.MAGNIFY_BOW).fovs(60, 0.9).end()
				.putBow(ArcheryItems.EAGLE_BOW).damage(6).punch(2).bothTimes(40).end()
				.putBow(ArcheryItems.WIND_BOW).punch(1).bothTimes(10).speed(3.9).end()
				.putBow(ArcheryItems.EXPLOSION_BOW).end()
				.putBow(ArcheryItems.FLAME_BOW).putEffect(ArcheryRegister.FLAME.get(), 100, 0).end()
				.putBow(ArcheryItems.FROZE_BOW).putEffect(ArcheryRegister.ICE.get(), 600, 0).end()
				.putBow(ArcheryItems.STORM_BOW).end()
				.putBow(ArcheryItems.SLOW_BOW).putEffect(ArcheryRegister.STONE_CAGE.get(), 100, 0).end()
				.putBow(ArcheryItems.WINTER_BOW).putEffect(ArcheryRegister.ICE.get(), 600, 0).end()
				.putBow(ArcheryItems.TURTLE_BOW).damage(6).bothTimes(40).speed(3).end()
				.putBow(ArcheryItems.EARTH_BOW).damage(10).bothTimes(60).speed(3).end()
				.putBow(ArcheryItems.GAIA_BOW).damage(16).bothTimes(80).speed(3).end()
		);

		map.put("stats/arrows", new BowArrowStatConfig()
				.putArrow(ArcheryItems.STARTER_ARROW).end()
				.putArrow(ArcheryItems.COPPER_ARROW).damage(1).end()
				.putArrow(ArcheryItems.IRON_ARROW).damage(1).punch(1).end()
				.putArrow(ArcheryItems.OBSIDIAN_ARROW).damage(2).end()
				.putArrow(ArcheryItems.NO_FALL_ARROW).end()
				.putArrow(ArcheryItems.ENDER_ARROW).end()
				.putArrow(ArcheryItems.TNT_1_ARROW).end()
				.putArrow(ArcheryItems.TNT_2_ARROW).end()
				.putArrow(ArcheryItems.TNT_3_ARROW).end()
				.putArrow(ArcheryItems.FIRE_1_ARROW).putEffect(ArcheryRegister.FLAME.get(), 100, 0).end()
				.putArrow(ArcheryItems.FIRE_2_ARROW).putEffect(ArcheryRegister.FLAME.get(), 200, 1).end()
				.putArrow(ArcheryItems.ICE_ARROW).putEffect(ArcheryRegister.ICE.get(), 600, 0).end()
				.putArrow(ArcheryItems.ACID_ARROW).end()
				.putArrow(ArcheryItems.DISPELL_ARROW).end()
		);
	}

}
