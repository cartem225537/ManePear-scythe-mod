package com.manepear.scythe;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MaceItem;
import net.minecraft.item.ItemStack;

public class ScytheItem extends MaceItem {

	public ScytheItem(Settings settings) {
		super(settings);
	}

	@Override
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHit(stack, target, attacker);
	}
}
