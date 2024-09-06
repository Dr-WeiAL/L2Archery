package dev.xkmc.l2archery.init;

import com.tterrag.registrate.providers.ProviderType;
import dev.xkmc.l2archery.compat.GolemCompat;
import dev.xkmc.l2archery.compat.JeedHelper;
import dev.xkmc.l2archery.content.config.BowArrowStatConfig;
import dev.xkmc.l2archery.events.ArrowAttackListener;
import dev.xkmc.l2archery.init.data.*;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryEnchantments;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2complements.init.data.TagGen;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.serial.config.ConfigTypeEntry;
import dev.xkmc.l2core.serial.config.PacketHandlerWithConfig;
import dev.xkmc.l2damagetracker.contents.attack.AttackEventHandler;
import net.mehvahdjukaar.jeed.Jeed;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(L2Archery.MODID)
@EventBusSubscriber(modid = L2Archery.MODID, bus = EventBusSubscriber.Bus.MOD)
public class L2Archery {

	public static final String MODID = "l2archery";

	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(MODID, 2);
	public static final ConfigTypeEntry<BowArrowStatConfig> STATS =
			new ConfigTypeEntry<>(HANDLER, "stats", BowArrowStatConfig.class);

	public L2Archery() {
		ArcheryRegister.register();
		ArcheryItems.register();
		ArcheryEffects.register();
		ArcheryEnchantments.register();
		ArcheryDamageMultiplex.register();
		AttackEventHandler.register(2000, new ArrowAttackListener());
		ArcheryConfig.init();
		if (ModList.get().isLoaded("modulargolems")) GolemCompat.register();
		if (ModList.get().isLoaded(Jeed.MOD_ID)) JeedHelper.register();
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);
		REGISTRATE.addDataGenerator(ProviderType.LANG, LangData::genLang);
		REGISTRATE.addDataGenerator(ProviderType.ADVANCEMENT, AdvGen::genAdvancements);
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, ArcheryTagGen::onEntityTagGen);
		REGISTRATE.addDataGenerator(TagGen.EFF_TAGS, ArcheryTagGen::onEffectTagGen);
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
		event.getGenerator().addProvider(gen, new ArcheryConfigGen(event.getGenerator()));
		new ArcheryDamageMultiplex(output, lookup, helper).generate(gen, event.getGenerator());
	}

	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event) {
	}

	public static ResourceLocation loc(String id) {
		return ResourceLocation.fromNamespaceAndPath(MODID, id);
	}

}
