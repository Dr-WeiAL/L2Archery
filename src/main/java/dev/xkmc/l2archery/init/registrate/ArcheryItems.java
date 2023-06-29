package dev.xkmc.l2archery.init.registrate;

import com.google.common.collect.ImmutableList;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.arrow.*;
import dev.xkmc.l2archery.content.feature.bow.*;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.item.ArrowConfig;
import dev.xkmc.l2archery.content.item.BowConfig;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.data.ArcheryDamageState;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.data.TagGen;
import dev.xkmc.l2complements.init.registrate.LCEffects;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static dev.xkmc.l2archery.init.L2Archery.REGISTRATE;

@SuppressWarnings({"ConstantCondition", "unsafe"})
public class ArcheryItems {

	public static final List<GenericBowItem> BOW_LIKE = new ArrayList<>();

	private static final RegistryEntry<CreativeModeTab> TAB;

	static {
		TAB = REGISTRATE.buildL2CreativeTab("archery", "L2 Archery", b -> b
				.icon(ArcheryItems.STARTER_BOW::asStack));
	}

	public static final ItemEntry<GenericBowItem> STARTER_BOW, IRON_BOW, MASTER_BOW, MAGNIFY_BOW, GLOW_AIM_BOW, ENDER_AIM_BOW,
			EAGLE_BOW, WIND_BOW, EXPLOSION_BOW, FLAME_BOW, FROZE_BOW, STORM_BOW, BLACKSTONE_BOW, WINTER_BOW, TURTLE_BOW,
			EARTH_BOW, GAIA_BOW, VOID_BOW, SUN_BOW;

	public static final ItemEntry<GenericArrowItem> STARTER_ARROW, COPPER_ARROW, IRON_ARROW, GOLD_ARROW, OBSIDIAN_ARROW,
			NO_FALL_ARROW, ENDER_ARROW, TNT_1_ARROW, TNT_2_ARROW, TNT_3_ARROW, FIRE_1_ARROW, FIRE_2_ARROW, DESTROYER_ARROW,
			ICE_ARROW, DISPELL_ARROW, ACID_ARROW, BLACKSTONE_ARROW, DIAMOND_ARROW, QUARTZ_ARROW, WITHER_ARROW, STORM_ARROW,
			VOID_ARROW;//TODO eternal arrow, bleed arrow

	public static final ItemEntry<UpgradeItem> UPGRADE;

	public static final RegistryEntry<Upgrade> GLOW_UP, NO_FALL_UP, FIRE_UP, ICE_UP, EXPLOSION_UP, ENDER_UP,
			MAGNIFY_UP_1, MAGNIFY_UP_2, MAGNIFY_UP_3, DAMAGE_UP, PUNCH_UP, BLACKSTONE_UP, HARM_UP, HEAL_UP, SHINE_UP,
			LEVITATE_UP, SUPERDAMAGE_UP, RAILGUN_UP, FLUX_UP, FLOAT_UP, SLOW_UP, POISON_UP, WITHER_UP, WEAK_UP, CORROSION_UP,
			CURSE_UP, CLEANSE_UP;

