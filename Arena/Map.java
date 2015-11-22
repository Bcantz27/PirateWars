package com.piraterevenge.PirateWars.Arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.Vector;

import com.piraterevenge.PirateWars.PirateWars;
import com.piraterevenge.PirateWars.GameController.Game;
import com.piraterevenge.PirateWars.GameController.Game.State;
import com.piraterevenge.PirateWars.Stats.StatsManager;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.md_5.bungee.api.ChatColor;

public class Map{
	
	private String name;
	private HashMap<Vector,Integer> mapBlockIds = new HashMap<Vector,Integer>();
	private HashMap<Vector,Integer> mapBlockData = new HashMap<Vector,Integer>();
	
	private ProtectedRegion entireMap;
	
	private Location classSelect;
	private Location spawnRed;
	private Location spawnBlue;
	private Location coreLocRed;
	private Location outterCoreLocRed;
	private Location coreLocBlue;
	private Location outterCoreLocBlue;
	private Location wallMin;
	private Location wallMax;
	
	private int redCoreHealth = 20;
	private int redOutterCoreHealth = 20;
	private int blueCoreHealth = 20;
	private int blueOutterCoreHealth = 20;
	
	public boolean isWallUp = false;
	
	public Map(String name, String entireMapRegion)
	{
		this.name = name;
		entireMap = WorldGuardPlugin.inst().getRegionManager(Bukkit.getWorld("PirateWars")).getRegion(entireMapRegion);
		calculateWallRegion();
		store();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void putWallUp()
	{
		Location loc = null;
		
		if(wallMin == null ||  wallMax == null){
			calculateWallRegion();
		}
		
		int xDif = wallMax.getBlockX() - wallMin.getBlockX();
		int zDif = wallMax.getBlockZ() - wallMin.getBlockZ();
		
		if(!isWallUp)
		{
			if(xDif < zDif){
				for(int j = wallMin.getBlockY(); j <= wallMax.getBlockY();j++)
				{
					for(int k = wallMin.getBlockZ(); k <= wallMax.getBlockZ();k++)
					{
						loc = new Location(Bukkit.getWorld("PirateWars"),wallMin.getBlockX(),j,k);
						loc.getBlock().setType(Material.BEDROCK);
					}
				}
				isWallUp = true;
			}
			else{
				for(int j = wallMin.getBlockY(); j <= wallMax.getBlockY();j++)
				{
					for(int k = wallMin.getBlockX(); k <= wallMax.getBlockX();k++)
					{
						loc = new Location(Bukkit.getWorld("PirateWars"),k,j,wallMin.getBlockZ());
						loc.getBlock().setType(Material.BEDROCK);
					}
				}
				isWallUp = true;
			}
		}
	}
	
	public boolean isWallUp()
	{
		return isWallUp;
	}
	
	public void putWallDown()
	{
		int xDif = wallMax.getBlockX() - wallMin.getBlockX();
		int zDif = wallMax.getBlockZ() - wallMin.getBlockZ();
		
		if(isWallUp)
		{
			if(xDif < zDif){
				for(int j = wallMin.getBlockY(); j <= wallMax.getBlockY();j++)
				{
					for(int k = wallMin.getBlockZ(); k <= wallMax.getBlockZ();k++)
					{
						new Location(Bukkit.getWorld("PirateWars"),wallMin.getBlockX(),j,k).getBlock().setType(Material.AIR);;
					}
				}
				isWallUp = false;
			}
			else{
				for(int j = wallMin.getBlockY(); j <= wallMax.getBlockY();j++)
				{
					for(int k = wallMin.getBlockX(); k <= wallMax.getBlockX();k++)
					{
						new Location(Bukkit.getWorld("PirateWars"),k,j,wallMin.getBlockZ()).getBlock().setType(Material.AIR);;
					}
				}
				isWallUp = false;
			}
		}
	}
	
	public void calculateWallRegion()
	{
		int x1 = entireMap.getMinimumPoint().getBlockX();
		int z1 = entireMap.getMinimumPoint().getBlockZ();
		int x2 = entireMap.getMaximumPoint().getBlockX();
		int z2 = entireMap.getMaximumPoint().getBlockZ();
		int y1 = entireMap.getMinimumPoint().getBlockY();
		int y2 = entireMap.getMaximumPoint().getBlockY();
		int midpoint = 0;
		
		System.out.println("Calc wall Region " + (x2-x1) + " " + (z2-z1));
		
		if(x2-x1 > z2-z1)
		{
			midpoint = (int) Math.floor((x2-x1)/2);
			midpoint = midpoint + x1;
			System.out.println("One " + midpoint);
			wallMin = new Location(Bukkit.getWorld("PirateWars"),midpoint,y1,z1);
			wallMax = new Location(Bukkit.getWorld("PirateWars"),midpoint,y2,z2);
		}
		else
		{
			midpoint = (int) Math.floor((z2-z1)/2);
			midpoint = midpoint + z1;
			System.out.println("Two " + midpoint);
			wallMin = new Location(Bukkit.getWorld("PirateWars"),x1,y1,midpoint);
			wallMax = new Location(Bukkit.getWorld("PirateWars"),x2,y2,midpoint);
		}
	}
	
	public ProtectedRegion getMapRegion()
	{
		return entireMap;
	}
	
	public int getRedCoreHealth(){
		return redCoreHealth;
	}
	
	public int getRedOutterCoreHealth(){
		return redOutterCoreHealth;
	}
	
	public int getBlueCoreHealth(){
		return blueCoreHealth;
	}
	
	public int getBlueOutterCoreHealth(){
		return blueOutterCoreHealth;
	}
	
	public Location getClassSelectLoc(){
		return classSelect;
	}
	
	public Location getRedSpawn()
	{
		return spawnRed;
	}
	
	public Location getBlueSpawn()
	{
		return spawnBlue;
	}
	
	public Location getRedCoreLoc()
	{
		return coreLocRed;
	}
	
	public Location getRedOutterCoreLoc()
	{
		return outterCoreLocRed;
	}
	
	public Location getBlueCoreLoc()
	{
		return coreLocBlue;
	}
	
	public Location getBlueOutterCoreLoc()
	{
		return outterCoreLocBlue;
	}
	
	public void setMapRegion(String entireMapRegion)
	{
		entireMap = WorldGuardPlugin.inst().getRegionManager(Bukkit.getWorld("PirateWars")).getRegion(entireMapRegion);
	}
	
	public void setClassSelect(Location loc)
	{
		classSelect = loc;
	}
	
	public void setRedSpawn(Location loc)
	{
		spawnRed = loc;
	}
	
	public void setBlueSpawn(Location loc)
	{
		spawnBlue = loc;
	}
	
	public void setRedCoreLoc(Location loc)
	{
		coreLocRed = loc;
	}
	
	public void setRedOutterCoreLoc(Location loc)
	{
		outterCoreLocRed = loc;
	}
	
	public void setBlueCoreLoc(Location loc)
	{
		coreLocBlue = loc;
	}
	
	public void setBlueOutterCoreLoc(Location loc)
	{
		outterCoreLocBlue = loc;
	}
	
	public void setRedCoreHealth(int health){
		redCoreHealth = health;
	}
	
	public void setRedOutterCoreHealth(int health){
		redOutterCoreHealth = health;
	}
	
	public void setBlueCoreHealth(int health){
		blueCoreHealth = health;
	}
	
	public void setBlueOutterCoreHealth(int health){
		blueOutterCoreHealth = health;
	}
	
	public void incrementRedCoreHealth(){
		redCoreHealth++;
	}
	
	public void incrementRedOutterCoreHealth(){
		redOutterCoreHealth++;
	}
	
	public void incrementBlueCoreHealth(){
		blueCoreHealth++;
	}
	
	public void incrementBlueOutterCoreHealth(){
		blueOutterCoreHealth++;
	}
	
	public void decrementRedCoreHealth(){
		redCoreHealth--;
	}
	
	public void decrementRedOutterCoreHealth(){
		redOutterCoreHealth--;
	}
	
	public void decrementBlueCoreHealth(){
		blueCoreHealth--;
	}
	
	public void decrementBlueOutterCoreHealth(){
		blueOutterCoreHealth--;
	}
	
	public void attackRedCore(Player p, Game g, BlockBreakEvent event){

		if(g.getTeam1().getTeam().contains(p)){
			p.sendMessage(ChatColor.RED + "Do not attack your own core!");
			event.setCancelled(true);
			return;
		}
		
		if(redOutterCoreHealth != 0){
			p.sendMessage(ChatColor.RED + "You must destroy the outter core first!");
			event.setCancelled(true);
			return;
		}
		
		if(g.getState() == State.WinnerFound){
			p.sendMessage(ChatColor.RED + "The game is ending.");
			event.setCancelled(true);
			return;
		}
		
		redCoreHealth--;
		StatsManager.getInstance().getPlayer(p).incrementCoreDamage();
		if(redCoreHealth == 0){
			g.sendAllPlayersMessage(ChatColor.RED + "Red Team's" + ChatColor.GREEN + " Core has been destoryed!");
			g.setWinner(2);
		}else{
			g.sendAllPlayersMessage(ChatColor.RED + p.getName() + ChatColor.GREEN + " is attacking the " + ChatColor.RED + "Red Team's" +ChatColor.GREEN + " core!");
			event.setCancelled(true);
		}
		
		if(redCoreHealth < 0){
			redCoreHealth = 0;
		}
	}
	
	public void attackRedOutterCore(Player p, Game g, BlockBreakEvent event){
		if(g.getTeam1().getTeam().contains(p)){
			p.sendMessage(ChatColor.RED + "Do not attack your own outter core!");
			event.setCancelled(true);
			return;
		}
		
		if(g.getState() == State.WinnerFound){
			p.sendMessage(ChatColor.RED + "The game is ending.");
			event.setCancelled(true);
			return;
		}
		
		redOutterCoreHealth--;
		StatsManager.getInstance().getPlayer(p).incrementCoreDamage();
		if(redOutterCoreHealth == 0){
			g.sendAllPlayersMessage(ChatColor.RED + "Red Team's" + ChatColor.GREEN + " Outter Core has been destoryed!");
		}else{
			g.sendAllPlayersMessage(ChatColor.RED + p.getName() + ChatColor.GREEN + " is attacking the " + ChatColor.RED + "Red Team's" +ChatColor.GREEN + " outter core!");
			event.setCancelled(true);
		}
		
		if(redOutterCoreHealth < 0){
			redOutterCoreHealth = 0;
		}
	}
	
	public void attackBlueCore(Player p, Game g, BlockBreakEvent event){
		if(g.getTeam2().getTeam().contains(p)){
			p.sendMessage(ChatColor.RED + "Do not attack your own core!");
			event.setCancelled(true);
			return;
		}
		
		if(blueOutterCoreHealth != 0){
			p.sendMessage(ChatColor.RED + "You must destroy the outter core first!");
			event.setCancelled(true);
			return;
		}
		
		if(g.getState() == State.WinnerFound){
			p.sendMessage(ChatColor.RED + "The game is ending.");
			event.setCancelled(true);
			return;
		}
		
		blueCoreHealth--;
		StatsManager.getInstance().getPlayer(p).incrementCoreDamage();
		if(blueCoreHealth == 0){
			g.sendAllPlayersMessage(ChatColor.BLUE + "Blue Team's" + ChatColor.GREEN + " Core has been destoryed!");
			g.setWinner(1);
		}else{
			g.sendAllPlayersMessage(ChatColor.RED + p.getName() + ChatColor.GREEN + " is attacking the " + ChatColor.BLUE + "Blue Team's" +ChatColor.GREEN + " core!");
			event.setCancelled(true);
		}
		
		if(blueCoreHealth < 0){
			blueCoreHealth = 0;
		}
	}
	
	public void attackBlueOutterCore(Player p, Game g, BlockBreakEvent event){
		if(g.getTeam2().getTeam().contains(p)){
			p.sendMessage(ChatColor.RED + "Do not attack your own outter core!");
			event.setCancelled(true);
			return;
		}
		
		if(g.getState() == State.WinnerFound){
			p.sendMessage(ChatColor.RED + "The game is ending.");
			event.setCancelled(true);
			return;
		}
		
		blueOutterCoreHealth--;
		StatsManager.getInstance().getPlayer(p).incrementCoreDamage();
		if(blueOutterCoreHealth == 0){
			g.sendAllPlayersMessage(ChatColor.BLUE + "Blue Team's" + ChatColor.GREEN + " Outter Core has been destoryed!");
		}else{
			g.sendAllPlayersMessage(ChatColor.RED + p.getName() + ChatColor.GREEN + " is attacking the " + ChatColor.BLUE + "Blue Team's" +ChatColor.GREEN + " outter core!");
			event.setCancelled(true);
		}
		
		if(blueOutterCoreHealth < 0){
			blueOutterCoreHealth = 0;
		}
	}
	
	public void resetMap(){
		redCoreHealth = 20;
		redOutterCoreHealth = 20;
		blueCoreHealth = 20;
		blueOutterCoreHealth = 20;
	}
	
	public void store()
	{
		Block tempBlock = null;
		
		mapBlockIds = new HashMap<Vector,Integer>();
		mapBlockData = new HashMap<Vector,Integer>();
		
		for(int i = entireMap.getMinimumPoint().getBlockX();i <= entireMap.getMaximumPoint().getBlockX();i++){
			for(int j = entireMap.getMinimumPoint().getBlockY(); j <= entireMap.getMaximumPoint().getBlockY();j++){
				for(int k = entireMap.getMinimumPoint().getBlockZ();k <= entireMap.getMaximumPoint().getBlockZ();k++){
					tempBlock = new Location(Bukkit.getWorld("PirateWars"),i,j,k).getBlock();
					mapBlockIds.put(new Vector(i,j,k),tempBlock.getTypeId());
					mapBlockData.put(new Vector(i,j,k),(int) tempBlock.getData());
				}
			}
		}
		System.out.println("Stored map: " + mapBlockIds.keySet().size() + " MIN: " + entireMap.getMinimumPoint().toString() + " MAX: " + entireMap.getMaximumPoint().toString());
	}
	
	public void regenerate()
	{	
		Location tempLoc = null;
		System.out.println("Regenerating map");
		for(Vector key: mapBlockIds.keySet()){
			tempLoc = new Location(Bukkit.getWorld("PirateWars"),key.getBlockX(),key.getBlockY(),key.getBlockZ());
			if(mapBlockIds.get(key) != tempLoc.getBlock().getTypeId())
			{
				tempLoc.getBlock().setTypeId(mapBlockIds.get(key));
				tempLoc.getBlock().setData(mapBlockData.get(key).byteValue());
			}
		}
	}

}
