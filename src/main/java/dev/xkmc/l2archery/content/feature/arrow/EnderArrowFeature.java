package dev.xkmc.l2archery.content.feature.arrow;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.feature.types.OnHitFeature;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EnderArrowFeature implements OnHitFeature {

	@Override
	public void onHitEntity(GenericArrowEntity genericArrow, LivingEntity target) {
		Entity owner = genericArrow.getOwner();
		if (owner != null) {
			Vec3 pos = owner.getPosition(1);
			Vec3 tpos = target.getPosition(1);
			owner.teleportTo(tpos.x, tpos.y, tpos.z);
			target.teleportTo(pos.x, pos.y, pos.z);
		}
	}

	@Override
	public void onHitBlock(GenericArrowEntity genericArrow, BlockHitResult result) {
		Entity owner = genericArrow.getOwner();
		if (owner != null) {
			Vec3 pos = result.getLocation();
			owner.teleportTo(pos.x, pos.y, pos.z);
		}
		genericArrow.discard();
	}

	@Override
	public void postHurtEntity(GenericArrowEntity genericArrow, LivingEntity target) {

	}

	@Override
	public void addTooltip(List<Component> list) {

	}

}
