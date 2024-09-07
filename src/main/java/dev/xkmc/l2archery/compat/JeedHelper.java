package dev.xkmc.l2archery.compat;

import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2library.serial.ingredients.EnchantmentIngredient;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import dev.xkmc.l2serial.serialization.codec.PacketCodec;
import dev.xkmc.l2serial.util.Wrappers;
import net.mehvahdjukaar.jeed.recipes.EffectProviderRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("ConstantConditions")
public class JeedHelper {

	public enum JeedType {
		BOW(id -> BowArrowStatConfig.get().getBowEffects(Wrappers.cast(BuiltInRegistries.ITEM.get(id)))
				.instances().stream().map(MobEffectInstance::getEffect).toList(),
				id -> Ingredient.of(BuiltInRegistries.ITEM.get(id))),
		ARROW(id -> BowArrowStatConfig.get().getArrowEffects(Wrappers.cast(BuiltInRegistries.ITEM.get(id)))
				.instances().stream().map(MobEffectInstance::getEffect).toList(),
				id -> Ingredient.of(BuiltInRegistries.ITEM.get(id))),
		UPGRADE(id -> BowArrowStatConfig.get().getUpgradeEffects(Wrappers.cast(ArcheryRegister.UPGRADE.get().getValue(id)))
				.instances().stream().map(MobEffectInstance::getEffect).toList(),
				id -> Ingredient.of(UpgradeItem.setUpgrade(ArcheryItems.UPGRADE.asStack(), ArcheryRegister.UPGRADE.get().getValue(id)))),
		ENCHANTMENT(id -> BowArrowStatConfig.get().getEnchEffects(Wrappers.cast(L2LibReg.ENCH.get().get(id)), 1)
				.instances().stream().map(MobEffectInstance::getEffect).toList(),
				id -> new EnchantmentIngredient(ForgeRegistries.ENCHANTMENTS.getValue(id), 1));

		private final Function<ResourceLocation, List<MobEffect>> effect;
		private final Function<ResourceLocation, Ingredient> display;

		JeedType(Function<ResourceLocation, List<MobEffect>> effect, Function<ResourceLocation, Ingredient> display) {
			this.effect = effect;
			this.display = display;
		}
	}

	@SerialClass
	public static class ArcheryJeedRecipe extends EffectProviderRecipe {

		@SerialClass.SerialField
		private JeedType jeedType;
		@SerialClass.SerialField
		private ResourceLocation entryName;


		public ArcheryJeedRecipe(ResourceLocation id) {
			super(id, null, NonNullList.create());
		}

		public ArcheryJeedRecipe(ResourceLocation id, JeedType type, ResourceLocation name) {
			this(id);
			this.jeedType = type;
			this.entryName = name;
		}

		@Override
		public Collection<MobEffect> getEffects() {
			return jeedType.effect.apply(entryName);
		}

		@Override
		public NonNullList<Ingredient> getIngredients() {
			var e = jeedType.display.apply(entryName);
			return NonNullList.of(e, e);
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return REC.get();
		}

	}

	public record ArcheryJeedFinished(JeedType jeedType, ResourceLocation entryName) {

	}

	public static class Serializer implements RecipeSerializer<ArcheryJeedRecipe> {

		@Override
		public ArcheryJeedRecipe fromJson(ResourceLocation id, JsonObject json) {
			return JsonCodec.from(json, ArcheryJeedRecipe.class, new ArcheryJeedRecipe(id));
		}

		@Override
		public @Nullable ArcheryJeedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
			return PacketCodec.from(buf, ArcheryJeedRecipe.class, new ArcheryJeedRecipe(id));
		}

		@Override
		public void toNetwork(FriendlyByteBuf buf, ArcheryJeedRecipe r) {
			PacketCodec.to(buf, r);
		}

	}

	public static RegistryEntry<RecipeSerializer<?>> REC = L2Archery.REGISTRATE.simple("jeed_archery",
			ForgeRegistries.Keys.RECIPE_SERIALIZERS, Serializer::new);

	public static void register() {

	}

}
