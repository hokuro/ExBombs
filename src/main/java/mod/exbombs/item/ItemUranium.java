/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ItemUranium extends Item {
	protected Random random;
	protected static boolean effect = true;

	public ItemUranium(Item.Properties property) {
		super(property);
		this.random = new Random();	}

	public static void setEffect(boolean hasEffect) {
		effect = hasEffect;
	}

	@Override
	public boolean hasEffect(ItemStack itemstack) {
		return effect;
	}

	@Override
	public void inventoryTick(ItemStack itemstack, World world, Entity entity, int ix, boolean flag) {
		if (world.isRemote) {
			return;
		}
		if (!(entity instanceof LivingEntity)) {
			return;
		}
		LivingEntity living = (LivingEntity)entity;
		living.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 1));
		living.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 100, 3));
		living.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 100, 3));
	}
}
