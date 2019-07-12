/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package mod.exbombs.item;

import java.util.Random;

import mod.exbombs.core.Mod_ExBombs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemUranium extends Item {
	protected Random random;
	protected static boolean effect = true;

	public ItemUranium() {
		super(new Item.Properties()
				.group(Mod_ExBombs.tabExBombs));
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
		if (!(entity instanceof EntityLivingBase)) {
			return;
		}
		EntityLivingBase living = (EntityLivingBase)entity;
		living.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 100, 1));
		living.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 100, 3));
		living.addPotionEffect(new PotionEffect(Potion.getPotionById(18), 100, 3));
	}
}
