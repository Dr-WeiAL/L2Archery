package dev.xkmc.l2archery.init.data;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeRoot;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeWrapper;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2damagetracker.init.data.DamageTypeAndTagsGen;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArcheryDamageMultiplex extends DamageTypeAndTagsGen {

	public static final DamageTypeRoot ARROW = new DamageTypeRoot(L2Archery.MODID, DamageTypes.ARROW,
			List.of(DamageTypeTags.IS_PROJECTILE), (type) -> new DamageType("arrow", 0.1F));

	public static void register() {
		ARROW.add(DefaultDamageState.BYPASS_MAGIC);
		ARROW.add(DefaultDamageState.BYPASS_ARMOR);
		ARROW.add(ArcheryDamageState.BYPASS_INVUL);
		DamageTypeRoot.configureGeneration(Set.of(L2DamageTracker.MODID, L2Archery.MODID), L2Archery.MODID, LIST);
	}

	protected static final List<DamageTypeWrapper> LIST = new ArrayList<>();

	public ArcheryDamageMultiplex() {
		super(L2Archery.REGISTRATE);
	}

	@Override
	protected void addDamageTypes(BootstrapContext<DamageType> ctx) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			ctx.register(wrapper.type(), wrapper.getObject());
		}
	}

	@Override
	protected void addDamageTypeTags(RegistrateTagsProvider.Impl<DamageType> pvd) {
		DamageTypeRoot.generateAll();
		for (DamageTypeWrapper wrapper : LIST) {
			wrapper.gen(pvd::addTag);
		}
	}

}
