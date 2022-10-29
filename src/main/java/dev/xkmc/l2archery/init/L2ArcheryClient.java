package dev.xkmc.l2archery.init;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class L2ArcheryClient {

	public static void onCtorClient(IEventBus bus, IEventBus eventBus) {
		bus.addListener(ClientRegister::registerOverlays);
		bus.addListener(ClientRegister::registerKeys);
		bus.addListener(L2ArcheryClient::clientSetup);
	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		ClientRegister.registerItemProperties();
	}

}
