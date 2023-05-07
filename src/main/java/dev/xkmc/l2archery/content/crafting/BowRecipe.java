package dev.xkmc.l2archery.content.crafting;

import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.serial.recipe.AbstractShapedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class BowRecipe extends AbstractShapedRecipe<BowRecipe> {

	final NonNullList<Ingredient> recipeItems;

	public BowRecipe(ResourceLocation rl, String group, int w, int h, NonNullList<Ingredient> ingredients, ItemStack result) {
		super(rl, group, w, h, ingredients, result);
		recipeItems = ingredients;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
		ItemStack bow = ItemStack.EMPTY;
		for (int i = 0; i < container.getContainerSize(); i++) {
			if (container.getItem(i).getItem() instanceof BowItem) {
				bow = container.getItem(i);
			}
		}
		ItemStack ans = super.assemble(container, access);
		if (!bow.isEmpty()) {
			ans.setTag(bow.getTag());
		}
		return ans;
	}

	@Override
	public Serializer<BowRecipe> getSerializer() {
		return ArcheryRegister.BOW_RECIPE.get();
	}
}
