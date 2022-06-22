package dev.xkmc.l2archery.content.explosion;

public class ExplosionHandler {

	public static void explode(BaseExplosion exp) {
		if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(exp.base.level(), exp)) return;
		exp.explode();
		exp.finalizeExplosion(true);
	}

}
