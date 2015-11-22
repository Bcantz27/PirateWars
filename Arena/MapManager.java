package com.piraterevenge.PirateWars.Arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MapManager {
	public static MapManager instance = new MapManager();
	
	private static List<Map> mapList = new ArrayList<Map>();
	private static List<String> avaliableMaps = new ArrayList<String>();
	private static Location endSpawnPoint;
	
	public MapManager()
	{
		instance = this;
	}
	
    public static MapManager getInstance()
    {
    	return instance;
    }
    
    public void addMap(Map m){
    	mapList.add(m);
    	avaliableMaps.add(m.getName());
    	System.out.println("Adding map" + avaliableMaps);
    }
    
    public List<Map> getMapList()
    {
    	return mapList;
    }
    
    public List<String> getAvaliableMapList()
    {
    	return avaliableMaps;
    }
    
	public Location getEndSpawnPoint(){
		return endSpawnPoint;
	}
	
	public void setEndSpawnPoint(Location loc){
		endSpawnPoint = loc;
	}
	
	public boolean isCoreLoc(Location loc){
		boolean flag = false;
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		if(mapList.get(i).getRedCoreLoc().equals(loc))
    		{
    			flag = true;
    		}
    		else if(mapList.get(i).getBlueCoreLoc().equals(loc))
    		{
    			flag = true;
    		}
    	}
    	
    	return flag;
	}
	public boolean isOutterCoreLoc(Location loc){
		boolean flag = false;
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		if(mapList.get(i).getRedOutterCoreLoc().equals(loc))
    		{
    			flag = true;
    		}
    		else if(mapList.get(i).getBlueOutterCoreLoc().equals(loc))
    		{
    			flag = true;
    		}
    	}
    	
    	return flag;
	}
	
	public boolean isNearCore(Location loc){
		boolean flag = false;
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		System.out.println(mapList.get(i).getBlueOutterCoreLoc().distance(loc));
    		if(mapList.get(i).getRedOutterCoreLoc().distance(loc) < 4){
    			flag = true;
    		}
    		else if(mapList.get(i).getBlueOutterCoreLoc().distance(loc) < 4)
    		{
    			flag = true;
    		}
    	}
		return flag;
	}
    
	public Map getRandomMap()
	{
		Map map = null;
		Random rand = new Random();
		
		if(avaliableMaps.size() > 0)
		{
			map = getMap(avaliableMaps.get(rand.nextInt(((avaliableMaps.size()-1) - 0) + 1) + 0));
			avaliableMaps.remove(map.getName());
		}
		
		return map;
	}
    
    public Map getMap(String name)
    {
    	int index = 0;
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		if(mapList.get(i).getName().equals(name))
    		{
    			index = i;
    		}
    	}
    	
		if(mapList.get(index).getName().equals(name))
		{
			return mapList.get(index);
		}
		else
		{
			return null;
		}
    }
    
    public void printMapList(Player p)
    {
    	String open = "|USED|";
    	p.sendMessage(ChatColor.GREEN + "Map List:");
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		if(this.getAvaliableMapList().contains(mapList.get(i).getName())){
    			open = "|OPEN|";
    		}else{
    			open = "|USED|";
    		}
    		p.sendMessage(ChatColor.GREEN + ""+ (i+1) + ". " + mapList.get(i).getName() + " " + open);
    	}
    }
    
    public void removeMap(String name)
    {
    	int index = 0;
    	for(int i = 0; i < mapList.size(); i++)
    	{
    		if(mapList.get(i).getName().equals(name))
    		{
    			index = i;
    		}
    	}
    	
		if(mapList.get(index).getName().equals(name))
		{
			mapList.remove(index);
		}
    }
}
