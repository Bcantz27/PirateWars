package com.piraterevenge.PirateWars.Classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

import com.shampaggon.crackshot.CSUtility;

import com.piraterevenge.PirateWars.Classes.PlayerClass;

public class ClassManager {
	
	private static List<ItemStack> engineerKit = new ArrayList<ItemStack>();
	private static List<ItemStack> demoKit = new ArrayList<ItemStack>();
	private static List<ItemStack> knightKit = new ArrayList<ItemStack>();
	private static List<ItemStack> musketeerKit = new ArrayList<ItemStack>();
	private static List<ItemStack> scoutKit = new ArrayList<ItemStack>();
	
	public ClassManager()
	{
		intKits();
	}
	
	public PlayerClass getClass(Type type)
	{
		return type.getClassType();
	}
	
	public static enum Type
	{

		Engineer{
			@Override
			public PlayerClass getClassType()
			{
				return new PlayerClass("Engineer",engineerKit);
			}
		},
		Demolition{
			@Override
			public PlayerClass getClassType()
			{
				return new PlayerClass("Demolition",demoKit);
			}
		},
		Knight{
			@Override
			public PlayerClass getClassType()
			{
				return new PlayerClass("Knight",knightKit);
			}
		},
		Musketeer{
			@Override
			public PlayerClass getClassType()
			{
				return new PlayerClass("Musketeer",musketeerKit);
			}
		},
		Scout{
			@Override
			public PlayerClass getClassType()
			{
				return new PlayerClass("Scout",scoutKit);
			}
		};

		public abstract PlayerClass getClassType();
	}
	
