package dev.xkmc.l2archery.content.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.item.*;
import dev.xkmc.l2archery.init.data.ArcheryConfig;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.base.overlay.OverlayUtils;
import dev.xkmc.l2library.base.overlay.SideBar;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public class BowInfoOverlay extends SideBar implements IGuiOverlay {

	public BowInfoOverlay() {
		super(60, 5);
	}

	@Override
	public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int width, int height) {
		if (!ease(partialTick))
			return;

		LocalPlayer player = Proxy.getClientPlayer();
		ItemStack bowStack = player.getMainHandItem();
		ItemStack arrowStack = player.getProjectile(bowStack);
		if (!(bowStack.getItem() instanceof GenericBowItem bow))
			return;
		ArrowData arrowData = bow.parseArrow(arrowStack);
		if (arrowData == null)
			return;

		gui.setupOverlayRenderState(true, false);
		OverlayUtils util = new OverlayUtils(width, height);
		BowData bowData = BowData.of(bow, bowStack);
		FeatureList features = FeatureList.merge(bowData.getFeatures(), arrowData.getFeatures());
		List<Component> text = new ArrayList<>();
		addStat(text, bowData, arrowData.getItem().getConfig());
		features.addEffectsTooltip(text);
		features.addTooltip(text);
		util.renderLongText(gui, poseStack, text);
	}

	private static void addStat(List<Component> list, BowData data, IGeneralConfig arrow) {
		IBowConfig bow = data.getConfig();
		double dmg = 2;
		var map = data.ench();
		int power = map.getOrDefault(Enchantments.POWER_ARROWS, 0);
		if (power > 0) {
			dmg += power * 0.5D + 0.5D;
		}
		int punch = map.getOrDefault(Enchantments.PUNCH_ARROWS, 0);
		dmg += bow.damage() + arrow.damage();
		dmg *= bow.speed();
		dmg = Math.ceil(dmg);
		String result = ATTRIBUTE_MODIFIER_FORMAT.format(dmg) + "~" + ATTRIBUTE_MODIFIER_FORMAT.format(dmg + dmg / 2 + 2);
		list.add(LangData.STAT_DAMAGE.getWithColor(result, ChatFormatting.GREEN));
		list.add(LangData.STAT_PUNCH.getWithColor(punch + bow.punch() + arrow.punch(), ChatFormatting.GREEN));
		list.add(LangData.STAT_PULL_TIME.getWithColor(bow.pull_time() / 20d, ChatFormatting.GREEN));
		list.add(LangData.STAT_SPEED.getWithColor(bow.speed() * 20, ChatFormatting.GREEN));
		list.add(LangData.STAT_FOV.getWithColor(ATTRIBUTE_MODIFIER_FORMAT.format(1 / (1 - bow.fov())), ChatFormatting.GREEN));
	}

	@Override
	public int getSignature() {
		LocalPlayer player = Proxy.getClientPlayer();
		ItemStack bowStack = player.getMainHandItem();
		ItemStack arrowStack = player.getProjectile(bowStack);
		return player.getInventory().selected + arrowStack.getItem().hashCode();
	}

	@Override
	public boolean isScreenOn() {
		LocalPlayer player = Proxy.getClientPlayer();
		if (player == null) return false;
		ItemStack bowStack = player.getMainHandItem();
		if (!(bowStack.getItem() instanceof BowItem)) return false;
		ItemStack arrowStack = player.getProjectile(bowStack);
		if (!ArcheryConfig.CLIENT.showInfo.get()) return false;
		if (!(bowStack.getItem() instanceof GenericBowItem bow)) return false;
		ArrowData arrowData = bow.parseArrow(arrowStack);
		return arrowData != null;
	}

	@Override
	protected int getXOffset(int width) {
		float progress = (max_ease - ease_time) / max_ease;
		return -Math.round(progress * width / 2);
	}
}
