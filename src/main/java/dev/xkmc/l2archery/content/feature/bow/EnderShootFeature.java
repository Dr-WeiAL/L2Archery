package dev.xkmc.l2archery.content.feature.bow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnPullFeature;
import dev.xkmc.l2archery.content.feature.types.OnShootFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.util.code.GenericItemStack;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Consumer;

public record EnderShootFeature(int range) implements OnShootFeature, OnPullFeature, IGlowFeature {

	@Override
	public boolean onShoot(Player player, Consumer<Consumer<GenericArrowEntity>> consumer) {
		if (player == null)
			return false;
		Entity target = RayTraceUtil.serverGetTarget(player);
		if (target == null)
			return false;
		consumer.accept(entity -> entity.setPos(target.position().lerp(target.getEyePosition(), 0.5).add(entity.getDeltaMovement().scale(-1))));
		return true;
	}

	@Override
	public void onPull(Player player, GenericItemStack<GenericBowItem> bow) {

	}

	@Override
	public void tickAim(Player player, GenericItemStack<GenericBowItem> bow) {
		RayTraceUtil.clientUpdateTarget(player, range);
	}

	@Override
	public void stopAim(Player player, GenericItemStack<GenericBowItem> bow) {
		RayTraceUtil.TARGET.updateTarget(null);
	}


	@Override
	public void addTooltip(List<Component> list) {
		list.add(LangData.FEATURE_ENDER_SHOOT.get());
	}
}
