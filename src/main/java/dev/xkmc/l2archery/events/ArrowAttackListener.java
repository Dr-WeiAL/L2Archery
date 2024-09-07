package dev.xkmc.l2archery.events;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;

public class ArrowAttackListener implements AttackListener {

	@Override
	public void onCreateSource(CreateSourceEvent event) {
		if (event.getDirect() instanceof GenericArrowEntity gen) {
			gen.onHurtEntity(event);
		}
	}

	@Override
	public void onHurt(DamageData.Offence cache) {
		if (cache.getSource().getDirectEntity() instanceof GenericArrowEntity gen) {
			gen.onHurtModification(cache);
		}
	}

}
