package dev.xkmc.l2archery.init;

import dev.xkmc.l2archery.content.client.ArrowDisplayOverlay;
import dev.xkmc.l2archery.content.client.BowFluxBarRenderer;
import dev.xkmc.l2archery.content.client.BowInfoOverlay;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;

public class L2ArcheryClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.register(L2ArcheryClient.class);
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(L2ArcheryClient::registerItemProperties);
	}

	public static final List<GenericBowItem> BOW_LIKE = new ArrayList<>();

	public static void registerItemProperties() {
		for (GenericBowItem bow : BOW_LIKE) {
			ItemProperties.register(bow, new ResourceLocation("pull"), (stack, level, entity, i) -> entity == null || entity.getUseItem() != stack ? 0.0F : bow.getPullForTime(entity, stack.getUseDuration() - entity.getUseItemRemainingTicks()));
			ItemProperties.register(bow, new ResourceLocation("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
		}
	}

	@SubscribeEvent
	public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
		BowFluxBarRenderer deco = new BowFluxBarRenderer();
		for (GenericBowItem bow : BOW_LIKE) {
			event.register(bow, deco);
		}
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
	}

	@SubscribeEvent
	public static void registerOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "arrow", new ArrowDisplayOverlay());
		event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "info", new BowInfoOverlay());
	}

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {

	}

}
