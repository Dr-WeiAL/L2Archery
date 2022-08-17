package dev.xkmc.l2archery.content.item;

import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.PotionArrowFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpectralArrowItem;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.alchemy.PotionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public record ArrowData(Item item, @Nullable CompoundTag tag) {

	public static ArrowData of(Item item) {
		return new ArrowData(item, null);
	}

	public static ArrowData of(Item item, ItemStack stack) {
		return new ArrowData(item, stack.getTag());
	}

	public FeatureList getFeatures() {
		if (item instanceof GenericArrowItem gen) {
			return gen.getFeatures();
		}
		FeatureList ans = new FeatureList();
		if (item instanceof SpectralArrowItem) {
			ans.add(new PotionArrowFeature(List.of(new MobEffectInstance(MobEffects.GLOWING, 200, 0))));
		} else if (item instanceof TippedArrowItem) {
			ItemStack stack = new ItemStack(item);
			stack.setTag(tag);
			List<MobEffectInstance> list = new ArrayList<>();
			for (MobEffectInstance ins : PotionUtils.getMobEffects(stack)) {
				list.add(new MobEffectInstance(ins.getEffect(), Math.max(ins.getDuration() / 8, 1),
						ins.getAmplifier(), ins.isAmbient(), ins.isVisible()));
			}
			ans.add(new PotionArrowFeature(list));
		}
		return ans;
	}

	public ItemStack stack() {
		if (item instanceof TippedArrowItem) {
			ItemStack ans = new ItemStack(item);
			ans.setTag(tag);
			return ans;
		}
		return item.getDefaultInstance();
	}

	public GenericArrowItem getItem() {
		return item instanceof GenericArrowItem gen ? gen : ArcheryItems.STARTER_ARROW.get();
	}
}
