package com.piraterevenge.PirateWars.Classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerClass {
	public String name;
	public List<ItemStack> kit = new ArrayList<ItemStack>();
	
	public PlayerClass(String name, List<ItemStack> kit)
	{
		this.name = name;
		this.kit = kit;
	}
	
	public List<ItemStack> getKit(int team)
	{
		LeatherArmorMeta meta = null;
		
		if(team == 1){
			for(int i = 0; i < kit.size(); i++){
				if(kit.get(i).getType() == Material.LEATHER_HELMET || kit.get(i).getType() == Material.LEATHER_CHESTPLATE || kit.get(i).getType() == Material.LEATHER_LEGGINGS || kit.get(i).getType() == Material.LEATHER_BOOTS){
					meta = (LeatherArmorMeta) kit.get(i).getItemMeta();
					meta.setColor(Color.RED);
					kit.get(i).setItemMeta(meta);
				}
			}
		}else if(team == 2){
			for(int i = 0; i < kit.size(); i++){
				if(kit.get(i).getType() == Material.LEATHER_HELMET || kit.get(i).getType() == Material.LEATHER_CHESTPLATE || kit.get(i).getType() == Material.LEATHER_LEGGINGS || kit.get(i).getType() == Material.LEATHER_BOOTS){
					meta = (LeatherArmorMeta) kit.get(i).getItemMeta();
					meta.setColor(Color.BLUE);
					kit.get(i).setItemMeta(meta);
				}
			}
		}
		
		return kit;
	}
	
	public String getName()
	{
		return name;
	}
}
