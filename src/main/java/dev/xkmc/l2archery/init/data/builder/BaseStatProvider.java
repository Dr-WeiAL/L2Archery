package dev.xkmc.l2archery.init.data.builder;

import net.neoforged.neoforge.common.data.DataMapProvider;

public interface BaseStatProvider<I, R> {

	DataMapProvider.Builder<R, I> builder();

}
