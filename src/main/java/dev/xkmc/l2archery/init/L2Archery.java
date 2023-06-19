package dev.xkmc.l2archery.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2archery.compat.GolemCompat;
import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.events.ArrowAttackListener;
import dev.xkmc.l2archery.init.data.*;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.ConfigTypeEntry;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Archery.MODID)
@Mod.EventBusSubscriber(modid = L2Archery.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class L2Archery {

	public static final String MODID = "l2archery";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			new ResourceLocation(L2Archery.MODID, "main"), 1
	);
	public static final ConfigTypeEntry<BowArrowStatConfig> STATS =
			new ConfigTypeEntry<>(HANDLER, "stats", BowArrowStatConfig.class);


	private static void registerRegistrates(IEventBus bus) {
		ArcheryRegister.register();
		ArcheryItems.register();
		ArcheryEffects.register();
		ArcheryDamageMultiplex.register();
		AttackEventHandler.register(2000, new ArrowAttackListener());
		ArcheryConfig.init();
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, AdvGen::genAdvancements);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, TagGen::onEntityTagGen);
	}

	private static void registerForgeEvents() {

	}

	public L2Archery() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerRegistrates(bus);
		registerForgeEvents();
	}

	@SubscribeEvent
	public static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ArcheryEffects.registerBrewingRecipe();
		});
	}

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		boolean gen = event.includeServer();
		var output = event.getGenerator().getPackOutput();
		var lookup = event.getLookupProvider();
		var helper = event.getExistingFileHelper();
		event.getGenerator().addProvider(gen, new ConfigGen(event.getGenerator()));
		new ArcheryDamageMultiplex(output, lookup, helper).generate(gen, event.getGenerator());
	}

	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event) {
	}

}
