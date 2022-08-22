package dev.xkmc.l2archery.init.registrate;

import com.google.common.collect.ImmutableList;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.arrow.*;
import dev.xkmc.l2archery.content.feature.bow.EnderShootFeature;
import dev.xkmc.l2archery.content.feature.bow.GlowTargetAimFeature;
import dev.xkmc.l2archery.content.feature.bow.PullEffectFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.content.item.ArrowConfig;
import dev.xkmc.l2archery.content.item.BowConfig;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
import dev.xkmc.l2archery.foundation.create.RefinedRadianceItem;
import dev.xkmc.l2archery.foundation.create.ShadowSteelItem;
import dev.xkmc.l2archery.foundation.item.WindBottle;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.builders.ItemBuilder;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static dev.xkmc.l2archery.init.L2Archery.REGISTRATE;

@SuppressWarnings({"rawtypes", "unsafe"})
@MethodsReturnNonnullByDefault
public class ArcheryItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<ItemEntry> icon;

		public Tab(String id, Supplier<ItemEntry> icon) {
			super(L2Archery.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().asStack();
		}
	}

	public static final Tab TAB_PROF = new Tab("archery", () -> ArcheryItems.STARTER_BOW);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_PROF);
	}

	public static final ItemEntry<GenericBowItem> STARTER_BOW, IRON_BOW, MASTER_BOW, MAGNIFY_BOW, GLOW_AIM_BOW, ENDER_AIM_BOW,
			EAGLE_BOW, WIND_BOW, EXPLOSION_BOW, FLAME_BOW, FROZE_BOW, STORM_BOW, BLACKSTONE_BOW, WINTER_BOW, TURTLE_BOW,
			EARTH_BOW, GAIA_BOW, VOID_BOW, SUN_BOW;

	public static final ItemEntry<GenericArrowItem> STARTER_ARROW, COPPER_ARROW, IRON_ARROW, GOLD_ARROW, OBSIDIAN_ARROW,
			NO_FALL_ARROW, ENDER_ARROW, TNT_1_ARROW, TNT_2_ARROW, TNT_3_ARROW, FIRE_1_ARROW, FIRE_2_ARROW, DESTROYER_ARROW,
			ICE_ARROW, DISPELL_ARROW, ACID_ARROW, BLACKSTONE_ARROW, DIAMOND_ARROW, QUARTZ_ARROW, WITHER_ARROW, STORM_ARROW;

	public static final ItemEntry<UpgradeItem> UPGRADE;

	public static final RegistryEntry<Upgrade> GLOW_UP, NO_FALL_UP, FIRE_UP, ICE_UP, EXPLOSION_UP, ENDER_UP,
			MAGNIFY_UP_1, MAGNIFY_UP_2, MAGNIFY_UP_3, DAMAGE_UP, PUNCH_UP, BLACKSTONE_UP, HARM_UP, HEAL_UP, SHINE_UP,
			LEVITATE_UP, SUPERDAMAGE_UP;

	public static final ItemEntry<WindBottle> WIND_BOTTLE;
	public static final ItemEntry<ShadowSteelItem> VOID_EYE;
	public static final ItemEntry<RefinedRadianceItem> SUN_MEMBRANE, SOUL_FLAME;
	public static final ItemEntry<Item> CAPTURED_WIND, CAPTURED_BULLET, EXPLOSION_SHARD, HARD_ICE,
			STORM_CORE, BLACKSTONE_CORE, RESONANT_FEATHER, SPACE_SHARD, FORCE_FIELD;

	static {
		{
			STARTER_BOW = genBow("starter_bow", 600);
			IRON_BOW = genBow("iron_bow", 1200);
			MASTER_BOW = genBow("master_bow", 1200);
			MAGNIFY_BOW = genBow("magnify_bow", 600, e -> e.add(new GlowTargetAimFeature(128)));
			GLOW_AIM_BOW = genBow("glow_aim_bow", 600, e -> e.add(new GlowTargetAimFeature(128)));
			ENDER_AIM_BOW = genBow("ender_aim_bow", 8, e -> e.add(new EnderShootFeature(128)));
			EAGLE_BOW = genBow("eagle_bow", 600, e -> e.add(new DamageArrowFeature(
					(a, s) -> s.bypassArmor(),
					LangData.FEATURE_PIERCE_ARMOR::get
			)));
			WIND_BOW = genBow("wind_bow", 600, e -> e
					.add(new NoFallArrowFeature(40))
					.add(new WindBowFeature())
					.add(new PullEffectFeature(List.of(
							() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1)
					))));
			EXPLOSION_BOW = genBow("explosion_bow", 32, e -> e
					.add(new ExplodeArrowFeature(3, true, false)));
			FLAME_BOW = genBow("flame_bow", 600, e -> e.add(new FireArrowFeature(100)));
			FROZE_BOW = genBow("froze_bow", 600);
			BLACKSTONE_BOW = genBow("slow_bow", 600);
			STORM_BOW = genBow("storm_bow", 600, e -> e
					.add(new ExplodeArrowFeature(3, false, false)));
			WINTER_BOW = genBow("winter_bow", 600, e -> e
					.add(new ExplodeArrowFeature(3, false, false)));
			TURTLE_BOW = genBow("turtle_bow", 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 2)
			))));
			EARTH_BOW = genBow("earth_bow", 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 5),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 3)
			))));
			GAIA_BOW = genBow("gaia_bow", 600, e -> e.add(new PullEffectFeature(List.of(
					() -> new MobEffectInstance(ArcheryRegister.STONE_CAGE.get(), 80, 0),
					() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 80, 4)
			))));
			VOID_BOW = genBow("void_bow", 32, e -> e.add(new EnderShootFeature(128))
					.add(new DamageArrowFeature(
							(a, s) -> s.bypassArmor().bypassMagic().bypassInvul(),
							LangData.FEATURE_PIERCE_INVUL::get
					)));
			SUN_BOW = genBow("sun_bow", 600, e -> e.add(new FireArrowFeature(200))
					.add(new ExplodeArrowFeature(4, true, false)));
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
					(a, s) -> s.bypassMagic(),
					LangData.FEATURE_PIERCE_MAGIC::get
			))).register();
			WITHER_ARROW = genArrow("wither_arrow", false);
			STORM_ARROW = genArrow("storm_arrow", false, e -> e.add(new ExplodeArrowFeature(3, false, false))).register();
		}
		{
			UPGRADE = REGISTRATE.item("upgrade", UpgradeItem::new).defaultModel().defaultLang().register();

			MAGNIFY_UP_1 = genUpgrade("magnify_x2", () -> new StatFeature(2, 10, 1, 0));
			MAGNIFY_UP_2 = genUpgrade("magnify_x4", () -> new StatFeature(4, 30, 1, 0));
			MAGNIFY_UP_3 = genUpgrade("magnify_x8", () -> new StatFeature(8, 50, 1, 0));
			DAMAGE_UP = genUpgrade("damage", () -> new StatFeature(1, 1, 2, 0));
			SUPERDAMAGE_UP = genUpgrade("super_damage", () -> new StatFeature(1, 1, 3, 0));
			PUNCH_UP = genUpgrade("punch", () -> new StatFeature(1, 1, 1, 3));

			GLOW_UP = genUpgrade("glow", () -> new GlowTargetAimFeature(128));
			NO_FALL_UP = genUpgrade("anti_gravity", () -> new NoFallArrowFeature(40));
			FIRE_UP = genUpgrade("soul_fire", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(ArcheryRegister.FLAME.get(), 100, 0))));
			ICE_UP = genUpgrade("frozen", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(ArcheryRegister.ICE.get(), 600, 0))));
			EXPLOSION_UP = genUpgrade("explosion", () -> new ExplodeArrowFeature(3, true, false));
			ENDER_UP = genUpgrade("void", () -> new EnderShootFeature(128));
			BLACKSTONE_UP = genUpgrade("blackstone", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(ArcheryRegister.STONE_CAGE.get(), 100, 0))));
			HARM_UP = genUpgrade("harm", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(MobEffects.HARM, 1, 1))));
			HEAL_UP = genUpgrade("heal", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(MobEffects.HEAL, 1, 1))));
			SHINE_UP = genUpgrade("glowing", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(MobEffects.GLOWING, 600, 0))));
			LEVITATE_UP = genUpgrade("levitate", () -> new PotionArrowFeature(
					List.of(new MobEffectInstance(MobEffects.LEVITATION, 300, 0))));
		}
		{
			WIND_BOTTLE = REGISTRATE.item("wind_capture_bottle", WindBottle::new).register();// tested
			VOID_EYE = REGISTRATE.item("void_eye", ShadowSteelItem::new).register();//TODO kill aggroed enderman 16 blocks in void
			CAPTURED_WIND = simpleItem("captured_wind");// player reach 200m/s
			CAPTURED_BULLET = simpleItem("captured_shulker_bullet");// TODO capture bullet
			SUN_MEMBRANE = REGISTRATE.item("sun_membrane", RefinedRadianceItem::new).register();//TODO kill phantom 200 blocks above maximum build height with arrow
			EXPLOSION_SHARD = simpleItem("explosion_shard");//TODO endure > 100 explosion damage
			HARD_ICE = simpleItem("hard_ice");// kill drowned with powder snow damage
			SOUL_FLAME = REGISTRATE.item("soul_flame", RefinedRadianceItem::new).register();//TODO kill fire immune mobs with soul flame
			STORM_CORE = simpleItem("storm_core");//TODO kill phantom with explosion
			BLACKSTONE_CORE = simpleItem("blackstone_core");//TODO kill guardian with stone cage effect
			RESONANT_FEATHER = simpleItem("resonant_feather");//TODO let chicken survive sonic boom
			SPACE_SHARD = simpleItem("space_shard");//TODO deal 1000 arrow damage
			FORCE_FIELD = simpleItem("force_field");//TODO kill wither with arrow

		}
	}

	public static void register() {
	}

	public static ItemEntry<GenericBowItem> genBow(String id, int durability) {
		return genBow(id, durability, e -> {
		});
	}

	public static ItemEntry<GenericBowItem> genBow(String id, int durability, Consumer<ImmutableList.Builder<BowArrowFeature>> consumer) {
		ImmutableList.Builder<BowArrowFeature> f = ImmutableList.builder();
		consumer.accept(f);
		return REGISTRATE.item(id, p -> new GenericBowItem(p.stacksTo(1).durability(durability),
						new BowConfig(new ResourceLocation(L2Archery.MODID, id), f.build())))
				.model(ArcheryItems::createBowModel).defaultLang().register();
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
		return REGISTRATE.item(id, p -> new GenericArrowItem(p, new ArrowConfig(
						new ResourceLocation(L2Archery.MODID, id), is_inf, f.build())))
				.model(ArcheryItems::createArrowModel);
	}


	public static <T extends GenericArrowItem> void createArrowModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		pvd.generated(ctx, new ResourceLocation(L2Archery.MODID, "item/arrow/" + ctx.getName()));
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

	public static RegistryEntry<Upgrade> genUpgrade(String str, Supplier<BowArrowFeature> sup) {
		return REGISTRATE.generic(ArcheryRegister.UPGRADE, str, () -> new Upgrade(sup)).defaultLang().register();
	}

}