	public void intKits()
	{
		ItemStack tempItem = null;
		CSUtility csu = new CSUtility();
		Wool wool = new Wool(DyeColor.BLACK);

		tempItem = new ItemStack(Material.LEATHER_HELMET,1);
		tempItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);								//Leather Helmet
		knightKit.add(tempItem);							//Leather Helmet
		musketeerKit.add(tempItem);							//Leather Helmet
		engineerKit.add(tempItem);							//Leather Helmet
		scoutKit.add(tempItem);								//Leather Helmet
		tempItem = new ItemStack(Material.LEATHER_CHESTPLATE,1);
		tempItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);								//Leather Chest
		knightKit.add(tempItem);							//Leather Chest
		musketeerKit.add(tempItem);							//Leather Chest
		engineerKit.add(tempItem);							//Leather Chest
		scoutKit.add(tempItem);								//Leather Chest
		tempItem = new ItemStack(Material.LEATHER_LEGGINGS,1);
		tempItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);								//Leather Legs
		knightKit.add(tempItem);							//Leather Legs
		musketeerKit.add(tempItem);							//Leather Legs
		engineerKit.add(tempItem);							//Leather Legs
		scoutKit.add(tempItem);								//Leather Legs
		tempItem = new ItemStack(Material.LEATHER_BOOTS,1);
		tempItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);								//Leather Boots
		knightKit.add(tempItem);							//Leather Boots
		musketeerKit.add(tempItem);							//Leather Boots
		engineerKit.add(tempItem);							//Leather Boots
		scoutKit.add(tempItem);								//Leather Boots
		
		tempItem = new ItemStack(Material.DIAMOND_SWORD,1);
		tempItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		knightKit.add(tempItem);							//Sword
		tempItem = new ItemStack(Material.IRON_PICKAXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		knightKit.add(tempItem);							//Axe
		knightKit.add(new ItemStack(282,1)); 				//Soup
		knightKit.add(new ItemStack(282,1)); 				//Soup
		knightKit.add(new ItemStack(282,1)); 				//Soup
		knightKit.add(new ItemStack(4,32));				//Cobblestone
		knightKit.add(new ItemStack(17,12)); 				//Log
		knightKit.add(new ItemStack(323,3)); 			//Sign
		tempItem = wool.toItemStack();
		tempItem.setAmount(3);
		knightKit.add(tempItem);							//Black Wool
		knightKit.add(new ItemStack(289,128)); 			//Ammo
		
		musketeerKit.add(csu.generateWeapon("Musket"));
		musketeerKit.add(csu.generateWeapon("Pistol"));
		tempItem = new ItemStack(Material.DIAMOND_HOE,1);
		tempItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);
		tempItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		musketeerKit.add(tempItem);							//Axe
		tempItem = new ItemStack(Material.IRON_PICKAXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		musketeerKit.add(tempItem);							//Axe
		musketeerKit.add(new ItemStack(282,1)); 				//Soup
		musketeerKit.add(new ItemStack(282,1)); 				//Soup
		musketeerKit.add(new ItemStack(282,1)); 				//Soup
		musketeerKit.add(new ItemStack(4,32));				//Cobblestone
		musketeerKit.add(new ItemStack(17,12)); 				//Log
		musketeerKit.add(new ItemStack(323,3)); 			//Sign
		tempItem = wool.toItemStack();
		tempItem.setAmount(3);
		musketeerKit.add(tempItem);							//Black Wool
		musketeerKit.add(new ItemStack(289,64)); 			//Ammo
		musketeerKit.add(new ItemStack(289,64)); 			//Ammo
		
		demoKit.add(csu.generateWeapon("Pistol"));
		tempItem = new ItemStack(Material.DIAMOND_AXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		tempItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
		tempItem.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);								//Axe
		tempItem = new ItemStack(Material.IRON_PICKAXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		demoKit.add(tempItem);							//Axe
		tempItem = csu.generateWeapon("Grenade");
		tempItem.setAmount(16);
		demoKit.add(tempItem);
		tempItem = csu.generateWeapon("Moltov");
		tempItem.setAmount(16);
		demoKit.add(tempItem);
		demoKit.add(new ItemStack(282,1)); 				//Soup
		demoKit.add(new ItemStack(282,1)); 				//Soup
		demoKit.add(new ItemStack(282,1)); 				//Soup
		demoKit.add(new ItemStack(4,32));				//Cobblestone
		demoKit.add(new ItemStack(17,12)); 				//Log
		demoKit.add(new ItemStack(323,3)); 			//Sign
		tempItem = wool.toItemStack();
		tempItem.setAmount(3);
		demoKit.add(tempItem);							//Black Wool
		demoKit.add(new ItemStack(289,64)); 				//Ammo
		
		engineerKit.add(csu.generateWeapon("Pistol"));
		tempItem = new ItemStack(Material.DIAMOND_PICKAXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		tempItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		tempItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		engineerKit.add(tempItem);							//Axe
		engineerKit.add(new ItemStack(282,1)); 				//Soup
		engineerKit.add(new ItemStack(282,1)); 				//Soup
		engineerKit.add(new ItemStack(282,1)); 				//Soup
		engineerKit.add(new ItemStack(17,64)); 				//Log
		engineerKit.add(new ItemStack(4,32)); 				//Cobblestone
		engineerKit.add(new ItemStack(323,16)); 			//Sign
		tempItem = wool.toItemStack();
		tempItem.setAmount(32);
		engineerKit.add(tempItem);							//Black Wool
		engineerKit.add(new ItemStack(289,64)); 			//Ammo
		
		scoutKit.add(csu.generateWeapon("Pistol"));
		tempItem = new ItemStack(Material.IRON_SWORD,1);
		tempItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		tempItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		scoutKit.add(tempItem);								//Axe
		tempItem = new ItemStack(Material.IRON_PICKAXE,1);
		tempItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		scoutKit.add(tempItem);								//Axe
		scoutKit.add(new ItemStack(282,1)); 				//Soup
		scoutKit.add(new ItemStack(282,1)); 				//Soup
		scoutKit.add(new ItemStack(282,1)); 				//Soup
		tempItem = new ItemStack(373,5);
		tempItem.setData(new MaterialData(16421));
		scoutKit.add(new ItemStack(368,2));					//Enderpearl
		scoutKit.add(new ItemStack(4,32)); 					//Cobblestone
		scoutKit.add(new ItemStack(17,12)); 				//Log
		scoutKit.add(new ItemStack(323,3)); 			//Sign
		tempItem = wool.toItemStack();
		tempItem.setAmount(3);
		scoutKit.add(tempItem);							//Black Wool
		scoutKit.add(new ItemStack(289,64)); 			//Ammo

	}

}
