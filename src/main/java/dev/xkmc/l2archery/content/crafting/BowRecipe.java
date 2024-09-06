package dev.xkmc.l2archery.content.crafting;

import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.serial.recipe.AbstractShapedRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class BowRecipe extends AbstractShapedRecipe<BowRecipe> {

	public BowRecipe(String group, ShapedRecipePattern pattern, ItemStack result) {
		super(group, pattern, result);
	}

	@Override
	public ItemStack assemble(CraftingInput container, HolderLookup.Provider access) {
		ItemStack bow = ItemStack.EMPTY;
		for (int i = 0; i < container.size(); i++) {
			if (container.getItem(i).getItem() instanceof BowItem) {
				bow = container.getItem(i);
			}
		}
		ItemStack ans = super.assemble(container, access);
		if (!bow.isEmpty()) {
			ans.applyComponents(bow.getComponentsPatch());
		}
		return ans;
	}

	@Override
	public Serializer<BowRecipe> getSerializer() {
		return ArcheryRegister.BOW_RECIPE.get();
	}
}
