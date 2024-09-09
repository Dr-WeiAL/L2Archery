package dev.xkmc.l2archery.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2archery.content.feature.bow.InfinityFeature;
import dev.xkmc.l2archery.content.item.GenericBowItem;
import dev.xkmc.l2archery.init.data.ArcheryTagGen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ProjectileWeaponItem.class)
public class ProjectileWeaponItemMixin {

	@WrapOperation(method = "useAmmo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArrowItem;isInfinite(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Z"))
	private static boolean l2archery$isInfinite$vanillaAdvancedInfinity(ArrowItem instance, ItemStack ammo, ItemStack bow, LivingEntity livingEntity, Operation<Boolean> original) {
		if (bow.getItem() instanceof GenericBowItem bowItem &&
				InfinityFeature.getLevel(bowItem.getFeatures(bow)) >= 2 &&
				ammo.is(ArcheryTagGen.ADVANCED_INFINITE_ARROWS)) {
			return true;
		}
		return original.call(instance, ammo, bow, livingEntity);
	}

}
