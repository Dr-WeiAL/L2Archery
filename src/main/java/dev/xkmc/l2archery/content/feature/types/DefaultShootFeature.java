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
		consumer.accept(entity -> {});
		return true;
	}

	@Override
	public void addTooltip(List<MutableComponent> list) {

	}

}
