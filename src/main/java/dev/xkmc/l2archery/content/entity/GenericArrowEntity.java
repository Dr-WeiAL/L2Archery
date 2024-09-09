package dev.xkmc.l2archery.content.entity;

import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.FlightControlFeature;
import dev.xkmc.l2archery.content.item.ArrowData;
import dev.xkmc.l2archery.content.item.BowData;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2core.util.ServerOnly;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.attack.DamageData;
import dev.xkmc.l2serial.serialization.codec.PacketCodec;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

@FieldsAreNonnullByDefault
public class GenericArrowEntity extends AbstractArrow implements IEntityWithComplexSpawn {

	public static final String TAG = "l2archery:rawShoot";

	public record ArrowEntityData(BowData bow, ArrowData arrow) {

		public static final ArrowEntityData DEFAULT = new ArrowEntityData(
				BowData.of(ArcheryItems.STARTER_BOW.get()),
				ArrowData.of(ArcheryItems.STARTER_ARROW.get()));

	}

	@ServerOnly
	public ArrowEntityData data = ArrowEntityData.DEFAULT;

	@ServerOnly
	public FeatureList features = new FeatureList();

	public GenericArrowEntity(EntityType<GenericArrowEntity> type, Level level) {
		super(type, level);
	}

	public GenericArrowEntity(Level level, LivingEntity user, ArrowEntityData data, FeatureList features, ItemStack bowStack) {
		super(ArcheryRegister.ET_ARROW.get(), user, level, data.arrow.stack(), bowStack);
		this.data = data;
		this.features = features;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level().isClientSide())
			features.hit().forEach(e -> e.onHitEntity(this, result.getEntity(), result));
		super.onHitEntity(result);
	}

	public void onHurtEntity(CreateSourceEvent ind) {
		if (!level().isClientSide())
			features.hit().forEach(e -> e.onHurtEntity(this, ind));
	}

	public void onHurtModification(DamageData.Offence cache) {
		if (!level().isClientSide())
			features.hit().forEach(e -> e.onHurtModifier(this, cache));
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return data.arrow().stack();
	}

	@Override
	public void doPostHurtEffects(LivingEntity target) {
		if (!level().isClientSide())
			features.hit().forEach(e -> e.postHurtEntity(this, target));
	}

	@ServerOnly
	@Override
	protected ItemStack getPickupItem() {
		return data.arrow.stack();
	}

	@Override
	public void tick() {
		Vec3 velocity = getDeltaMovement();
		super.tick();
		if (hasImpulse) velocity = getDeltaMovement();
		FlightControlFeature flight = features.flight();
		flight.tickMotion(this, velocity);
		if (flight.life > 0 && this.tickCount > flight.life) {
			this.discard();
		}
	}

	protected void tickDespawn() {
		++this.life;
		if (this.life >= features.flight().ground_life) {
			this.discard();
		}
	}

	@Override
	public void shoot(double vx, double vy, double vz, float v, float variation) {
		if (getTags().contains(TAG)) {
			removeTag(TAG);
			if (getOwner() instanceof Mob mob) {
				var target = mob.getTarget();
				if (target != null && target.isAlive()) {
					float speed = data.bow().getConfig().speed() / 3 * v;
					float gravity = features.flight().gravity;
					MobShootHelper.shootAimHelper(target, this, speed, gravity);
					return;
				}
			}
		}
		super.shoot(vx, vy, vz, v, variation);
	}

	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		features.hit().forEach(e -> e.onHitBlock(this, result));
	}

	@ServerOnly
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		Tag data_tag = new TagCodec(level().registryAccess()).valueToTag(ArrowEntityData.class, data);
		if (data_tag != null) {
			tag.put(L2Archery.MODID, data_tag);
		}
	}

	@ServerOnly
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains(L2Archery.MODID)) {
			CompoundTag data_tag = tag.getCompound(L2Archery.MODID);
			ArrowEntityData temp = new TagCodec(level().registryAccess()).valueFromTag(data_tag, ArrowEntityData.class);
			data = temp == null ? ArrowEntityData.DEFAULT : temp;
		}
		features = FeatureList.merge(data.bow.getFeatures(), data.arrow().getFeatures());
	}

	@Override
	public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
		PacketCodec.to(buffer, data);
		var owner = getOwner();
		buffer.writeInt(owner == null ? -1 : owner.getId());
		buffer.writeInt(getTags().size());
		for (var e : getTags()) {
			buffer.writeUtf(e);
		}
	}

	@Override
	public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
		ArrowEntityData temp = PacketCodec.from(additionalData, ArrowEntityData.class, null);
		data = temp == null ? ArrowEntityData.DEFAULT : temp;
		features = FeatureList.merge(data.bow.getFeatures(), data.arrow().getFeatures());
		features.shot().forEach(e -> e.onClientShoot(this));
		int id = additionalData.readInt();
		Entity owner = id == -1 ? null : level().getEntity(id);
		setOwner(owner);
		int size = additionalData.readInt();
		for (int i = 0; i < size; i++) {
			addTag(additionalData.readUtf());
		}
	}

}
