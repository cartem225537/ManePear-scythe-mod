package com.manepear.scythe;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetEnchantmentsLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ScytheLoot {

	private static final Identifier ANCIENT_CITY =
			Identifier.ofVanilla("chests/ancient_city");

	public static void register() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			if (!key.getValue().equals(ANCIENT_CITY)) {
				return;
			}

			RegistryEntry.Reference<Enchantment> reaping = registries
					.getOrThrow(RegistryKeys.ENCHANTMENT)
					.getOrThrow(RegistryKey.of(RegistryKeys.ENCHANTMENT,
							Identifier.of(ScytheMod.MOD_ID, "reaping")));

			LootPool lowPool = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1.0f))
					.conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.01f))
					.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
							.apply(new SetEnchantmentsLootFunction.Builder()
									.enchantment(reaping, ConstantLootNumberProvider.create(1.0f))))
					.build();

			LootPool midPool = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1.0f))
					.conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.01f))
					.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
							.apply(new SetEnchantmentsLootFunction.Builder()
									.enchantment(reaping, ConstantLootNumberProvider.create(2.0f))))
					.build();

			LootPool highPool = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1.0f))
					.conditionally(net.minecraft.loot.condition.RandomChanceLootCondition.builder(0.005f))
					.with(ItemEntry.builder(Items.ENCHANTED_BOOK)
							.apply(new SetEnchantmentsLootFunction.Builder()
									.enchantment(reaping, ConstantLootNumberProvider.create(3.0f))))
					.build();

			tableBuilder.pool(lowPool);
			tableBuilder.pool(midPool);
			tableBuilder.pool(highPool);
		});
	}
}
