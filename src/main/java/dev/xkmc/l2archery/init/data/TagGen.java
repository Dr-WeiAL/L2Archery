package dev.xkmc.l2archery.init.data;

import dev.xkmc.l2archery.init.L2Archery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class TagGen {
	public static final String TAG_ENERGY = "Energy";
	public static final String ID_FORGE = "forge";
	public static final TagKey<Item> FORGE_BOWS = Tags.Items.TOOLS_BOWS;
	public static final TagKey<Item> FORGE_ARROWS = ItemTags.ARROWS;

	public static final TagKey<Item> PROF_BOWS = ItemTags.create(new ResourceLocation(L2Archery.MODID, "bows"));
	public static final TagKey<Item> PROF_ARROWS = ItemTags.create(new ResourceLocation(L2Archery.MODID, "arrows"));

}
