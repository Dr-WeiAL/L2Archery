package dev.xkmc.l2archery.content.upgrade;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class BowUpgradeBuilder implements RecipeBuilder {
	private final Upgrade upgrade;
	private final List<String> rows = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	@Nullable
	private String group;

	public BowUpgradeBuilder(Upgrade upgrade) {
		this.upgrade = upgrade;
	}

	public BowUpgradeBuilder define(Character character, TagKey<Item> tagKey) {
		return this.define(character, Ingredient.of(tagKey));
	}

	public BowUpgradeBuilder define(Character character, ItemLike itemLike) {
		return this.define(character, Ingredient.of(itemLike));
	}

	public BowUpgradeBuilder define(Character character, Ingredient ingredient) {
		if (this.key.containsKey(character)) {
			throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
		} else if (character == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			this.key.put(character, ingredient);
			return this;
		}
	}

	public BowUpgradeBuilder pattern(String str) {
		if (!this.rows.isEmpty() && str.length() != this.rows.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.rows.add(str);
			return this;
		}
	}

	public BowUpgradeBuilder unlockedBy(String name, CriterionTriggerInstance instance) {
		this.advancement.addCriterion(name, instance);
		return this;
	}

	public BowUpgradeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	public Item getResult() {
		return ArcheryItems.UPGRADE.get();
	}

	public void save(Consumer<FinishedRecipe> pvd) {
		ResourceLocation id = this.upgrade.getRegistryName();
		this.save(pvd, new ResourceLocation(id.getNamespace(), "upgrades/" + id.getPath()));
	}

	public void save(Consumer<FinishedRecipe> p_126141_, ResourceLocation p_126142_) {
		this.ensureValid(p_126142_);
		this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126142_)).rewards(AdvancementRewards.Builder.recipe(p_126142_)).requirements(RequirementsStrategy.OR);
		p_126141_.accept(new Result(p_126142_, this.upgrade, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new ResourceLocation(p_126142_.getNamespace(), "recipes/" + p_126142_.getPath())));
	}

	private void ensureValid(ResourceLocation p_126144_) {
		if (this.rows.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for shaped recipe " + p_126144_ + "!");
		} else {
			Set<Character> set = Sets.newHashSet(this.key.keySet());
			set.remove(' ');

			for (String s : this.rows) {
				for (int i = 0; i < s.length(); ++i) {
					char c0 = s.charAt(i);
					if (!this.key.containsKey(c0) && c0 != ' ') {
						throw new IllegalStateException("Pattern in recipe " + p_126144_ + " uses undefined symbol '" + c0 + "'");
					}

					set.remove(c0);
				}
			}

			if (!set.isEmpty()) {
				throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_126144_);
			} else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
				throw new IllegalStateException("Shaped recipe " + p_126144_ + " only takes in a single item - should it be a shapeless recipe instead?");
			} else if (this.advancement.getCriteria().isEmpty()) {
				throw new IllegalStateException("No way of obtaining recipe " + p_126144_);
			}
		}
	}

	public static class Result implements FinishedRecipe {
		private final ResourceLocation id;
		private final Upgrade up;
		private final String group;
		private final List<String> pattern;
		private final Map<Character, Ingredient> key;
		private final Advancement.Builder advancement;
		private final ResourceLocation advancementId;

		public Result(ResourceLocation id, Upgrade up, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation adId) {
			this.id = id;
			this.up = up;
			this.group = group;
			this.pattern = pattern;
			this.key = key;
			this.advancement = advancement;
			this.advancementId = adId;
		}

		public void serializeRecipeData(JsonObject json) {
			if (!this.group.isEmpty()) {
				json.addProperty("group", this.group);
			}

			JsonArray jsonarray = new JsonArray();

			for (String s : this.pattern) {
				jsonarray.add(s);
			}

			json.add("pattern", jsonarray);
			JsonObject jsonobject = new JsonObject();

			for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
				jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
			}

			json.add("key", jsonobject);

			JsonObject result = new JsonObject();
			result.addProperty("item", ArcheryItems.UPGRADE.getId().toString());
			JsonObject tag = new JsonObject();
			tag.addProperty("upgrade", up.getRegistryName().toString());
			result.add("nbt", tag);

			json.add("result", result);
		}

		public RecipeSerializer<?> getType() {
			return RecipeSerializer.SHAPED_RECIPE;
		}

		public ResourceLocation getId() {
			return this.id;
		}

		@Nullable
		public JsonObject serializeAdvancement() {
			return this.advancement.serializeToJson();
		}

		@Nullable
		public ResourceLocation getAdvancementId() {
			return this.advancementId;
		}
	}
}
