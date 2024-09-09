package dev.xkmc.l2archery.content.feature.types;

import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.item.GenericArrowItem;
import dev.xkmc.l2archery.content.item.IGeneralConfig;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Consumer;

public class DefaultShootFeature implements OnShootFeature {

	public static final DefaultShootFeature INSTANCE = new DefaultShootFeature();

	@Override
	public boolean onShoot(LivingEntity player, Consumer<Consumer<GenericArrowEntity>> consumer) {
		consumer.accept(entity -> {
			Item arr = entity.data.arrow().item();
			IGeneralConfig config = arr instanceof GenericArrowItem gen ? gen.getConfig() : null;
			double damage = entity.getBaseDamage() + entity.data.bow().getConfig().damage() + (config == null ? 0 : config.damage());
			entity.setBaseDamage(Math.max(damage, 0.5));
		});
		return true;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {

	}

}
