package dev.xkmc.l2archery.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2archery.compat.GolemCompat;
import dev.xkmc.l2archery.events.GenericEventHandler;
import dev.xkmc.l2archery.init.data.*;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
		ArcheryEffects.register();
		NetworkManager.register();
		ArcheryDamageMultiplex.register();
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, AdvGen::genAdvancements);
	}

	private static void registerForgeEvents() {
		ArcheryConfig.init();
		MinecraftForge.EVENT_BUS.register(GenericEventHandler.class);

	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(L2Archery::setup);
		bus.addListener(EventPriority.LOWEST, L2Archery::gatherData);
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
			ArcheryEffects.registerBrewingRecipe();
		});
	}

	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		var output = event.getGenerator().getPackOutput();
		var lookup = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		event.getGenerator().addProvider(gen, new ConfigGen(event.getGenerator()));
		new ArcheryDamageMultiplex(output, lookup, helper).generate(gen, event.getGenerator());
	}

	public static void registerCaps(RegisterCapabilitiesEvent event) {
	}

}