	static {
		{
			STARTER_BOW = genBow("starter_bow", 0, 600).register();

			IRON_BOW = genBow("iron_bow", 1, 1200).register();
			MASTER_BOW = genBow("master_bow", 1, 1200).register();
			GLOW_AIM_BOW = genBow("glow_aim_bow", 1, 300, e -> e
					.add(new NoFallArrowFeature(40))
					.add(new GlowTargetAimFeature(128)))
					.lang("Sniper Bow").register();

			MAGNIFY_BOW = genBow("magnify_bow", 2, 300, e -> e
					.add(new NoFallArrowFeature(40))
					.add(new GlowTargetAimFeature(128)))
					.lang("Sniper Bow with Lens").register();
			ENDER_AIM_BOW = genBow("ender_aim_bow", 2, 600, e -> e.add(new EnderShootFeature(128)))
					.lang("Ender Bow").register();
			EAGLE_BOW = genBow("eagle_bow", 2, 600, e -> e.add(new DamageArrowFeature(
					(a, s) -> s.enable(DefaultDamageState.BYPASS_ARMOR),
					LangData.FEATURE_PIERCE_ARMOR::get
			))).register();
			EXPLOSION_BOW = genBow("explosion_bow", 2, 300, e -> e
					.add(new ExplodeArrowFeature(3, true, false))).register();
			FLAME_BOW = genBow("flame_bow", 2, 600, e -> e.add(new FireArrowFeature(100)))
					.lang("Blazing Bow").register();
			FROZE_BOW = genBow("froze_bow", 2, 600).lang("Freezing Bow").register();
			BLACKSTONE_BOW = genBow("slow_bow", 2, 600).lang("Bow of Seal").register();
			STORM_BOW = genBow("storm_bow", 2, 600, e -> e
					.add(new ExplodeArrowFeature(3, false, false)))
					.lang("Approaching Storm").register();

			TURTLE_BOW = genBow("turtle_bow", 2, 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2)
			)))).register();
			EARTH_BOW = genBow("earth_bow", 2, 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 3)
			)))).lang("Bow of the Earth").register();

			WIND_BOW = genBow("wind_bow", 3, 600, e -> e
					.add(new NoFallArrowFeature(40))
					.add(new WindBowFeature())
					.add(new PullEffectFeature(List.of(
							() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1)
					)))).lang("Bless of Favonius").register();

			WINTER_BOW = genBow("winter_bow", 3, 600, e -> e
					.add(new ExplodeArrowFeature(3, false, false)))
					.lang("Ever Freezing Night").register();
			GAIA_BOW = genBow("gaia_bow", 3, 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(LCEffects.STONE_CAGE.get(), 80, 0),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 4)
			)))).lang("Bless of Gaia").register();
			VOID_BOW = genBow("void_bow", 3, 300, e -> e.add(new EnderShootFeature(128))
					.add(new DamageArrowFeature(
							(a, s) -> s.enable(ArcheryDamageState.BYPASS_INVUL),
							LangData.FEATURE_PIERCE_INVUL::get
					))).lang("Sight of the Void (Creative Only)").register();
			SUN_BOW = genBow("sun_bow", 3, 600, e -> e.add(new FireArrowFeature(200))
					.add(new ExplodeArrowFeature(4, true, false)))
					.lang("Bless of Helios").register();
		}
		{
			STARTER_ARROW = genArrow("starter_arrow", true);
			COPPER_ARROW = genArrow("copper_arrow", false);
			IRON_ARROW = genArrow("iron_arrow", false);
			GOLD_ARROW = genArrow("gold_arrow", false);
			OBSIDIAN_ARROW = genArrow("obsidian_arrow", false);
			BLACKSTONE_ARROW = genArrow("blackstone_arrow", false);
			QUARTZ_ARROW = genArrow("quartz_arrow", false);
			DIAMOND_ARROW = genArrow("diamond_arrow", false);
			DESTROYER_ARROW = genArrow("destroyer_arrow", false);
			NO_FALL_ARROW = genArrow("no_fall_arrow", false, e -> e.add(new NoFallArrowFeature(40))).lang("Anti-Gravity Arrow").register();
			ENDER_ARROW = genArrow("ender_arrow", false, e -> e.add(new EnderArrowFeature())).register();
			TNT_1_ARROW = genArrow("tnt_arrow_lv1", false, e -> e.add(new ExplodeArrowFeature(2, true, false))).lang("Explosion Arrow").register();
			TNT_2_ARROW = genArrow("tnt_arrow_lv2", false, e -> e.add(new ExplodeArrowFeature(4, true, false))).lang("TNT Arrow").register();
			TNT_3_ARROW = genArrow("tnt_arrow_lv3", false, e -> e.add(new ExplodeArrowFeature(6, true, false))).lang("End Crystal Arrow").register();
			FIRE_1_ARROW = genArrow("fire_arrow_lv1", false, e -> e.add(new FireArrowFeature(100))).lang("Soul Fire Arrow").register();
			FIRE_2_ARROW = genArrow("fire_arrow_lv2", false, e -> e.add(new FireArrowFeature(200))).lang("Cursed Fire Arrow").register();
			ICE_ARROW = genArrow("frozen_arrow", false);
			ACID_ARROW = genArrow("acid_arrow", false);
			DISPELL_ARROW = genArrow("dispell_arrow", false, e -> e.add(new DamageArrowFeature(
					(a, s) -> s.enable(DefaultDamageState.BYPASS_MAGIC),
					LangData.FEATURE_PIERCE_MAGIC::get
			))).register();
			WITHER_ARROW = genArrow("wither_arrow", false, e -> e.add(new DamageArrowFeature(
					(a, s) -> s.enable(DefaultDamageState.BYPASS_ARMOR),
					LangData.FEATURE_PIERCE_ARMOR::get
			))).register();
			STORM_ARROW = genArrow("storm_arrow", false, e -> e.add(new ExplodeArrowFeature(3, false, false))).register();
			VOID_ARROW = genArrow("void_arrow", false, e -> e.add(new DamageArrowFeature(
					(a, s) -> s.enable(ArcheryDamageState.BYPASS_INVUL),
					LangData.FEATURE_PIERCE_INVUL::get
			)).add(new VoidArrowFeature())).lang("Void Arrow (Creative Only)").register();
		}
		{
			UPGRADE = REGISTRATE.item("upgrade", UpgradeItem::new).defaultModel().defaultLang()
					.tab(TAB.getKey(), e -> ArcheryItems.UPGRADE.get().fillItemCategory(e)).register();

			MAGNIFY_UP_1 = genUpgrade("magnify_x2", () -> new StatFeature(2, 10, 1, 0, 1));
			MAGNIFY_UP_2 = genUpgrade("magnify_x4", () -> new StatFeature(4, 30, 1, 0, 1));
			MAGNIFY_UP_3 = genUpgrade("magnify_x8", () -> new StatFeature(8, 50, 1, 0, 1));
			DAMAGE_UP = genUpgrade("damage", () -> new StatFeature(1, 0, 2, 0, 1));
			SUPERDAMAGE_UP = genUpgrade("super_damage", () -> new StatFeature(1, 0, 3, 0, 1));
			PUNCH_UP = genUpgrade("punch", () -> new StatFeature(1, 0, 1, 3, 1));

			GLOW_UP = genUpgrade("glow", () -> new GlowTargetAimFeature(128));
			NO_FALL_UP = genUpgrade("anti_gravity", () -> new NoFallArrowFeature(40));
			EXPLOSION_UP = genUpgrade("explosion", () -> new ExplodeArrowFeature(3, true, false));
			ENDER_UP = genUpgrade("void", () -> new EnderShootFeature(128));
			RAILGUN_UP = genUpgrade("railgun", () -> new StatFeature(1, 1, 1, 0, 100));
			FLUX_UP = genUpgrade("flux_up", () -> FluxFeature.DEFAULT);

			FIRE_UP = genPotionUpgrade("soul_fire");
			ICE_UP = genPotionUpgrade("frozen");
			BLACKSTONE_UP = genPotionUpgrade("blackstone");
			HARM_UP = genPotionUpgrade("harm");
			HEAL_UP = genPotionUpgrade("heal");
			SHINE_UP = genPotionUpgrade("glowing");
			LEVITATE_UP = genPotionUpgrade("levitate");
			FLOAT_UP = genPotionUpgrade("levitation");
			SLOW_UP = genPotionUpgrade("slowness");
			POISON_UP = genPotionUpgrade("poison");
			WITHER_UP = genPotionUpgrade("wither");
			WEAK_UP = genPotionUpgrade("weak");
			CORROSION_UP = genPotionUpgrade("corrosion");
			CURSE_UP = genPotionUpgrade("curse");
			CLEANSE_UP = genPotionUpgrade("cleanse");
		}
	}

	public static void register() {
	}

	public static ItemBuilder<GenericBowItem, L2Registrate> genBow(String id, int rank, int durability) {
		return genBow(id, rank, durability, e -> {
		});
	}

	public static ItemBuilder<GenericBowItem, L2Registrate> genBow(String id, int rank, int durability, Consumer<ImmutableList.Builder<BowArrowFeature>> consumer) {
		ImmutableList.Builder<BowArrowFeature> f = ImmutableList.builder();
		consumer.accept(f);
		return REGISTRATE.item(id, p -> new GenericBowItem(p.stacksTo(1).durability(durability), e -> new BowConfig(e, rank, f.build())))
				.model(ArcheryItems::createBowModel).defaultLang().tag(TagGen.FORGE_BOWS, TagGen.PROF_BOWS);
	}

	private static final float[] BOW_PULL_VALS = {0, 0.65f, 0.9f};

	public static <T extends GenericBowItem> void createBowModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		ItemModelBuilder builder = pvd.withExistingParent(ctx.getName(), "minecraft:bow");
		builder.texture("layer0", "item/bow/" + ctx.getName() + "/bow");
		for (int i = 0; i < 3; i++) {
			String name = ctx.getName() + "/bow_pulling_" + i;
			ItemModelBuilder ret = pvd.getBuilder("item/bow/" + name).parent(new ModelFile.UncheckedModelFile("minecraft:item/bow_pulling_" + i));
			ret.texture("layer0", "item/bow/" + name);
			ItemModelBuilder.OverrideBuilder override = builder.override();
			override.predicate(new ResourceLocation("pulling"), 1);
			if (BOW_PULL_VALS[i] > 0)
				override.predicate(new ResourceLocation("pull"), BOW_PULL_VALS[i]);
			override.model(new ModelFile.UncheckedModelFile(L2Archery.MODID + ":item/bow/" + name));
		}
	}

	public static ItemEntry<GenericArrowItem> genArrow(String id, boolean is_inf) {
		return genArrow(id, is_inf, e -> {
		}).register();
	}

	public static ItemBuilder<GenericArrowItem, L2Registrate> genArrow(String id, boolean is_inf, Consumer<ImmutableList.Builder<BowArrowFeature>> consumer) {
		ImmutableList.Builder<BowArrowFeature> f = ImmutableList.builder();
		consumer.accept(f);
		return REGISTRATE.item(id, p -> new GenericArrowItem(p, e -> new ArrowConfig(e, is_inf, f.build())))
				.model(ArcheryItems::createArrowModel).tag(TagGen.FORGE_ARROWS, TagGen.PROF_ARROWS)
				.defaultLang();
	}

	public static <T extends GenericArrowItem> void createArrowModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		pvd.generated(ctx, new ResourceLocation(L2Archery.MODID, "item/arrow/" + ctx.getName()));
	}

	public static RegistryEntry<Upgrade> genUpgrade(String str, Supplier<BowArrowFeature> sup) {
		return REGISTRATE.generic(ArcheryRegister.UPGRADE, str, () -> new Upgrade(sup)).defaultLang().register();
	}

	public static RegistryEntry<Upgrade> genPotionUpgrade(String str) {
		return REGISTRATE.generic(ArcheryRegister.UPGRADE, str, () -> new Upgrade(PotionArrowFeature::fromUpgradeConfig)).defaultLang().register();
	}

}
