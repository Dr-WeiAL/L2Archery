package dev.xkmc.l2archery.content.upgrade;

import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2core.serial.recipe.DataRecipeWrapper;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class BowUpgradeBuilder {

	private final ShapedRecipeBuilder recipe;
	private final Upgrade upgrade;

	public BowUpgradeBuilder(Upgrade upgrade) {
		this.recipe = new ShapedRecipeBuilder(RecipeCategory.COMBAT, ArcheryItems.UPGRADE, 1);
		this.upgrade = upgrade;
	}

	public BowUpgradeBuilder unlockedBy(String name, Criterion<?> criterion) {
		this.recipe.unlockedBy(name, criterion);
		return this;
	}


	public BowUpgradeBuilder pattern(String id) {
		recipe.pattern(id);
		return this;
	}

	public BowUpgradeBuilder define(char ch, ItemLike item) {
		recipe.define(ch, item);
		return this;
	}

	public BowUpgradeBuilder define(char ch, Ingredient item) {
		recipe.define(ch, item);
		return this;
	}

	public void save(RecipeOutput pvd) {
		recipe.save(new DataRecipeWrapper(pvd, upgrade.item()));
	}

}
