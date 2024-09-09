package dev.xkmc.l2archery.init.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.registrate.ArcheryEffects;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2complements.init.data.LCTagGen;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

public class ArcheryTagGen {

	public static final TagKey<Item> FORGE_BOWS = Tags.Items.TOOLS_BOW;
	public static final TagKey<Item> FORGE_ARROWS = ItemTags.ARROWS;

	public static final TagKey<Item> PROF_BOWS = ItemTags.create(L2Archery.loc("bows"));
	public static final TagKey<Item> PROF_ARROWS = ItemTags.create(L2Archery.loc("arrows"));

	public static void onEntityTagGen(RegistrateTagsProvider.IntrinsicImpl<EntityType<?>> pvd) {
		pvd.addTag(EntityTypeTags.ARROWS).add(ArcheryRegister.ET_ARROW.get());
	}

	public static void onEffectTagGen(RegistrateTagsProvider.IntrinsicImpl<MobEffect> pvd) {
		pvd.addTag(LCTagGen.SKILL_EFFECT).add(
				ArcheryEffects.QUICK_PULL.get(),
				ArcheryEffects.RUN_BOW.get());
	}

	public static void genItemTag(RegistrateItemTagsProvider pvd) {

	}
}
