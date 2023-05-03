package dev.xkmc.l2archery.events;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2library.init.events.attack.AttackListener;
import dev.xkmc.l2library.init.events.attack.CreateSourceEvent;

public class ArrowAttackListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getDirect() instanceof GenericArrowEntity gen) {
			gen.onHurtEntity(event);
		}
	}
}
