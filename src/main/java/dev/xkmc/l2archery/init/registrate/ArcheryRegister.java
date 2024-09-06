package dev.xkmc.l2archery.init.registrate;

import com.tterrag.registrate.util.entry.EntityEntry;
import dev.xkmc.l2archery.content.crafting.BowRecipe;
import dev.xkmc.l2archery.content.entity.GenericArrowEntity;
import dev.xkmc.l2archery.content.entity.GenericArrowRenderer;
import dev.xkmc.l2archery.content.stats.BowArrowStatType;
import dev.xkmc.l2archery.content.stats.StatType;
import dev.xkmc.l2archery.content.upgrade.Upgrade;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2complements.init.L2Complements;
import dev.xkmc.l2core.init.reg.registrate.L2Registrate;
import dev.xkmc.l2core.init.reg.registrate.SimpleEntry;
import dev.xkmc.l2core.init.reg.simple.SR;
import dev.xkmc.l2core.init.reg.simple.Val;
import dev.xkmc.l2core.serial.recipe.AbstractShapedRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ArcheryRegister {

	public static final L2Registrate.RegistryInstance<BowArrowStatType> STAT_TYPE = L2Archery.REGISTRATE
			.newRegistry("stat_type", BowArrowStatType.class);
	public static final L2Registrate.RegistryInstance<Upgrade> UPGRADE = L2Archery.REGISTRATE
			.newRegistry("upgrade", Upgrade.class);

	public static final SimpleEntry<BowArrowStatType> DAMAGE = regStat("damage", StatType.COMMON, 0);
	public static final SimpleEntry<BowArrowStatType> PUNCH = regStat("punch", StatType.COMMON, 0);
	public static final SimpleEntry<BowArrowStatType> SPEED = regStat("speed", StatType.BOW, 3);
	public static final SimpleEntry<BowArrowStatType> PULL_TIME = regStat("pull_time", StatType.BOW, 20);
	public static final SimpleEntry<BowArrowStatType> FOV_TIME = regStat("fov_time", StatType.BOW, 20);
	public static final SimpleEntry<BowArrowStatType> FOV = regStat("max_fov", StatType.BOW, 0.15);

	public static final EntityEntry<GenericArrowEntity> ET_ARROW = L2Archery.REGISTRATE
			.<GenericArrowEntity>entity("generic_arrow", GenericArrowEntity::new, MobCategory.MISC)
			.properties(e -> e.sized(0.5F, 0.5F)
					.clientTrackingRange(4).updateInterval(20)
					.setShouldReceiveVelocityUpdates(true))
			.renderer(() -> GenericArrowRenderer::new)
			.defaultLang().register();

	private static final SR<RecipeSerializer<?>> RS = SR.of(L2Complements.REG, BuiltInRegistries.RECIPE_SERIALIZER);

	public static final Val<AbstractShapedRecipe.Serializer<BowRecipe>> BOW_RECIPE =
			RS.reg("bow_craft", () -> new AbstractShapedRecipe.Serializer<>(BowRecipe::new));

	public static SimpleEntry<BowArrowStatType> regStat(String id, StatType type, double def) {
		return new SimpleEntry<>(L2Archery.REGISTRATE.generic(STAT_TYPE, id, () -> new BowArrowStatType(type, def)).defaultLang().register());
	}

	public static void register() {
	}

}
