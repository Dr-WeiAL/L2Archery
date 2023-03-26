package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.IGeneralConfig;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;
import java.util.function.Consumer;

public class DefaultShootFeature implements OnShootFeature {

	public static final DefaultShootFeature INSTANCE = new DefaultShootFeature();

	@Override
	public boolean onShoot(LivingEntity player, Consumer<Consumer<GenericArrowEntity>> consumer) {
		consumer.accept(entity -> {
			entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F,
					entity.data.power() * entity.data.bow().getConfig().speed(), 1.0F);
			if (entity.data.power() == 1.0F) {
				entity.setCritArrow(true);
			}
			var map = entity.data.bow().ench();
			int power = map.getOrDefault(Enchantments.POWER_ARROWS, 0);
			if (power > 0) {
				entity.setBaseDamage(entity.getBaseDamage() + (double) power * 0.5D + 0.5D);
			}
			int punch = map.getOrDefault(Enchantments.PUNCH_ARROWS, 0);
			if (punch > 0) {
				entity.setKnockback(punch);
			}
			if (map.getOrDefault(Enchantments.FLAMING_ARROWS, 0) > 0) {
				entity.setSecondsOnFire(100);
			}
			if (entity.data.no_consume()) {
				entity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			}
			Item arr = entity.data.arrow().item();
			IGeneralConfig config = arr instanceof GenericArrowItem gen ? gen.getConfig() : null;
			int knock = entity.getKnockback() + entity.data.bow().getConfig().punch() + (config == null ? 0 : config.punch());
			double damage = entity.getBaseDamage() + entity.data.bow().getConfig().damage() + (config == null ? 0 : config.damage());
			entity.setKnockback(Math.max(0, knock));
			entity.setBaseDamage(Math.max(damage, 0.5));
		});
		return true;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {

	}

}
