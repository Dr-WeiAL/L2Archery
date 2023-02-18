package dev.xkmc.l2archery.content.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.xkmc.l2archery.content.energy.IFluxItem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class BowFluxBarRenderer {

	public static boolean renderFluxBar(Font font, ItemStack stack, int x, int y, float blitOffset) {
		IFluxItem item = (IFluxItem) stack.getItem();
		if (item.getFluxFeature(stack) == null) return false;
		Tesselator t = Tesselator.getInstance();
		BufferBuilder builder = t.getBuilder();
		int w = Mth.ceil(13.0F * item.getEnergyStored(stack) / item.getMaxEnergyStored(stack));
		fillRect(builder, x + 2, y + 14, w, 1, 0, 255, 255, 255, 255);
		fillRect(builder, x + 2 + w, y + 14, 13 - w, 1, 0, 0, 0, 0, 255);
		return true;
	}

	/**
	 * Draw with the WorldRenderer
	 */
	private static void fillRect(BufferBuilder buffer, int x, int y, int w, int h, int z, int r, int g, int b, int a) {
		RenderSystem.disableDepthTest();
		RenderSystem.disableTexture();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		buffer.vertex(x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.vertex(x + w, y, z).color(r, g, b, a).endVertex();
		BufferUploader.drawWithShader(buffer.end());
	}


}