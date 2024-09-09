package dev.xkmc.l2archery.content.upgrade;

import com.tterrag.registrate.util.CreativeModeTabModifier;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.core.StatFeature;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpgradeItem extends Item {

	private static final String KEY = "upgrade";

	@Nullable
	public static Upgrade getUpgrade(ItemStack stack) {
		return ArcheryItems.ITEM_UPGRADE.get(stack);
	}

	public static ItemStack setUpgrade(ItemStack stack, Upgrade upgrade) {
		return ArcheryItems.ITEM_UPGRADE.set(stack, upgrade);
	}

	public UpgradeItem(Properties props) {
		super(props);
	}

	public void fillItemCategory(CreativeModeTabModifier tab) {
		tab.accept(new ItemStack(this));
		for (Upgrade upgrade : ArcheryRegister.UPGRADE.get().stream().toList())
			tab.accept(setUpgrade(new ItemStack(this), upgrade));
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
	public void appendHoverText(ItemStack stack, TooltipContext level, List<Component> list, TooltipFlag flag) {
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
			if (upgrade.getFeature() instanceof StatFeature stat) {
				if (!stat.addStatHolder(new HashSet<>(Set.of(StatHolder.DAMAGE)))) {
					list.add(LangData.DAMAGE_UPGRADE.get());
				}
			}
		}
	}
}
