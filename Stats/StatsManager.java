package com.piraterevenge.PirateWars.Stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.block.Sign;


public class StatsManager {
	private static StatsManager instance;
	private ArrayList<PWPlayer> playerStats = new ArrayList<PWPlayer>();
	
	private HashMap<String,Integer> topKills = new HashMap<String,Integer>();
	private HashMap<String,Integer> topDeaths = new HashMap<String,Integer>();
	private HashMap<String,Integer> topWins = new HashMap<String,Integer>();
	private HashMap<String,Integer> topLoses = new HashMap<String,Integer>();
	private HashMap<String,Float> topKDR = new HashMap<String,Float>();
	private HashMap<String,Integer> topCore = new HashMap<String,Integer>();
	
	private Location[] topKillsSign = new Location[20];
	private Location[] topDeathsSign = new Location[20];
	private Location[] topWinsSign = new Location[20];
	private Location[] topLosesSign = new Location[20];
	private Location[] topKDRSign = new Location[20];
	private Location[] topCoreSign = new Location[20];
	
	public StatsManager(){
		instance = this;
	}
	
	public static StatsManager getInstance(){
		return instance;
	}
	
	public void addNewPlayer(Player p){
		System.out.println("Adding new");
		playerStats.add(new PWPlayer(p.getName()));
	}
	
	public void addExistingPlayer(String name,int wins, int loses, int kills, int deaths, int elo){
		playerStats.add(new PWPlayer(name,wins,loses,kills,deaths,elo));
	}
	
	public Location[] getKillSigns(){
		return topKillsSign;
	}
	
	public Location[] getDeathSigns(){
		return topDeathsSign;
	}
	
	public Location[] getWinsSigns(){
		return topWinsSign;
	}
	
	public Location[] getLosesSigns(){
		return topLosesSign;
	}
	
	public Location[] getKDRSigns(){
		return topLosesSign;
	}
	
	public Location[] getCoreSigns(){
		return topCoreSign;
	}
	
	public ArrayList<PWPlayer> getPlayerStats(){
		return playerStats;
	}
	
	public void addTopKillsSign(Location loc, int rank){
		topKillsSign[rank-1] = loc;
	}
	
	public void removeTopKillsSign(int rank){
		topKillsSign[rank-1] = null;
	}
	
	public void addTopDeathsSign(Location loc, int rank){
		topDeathsSign[rank-1] = loc;
	}
	
	public void removeTopDeathsSign(int rank){
		topDeathsSign[rank-1] = null;
	}
	
	public void addTopWinsSign(Location loc, int rank){
		topWinsSign[rank-1] = loc;
	}
	
	public void removeTopWinsSign(int rank){
		topWinsSign[rank-1] = null;
	}
	
	public void addTopLosesSign(Location loc, int rank){
		topLosesSign[rank-1] = loc;
	}
	
	public void removeTopLosesSign(int rank){
		topLosesSign[rank-1] = null;
	}
	
	public void addTopKDRSign(Location loc, int rank){
		topKDRSign[rank-1] = loc;
	}
	
	public void removeTopKDRSign(int rank){
		topKDRSign[rank-1] = null;
	}
	
	public void addTopCoreSign(Location loc, int rank){
		topCoreSign[rank-1] = loc;
	}
	
	public void removeTopCoreSign(int rank){
		topCoreSign[rank-1] = null;
	}
	
