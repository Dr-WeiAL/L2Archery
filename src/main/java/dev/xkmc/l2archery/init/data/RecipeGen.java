package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.content.upgrade.BowUpgradeBuilder;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2library.base.ingredients.EnchantmentIngredient;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.function.BiFunction;

public class RecipeGen {

	public static void genRecipe(RegistrateRecipeProvider pvd) {

		//arrow
		{
			cross(pvd, Items.IRON_NUGGET, Items.ARROW, ArcheryItems.STARTER_ARROW.get(), 4);
			cross(pvd, Items.COPPER_INGOT, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.COPPER_ARROW.get(), 4);
			cross(pvd, Items.IRON_INGOT, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.IRON_ARROW.get(), 4);
			cross(pvd, Items.OBSIDIAN, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.OBSIDIAN_ARROW.get(), 4);
			cross(pvd, Items.PHANTOM_MEMBRANE, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.NO_FALL_ARROW.get(), 4);
			cross(pvd, Items.BLUE_ICE, ArcheryItems.STARTER_ARROW.get(), ArcheryItems.ICE_ARROW.get(), 4);
			full(pvd, Items.FIRE_CHARGE, ArcheryItems.STARTER_ARROW.get(), Items.TORCH, ArcheryItems.FIRE_1_ARROW.get(), 4);
			full(pvd, Items.BLAZE_ROD, ArcheryItems.FIRE_1_ARROW.get(), Items.BLAZE_POWDER, ArcheryItems.FIRE_2_ARROW.get(), 4);
			full(pvd, Items.TNT, ArcheryItems.STARTER_ARROW.get(), Items.FIRE_CHARGE, ArcheryItems.TNT_1_ARROW.get(), 4);
			full(pvd, Items.END_CRYSTAL, ArcheryItems.TNT_1_ARROW.get(), Items.TNT, ArcheryItems.TNT_2_ARROW.get(), 4);
			full(pvd, Items.CREEPER_HEAD, ArcheryItems.TNT_2_ARROW.get(), Items.END_CRYSTAL, ArcheryItems.TNT_3_ARROW.get(), 4);
			full(pvd, Items.OBSIDIAN, Items.END_ROD, Items.ENDER_EYE, ArcheryItems.ENDER_ARROW.get(), 4);
		}

		// bow
		{

			unlock(pvd, new ShapedRecipeBuilder(ArcheryItems.STARTER_BOW.get(), 1)::unlockedBy, Items.BOW)
					.pattern(" AC").pattern("ABC").pattern(" AC")
					.define('A', Items.BAMBOO)
					.define('B', Items.BOW)
					.define('C', Items.IRON_NUGGET)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(ArcheryItems.GLOW_AIM_BOW.get(), 1)::unlockedBy, ArcheryItems.STARTER_BOW.get())
					.pattern("GGG").pattern("EBG").pattern("GGG")
					.define('G', Items.GLOWSTONE_DUST)
					.define('B', ArcheryItems.STARTER_BOW.get())
					.define('E', Items.ENDER_EYE)
					.save(pvd);

			unlock(pvd, new ShapelessRecipeBuilder(ArcheryItems.MAGNIFY_BOW.get(), 1)::unlockedBy, ArcheryItems.GLOW_AIM_BOW.get())
					.requires(ArcheryItems.GLOW_AIM_BOW.get())
					.requires(Items.SPYGLASS)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(ArcheryItems.IRON_BOW.get(), 1)::unlockedBy, ArcheryItems.STARTER_BOW.get())
					.pattern(" AC").pattern("ABC").pattern(" AC")
					.define('A', Items.IRON_INGOT)
					.define('B', ArcheryItems.STARTER_BOW.get())
					.define('C', Items.IRON_NUGGET)
					.save(pvd);

			unlock(pvd, new ShapedRecipeBuilder(ArcheryItems.ENDER_AIM_BOW.get(), 1)::unlockedBy, ArcheryItems.GLOW_AIM_BOW.get())
					.pattern("ROP").pattern("ABP").pattern("ROP")
					.define('O', Items.OBSIDIAN)
					.define('R', Items.END_ROD)
					.define('P', Items.ENDER_PEARL)
					.define('A', Items.ENDER_EYE)
					.define('B', ArcheryItems.GLOW_AIM_BOW.get())
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

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.GLOW.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("BCB").pattern("BAB").pattern("B B")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.GLOWSTONE_DUST)
					.define('C', Items.ENDER_EYE)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.FIRE.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("D D").pattern("CAC").pattern("BBB")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.SOUL_SAND)
					.define('C', Items.BLAZE_ROD)
					.define('D', Items.GHAST_TEAR)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.ICE.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("C C").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.POWDER_SNOW_BUCKET)
					.define('C', Items.BLUE_ICE)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.EXPLOSION.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("C C").pattern("BAB").pattern("CDC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.NETHER_STAR)
					.define('C', Items.CREEPER_HEAD)
					.define('D', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.NO_FALL.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("C C").pattern("CAC").pattern("CBC")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.ELYTRA)
					.define('C', Items.PHANTOM_MEMBRANE)
					.save(pvd);

			unlock(pvd, new BowUpgradeBuilder(ArcheryItems.ENDER.get())::unlockedBy, ArcheryItems.UPGRADE.get())
					.pattern("3 4").pattern("CAC").pattern("1B2")
					.define('A', ArcheryItems.UPGRADE.get())
					.define('B', Items.DRAGON_HEAD)
					.define('C', Items.ENDER_PEARL)
					.define('1', new EnchantmentIngredient(Enchantments.INFINITY_ARROWS, 1))
					.define('2', new EnchantmentIngredient(Enchantments.MENDING, 1))
					.define('3', new EnchantmentIngredient(Enchantments.BINDING_CURSE, 1))
					.define('4', new EnchantmentIngredient(Enchantments.VANISHING_CURSE, 1))
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
