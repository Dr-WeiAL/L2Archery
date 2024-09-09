package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2archery.content.effects.QuickPullEffect;
import dev.xkmc.l2archery.content.effects.RunBowEffect;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArcheryEffects {

	public static final SimpleEntry<MobEffect> RUN_BOW = genEffect("run_bow", "Sprinting Archer",
			() -> new RunBowEffect(MobEffectCategory.BENEFICIAL, 0xffffff),
			"Allow player to sprint while pulling bow");

	public static final SimpleEntry<MobEffect> QUICK_PULL = genEffect("quick_pull", "Fast Pulling",
			() -> new QuickPullEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF),
			"Increase pulling speed");

	public static SimpleEntry<MobEffect> genEffect(String name, String lang, NonNullSupplier<MobEffect> sup, String desc) {
		return new SimpleEntry<>(L2Archery.REGISTRATE.effect(name, sup, desc)
				.lang(MobEffect::getDescriptionId).register());
	}

	private static final List<Consumer<PotionBrewing.Builder>> TEMP = new ArrayList<>();

	public static void registerBrewingRecipe(RegisterBrewingRecipesEvent event) {
		var builder = event.getBuilder();
		TEMP.forEach(e -> e.accept(builder));
	}

	public static void register() {
		regPotion2("run_bow", RUN_BOW, LCItems.CAPTURED_WIND::get, 1200, 3600);
		regPotion3("quick_pull", QUICK_PULL, LCItems.STORM_CORE::get, 600, 1200, 3600, 0, 1);
	}

	private static SimpleEntry<Potion> genPotion(String name, NonNullSupplier<Potion> sup) {
		return L2Archery.REGISTRATE.potion(name, sup);
	}

	private static void regPotion2(String id, Holder<MobEffect> sup, Supplier<Item> item, int dur, int durLong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup, dur)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup, durLong)));
		TEMP.add(e -> {
			e.addMix(Potions.AWKWARD, item.get(), potion);
			e.addMix(potion, Items.REDSTONE, longPotion);
		});
	}

	private static void regPotion3(String id, Holder<MobEffect> sup, Supplier<Item> item, int durStrong, int dur, int durLong, int amp, int ampStrong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup, dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup, durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup, durStrong, ampStrong)));
		TEMP.add(e -> {
			e.addMix(Potions.AWKWARD, item.get(), potion);
			e.addMix(potion, Items.REDSTONE, longPotion);
			e.addMix(potion, Items.GLOWSTONE_DUST, strongPotion);
		});
	}

}
