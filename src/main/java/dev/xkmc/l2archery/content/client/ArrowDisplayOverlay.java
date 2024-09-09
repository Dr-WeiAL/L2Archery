package dev.xkmc.l2archery.content.client;

import dev.xkmc.l2archery.init.data.ArcheryConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;

public class ArrowDisplayOverlay implements LayeredDraw.Layer {

	@Override
	public void render(GuiGraphics g, DeltaTracker deltaTracker) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player == null) return;
		if (!ArcheryConfig.CLIENT.showArrow.get()) return;
		ItemStack bowStack = player.getMainHandItem();
		if (!(bowStack.getItem() instanceof BowItem)) return;
		ItemStack arrowStack = player.getProjectile(bowStack);
		int x = g.guiWidth() / 2 + 16;
		int y = g.guiHeight() / 2 - 8;
		g.renderItem(arrowStack, x, y);
		g.renderItemDecorations(Minecraft.getInstance().font, arrowStack, x, y);
	}

}
