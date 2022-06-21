package dev.xkmc.l2archery.init.registrate;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.builders.ItemBuilder;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.arrow.*;
import dev.xkmc.l2archery.content.feature.bow.DefaultShootFeature;
import dev.xkmc.l2archery.content.feature.bow.EnderShootFeature;
import dev.xkmc.l2archery.content.feature.bow.GlowTargetAimFeature;
import dev.xkmc.l2archery.content.feature.bow.WindBowFeature;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.L2Archery;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.util.NonNullLazy;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.xkmc.l2archery.init.L2Archery.REGISTRATE;

@SuppressWarnings({"rawtypes", "unchecked", "unsafe"})
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

	public static final Tab TAB_PROF = new Tab("profession", () -> ArcheryItems.STARTER_BOW);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_PROF);
	}

	// -------- archery --------
	public static final ItemEntry<GenericBowItem> STARTER_BOW, IRON_BOW, MAGNIFY_BOW, GLOW_AIM_BOW, ENDER_AIM_BOW,
			EAGLE_BOW, WIND_BOW;

	public static final ItemEntry<GenericArrowItem> STARTER_ARROW, COPPER_ARROW, IRON_ARROW, OBSIDIAN_ARROW,
			NO_FALL_ARROW, ENDER_ARROW, TNT_1_ARROW, TNT_2_ARROW, TNT_3_ARROW, FIRE_1_ARROW, FIRE_2_ARROW,
			ICE_ARROW, DISPELL_ARROW, ACID_ARROW;

	static {
		STARTER_BOW = genBow("starter_bow", 600, 0, 0, FeatureList::end);
		IRON_BOW = genBow("iron_bow", 1200, 1, 0, 40, 3.9f, FeatureList::end);
		MAGNIFY_BOW = genBow("magnify_bow", 600, 0, 0, 20, 3.0f, 60, 0.9f, e -> e.add(new GlowTargetAimFeature(128)));
		GLOW_AIM_BOW = genBow("glow_aim_bow", 600, 0, 0, e -> e.add(new GlowTargetAimFeature(128)));
		ENDER_AIM_BOW = genBow("ender_aim_bow", 8, -1, 0, e -> e.add(new EnderShootFeature(128)));
		EAGLE_BOW = genBow("eagle_bow", 600, 6, 2, 40, 3f, e -> e.add(new DamageArrowFeature(
				a -> DamageSource.arrow(a, a.getOwner()).bypassArmor(),
				a -> (float) (a.getBaseDamage() * a.getDeltaMovement().length())
		)));
		WIND_BOW = genBow("wind_bow", 600, 0, 1, 10, 3.9f, e -> e
				.add(new NoFallArrowFeature(40))
				.add(new WindBowFeature()));

		STARTER_ARROW = genArrow("starter_arrow", 0, 0, true, FeatureList::end);
		COPPER_ARROW = genArrow("copper_arrow", 1, 0, false, FeatureList::end);
		IRON_ARROW = genArrow("iron_arrow", 1, 1, false, FeatureList::end);
		OBSIDIAN_ARROW = genArrow("obsidian_arrow", 1.5f, 0, false, FeatureList::end);
		NO_FALL_ARROW = genArrow("no_fall_arrow", 0, 0, false, e -> e.add(new NoFallArrowFeature(40)));
		ENDER_ARROW = genArrow("ender_arrow", -1, 0, false, e -> e.add(new EnderArrowFeature()));
		TNT_1_ARROW = genArrow("tnt_arrow_lv1", 0, 0, false, e -> e.add(new ExplodeArrowFeature(2)));
		TNT_2_ARROW = genArrow("tnt_arrow_lv2", 0, 0, false, e -> e.add(new ExplodeArrowFeature(4)));
		TNT_3_ARROW = genArrow("tnt_arrow_lv3", 0, 0, false, e -> e.add(new ExplodeArrowFeature(6)));
		FIRE_1_ARROW = genArrow("fire_arrow_lv1", 0, 0, false, e -> e
				.add(new FireArrowFeature(100)).add(new PotionArrowFeature(
						new MobEffectInstance(ArcheryRegister.FLAME.get(), 100, 0))));
		FIRE_2_ARROW = genArrow("fire_arrow_lv2", 0, 0, false, e -> e
				.add(new FireArrowFeature(200)).add(new PotionArrowFeature(
						new MobEffectInstance(ArcheryRegister.FLAME.get(), 100, 1))));
		ICE_ARROW = genArrow("frozen_arrow", 0, 0, false, e -> e.add(new PotionArrowFeature(
				new MobEffectInstance(ArcheryRegister.ICE.get(), 600)
				//, new MobEffectInstance(LightlandVanillaMagic.WATER_TRAP.get(), 200) TODO
		)));
		ACID_ARROW = genArrow("acid_arrow", 2, 0, false, e -> e.add(new PotionArrowFeature(
				//new MobEffectInstance(LightlandVanillaMagic.ARMOR_BREAKER.get(), 600) TODO
		)));
		DISPELL_ARROW = genArrow("dispell_arrow", 0, 0, false, e -> e.add(new DamageArrowFeature(
				a -> DamageSource.arrow(a, a.getOwner()).bypassMagic(),
				a -> (float) (a.getBaseDamage() * a.getDeltaMovement().length())
		)));
	}


	public static void register() {
	}

	public static ItemEntry<GenericBowItem> genBow(String id, int durability, float damage, int punch, Consumer<FeatureList> consumer) {
		return genBow(id, durability, damage, punch, 20, 3.0f, 20, 0.15f, consumer);
	}

	public static ItemEntry<GenericBowItem> genBow(String id, int durability, float damage, int punch, int pull_time, float speed, Consumer<FeatureList> consumer) {
		return genBow(id, durability, damage, punch, pull_time, speed, pull_time, 0.15f, consumer);
	}

	public static ItemEntry<GenericBowItem> genBow(String id, int durability, float damage, int punch, int pull_time, float speed, int fov_time, float fov, Consumer<FeatureList> consumer) {
		FeatureList features = new FeatureList().add(DefaultShootFeature.INSTANCE);
		consumer.accept(features);
		return REGISTRATE.item(id, p -> new GenericBowItem(p.stacksTo(1).durability(durability),
						new GenericBowItem.BowConfig(damage, punch, pull_time, speed, fov_time, fov, features)))
				.model(ArcheryItems::createBowModel).defaultLang().register();
	}

	public static ItemEntry<GenericArrowItem> genArrow(String id, float damage, int punch, boolean is_inf, Consumer<FeatureList> consumer) {
		NonNullLazy<FeatureList> f = NonNullLazy.of(() -> {
			FeatureList features = new FeatureList();
			consumer.accept(features);
			return features;
		});
		return REGISTRATE.item(id, p -> new GenericArrowItem(p, new GenericArrowItem.ArrowConfig(damage, punch, is_inf, f)))
				.defaultModel().defaultLang().register();
	}

	@SuppressWarnings({"rawtypes", "unsafe", "unchecked"})
	public static <T extends ArmorItem> ItemEntry<T>[] genArmor(String id, BiFunction<EquipmentSlot, Item.Properties, T> sup, Function<ItemBuilder<T, L2Registrate>, ItemBuilder<T, L2Registrate>> func) {
		ItemEntry[] ans = new ItemEntry[4];
		ans[0] = func.apply(REGISTRATE.item(id + "_helmet", p -> sup.apply(EquipmentSlot.HEAD, p))).defaultLang().register();
		ans[1] = func.apply(REGISTRATE.item(id + "_chestplate", p -> sup.apply(EquipmentSlot.CHEST, p))).defaultLang().register();
		ans[2] = func.apply(REGISTRATE.item(id + "_leggings", p -> sup.apply(EquipmentSlot.LEGS, p))).defaultLang().register();
		ans[3] = func.apply(REGISTRATE.item(id + "_boots", p -> sup.apply(EquipmentSlot.FEET, p))).defaultLang().register();
		return ans;
	}

	private static final float[] BOW_PULL_VALS = {0, 0.65f, 0.9f};

	public static <T extends GenericBowItem> void createBowModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		ItemModelBuilder builder = pvd.withExistingParent(ctx.getName(), "minecraft:bow");
		builder.texture("layer0", "item/" + ctx.getName());
		for (int i = 0; i < 3; i++) {
			String name = ctx.getName() + "_pulling_" + i;
			ItemModelBuilder ret = pvd.getBuilder(name).parent(new ModelFile.UncheckedModelFile("minecraft:item/bow_pulling_" + i));
			ret.texture("layer0", "item/" + name);
			ItemModelBuilder.OverrideBuilder override = builder.override();
			override.predicate(new ResourceLocation("pulling"), 1);
			if (BOW_PULL_VALS[i] > 0)
				override.predicate(new ResourceLocation("pull"), BOW_PULL_VALS[i]);
			override.model(new ModelFile.UncheckedModelFile(L2Archery.MODID + ":item/" + name));
		}
	}

	public static <T extends Item> void createDoubleLayerModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		ItemModelBuilder builder = pvd.withExistingParent(ctx.getName(), "minecraft:generated");
		builder.texture("layer0", "item/" + ctx.getName());
		builder.texture("layer1", "item/" + ctx.getName() + "_overlay");
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

}
