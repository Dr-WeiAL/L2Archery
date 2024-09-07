package dev.xkmc.l2archery.content.energy;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import static dev.xkmc.l2archery.init.data.ArcheryTagGen.TAG_ENERGY;
import static net.minecraft.util.Mth.clamp;


/**
 * Implement this interface on Item classes that support external manipulation of their internal energy storage.
 * <p>
 * NOTE: Use of NBT data on the containing ItemStack is encouraged.
 *
 * @author King Lemming
 */
public interface IEnergyContainerItem {

	ItemCapability<IEnergyStorage, @Nullable Void> getEnergyCapability();

	int getExtract(ItemStack container);

	int getReceive(ItemStack container);

	/**
	 * Get the max amount of energy that can be stored in the container item.
	 */
	int getMaxEnergyStored(ItemStack container);

	default CompoundTag getOrCreateEnergyTag(ItemStack container) {
		return container.getOrCreateTag();
	}

	default int getSpace(ItemStack container) {
		return getMaxEnergyStored(container) - getEnergyStored(container);
	}

	static int round(double d) {
		return (int) (d + 0.5D);
	}

	default int getScaledEnergyStored(ItemStack container, int scale) {
		return round((double) getEnergyStored(container) * scale / getMaxEnergyStored(container));
	}

	/**
	 * Get the amount of energy currently stored in the container item.
	 */
	default int getEnergyStored(ItemStack container) {
		CompoundTag tag = getOrCreateEnergyTag(container);
		return Math.min(tag.getInt(TAG_ENERGY), getMaxEnergyStored(container));
	}

	default void setEnergyStored(ItemStack container, int energy) {
		CompoundTag tag = getOrCreateEnergyTag(container);
		tag.putInt(TAG_ENERGY, clamp(energy, 0, getMaxEnergyStored(container)));
	}

	/**
	 * Adds energy to a container item. Returns the quantity of energy that was accepted. This should always return 0
	 * if the item cannot be externally charged.
	 *
	 * @param container  ItemStack to be charged.
	 * @param maxReceive Maximum amount of energy to be sent into the item.
	 * @param simulate   If TRUE, the charge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) received by the item.
	 */
	default int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		CompoundTag tag = getOrCreateEnergyTag(container);
		int stored = Math.min(tag.getInt(TAG_ENERGY), getMaxEnergyStored(container));
		int receive = Math.min(Math.min(maxReceive, getReceive(container)), getSpace(container));

		if (!simulate) {
			stored += receive;
			tag.putInt(TAG_ENERGY, stored);
		}
		return receive;
	}

	/**
	 * Removes energy from a container item. Returns the quantity of energy that was removed. This should always
	 * return 0 if the item cannot be externally discharged.
	 *
	 * @param container  ItemStack to be discharged.
	 * @param maxExtract Maximum amount of energy to be extracted from the item.
	 * @param simulate   If TRUE, the discharge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) extracted from the item.
	 */
	default int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		CompoundTag tag = getOrCreateEnergyTag(container);
		int stored = Math.min(tag.getInt(TAG_ENERGY), getMaxEnergyStored(container));
		int extract = Math.min(Math.min(maxExtract, getExtract(container)), stored);

		if (!simulate) {
			stored -= extract;
			tag.putInt(TAG_ENERGY, stored);
		}
		return extract;
	}

}

