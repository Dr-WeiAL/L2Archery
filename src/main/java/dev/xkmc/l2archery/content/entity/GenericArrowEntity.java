package dev.xkmc.l2archery.content.entity;

import dev.xkmc.l2archery.content.feature.FeatureList;
import dev.xkmc.l2archery.content.feature.types.FlightControlFeature;
import dev.xkmc.l2archery.content.item.ArrowData;
import dev.xkmc.l2archery.content.item.BowData;
import dev.xkmc.l2archery.init.L2Archery;
import dev.xkmc.l2archery.init.registrate.ArcheryItems;
import dev.xkmc.l2archery.init.registrate.ArcheryRegister;
import dev.xkmc.l2library.init.events.attack.CreateSourceEvent;
import dev.xkmc.l2library.serial.codec.PacketCodec;
import dev.xkmc.l2library.serial.codec.TagCodec;
import dev.xkmc.l2library.util.annotation.ServerOnly;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

@FieldsAreNonnullByDefault
public class GenericArrowEntity extends AbstractArrow implements IEntityAdditionalSpawnData {

	public record ArrowEntityData(BowData bow, ArrowData arrow,
								  boolean no_consume, float power) {

		public static final ArrowEntityData DEFAULT = new ArrowEntityData(
				BowData.of(ArcheryItems.STARTER_BOW.get()),
				ArrowData.of(ArcheryItems.STARTER_ARROW.get()),
				false, 1);

	}

	@ServerOnly
	public ArrowEntityData data = ArrowEntityData.DEFAULT;

	@ServerOnly
	public FeatureList features = new FeatureList();

	public GenericArrowEntity(EntityType<GenericArrowEntity> type, Level level) {
		super(type, level);
	}

	public GenericArrowEntity(Level level, LivingEntity user, ArrowEntityData data, FeatureList features) {
		super(ArcheryRegister.ET_ARROW.get(), user, level);
		this.data = data;
		this.features = features;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level.isClientSide())
			features.hit().forEach(e -> e.onHitEntity(this, result.getEntity(), result));
		super.onHitEntity(result);
	}

	public void onHurtEntity(CreateSourceEvent ind) {
		if (!level.isClientSide())
			features.hit().forEach(e -> e.onHurtEntity(this, ind));
	}

	@Override
	public void doPostHurtEffects(LivingEntity target) {
		if (!level.isClientSide())
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

	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);
		features.hit().forEach(e -> e.onHitBlock(this, result));
	}

	@ServerOnly
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		Tag data_tag = TagCodec.valueToTag(data);
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
			ArrowEntityData temp = TagCodec.valueFromTag(data_tag, ArrowEntityData.class);
			data = temp == null ? ArrowEntityData.DEFAULT : temp;
		}
		features = FeatureList.merge(data.bow.getFeatures(), data.arrow().getFeatures());
	}

	@Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		PacketCodec.to(buffer, data);
	}

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		ArrowEntityData temp = PacketCodec.from(additionalData, ArrowEntityData.class, null);
		data = temp == null ? ArrowEntityData.DEFAULT : temp;
		features = FeatureList.merge(data.bow.getFeatures(), data.arrow().getFeatures());
		features.shot().forEach(e -> e.onClientShoot(this));
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
