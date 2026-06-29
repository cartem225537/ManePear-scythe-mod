package com.manepear.scythe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ScytheMod implements ModInitializer {
	public static final String MOD_ID = "scythemod";

	public static final RegistryKey<Item> SCYTHE_KEY =
			RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "scythe"));

	public static final Item SCYTHE = new ScytheItem(
			new Item.Settings()
					.registryKey(SCYTHE_KEY)
					.maxCount(1)
					.maxDamage(500)
					.rarity(Rarity.EPIC)
					.attributeModifiers(buildAttributes())
	);

	private static AttributeModifiersComponent buildAttributes() {
		return AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
						new EntityAttributeModifier(
								Identifier.ofVanilla("base_attack_damage"),
								9.0,
								EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED,
						new EntityAttributeModifier(
								Identifier.ofVanilla("base_attack_speed"),
								-2.4,
								EntityAttributeModifier.Operation.ADD_VALUE),
						AttributeModifierSlot.MAINHAND)
				.build();
	}

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, SCYTHE_KEY, SCYTHE);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> entries.add(SCYTHE));

		ScytheReapingHandler.register();
		ScytheLoot.register();
	}
}
