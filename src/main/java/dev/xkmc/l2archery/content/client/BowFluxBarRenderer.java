package dev.xkmc.l2archery.content.client;

import dev.xkmc.l2archery.content.energy.IFluxItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;

import static dev.xkmc.l2library.base.overlay.ItemSelSideBar.color;

public class BowFluxBarRenderer implements IItemDecorator {

	public boolean render(GuiGraphics g, Font font, ItemStack stack, int x, int y) {
		IFluxItem item = (IFluxItem) stack.getItem();
		if (item.getFluxFeature(stack) == null) return false;
		int w = Mth.ceil(13.0F * item.getEnergyStored(stack) / item.getMaxEnergyStored(stack));
		g.fill(x + 2, y + 14, x + 2 + w, y + 14+1, color(255, 255, 255, 255));
		g.fill(x + 2 + w, y + 14, x + 2 + w+13 - w, y + 14+1, color(0, 0, 0, 255));
		return true;
	}

}