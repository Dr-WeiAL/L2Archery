package dev.xkmc.l2archery.init.registrate;

import dev.xkmc.l2archery.foundation.effect.*;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2foundation.init.L2Foundation;
import dev.xkmc.l2library.repack.registrate.builders.NoConfigBuilder;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ArcheryEffects {

	public static final RegistryEntry<FlameEffect> FLAME = genEffect("flame", () -> new FlameEffect(MobEffectCategory.HARMFUL, 0xFF0000));
	public static final RegistryEntry<IceEffect> ICE = genEffect("frozen", () -> new IceEffect(MobEffectCategory.HARMFUL, 0x7f7fff));
	public static final RegistryEntry<ArmorReduceEffect> ARMOR_REDUCE = genEffect("armor_reduce", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
	public static final RegistryEntry<MobEffect> RUN_BOW = genEffect("run_bow", () -> new RunBowEffect(MobEffectCategory.BENEFICIAL, 0xffffff));
	public static final RegistryEntry<QuickPullEffect> QUICK_PULL = genEffect("quick_pull", () -> new QuickPullEffect(MobEffectCategory.BENEFICIAL, 0xFFFFFF));
	public static final RegistryEntry<StoneCageEffect> STONE_CAGE = genEffect("stone_cage", () -> new StoneCageEffect(MobEffectCategory.HARMFUL, 0x000000));

	public static final List<RegistryEntry<? extends Potion>> POTION_LIST = new ArrayList<>();

	public static final RegistryEntry<Potion> POTION_FLAME = genPotion("flame", () -> new Potion(new MobEffectInstance(FLAME.get(), 600)));
	public static final RegistryEntry<Potion> POTION_FLAME_LONG = genPotion("long_flame", () -> new Potion(new MobEffectInstance(FLAME.get(), 1200)));
	public static final RegistryEntry<Potion> POTION_FLAME_STRONG = genPotion("strong_flame", () -> new Potion(new MobEffectInstance(FLAME.get(), 400, 1)));
	public static final RegistryEntry<Potion> POTION_ICE = genPotion("frozen", () -> new Potion(new MobEffectInstance(ICE.get(), 3600)));
	public static final RegistryEntry<Potion> POTION_ICE_LONG = genPotion("long_frozen", () -> new Potion(new MobEffectInstance(ICE.get(), 9600)));
	public static final RegistryEntry<Potion> POTION_STON = genPotion("stone_cage", () -> new Potion(new MobEffectInstance(STONE_CAGE.get(), 1200)));
	public static final RegistryEntry<Potion> POTION_STONE_LONG = genPotion("long_stone_cage", () -> new Potion(new MobEffectInstance(STONE_CAGE.get(), 3600)));
	public static final RegistryEntry<Potion> POTION_RUN_BOW = genPotion("run_bow", () -> new Potion(new MobEffectInstance(RUN_BOW.get(), 1200)));
	public static final RegistryEntry<Potion> POTION_RUN_BOW_LONG = genPotion("long_run_bow", () -> new Potion(new MobEffectInstance(RUN_BOW.get(), 3600)));
	public static final RegistryEntry<Potion> POTION_RUN_BOW_STRONG = genPotion("strong_run_bow", () -> new Potion(new MobEffectInstance(RUN_BOW.get(), 600, 1)));
	public static final RegistryEntry<Potion> POTION_QUICK_PULL = genPotion("quick_pull", () -> new Potion(new MobEffectInstance(QUICK_PULL.get(), 1200)));
	public static final RegistryEntry<Potion> POTION_QUICK_PULL_LONG = genPotion("long_quick_pull", () -> new Potion(new MobEffectInstance(QUICK_PULL.get(), 3600)));
	public static final RegistryEntry<Potion> POTION_QUICK_PULL_STRONG = genPotion("strong_quick_pull", () -> new Potion(new MobEffectInstance(QUICK_PULL.get(), 600, 1)));
	public static final RegistryEntry<Potion> POTION_LEVITATION = genPotion("levitation", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 200)));
	public static final RegistryEntry<Potion> POTION_LEVITATION_LONG = genPotion("long_levitation", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 600)));
	public static final RegistryEntry<Potion> POTION_RESISTANCE = genPotion("resistance", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1)));
	public static final RegistryEntry<Potion> POTION_RESISTANCE_LONG = genPotion("long_resistance", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1)));
	public static final RegistryEntry<Potion> POTION_RESISTANCE_STRONG = genPotion("strong_resistance", () -> new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 2)));

	public static <T extends MobEffect> RegistryEntry<T> genEffect(String name, NonNullSupplier<T> sup) {
		return L2Archery.REGISTRATE.entry(name, cb -> new NoConfigBuilder<>(L2Archery.REGISTRATE, L2Archery.REGISTRATE, name, cb, ForgeRegistries.Keys.MOB_EFFECTS, sup))
				.lang(MobEffect::getDescriptionId).register();
	}


	public static <T extends Potion> RegistryEntry<T> genPotion(String name, NonNullSupplier<T> sup) {
		RegistryEntry<T> ans = L2Foundation.REGISTRATE.entry(name, (cb) -> new NoConfigBuilder<>(L2Archery.REGISTRATE, L2Archery.REGISTRATE, name, cb, ForgeRegistries.Keys.POTIONS, sup)).register();
		POTION_LIST.add(ans);
		return ans;
	}

	public static void register() {
	}

}
