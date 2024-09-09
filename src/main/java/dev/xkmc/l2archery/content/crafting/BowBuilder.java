package dev.xkmc.l2archery.content.crafting;

import dev.xkmc.l2core.serial.recipe.CustomShapedBuilder;
import net.minecraft.world.level.ItemLike;

public class BowBuilder extends CustomShapedBuilder<BowRecipe> {

	public BowBuilder(ItemLike result, int count) {
		super(BowRecipe::new, result, count);
	}

}
