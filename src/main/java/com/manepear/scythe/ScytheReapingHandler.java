package com.manepear.scythe;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class ScytheReapingHandler {

	public static final RegistryKey<Enchantment> REAPING_KEY =
			RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ScytheMod.MOD_ID, "reaping"));

	public static void register() {
		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamage, damageTaken, blocked) -> {
			handleReaping(entity, source);
		});
	}

	private static void handleReaping(LivingEntity victim, DamageSource source) {
		if (!(victim instanceof PlayerEntity)) {
			return;
		}
		if (!(source.getAttacker() instanceof PlayerEntity attacker)) {
			return;
		}
		if (!(attacker.getWorld() instanceof ServerWorld serverWorld)) {
			return;
		}

		ItemStack weapon = attacker.getMainHandStack();
		if (!(weapon.getItem() instanceof ScytheItem)) {
			return;
		}

		RegistryEntry<Enchantment> reaping = serverWorld.getRegistryManager()
				.getOrThrow(RegistryKeys.ENCHANTMENT)
				.getEntry(REAPING_KEY)
				.orElse(null);
		if (reaping == null) {
			return;
		}

		int level = net.minecraft.enchantment.EnchantmentHelper.getLevel(reaping, weapon);
		if (level <= 0) {
			return;
		}

		float stolen = level;
		attacker.heal(stolen);

		ScytheAdvancements.grantVampire(attacker);
	}
}
