package dev.xkmc.l2archery.content.energy;

import dev.xkmc.l2archery.content.feature.bow.FluxFeature;
import dev.xkmc.l2archery.init.data.LangData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;


public interface IFluxItem extends IEnergyContainerItem, IForgeItem {

	default Capability<? extends IEnergyStorage> getEnergyCapability() {
		return ForgeCapabilities.ENERGY;
	}

	@Nullable
	FluxFeature getFluxFeature(ItemStack stack);

	int getStorageRank(ItemStack stack);

	int getConsumptionRank(ItemStack stack);

	// region IEnergyContainerItem
	@Override
	default int getExtract(ItemStack container) {
		FluxFeature fluxFeature = getFluxFeature(container);
		if (fluxFeature == null) return 0;
		return fluxFeature.extract();
	}

	@Override
	default int getReceive(ItemStack container) {
		FluxFeature fluxFeature = getFluxFeature(container);
		if (fluxFeature == null) return 0;
		return fluxFeature.receive();
	}

	@Override
	default int getMaxEnergyStored(ItemStack container) {
		FluxFeature fluxFeature = getFluxFeature(container);
		if (fluxFeature == null) return 0;
		return (int) (Math.pow(2, getStorageRank(container)) * fluxFeature.maxEnergy());
	}

	default int getEnergyPerUse(ItemStack container) {
		FluxFeature fluxFeature = getFluxFeature(container);
		if (fluxFeature == null) return 0;
		return (int) (Math.pow(2, getConsumptionRank(container)) * fluxFeature.perUsed());
	}

	default boolean hasEnergy(ItemStack stack, int amount) {
		return getEnergyStored(stack) >= amount;
	}

	default boolean useEnergy(ItemStack stack, int amount, boolean simulate) {
		if (simulate) {
			return hasEnergy(stack, amount);
		}
		if (hasEnergy(stack, amount)) {
			extractEnergy(stack, amount, false);
			return true;
		}
		return false;
	}

	default boolean useEnergy(ItemStack stack, int amount, Entity entity) {
		if (entity instanceof Player player && player.isCreative()) return true;
		return useEnergy(stack, amount, false);
	}

	default <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		if (useEnergy(stack, amount * getEnergyPerUse(stack), entity)) return 0;
		return amount;
	}

	@Override
	default ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new EnergyContainerItemWrapper(stack, this, getEnergyCapability());
	}

	default void tooltipDelegate(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		if (getFluxFeature(stack) == null) return;
		tooltip.add(LangData.ENERGY_STORED.get(getScaledNumber(getEnergyStored(stack)), getScaledNumber(getMaxEnergyStored(stack))));
	}

	static String getScaledNumber(long number) {
		if (number >= 1_000_000_000) {
			return number / 1_000_000_000 + "." + (number % 1_000_000_000 / 100_000_000) + (number % 100_000_000 / 10_000_000) + "G";
		} else if (number >= 1_000_000) {
			return number / 1_000_000 + "." + (number % 1_000_000 / 100_000) + (number % 100_000 / 10_000) + "M";
		} else if (number >= 1000) {
			return number / 1000 + "." + (number % 1000 / 100) + (number % 100 / 10) + "k";
		} else {
			return String.valueOf(number);
		}
	}

}
