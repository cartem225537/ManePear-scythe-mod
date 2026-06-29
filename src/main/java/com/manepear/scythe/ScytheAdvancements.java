package com.manepear.scythe;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class ScytheAdvancements {

	public static final Identifier VAMPIRE_ID = Identifier.of(ScytheMod.MOD_ID, "vampire");

	public static void grantVampire(net.minecraft.entity.player.PlayerEntity player) {
		if (!(player instanceof ServerPlayerEntity serverPlayer)) {
			return;
		}
		MinecraftServer server = serverPlayer.getServer();
		if (server == null) {
			return;
		}
		AdvancementEntry entry = server.getAdvancementLoader().get(VAMPIRE_ID);
		if (entry == null) {
			return;
		}
		AdvancementProgress progress = serverPlayer.getAdvancementTracker().getProgress(entry);
		if (progress.isDone()) {
			return;
		}
		for (String criterion : progress.getUnobtainedCriteria()) {
			serverPlayer.getAdvancementTracker().grantCriterion(entry, criterion);
		}
	}
}