	public void updateSigns(){
		
		//update kills
		for(int i = 0; i < 20; i++){
			if(topKillsSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topKillsSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopKillsName(i+1);
					int kills = StatsManager.getInstance().getTopKillsVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". Most Kills");
					sign.setLine(1, name);
					sign.setLine(2, "Kills: " + kills);
					sign.update();
				}
			}
		}
		
		//update deaths
		for(int i = 0; i < 20; i++){
			if(topDeathsSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topDeathsSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopDeathsName(i+1);
					int deaths = StatsManager.getInstance().getTopDeathsVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". Most Deaths");
					sign.setLine(1, name);
					sign.setLine(2, "Deaths: " + deaths);
					sign.update();
				}
			}
		}
		
		//update Wins
		for(int i = 0; i < 20; i++){
			if(topWinsSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topWinsSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopWinsName(i+1);
					int wins = StatsManager.getInstance().getTopWinsVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". Most Wins");
					sign.setLine(1, name);
					sign.setLine(2, "Wins: " + wins);
					sign.update();
				}
			}
		}
		
		//update loses
		for(int i = 0; i < 20; i++){
			if(topLosesSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topLosesSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopLosesName(i+1);
					int loses = StatsManager.getInstance().getTopLosesVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". Most Loses");
					sign.setLine(1, name);
					sign.setLine(2, "Loses: " + loses);
					sign.update();
				}
			}
		}
		
		//update kdr
		for(int i = 0; i < 20; i++){
			if(topKDRSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topKDRSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopKDRName(i+1);
					float kdr = StatsManager.getInstance().getTopKDRVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". KDR");
					sign.setLine(1, name);
					sign.setLine(2, "KDR: " + kdr);
					sign.update();
				}
			}
		}
		
		//update core
		for(int i = 0; i < 20; i++){
			if(topCoreSign[i] != null){
				Block b = Bukkit.getWorld("world").getBlockAt(topCoreSign[i]);
				
				if(b.getTypeId() == 63 || b.getTypeId() == 68){
					Sign sign = (Sign) b.getState();
					
					String name = StatsManager.getInstance().getTopCoreName(i+1);
					float coredam = StatsManager.getInstance().getTopCoreVal(i+1);
					
					if(name == null){
						name = "None";
					}
	
					sign.setLine(0, ChatColor.GREEN + "#" + (i+1) + ". Core Damage");
					sign.setLine(1, name);
					sign.setLine(2, "CoreDam: " + coredam);
					sign.update();
				}
			}
		}
	}
	
	public PWPlayer getPlayer(Player p){
		PWPlayer stats = null;

		for(PWPlayer pl: playerStats){
			if(pl.getName().equalsIgnoreCase(p.getName())){
				stats = pl;
			}
		}
		
		if(stats == null){
			addNewPlayer(p);
			return getPlayer(p);
		}
		
		return stats;
	}
	
	public PWPlayer getPlayer(String name){
		PWPlayer stats = null;

		for(PWPlayer pl: playerStats){
			if(pl.getName().equalsIgnoreCase(name)){
				stats = pl;
			}
		}
		
		return stats;
	}
	
	public void calculateTopLists(){
		calculateTopKillList();
		calculateTopDeathList();
		calculateTopWinList();
		calculateTopLoseList();
		calculateTopKDRList();
		calculateTopCoreList();
		updateSigns();
	}
	
	public void calculateTopKillList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topKills.put(playerStats.get(i).getName(), playerStats.get(i).getKills());
		}
		
		topKills = sortHashMapByValuesI(topKills);
	}
	
	public void calculateTopDeathList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topDeaths.put(playerStats.get(i).getName(), playerStats.get(i).getDeaths());
		}
		
		topDeaths = sortHashMapByValuesI(topDeaths);
	}
	
	public void calculateTopWinList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topWins.put(playerStats.get(i).getName(), playerStats.get(i).getWins());
		}
		
		topWins = sortHashMapByValuesI(topWins);
	}
	
	public void calculateTopLoseList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topLoses.put(playerStats.get(i).getName(), playerStats.get(i).getLoses());
		}
		
		topLoses = sortHashMapByValuesI(topLoses);
	}
	
	public void calculateTopKDRList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topKDR.put(playerStats.get(i).getName(), playerStats.get(i).getKDR());
		}
		
		topKDR = sortHashMapByValuesF(topKDR);
	}
	
	public void calculateTopCoreList(){
		
		for(int i = 0; i < playerStats.size(); i ++){
			topCore.put(playerStats.get(i).getName(), playerStats.get(i).getCoreDamage());
		}
		
		topCore = sortHashMapByValuesI(topCore);
	}
	
	public String getTopKillsName(int rank){
		List<String> list = new ArrayList(topKills.keySet());
		return list.get(rank-1);
	}
	
	public Integer getTopKillsVal(int rank){
		List<Integer> list = new ArrayList(topKills.values());
		return list.get(rank-1);
	}
	
	public String getTopDeathsName(int rank){
		List<String> list = new ArrayList(topDeaths.keySet());
		return list.get(rank-1);
	}
	
	public Integer getTopDeathsVal(int rank){
		List<Integer> list = new ArrayList(topDeaths.values());
		return list.get(rank-1);
	}
	
	public String getTopWinsName(int rank){
		List<String> list = new ArrayList(topWins.keySet());
		return list.get(rank-1);
	}
	
	public Integer getTopWinsVal(int rank){
		List<Integer> list = new ArrayList(topWins.values());
		return list.get(rank-1);
	}
	
	public String getTopLosesName(int rank){
		List<String> list = new ArrayList(topLoses.keySet());
		return list.get(rank-1);
	}
	
	public Integer getTopLosesVal(int rank){
		List<Integer> list = new ArrayList(topLoses.values());
		return list.get(rank-1);
	}
	
	public String getTopKDRName(int rank){
		List<String> list = new ArrayList(topKDR.keySet());
		return list.get(rank-1);
	}
	
	public Float getTopKDRVal(int rank){
		List<Float> list = new ArrayList(topKDR.values());
		return list.get(rank-1);
	}
	
	public String getTopCoreName(int rank){
		List<String> list = new ArrayList(topCore.keySet());
		return list.get(rank-1);
	}
	
	public Integer getTopCoreVal(int rank){
		List<Integer> list = new ArrayList(topCore.values());
		return list.get(rank-1);
	}
	
	public void displayTopKills(Player p){
		
		p.sendMessage(ChatColor.GOLD + "======PIRATE=WARS=TOP=KILLS======");
		
		displayHashMap(p,topKills,20);
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayTopDeaths(Player p){
		
		p.sendMessage(ChatColor.GOLD + "======PIRATE=WARS=TOP=DEATHS======");
		
		displayHashMap(p,topDeaths,20);
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayTopWins(Player p){
		
		p.sendMessage(ChatColor.GOLD + "======PIRATE=WARS=TOP=WINS======");
		
		displayHashMap(p,topWins,20);
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayTopLoses(Player p){
		
		p.sendMessage(ChatColor.GOLD + "======PIRATE=WARS=TOP=LOSES======");
		
		displayHashMap(p,topLoses,20);
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayTopKDR(Player p){
		
		p.sendMessage(ChatColor.GOLD + "======PIRATE=WARS=TOP=KDR======");
		
		displayHashMap(p,topKDR,20);
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayPlayerStats(Player p){
		PWPlayer pl = getPlayer(p);
		
		p.sendMessage(ChatColor.GOLD + "========PIRATE=WARS=STATS==========");
		
		if(pl == null){
			p.sendMessage(ChatColor.GREEN + "You have not played Pirate Wars.");
		}else{
			p.sendMessage(ChatColor.GREEN + "Kills: " + ChatColor.GOLD + pl.getKills());
			p.sendMessage(ChatColor.GREEN + "Deaths: " + ChatColor.GOLD + pl.getDeaths());
			p.sendMessage(ChatColor.GREEN + "KDR: " + ChatColor.GOLD + pl.getKDR());
			p.sendMessage(ChatColor.GREEN + "Wins: " + ChatColor.GOLD + pl.getWins());
			p.sendMessage(ChatColor.GREEN + "Loses: " + ChatColor.GOLD + pl.getLoses());
			p.sendMessage(ChatColor.GREEN + "Core Damage: " + ChatColor.GOLD + pl.getCoreDamage());
		}
		
		p.sendMessage(ChatColor.GOLD + "=================================");
	}
	
	public void displayHashMap(Player p, HashMap passedMap, int max){
		   List mapKeys = new ArrayList(passedMap.keySet());
		   List mapValues = new ArrayList(passedMap.values());
		   
		   Iterator keyIt = mapKeys.iterator();
		   
		   int i = 0;
		   
		   while (keyIt.hasNext() && i < max) {
			   Object key = keyIt.next();
			   p.sendMessage(ChatColor.GOLD + "#" + (i+1) + ": "+ ChatColor.GREEN + key + " - " + ChatColor.GOLD + passedMap.get(key));
			   i++;
		   }
	}
	
	public LinkedHashMap sortHashMapByValuesI(HashMap passedMap) {
		   List mapKeys = new ArrayList(passedMap.keySet());
		   List mapValues = new ArrayList(passedMap.values());
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Collections.sort(mapKeys, Collections.reverseOrder());

		   LinkedHashMap sortedMap = new LinkedHashMap();

		   Iterator valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((String)key, (Integer)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
	}
	
	public LinkedHashMap sortHashMapByValuesF(HashMap passedMap) {
		   List mapKeys = new ArrayList(passedMap.keySet());
		   List mapValues = new ArrayList(passedMap.values());
		   Collections.sort(mapValues, Collections.reverseOrder());
		   Collections.sort(mapKeys, Collections.reverseOrder());

		   LinkedHashMap sortedMap = new LinkedHashMap();

		   Iterator valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((String)key, (Float)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
	}
}
