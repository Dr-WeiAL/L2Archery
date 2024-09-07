package dev.xkmc.l2archery.init;

import dev.xkmc.l2archery.content.client.ArrowDisplayOverlay;
import dev.xkmc.l2archery.content.client.BowFluxBarRenderer;
import dev.xkmc.l2archery.content.client.BowInfoOverlay;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(value = Dist.CLIENT, modid = L2Archery.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2ArcheryClient {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(L2ArcheryClient::registerItemProperties);
	}

	public static void registerItemProperties() {
		for (GenericBowItem bow : ArcheryItems.BOW_LIKE) {
			ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pull"),
					(stack, level, entity, i) -> entity == null || entity.getUseItem() != stack ? 0.0F :
							bow.getPullForTime(entity, stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()));
			ItemProperties.register(bow, ResourceLocation.withDefaultNamespace("pulling"),
					(stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
		}
	}

	@SubscribeEvent
	public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
		BowFluxBarRenderer deco = new BowFluxBarRenderer();
		for (GenericBowItem bow : ArcheryItems.BOW_LIKE) {
			event.register(bow, deco);
		}
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
	}

	@SubscribeEvent
	public static void registerOverlays(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR, L2Archery.loc("arrow"), new ArrowDisplayOverlay());
		event.registerBelow(VanillaGuiLayers.HOTBAR, L2Archery.loc("info"), new BowInfoOverlay());
	}

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {

	}

}
