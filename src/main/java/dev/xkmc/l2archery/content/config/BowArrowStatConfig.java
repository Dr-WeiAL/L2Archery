package dev.xkmc.l2archery.content.config;

import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.init.NetworkManager;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.HashMap;

@SerialClass
public class BowArrowStatConfig extends BaseConfig {

	public static BowArrowStatConfig get() {
		return NetworkManager.STATS.getMerged();
	}

	@SerialClass.SerialField
	public HashMap<ResourceLocation, HashMap<BowArrowStatType, Double>> bow_stats = new HashMap<>();

	@SerialClass.SerialField
	public HashMap<ResourceLocation, HashMap<BowArrowStatType, Double>> arrow_stats = new HashMap<>();

	@SerialClass.SerialField
	public HashMap<ResourceLocation, HashMap<MobEffect, Effect>> bow_effects = new HashMap<>();

	@SerialClass.SerialField
	public HashMap<ResourceLocation, HashMap<MobEffect, Effect>> arrow_effects = new HashMap<>();

	public record Effect(int duration, int amplifier) {
	}

	@DataGenOnly
	public BowBuilder putBow(RegistryEntry<GenericBowItem> bow) {
		return new BowBuilder(this, bow);
	}


	@DataGenOnly
	public ArrowBuilder putArrow(RegistryEntry<GenericArrowItem> arrow) {
		return new ArrowBuilder(this, arrow);
	}


	@DataGenOnly
	public static class BowBuilder {

		private final BowArrowStatConfig config;
		private final ResourceLocation id;

		private final HashMap<BowArrowStatType, Double> stats = new HashMap<>();
		private final HashMap<MobEffect, Effect> effects = new HashMap<>();

		private BowBuilder(BowArrowStatConfig config, RegistryEntry<GenericBowItem> bow) {
			this.config = config;
			this.id = bow.getId();
		}

		public BowBuilder putStat(BowArrowStatType type, double val) {
			this.stats.put(type, val);
			return this;
		}

		public BowBuilder damage(double val) {
			return putStat(ArcheryRegister.DAMAGE.get(), val);
		}

		public BowBuilder punch(double val) {
			return putStat(ArcheryRegister.PUNCH.get(), val);
		}

		public BowBuilder speed(double val) {
			return putStat(ArcheryRegister.SPEED.get(), val);
		}

		public BowBuilder bothTimes(double val) {
			putStat(ArcheryRegister.PULL_TIME.get(), val);
			putStat(ArcheryRegister.FOV_TIME.get(), val);
			return this;
		}

		public BowBuilder fovs(int time, double fov) {
			putStat(ArcheryRegister.FOV_TIME.get(), time);
			putStat(ArcheryRegister.FOV.get(), fov);
			return this;
		}

		public BowBuilder putEffect(MobEffect type, int duration, int amplifier) {
			this.effects.put(type, new Effect(duration, amplifier));
			return this;
		}

		public BowArrowStatConfig end() {
			if (stats.size() > 0)
				config.bow_stats.put(id, stats);
			if (effects.size() > 0)
				config.bow_effects.put(id, effects);
			return config;
		}

	}

	@DataGenOnly
	public static class ArrowBuilder {

		private final BowArrowStatConfig config;
		private final ResourceLocation id;

		private final HashMap<BowArrowStatType, Double> stats = new HashMap<>();
		private final HashMap<MobEffect, Effect> effects = new HashMap<>();

		private ArrowBuilder(BowArrowStatConfig config, RegistryEntry<GenericArrowItem> bow) {
			this.config = config;
			this.id = bow.getId();
		}

		public ArrowBuilder putStat(BowArrowStatType type, double val) {
			this.stats.put(type, val);
			return this;
		}

		public ArrowBuilder damage(double val) {
			return putStat(ArcheryRegister.DAMAGE.get(), val);
		}

		public ArrowBuilder punch(double val) {
			return putStat(ArcheryRegister.PUNCH.get(), val);
		}

		public ArrowBuilder putEffect(MobEffect type, int duration, int amplifier) {
			this.effects.put(type, new Effect(duration, amplifier));
			return this;
		}

		public BowArrowStatConfig end() {
			if (stats.size() > 0)
				config.arrow_stats.put(id, stats);
			if (effects.size() > 0)
				config.arrow_effects.put(id, effects);
			return config;
		}

	}


}
