package dev.xkmc.l2archery.content.entity;

import dev.xkmc.l2archery.content.item.GenericArrowItem;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class GenericArrowRenderer extends ArrowRenderer<GenericArrowEntity> {

	public GenericArrowRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public ResourceLocation getTextureLocation(GenericArrowEntity entity) {
		GenericArrowItem arrow = entity.data.arrow().item();
		ResourceLocation rl = ForgeRegistries.ITEMS.getKey(arrow);
		return new ResourceLocation(rl.getNamespace(), "textures/entity/arrow/" + rl.getPath() + ".png");
	}

}
