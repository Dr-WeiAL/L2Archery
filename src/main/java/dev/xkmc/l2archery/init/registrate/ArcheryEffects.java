package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.builders.NoConfigBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2archery.content.effects.QuickPullEffect;
import dev.xkmc.l2archery.content.effects.RunBowEffect;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2complements.init.registrate.LCItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ArcheryEffects {

	public static final List<RegistryEntry<? extends Potion>> POTION_LIST = new ArrayList<>();
	public static final Map<String, String> NAME_CACHE = new HashMap<>();

	public static final RegistryEntry<RunBowEffect> RUN_BOW = genEffect("run_bow", "Sprinting Archer", () -> new RunBowEffect(MobEffectCategory.BENEFICIAL, 0xffffff));
	public static final RegistryEntry<QuickPullEffect> QUICK_PULL = genEffect("quick_pull", "Fast Pulling", () -> new QuickPullEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF));


	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, String lang, NonNullSupplier<T> sup) {
		NAME_CACHE.put(name, lang);
		return L2Archery.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Archery.REGISTRATE, L2Archery.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}

	private static final List<Runnable> TEMP = new ArrayList<>();

	public static void registerBrewingRecipe() {
		TEMP.forEach(Runnable::run);
	}

	public static void register() {
		regPotion2("run_bow", RUN_BOW::get, LCItems.CAPTURED_WIND::get, 1200, 3600);
		regPotion3("quick_pull", QUICK_PULL::get, LCItems.STORM_CORE::get, 600, 1200, 3600, 0, 1);
	}

	private static <T extends Potion> RegistryEntry<T> genPotion(String name, NonNullSupplier<T> sup) {
		RegistryEntry<T> ans = L2Archery.REGISTRATE.entry(name, (cb) -> new NoConfigBuilder<>(L2Archery.REGISTRATE, L2Archery.REGISTRATE, name, cb, ForgeRegistries.Keys.POTIONS, sup)).register();
		POTION_LIST.add(ans);
		return ans;
	}

	private static void regPotion2(String id, Supplier<MobEffect> sup, Supplier<Item> item, int dur, int durLong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong)));
		TEMP.add(() -> {
			PotionBrewing.addMix(Potions.AWKWARD, item.get(), potion.get());
			PotionBrewing.addMix(potion.get(), Items.REDSTONE, longPotion.get());
		});
	}

	private static void regPotion3(String id, Supplier<MobEffect> sup, Supplier<Item> item, int durStrong, int dur, int durLong, int amp, int ampStrong) {
		var potion = genPotion(id, () -> new Potion(new MobEffectInstance(sup.get(), dur, amp)));
		var longPotion = genPotion("long_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durLong, amp)));
		var strongPotion = genPotion("strong_" + id, () -> new Potion(new MobEffectInstance(sup.get(), durStrong, ampStrong)));
		TEMP.add(() -> {
			PotionBrewing.addMix(Potions.AWKWARD, item.get(), potion.get());
			PotionBrewing.addMix(potion.get(), Items.REDSTONE, longPotion.get());
			PotionBrewing.addMix(potion.get(), Items.GLOWSTONE_DUST, strongPotion.get());
		});
	}

}
