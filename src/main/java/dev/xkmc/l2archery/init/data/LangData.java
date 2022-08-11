package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;

import javax.annotation.Nullable;
import java.util.Locale;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public enum LangData {
	STAT_DAMAGE("stat.damage", "Damage: %s", 1, ChatFormatting.BLUE),
	STAT_PUNCH("stat.punch", "Punch: %s", 1, ChatFormatting.BLUE),
	STAT_PULL_TIME("stat.pull_time", "Pull Time: %s seconds", 1, ChatFormatting.BLUE),
	STAT_SPEED("stat.speed", "Arrow Speed: %s m/s", 1, ChatFormatting.BLUE),
	STAT_FOV("stat.fov", "Magnification: %s", 1, ChatFormatting.BLUE),
	STAT_EFFECT("stat.effects", "Apply Effects on Hit:", 0, ChatFormatting.GREEN),

	FEATURE_NO_FALL("feature.no_fall", "Arrow will not feel gravity, but will disappear after %s seconds.", 1, null),
	FEATURE_AIM_GLOW("feature.aim_flow", "Aimed targets within range of %s will appear glowing (only to you).", 1, null),
	FEATURE_WIND_BOW("feature.wind_bow", "Pulling bow will not slow down player, and player will get speed bonus for short time.", 0, null),
	FEATURE_ENDER_SHOOT("feature.ender_shoot", "When shooting aimed target, teleport arrow directly to the front of target. Arrow will not be released otherwise.", 0, null),
	FEATURE_FIRE("feature.fire", "When hit target, set target on fire for %s seconds.", 1, null),
	FEATURE_ENDER_ARROW("feature.ender_arrow", "When hitting entity, exchange location of player and target. Otherwise, teleport player to hit block.", 0, null),
	FEATURE_EXPLOSION_ALL("feature.explosion.all", "Create an explosion of radius %s on hit.", 1, null),
	FEATURE_EXPLOSION_HURT("feature.explosion.hurt", "Create an explosion of radius %s on hit. Will not destroy block.", 1, null),
	FEATURE_PIERCE_ARMOR("feature.pierce_armor", "Arrow Damage will pierce armor", 0, null),
	FEATURE_PIERCE_MAGIC("feature.pierce_magic", "Arrow Damage will bypass magic protection", 0, null);

	private final String key, def;
	private final int arg;
	private final ChatFormatting format;


	LangData(String key, String def, int arg, @Nullable ChatFormatting format) {
		this.key = L2Archery.MODID + "." + key;
		this.def = def;
		this.arg = arg;
		this.format = format;
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent getTranslate(String s) {
		return Component.translatable(L2Archery.MODID + "." + s);
	}

	public MutableComponent get(Object... args) {
		if (args.length != arg)
			throw new IllegalArgumentException("for " + name() + ": expect " + arg + " parameters, got " + args.length);
		MutableComponent ans = MutableComponent.create(new TranslatableContents(key, args));
		if (format != null) {
			return ans.withStyle(format);
		}
		return ans;
	}

	public MutableComponent getWithSign(double val) {
		String sign = val > 0 ? "attribute.modifier.plus." : "attribute.modifier.take.";
		var comp = Component.translatable(sign, ATTRIBUTE_MODIFIER_FORMAT.format(val));
		return get(comp);
	}

	public static void genLang(RegistrateLangProvider pvd) {
		for (LangData lang : LangData.values()) {
			pvd.add(lang.key, lang.def);
		}
		pvd.add("itemGroup." + L2Archery.MODID + ".archery", "L2 Archery");

	}

}
