package dev.xkmc.l2archery.content.client;

import dev.xkmc.l2archery.init.data.ArcheryConfig;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ArrowDisplayOverlay implements LayeredDraw.Layer {

	@Override
	public void render(GuiGraphics g, DeltaTracker deltaTracker) {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return;
		if (!ArcheryConfig.CLIENT.showArrow.get()) return;
		ItemStack bowStack = player.getMainHandItem();
		if (!(bowStack.getItem() instanceof BowItem)) return;
		ItemStack arrowStack = player.getProjectile(bowStack);
		gui.setupOverlayRenderState(true, false);
		int x = gui.screenWidth / 2 + 16;
		int y = gui.screenHeight / 2 - 8;
		g.renderItem(arrowStack, x, y);
		g.renderItemDecorations(gui.getFont(), arrowStack, x, y);
	}

}
