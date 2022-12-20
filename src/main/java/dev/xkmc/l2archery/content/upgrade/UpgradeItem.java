package dev.xkmc.l2archery.content.upgrade;

import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UpgradeItem extends Item {

	private static final String KEY = "upgrade";

	@Nullable
	public static Upgrade getUpgrade(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains(KEY)) return null;
		return ArcheryRegister.UPGRADE.get().getValue(new ResourceLocation(tag.getString(KEY)));
	}

	public static ItemStack setUpgrade(ItemStack stack, Upgrade upgrade) {
		stack.getOrCreateTag().putString(KEY, upgrade.getID());
		return stack;
	}

	public UpgradeItem(Properties props) {
		super(props);
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
		if (this.allowedIn(tab)) {
			list.add(new ItemStack(this));
			for (Upgrade upgrade : ArcheryRegister.UPGRADE.get().getValues())
				list.add(setUpgrade(new ItemStack(this), upgrade));
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return getUpgrade(stack) != null;
	}

	@Override
	public Component getName(ItemStack stack) {
		Upgrade upgrade = getUpgrade(stack);
		if (upgrade != null)
			return Component.translatable(upgrade.getDescriptionId());
		return super.getName(stack);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
		Upgrade upgrade = getUpgrade(stack);
		if (upgrade != null) {
			if (upgrade.getFeature() instanceof PotionArrowFeature arr) {
				PotionArrowFeature.addTooltip(arr.instances(), list);
			}
			List<MutableComponent> temp = new ArrayList<>();
			upgrade.getFeature().addTooltip(temp);
			for (MutableComponent c : temp) {
				if (c.getStyle().getColor() == null)
					c.withStyle(ChatFormatting.GOLD);
				list.add(c);
			}
		}
	}
}
