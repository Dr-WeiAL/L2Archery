package dev.xkmc.l2archery.content.enchantment;

import dev.xkmc.l2archery.content.feature.BowArrowFeature;
import dev.xkmc.l2archery.content.feature.core.PotionArrowFeature;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.init.L2LibReg;
import dev.xkmc.l2core.util.Proxy;

public class PotionArrowEnchantment extends BaseBowEnchantment {

	@Override
	public BowArrowFeature getFeature(int v) {
		var reg = Proxy.getRegistryAccess();
		if (reg != null) {
			var ans = ArcheryRegister.ENCH_STAT.get(reg, L2LibReg.ENCH.get().wrapAsHolder(this));
			if (ans != null) return ans.getEffects(v);
		}
		return PotionArrowFeature.NULL;
	}

}
