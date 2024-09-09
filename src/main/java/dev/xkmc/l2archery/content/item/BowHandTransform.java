package dev.xkmc.l2archery.content.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public record BowHandTransform() implements IClientItemExtensions {

	@Override
	public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack stack, float partialTick, float equipProcess, float swingProcess) {
		if (!(stack.getItem() instanceof GenericBowItem bow)) return false;
		int k = arm == HumanoidArm.RIGHT ? 1 : -1;
		poseStack.translate(k * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
		poseStack.translate((float) k * -0.2785682F, 0.18344387F, 0.15731531F);
		poseStack.mulPose(Axis.XP.rotationDegrees(-13.935F));
		poseStack.mulPose(Axis.YP.rotationDegrees((float) k * 35.3F));
		poseStack.mulPose(Axis.ZP.rotationDegrees((float) k * -9.785F));
		float pullTime = (float) stack.getUseDuration(player) - ((float) player.getUseItemRemainingTicks() - partialTick + 1.0F);
		float power = bow.getPowerForTime(player, pullTime);
		if (power > 1) {
			power = 1;
		}
		if (power > 0.1F) {
			float a = Mth.sin((pullTime - 0.1F) * 1.3F);
			float t = power - 0.1F;
			poseStack.translate(0, a * t * 0.004F, 0);
		}
		poseStack.translate(0, 0, power * 0.04F);
		poseStack.scale(1, 1, 1 + power * 0.2F);
		poseStack.mulPose(Axis.YN.rotationDegrees(k * 45));
		return true;
	}

}
