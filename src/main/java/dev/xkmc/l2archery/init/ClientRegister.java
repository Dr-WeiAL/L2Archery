package dev.xkmc.l2archery.init;

import dev.xkmc.l2archery.content.item.GenericBowItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class ClientRegister {

	public static final List<GenericBowItem> BOW_LIKE = new ArrayList<>();

	@OnlyIn(Dist.CLIENT)
	public static void registerItemProperties() {
		for (GenericBowItem bow : BOW_LIKE) {
			ItemProperties.register(bow, new ResourceLocation("pull"), (stack, level, entity, i) -> entity == null || entity.getUseItem() != stack ? 0.0F : bow.getPullForTime(entity, stack.getUseDuration() - entity.getUseItemRemainingTicks()));
			ItemProperties.register(bow, new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerOverlays() {
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeys() {

	}

}
