package dev.xkmc.l2archery.content.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.l2archery.init.data.ArcheryConfig;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ArrowDisplayOverlay implements IGuiOverlay {

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return;
		if (!ArcheryConfig.CLIENT.showArrow.get()) return;
		ItemStack bowStack = player.getMainHandItem();
		if (!(bowStack.getItem() instanceof BowItem)) return;
		ItemStack arrowStack = player.getProjectile(bowStack);
		gui.setupOverlayRenderState(true, false);
		int x = gui.screenWidth / 2 + 16;
		int y = gui.screenHeight / 2 - 8;
		Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(arrowStack, x, y);
		Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, arrowStack, x, y);
	}

}
