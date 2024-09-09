package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2damagetracker.contents.damage.DamageState;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.Locale;
import java.util.function.Consumer;

public enum ArcheryDamageState implements DamageState {
	BYPASS_INVUL(L2DamageTypes.BYPASS_INVUL.tags());

	private final TagKey<DamageType>[] tags;

	@SafeVarargs
	ArcheryDamageState(TagKey<DamageType>... tags) {
		this.tags = tags;
	}

	@Override
	public void gatherTags(Consumer<TagKey<DamageType>> collector) {
		for (var tag : tags) {
			collector.accept(tag);
		}
	}

	@Override
	public void removeTags(Consumer<TagKey<DamageType>> consumer) {

	}

	@Override
	public ResourceLocation getId() {
		return L2Archery.loc(name().toLowerCase(Locale.ROOT));
	}

	@Override
	public boolean overrides(DamageState state) {
		return true;
	}


}

