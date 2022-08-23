package dev.xkmc.l2archery.compat;

import dev.xkmc.l2archery.content.upgrade.UpgradeItem;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2library.base.NamedEntry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

@JeiPlugin
public class ArcheryJEIPlugin implements IModPlugin {

	public static final ResourceLocation ID = new ResourceLocation(L2Archery.MODID, "main");

	public static final ResourceLocation NONE = new ResourceLocation(L2Archery.MODID, "empty");


	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(ArcheryItems.UPGRADE.get(), (stack, ctx) ->
				Optional.ofNullable(UpgradeItem.getUpgrade(stack)).map(NamedEntry::getRegistryName).orElse(NONE).toString());
	}
}
