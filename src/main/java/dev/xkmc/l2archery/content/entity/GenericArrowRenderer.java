package dev.xkmc.l2archery.content.entity;

import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class GenericArrowRenderer extends ArrowRenderer<GenericArrowEntity> {

	public GenericArrowRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public ResourceLocation getTextureLocation(GenericArrowEntity entity) {
		GenericArrowItem arrow;
		if (entity.data.arrow().item() instanceof GenericArrowItem gen) {
			arrow = gen;
		} else {
			arrow = ArcheryItems.STARTER_ARROW.get();
		}
		ResourceLocation rl = BuiltInRegistries.ITEM.getKey(arrow);
		return rl.withPath("textures/entity/arrow/arrow.png");
	}

}
