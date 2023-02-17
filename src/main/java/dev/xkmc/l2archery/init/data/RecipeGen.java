package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.content.crafting.BowBuilder;
import dev.xkmc.l2archery.content.upgrade.BowUpgradeBuilder;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2complements.init.data.LCMats;
import dev.xkmc.l2complements.init.registrate.LCItems;
import dev.xkmc.l2library.base.ingredients.EnchantmentIngredient;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;

import java.util.function.BiFunction;

public class RecipeGen {

	public static void genRecipe(RegistrateRecipeProvider pvd) {

		//arrow
		{
			cross(pvd, Items.IRON_NUGGET, Items.ARROW, ArcheryItems.STARTER_ARROW.get(), 4);
			cross(pvd, Items.COPPER_INGOT, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.COPPER_ARROW.get(), 4);
			cross(pvd, Items.IRON_INGOT, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.IRON_ARROW.get(), 4);
			cross(pvd, Items.GOLD_INGOT, ArcheryItems.IRON_ARROW.get(), ArcheryItems.GOLD_ARROW.get(), 4);
			cross(pvd, Items.OBSIDIAN, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.OBSIDIAN_ARROW.get(), 4);
			cross(pvd, Items.DIAMOND, ArcheryItems.OBSIDIAN_ARROW.get(), ArcheryItems.DIAMOND_ARROW.get(), 4);
			cross(pvd, LCItems.EXPLOSION_SHARD.get(), ArcheryItems.OBSIDIAN_ARROW.get(), ArcheryItems.DESTROYER_ARROW.get(), 4);
			cross(pvd, ArcheryItems.OBSIDIAN_ARROW.get(), Items.QUARTZ, ArcheryItems.QUARTZ_ARROW.get(), 1);
			cross(pvd, ArcheryItems.STARTER_ARROW.get(), Items.WITHER_ROSE, ArcheryItems.WITHER_ARROW.get(), 1);
			cross(pvd, ArcheryItems.STARTER_ARROW.get(), Items.BLACKSTONE, ArcheryItems.BLACKSTONE_ARROW.get(), 1);
			cross(pvd, Items.PHANTOM_MEMBRANE, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.NO_FALL_ARROW.get(), 4);
			cross(pvd, Items.BLUE_ICE, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.ICE_ARROW.get(), 4);
			full(pvd, Items.SOUL_SOIL, ArcheryItems.STARTER_ARROW.get(), Items.GUNPOWDER, ArcheryItems.FIRE_1_ARROW.get(), 4);
			full(pvd, Items.GHAST_TEAR, ArcheryItems.FIRE_1_ARROW.get(), Items.BLAZE_ROD, ArcheryItems.FIRE_2_ARROW.get(), 4);
			full(pvd, Items.TNT, ArcheryItems.STARTER_ARROW.get(), Items.FIRE_CHARGE, ArcheryItems.TNT_1_ARROW.get(), 4);
			full(pvd, Items.END_CRYSTAL, ArcheryItems.TNT_1_ARROW.get(), Items.TNT, ArcheryItems.TNT_2_ARROW.get(), 4);
			full(pvd, Items.CREEPER_HEAD, ArcheryItems.TNT_2_ARROW.get(), Items.END_CRYSTAL, ArcheryItems.TNT_3_ARROW.get(), 4);
			full(pvd, Items.OBSIDIAN, Items.END_ROD, Items.ENDER_EYE, ArcheryItems.ENDER_ARROW.get(), 4);
			full(pvd, Items.PHANTOM_MEMBRANE, ArcheryItems.STARTER_ARROW.get(), Items.GUNPOWDER, ArcheryItems.STORM_ARROW.get(), 4);
			cross(pvd, LCItems.RESONANT_FEATHER.get(), ArcheryItems.STARTER_ARROW.get(), ArcheryItems.DISPELL_ARROW.get(), 4);
			full(pvd, Items.LAVA_BUCKET, ArcheryItems.STARTER_ARROW.get(), Items.MAGMA_CREAM, ArcheryItems.ACID_ARROW.get(), 4);
			full(pvd, ArcheryItems.DESTROYER_ARROW.get(), LCItems.SPACE_SHARD.get(), LCItems.VOID_EYE.get(), ArcheryItems.VOID_ARROW.get(), 1);
		}

		// bow
		{

			// root
			unlock(pvd, new BowBuilder(ArcheryItems.STARTER_BOW.get(), 1)::unlockedBy, Items.BOW)
					.pattern(" AC").pattern("ABC").pattern(" AC")
					.define('A', Items.BAMBOO)
					.define('B', Items.BOW)
					.define('C', Items.VINE)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.GLOW_AIM_BOW.get(), 1)::unlockedBy, ArcheryItems.STARTER_BOW.get())
					.pattern("GGC").pattern("FBC").pattern("GGC")
					.define('C', Items.PHANTOM_MEMBRANE)
					.define('G', Items.GLOWSTONE_DUST)
					.define('B', ArcheryItems.STARTER_BOW.get())
					.define('F', Items.ENDER_EYE)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.IRON_BOW.get(), 1)::unlockedBy, ArcheryItems.STARTER_BOW.get())
					.pattern(" AC").pattern("ABC").pattern(" AC")
					.define('A', Items.IRON_INGOT)
					.define('B', ArcheryItems.STARTER_BOW.get())
					.define('C', Items.WEEPING_VINES)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.MASTER_BOW.get(), 1)::unlockedBy, ArcheryItems.STARTER_BOW.get())
					.pattern(" AC").pattern("ABC").pattern(" AC")
					.define('A', Items.COPPER_INGOT)
					.define('B', ArcheryItems.STARTER_BOW.get())
					.define('C', Items.TWISTING_VINES)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.MAGNIFY_BOW.get(), 1)::unlockedBy, ArcheryItems.GLOW_AIM_BOW.get())
					.pattern("A").pattern("B")
					.define('B', ArcheryItems.GLOW_AIM_BOW.get())
					.define('A', Items.SPYGLASS)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.ENDER_AIM_BOW.get(), 1)::unlockedBy, ArcheryItems.GLOW_AIM_BOW.get())
					.pattern("2OR").pattern("ABR").pattern("1OR")
					.define('O', Items.DRAGON_HEAD)
					.define('R', LCMats.SHULKERATE.getIngot())
					.define('1', new EnchantmentIngredient(Enchantments.BINDING_CURSE, 1))
					.define('2', new EnchantmentIngredient(Enchantments.VANISHING_CURSE, 1))
					.define('A', LCItems.VOID_EYE.get())
					.define('B', ArcheryItems.GLOW_AIM_BOW.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.VOID_BOW.get(), 1)::unlockedBy, ArcheryItems.ENDER_AIM_BOW.get())
					.pattern("2OR").pattern("ABR").pattern("1OR")
					.define('O', Items.DRAGON_HEAD)
					.define('R', LCItems.SPACE_SHARD.get())
					.define('1', new EnchantmentIngredient(Enchantments.MENDING, 1))
					.define('2', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('A', LCItems.VOID_EYE.get())
					.define('B', ArcheryItems.ENDER_AIM_BOW.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.FLAME_BOW.get(), 1)::unlockedBy, ArcheryItems.MASTER_BOW.get())
					.pattern("DCB").pattern("EAB").pattern("DCB")
					.define('A', ArcheryItems.MASTER_BOW.get())
					.define('B', Items.SOUL_SOIL)
					.define('C', Items.BLAZE_ROD)
					.define('D', Items.GHAST_TEAR)
					.define('E', LCItems.SOUL_FLAME.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.EXPLOSION_BOW.get(), 1)::unlockedBy, ArcheryItems.FLAME_BOW.get())
					.pattern("DDB").pattern("CAB").pattern("DDB")
					.define('A', ArcheryItems.FLAME_BOW.get())
					.define('B', Items.BLAZE_ROD)
					.define('D', LCItems.EXPLOSION_SHARD.get())
					.define('C', Items.NETHER_STAR)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.SUN_BOW.get(), 1)::unlockedBy, ArcheryItems.EXPLOSION_BOW.get())
					.pattern("EDB").pattern("CAB").pattern("EDB")
					.define('A', ArcheryItems.EXPLOSION_BOW.get())
					.define('B', LCItems.EXPLOSION_SHARD.get())
					.define('C', LCItems.SUN_MEMBRANE.get())
					.define('D', Items.NETHER_STAR)
					.define('E', Items.CREEPER_HEAD)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.FROZE_BOW.get(), 1)::unlockedBy, ArcheryItems.MASTER_BOW.get())
					.pattern("BBB").pattern("DAB").pattern("BBB")
					.define('A', ArcheryItems.MASTER_BOW.get())
					.define('B', Items.BLUE_ICE)
					.define('D', Items.POWDER_SNOW_BUCKET)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.WINTER_BOW.get(), 1)::unlockedBy, ArcheryItems.FROZE_BOW.get())
					.pattern("CBB").pattern("DAB").pattern("CBB")
					.define('A', ArcheryItems.FROZE_BOW.get())
					.define('B', Items.BLUE_ICE)
					.define('C', Items.POWDER_SNOW_BUCKET)
					.define('D', LCItems.HARD_ICE.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.STORM_BOW.get(), 1)::unlockedBy, ArcheryItems.MASTER_BOW.get())
					.pattern("DCB").pattern("EAB").pattern("DCB")
					.define('A', ArcheryItems.MASTER_BOW.get())
					.define('B', Items.PHANTOM_MEMBRANE)
					.define('C', Items.FEATHER)
					.define('D', Items.GUNPOWDER)
					.define('E', LCItems.STORM_CORE.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.BLACKSTONE_BOW.get(), 1)::unlockedBy, ArcheryItems.MASTER_BOW.get())
					.pattern("CBB").pattern("DAB").pattern("CBB")
					.define('A', ArcheryItems.MASTER_BOW.get())
					.define('B', Items.BLACKSTONE)
					.define('C', Items.SOUL_SAND)
					.define('D', Items.GILDED_BLACKSTONE)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.TURTLE_BOW.get(), 1)::unlockedBy, ArcheryItems.IRON_BOW.get())
					.pattern("CCB").pattern("DAB").pattern("CCB")
					.define('A', ArcheryItems.IRON_BOW.get())
					.define('B', Items.SCUTE)
					.define('C', Items.IRON_INGOT)
					.define('D', Items.GOLD_INGOT)
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.EARTH_BOW.get(), 1)::unlockedBy, ArcheryItems.TURTLE_BOW.get())
					.pattern("CCB").pattern("DAB").pattern("CCB")
					.define('A', ArcheryItems.TURTLE_BOW.get())
					.define('B', Items.BLACKSTONE)
					.define('C', Items.GOLD_INGOT)
					.define('D', LCItems.BLACKSTONE_CORE.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.GAIA_BOW.get(), 1)::unlockedBy, ArcheryItems.EARTH_BOW.get())
					.pattern("CCB").pattern("DAB").pattern("CCB")
					.define('A', ArcheryItems.EARTH_BOW.get())
					.define('B', LCItems.BLACKSTONE_CORE.get())
					.define('C', Items.NETHERITE_INGOT)
					.define('D', LCItems.FORCE_FIELD.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.EAGLE_BOW.get(), 1)::unlockedBy, ArcheryItems.IRON_BOW.get())
					.pattern("1LO").pattern("AB2").pattern("1LO")
					.define('O', LCItems.RESONANT_FEATHER.get())
					.define('L', Items.LEATHER)
					.define('1', new EnchantmentIngredient(Enchantments.POWER_ARROWS, 5))
					.define('2', new EnchantmentIngredient(Enchantments.PUNCH_ARROWS, 2))
					.define('A', Items.NETHERITE_INGOT)
					.define('B', ArcheryItems.IRON_BOW.get())
					.save(pvd);

			unlock(pvd, new BowBuilder(ArcheryItems.WIND_BOW.get(), 1)::unlockedBy, ArcheryItems.STORM_BOW.get())
					.pattern("CCB").pattern("DAB").pattern("CCB")
					.define('A', ArcheryItems.STORM_ARROW.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('C', Items.PHANTOM_MEMBRANE)
					.define('D', LCItems.CAPTURED_BULLET.get())
					.save(pvd);

		}

		// upgrade
		{
			unlock(pvd, new ShapedRecipeBuilder(ArcheryItems.UPGRADE.get(), 1)::unlockedBy, Items.AMETHYST_SHARD)
					.pattern("BCB").pattern("DAD").pattern("BCB")
					.define('A', Items.AMETHYST_SHARD)
					.define('B', Items.GOLD_NUGGET)
					.define('C', Items.REDSTONE)
					.define('D', Items.LAPIS_LAZULI)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.GLOW_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("BCB").pattern("BAB").pattern("B B")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.GLOWSTONE_DUST)
					.define('C', Items.ENDER_EYE)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.FIRE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("DED").pattern("CAC").pattern("BBB")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.SOUL_SOIL)
					.define('C', Items.BLAZE_ROD)
					.define('D', Items.GHAST_TEAR)
					.define('E', LCItems.SOUL_FLAME.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.ICE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.POWDER_SNOW_BUCKET)
					.define('C', Items.BLUE_ICE)
					.define('E', LCItems.HARD_ICE.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.EXPLOSION_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("BAB").pattern("CDC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.CREEPER_HEAD)
					.define('D', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('E', LCItems.EXPLOSION_SHARD.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.NO_FALL_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('C', Items.PHANTOM_MEMBRANE)
					.define('E', LCItems.CAPTURED_BULLET.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.ENDER_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("3 4").pattern("CAC").pattern("1B2")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', LCItems.VOID_EYE.get())
					.define('C', Items.DRAGON_HEAD)
					.define('1', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('2', new EnchantmentIngredient(Enchantments.MENDING, 1))
					.define('3', new EnchantmentIngredient(Enchantments.BINDING_CURSE, 1))
					.define('4', new EnchantmentIngredient(Enchantments.VANISHING_CURSE, 1))
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.DAMAGE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("C C").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', ArcheryItems.DIAMOND_ARROW.get())
					.define('C', new EnchantmentIngredient(Enchantments.POWER_ARROWS, 5))
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.PUNCH_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("C C").pattern(" A ").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', new EnchantmentIngredient(Enchantments.PUNCH_ARROWS, 2))
					.define('C', ArcheryItems.GOLD_ARROW.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.MAGNIFY_UP_1.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern(" B ").pattern("CAC").pattern(" C ")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.SPYGLASS)
					.define('C', Items.COPPER_INGOT)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.MAGNIFY_UP_2.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern(" B ").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.SPYGLASS)
					.define('C', Items.COPPER_INGOT)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.MAGNIFY_UP_3.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CBC").pattern("BAB").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.SPYGLASS)
					.define('C', Items.COPPER_INGOT)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.SHINE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CBC").pattern("DAD").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('C', Items.SPECTRAL_ARROW)
					.define('D', Items.GLOWSTONE_DUST)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.SUPERDAMAGE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CBC").pattern("DAD").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', LCItems.CAPTURED_WIND.get())
					.define('D', LCItems.EXPLOSION_SHARD.get())
					.define('C', new EnchantmentIngredient(Enchantments.POWER_ARROWS, 5))
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.BLACKSTONE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.GILDED_BLACKSTONE)
					.define('C', Items.BLACKSTONE)
					.define('E', LCItems.BLACKSTONE_CORE.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.HARM_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.DRAGON_BREATH)
					.define('C', Items.FERMENTED_SPIDER_EYE)
					.define('E', Items.NETHER_WART)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.HEAL_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.DRAGON_BREATH)
					.define('C', Items.GLISTERING_MELON_SLICE)
					.define('E', Items.NETHER_WART)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.LEVITATE_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CEC").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.DRAGON_BREATH)
					.define('C', LCItems.CAPTURED_BULLET.get())
					.define('E', Items.NETHER_WART)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.RAILGUN_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("CBC").pattern("BAB").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('C', LCItems.CAPTURED_WIND.get())
					.define('B', LCItems.SPACE_SHARD.get())
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.FLUX_UP.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern(" X ")
					.pattern("ICI")
					.pattern("GPG")
					.define('C', TagGen.GEARS_GOLD)
					.define('G', TagGen.GEARS_TIN)
					.define('I', Tags.Items.INGOTS_IRON)
					.define('P', Items.REDSTONE)
					.define('X', ArcheryItems.UPGRADE.get())
					.save(pvd);
		}

	}

	private static void cross(RegistrateRecipeProvider pvd, Item core, Item side, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern(" S ").pattern("SCS").pattern(" S ")
				.define('S', side).define('C', core)
				.save(pvd);
	}

	private static void full(RegistrateRecipeProvider pvd, Item core, Item side, Item corner, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern("CSC").pattern("SAS").pattern("CSC")
				.define('A', core).define('S', side).define('C', corner)
				.save(pvd);
	}

	private static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

}
