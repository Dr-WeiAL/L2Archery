package dev.xkmc.l2archery.content.upgrade;

import java.util.ArrayList;

public record BowUpgrade(int additional, ArrayList<Upgrade> list) {

	public static final BowUpgrade DEF = new BowUpgrade(0, new ArrayList<>());

	public BowUpgrade add(Upgrade upgrade) {
		var ans = new ArrayList<>(list);
		ans.add(upgrade);
		return new BowUpgrade(additional, ans);
	}

	public BowUpgrade clear() {
		return new BowUpgrade(additional, new ArrayList<>());
	}

}
