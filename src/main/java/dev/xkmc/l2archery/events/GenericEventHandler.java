package dev.xkmc.l2archery.events;

import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("unused")
public class GenericEventHandler {

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void fov(FOVModifierEvent event) {
		Player player = Proxy.getClientPlayer();
		if (player == null)
			return;
		if (player.getMainHandItem().getItem() instanceof GenericBowItem bow) {
			float f = event.getFov();
			float i = player.getTicksUsingItem();
			MobEffectInstance ins = player.getEffect(ArcheryRegister.QUICK_PULL.get());
			if (ins != null) {
				i *= 1.5 + 0.5 * ins.getAmplifier();
			}
			float p = bow.config.fov_time();
			event.setNewFov(f * (1 - Math.min(1, i / p) * bow.config.fov()));
		}
	}
}
