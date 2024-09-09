package dev.xkmc.l2archery.compat;

import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.modulargolems.events.event.GolemBowAttackEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;

public class GolemCompat {

	public static void register() {
		NeoForge.EVENT_BUS.register(GolemCompat.class);
	}

	@SubscribeEvent
	public static void onEquip(GolemBowAttackEvent event) {
		var golem = event.getEntity();
		if (event.getStack().getItem() instanceof GenericBowItem item) {
			/* TODO
			var opt = item.releaseUsingAndShootArrow(event.getStack(),
					golem.level(), golem, golem.getUseItemRemainingTicks());
			if (opt.isPresent()) {
				var arrow = opt.get();
				event.setArrow(arrow, arrow.pickup != AbstractArrow.Pickup.ALLOWED, true);
				if (arrow instanceof GenericArrowEntity entity) {
					event.setParams(entity.data.bow().getConfig().speed(), entity.features.flight().gravity);
				}
			}

			 */
		}
	}


}
