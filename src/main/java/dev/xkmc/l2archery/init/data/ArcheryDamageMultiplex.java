package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2library.init.L2Library;
import dev.xkmc.l2library.init.data.DamageTypeAndTagsGen;
import dev.xkmc.l2library.init.events.damage.DamageTypeRoot;
import dev.xkmc.l2library.init.events.damage.DamageTypeWrapper;
import dev.xkmc.l2library.init.events.damage.DamageWrapperTagProvider;
import dev.xkmc.l2library.init.events.damage.DefaultDamageState;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArcheryDamageMultiplex extends DamageTypeAndTagsGen {

	public static final DamageTypeRoot ARROW = new DamageTypeRoot(DamageTypes.ARROW,
			(type) -> new DamageType("mob", 0.1F));

	public static void register() {
		ARROW.add(DefaultDamageState.BYPASS_MAGIC);
		ARROW.add(DefaultDamageState.BYPASS_ARMOR);
		ARROW.add(ArcheryDamageState.BYPASS_INVUL);
		DamageTypeRoot.configureGeneration(Set.of(L2Archery.MODID), L2Archery.MODID, LIST);
		DamageTypeRoot.configureGeneration(Set.of(L2Library.MODID), L2Archery.MODID, LIST);
	}

	protected static final List<DamageTypeWrapper> LIST = new ArrayList<>();

	public ArcheryDamageMultiplex(PackOutput output,
								  CompletableFuture<HolderLookup.Provider> pvd,
								  ExistingFileHelper files) {
		super(output, pvd, files, L2Archery.MODID);
	}

	@Override
	protected void addDamageTypes(BootstapContext<DamageType> ctx) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			ctx.register(wrapper.type(), wrapper.getObject());
		}
	}

	@Override
	protected void addDamageTypeTags(DamageWrapperTagProvider pvd, HolderLookup.Provider lookup) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			wrapper.gen(pvd, lookup);
		}
	}

}
