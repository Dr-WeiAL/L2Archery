package dev.xkmc.l2archery.content.feature.bow;

import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.content.feature.types.OnPullFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.data.LangData;
import dev.xkmc.l2library.base.effects.EffectUtil;
import dev.xkmc.l2library.util.code.GenericItemStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Supplier;

public record PullEffectFeature(List<Supplier<MobEffectInstance>> effects) implements OnPullFeature {

	@Override
	public void onPull(Player player, GenericItemStack<GenericBowItem> bow) {
		if (player instanceof ServerPlayer) {
			for (var eff : effects) {
				EffectUtil.addEffect(player, eff.get(), EffectUtil.AddReason.SELF, player);
			}
		}
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {
		list.add(LangData.FEATURE_PULL_EFFECT.get());
		for (var eff : effects) {
			list.add(PotionArrowFeature.getTooltip(eff.get()));
		}
	}

}
