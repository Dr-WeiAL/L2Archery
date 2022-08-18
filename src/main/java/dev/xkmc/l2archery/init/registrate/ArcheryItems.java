package dev.xkmc.l2archery.init.registrate;

import com.google.common.collect.ImmutableList;
import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.arrow.*;
import dev.xkmc.l2archery.content.feature.bow.EnderShootFeature;
import dev.xkmc.l2archery.content.feature.bow.GlowTargetAimFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.content.item.ArrowConfig;
import dev.xkmc.l2archery.content.item.BowConfig;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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

	// -------- archery --------

	public static final ItemEntry<GenericBowItem> STARTER_BOW, IRON_BOW, MAGNIFY_BOW, GLOW_AIM_BOW, ENDER_AIM_BOW,
			EAGLE_BOW, WIND_BOW, EXPLOSION_BOW;

	public static final ItemEntry<GenericArrowItem> STARTER_ARROW, COPPER_ARROW, IRON_ARROW, OBSIDIAN_ARROW,
			NO_FALL_ARROW, ENDER_ARROW, TNT_1_ARROW, TNT_2_ARROW, TNT_3_ARROW, FIRE_1_ARROW, FIRE_2_ARROW,
			ICE_ARROW, DISPELL_ARROW, ACID_ARROW;

	public static final ItemEntry<UpgradeItem> UPGRADE;

	public static final RegistryEntry<Upgrade> GLOW, NO_FALL, FIRE, ICE, EXPLOSION, ENDER;

	static {
		{
			STARTER_BOW = genBow("starter_bow", 600);
			IRON_BOW = genBow("iron_bow", 1200);
			MAGNIFY_BOW = genBow("magnify_bow", 600, e -> e.add(new GlowTargetAimFeature(128)));
			GLOW_AIM_BOW = genBow("glow_aim_bow", 600, e -> e.add(new GlowTargetAimFeature(128)));
			ENDER_AIM_BOW = genBow("ender_aim_bow", 8, e -> e.add(new EnderShootFeature(128)));
			EAGLE_BOW = genBow("eagle_bow", 600, e -> e.add(new DamageArrowFeature(
					a -> DamageSource.arrow(a, a.getOwner()).bypassArmor(),
					a -> (float) (a.getBaseDamage() * a.getDeltaMovement().length()),
					LangData.FEATURE_PIERCE_ARMOR::get
			)));
			WIND_BOW = genBow("wind_bow", 600, e -> e
					.add(new NoFallArrowFeature(40))
					.add(new WindBowFeature()));
			EXPLOSION_BOW = genBow("explosion_bow", 16, e -> e
					.add(new ExplodeArrowFeature(3, true, false)));
		}
		{
			STARTER_ARROW = genArrow("starter_arrow", true);
			COPPER_ARROW = genArrow("copper_arrow", false);
			IRON_ARROW = genArrow("iron_arrow", false);
			OBSIDIAN_ARROW = genArrow("obsidian_arrow", false);
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
					a -> DamageSource.arrow(a, a.getOwner()).bypassMagic(),
					a -> (float) (a.getBaseDamage() * a.getDeltaMovement().length()),
					LangData.FEATURE_PIERCE_MAGIC::get
			))).register();
		}
		UPGRADE = REGISTRATE.item("upgrade", UpgradeItem::new).defaultModel().defaultLang().register();

		GLOW = genUpgrade("glow", () -> new GlowTargetAimFeature(128));
		NO_FALL = genUpgrade("anti_gravity", () -> new NoFallArrowFeature(40));
		FIRE = genUpgrade("soul_fire", () -> new PotionArrowFeature(
				List.of(new MobEffectInstance(ArcheryRegister.FLAME.get(), 100, 0))));
		ICE = genUpgrade("frozen", () -> new PotionArrowFeature(
				List.of(new MobEffectInstance(ArcheryRegister.ICE.get(), 600, 0))));
		EXPLOSION = genUpgrade("explosion", () -> new ExplodeArrowFeature(3, true, false));
		ENDER = genUpgrade("void", () -> new EnderShootFeature(128));

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

	private static final float[] BOW_PULL_VALS = {0, 0.65f, 0.9f};

	public static <T extends GenericArrowItem> void createArrowModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		pvd.generated(ctx, new ResourceLocation(L2Archery.MODID, "item/arrow/" + ctx.getName()));
	}

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

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

	public static RegistryEntry<Upgrade> genUpgrade(String str, Supplier<BowArrowFeature> sup) {
		return REGISTRATE.generic(ArcheryRegister.UPGRADE, str, () -> new Upgrade(sup)).defaultLang().register();
	}

}
