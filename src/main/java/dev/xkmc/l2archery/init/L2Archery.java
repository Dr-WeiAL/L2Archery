package dev.xkmc.l2archery.init;

import dev.xkmc.l2archery.events.GenericEventHandler;
import dev.xkmc.l2archery.init.data.RecipeGen;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("l2archery")
public class L2Archery {

	public static final String MODID = "l2archery";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	private static void registerRegistrates(IEventBus bus) {
		ArcheryRegister.register();
		ArcheryItems.register();
		NetworkManager.register();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
	}

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(GenericEventHandler.class);

	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Archery::setup);
		bus.addListener(L2ArcheryClient::clientSetup);
		bus.addListener(EventPriority.LOWEST, L2Archery::gatherData);
		bus.addListener(L2Archery::onParticleRegistryEvent);
		bus.addListener(L2Archery::registerCaps);
	}


	public L2Archery() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> L2ArcheryClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}

	public static void gatherData(GatherDataEvent event) {
	}

	public static void onParticleRegistryEvent(ParticleFactoryRegisterEvent event) {
	}

	public static void registerCaps(RegisterCapabilitiesEvent event) {
	}

}
