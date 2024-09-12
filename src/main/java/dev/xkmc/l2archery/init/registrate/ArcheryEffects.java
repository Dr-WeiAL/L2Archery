package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2archery.content.effects.QuickPullEffect;
import dev.xkmc.l2archery.content.effects.RunBowEffect;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.reg.registrate.PotionBuilder;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ArcheryEffects {

	public static final SimpleEntry<MobEffect> RUN_BOW = genEffect("sprinting_archer", "Sprinting Archer",
			() -> new RunBowEffect(MobEffectCategory.BENEFICIAL, 0xffffff),
			"Allow player to sprint while pulling L2Archery bow");

	public static final SimpleEntry<MobEffect> QUICK_PULL = genEffect("fast_pulling", "Fast Pulling",
			() -> new QuickPullEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF),
			"Increase pulling speed for L2Archery bows");

	static {
		var builder = new PotionBuilder(L2Archery.REGISTRATE, LCEffects.BUILDER);
		builder.regPotion2("sprinting_archer", RUN_BOW, LCItems.CAPTURED_WIND, 1200, 3600);
		builder.regPotion3("fast_pulling", QUICK_PULL, LCItems.STORM_CORE, 600, 1200, 3600, 0, 1);
	}

	public static SimpleEntry<MobEffect> genEffect(String name, String lang, NonNullSupplier<MobEffect> sup, String desc) {
		return new SimpleEntry<>(L2Archery.REGISTRATE.effect(name, sup, desc)
				.lang(MobEffect::getDescriptionId, lang).register());
	}

	public static void register() {
	}

}
