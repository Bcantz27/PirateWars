package com.piraterevenge.PirateWars.Data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.piraterevenge.PirateWars.PirateWars;
import com.piraterevenge.PirateWars.Arena.Map;
import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.GameController.Crew;
import com.piraterevenge.PirateWars.GameController.CrewManager;
import com.piraterevenge.PirateWars.Stats.PWPlayer;
import com.piraterevenge.PirateWars.Stats.StatsManager;

public class SaveLoad {
	public static SaveLoad instance = new SaveLoad();
	
	public SaveLoad()
	{
		instance = this;
	}
	
	public static SaveLoad getInstance()
	{
		return instance;
	}
	
	public void saveAll()
	{
		saveSignData();
		saveMapData();
		saveStatSignsData();
		savePlayerData();
	}
	
	public void loadAll()
	{
		loadSignData();
		loadMapData();
		loadStatSignsData();
		loadPlayerData();
	}
	
	public void resetAll()
	{
		DataManager.getInstance().getLobbySigns().clear();
		MapManager.getInstance().getMapList().clear();
	}
	
	private void saveStatSignsData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		Location[] killSigns = StatsManager.getInstance().getKillSigns();
		Location[] deathSigns = StatsManager.getInstance().getDeathSigns();
		Location[] winSigns = StatsManager.getInstance().getWinsSigns();
		Location[] loseSigns = StatsManager.getInstance().getLosesSigns();
		Location[] kdrSigns = StatsManager.getInstance().getKDRSigns();
		Location[] coreSigns = StatsManager.getInstance().getCoreSigns();
		
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "StatSigns.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Saving StatSigns");
        if(!pluginFolder.exists()){
            try{
            	pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            	configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(killSigns[i] != null){
	        	playerDataConfig.set("kills"+i+".x", killSigns[i].getBlockX());
	        	playerDataConfig.set("kills"+i+".y", killSigns[i].getBlockY());
	        	playerDataConfig.set("kills"+i+".z", killSigns[i].getBlockZ());
        	}
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(deathSigns[i] != null){
	        	playerDataConfig.set("deaths"+i+".x", deathSigns[i].getBlockX());
	        	playerDataConfig.set("deaths"+i+".y", deathSigns[i].getBlockY());
	        	playerDataConfig.set("deaths"+i+".z", deathSigns[i].getBlockZ());
        	}
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(winSigns[i] != null){
	        	playerDataConfig.set("wins"+i+".x", winSigns[i].getBlockX());
	        	playerDataConfig.set("wins"+i+".y", winSigns[i].getBlockY());
	        	playerDataConfig.set("wins"+i+".z", winSigns[i].getBlockZ());
        	}
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(loseSigns[i] != null){
	        	playerDataConfig.set("loses"+i+".x", loseSigns[i].getBlockX());
	        	playerDataConfig.set("loses"+i+".y", loseSigns[i].getBlockY());
	        	playerDataConfig.set("loses"+i+".z", loseSigns[i].getBlockZ());
        	}
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(kdrSigns[i] != null){
	        	playerDataConfig.set("kdr"+i+".x", kdrSigns[i].getBlockX());
	        	playerDataConfig.set("kdr"+i+".y", kdrSigns[i].getBlockY());
	        	playerDataConfig.set("kdr"+i+".z", kdrSigns[i].getBlockZ());
        	}
        }
        
        for(int i = 0; i < 20;i++)
        {
        	if(coreSigns[i] != null){
	        	playerDataConfig.set("core"+i+".x", coreSigns[i].getBlockX());
	        	playerDataConfig.set("core"+i+".y", coreSigns[i].getBlockY());
	        	playerDataConfig.set("core"+i+".z", coreSigns[i].getBlockZ());
        	}
        }
              
        try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveCrewData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		List<Crew> crews = CrewManager.getInstance().getCrewList();
		
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Crews.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Saving Crews");
        if(!pluginFolder.exists()){
            try{
            	pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            	configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        playerDataConfig.set("length", crews.size());
        for(int i = 0; i < crews.size();i++)
        {
        	playerDataConfig.set("crew"+i+".name", crews.get(i).getName());
        	playerDataConfig.set("crew"+i+".memberLength", crews.get(i).getMembers().size());
        	for(int j = 0; j < crews.get(i).getMembers().size(); j++){
        		playerDataConfig.set("crew"+i+".member"+j+".name", crews.get(i).getMembers().get(j));
        	}
        }
              
        try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void savePlayerData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		ArrayList<PWPlayer> playerStats = StatsManager.getInstance().getPlayerStats();
		
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Stats.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Saving Stats");
        if(!pluginFolder.exists()){
            try{
            	pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            	configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        playerDataConfig.set("length", playerStats.size());
        for(int i = 0; i < playerStats.size();i++)
        {
        	playerDataConfig.set("player"+i+".name", playerStats.get(i).getName());
        	playerDataConfig.set("player"+i+".kills", playerStats.get(i).getKills());
        	playerDataConfig.set("player"+i+".deaths", playerStats.get(i).getDeaths());
        	playerDataConfig.set("player"+i+".wins", playerStats.get(i).getWins());
        	playerDataConfig.set("player"+i+".loses", playerStats.get(i).getLoses());
        	playerDataConfig.set("player"+i+".coredamage", playerStats.get(i).getCoreDamage());
        }
              
        try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveSignData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		List<Location> lobbySigns = DataManager.getInstance().getLobbySigns();
		List<Location> classSigns = DataManager.getInstance().getClassSigns();
		
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Signs.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Saving Signs");
        if(!pluginFolder.exists()){
            try{
            	pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            	configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        playerDataConfig.set("loblength", lobbySigns.size());
        for(int i = 0; i < lobbySigns.size();i++)
        {
        	playerDataConfig.set("lobby"+i+".x", lobbySigns.get(i).getBlockX());
        	playerDataConfig.set("lobby"+i+".y", lobbySigns.get(i).getBlockY());
        	playerDataConfig.set("lobby"+i+".z", lobbySigns.get(i).getBlockZ());
        }
        
        playerDataConfig.set("classlength", classSigns.size());
        for(int i = 0; i < classSigns.size();i++)
        {
        	playerDataConfig.set("class"+i+".x", classSigns.get(i).getBlockX());
        	playerDataConfig.set("class"+i+".y", classSigns.get(i).getBlockY());
        	playerDataConfig.set("class"+i+".z", classSigns.get(i).getBlockZ());
        }
              
        try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveMapData()
	{
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		List<Map> mapRegions = MapManager.getInstance().getMapList();
		Location endSpawnPoint = MapManager.getInstance().getEndSpawnPoint();
		
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Maps.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Saving Maps");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        playerDataConfig.set("length", mapRegions.size());
        playerDataConfig.set("endspawnpoint.x", endSpawnPoint.getBlockX());
        playerDataConfig.set("endspawnpoint.y", endSpawnPoint.getBlockY());
        playerDataConfig.set("endspawnpoint.z", endSpawnPoint.getBlockZ());
        for(int i = 0; i <mapRegions.size();i++)
        {
        	playerDataConfig.set("map"+i+".name", mapRegions.get(i).getName());
        	playerDataConfig.set("map"+i+".mainregion", mapRegions.get(i).getMapRegion().getId());
    		playerDataConfig.set("map"+i+".redspawn.x", mapRegions.get(i).getRedSpawn().getBlockX());
    		playerDataConfig.set("map"+i+".redspawn.y", mapRegions.get(i).getRedSpawn().getBlockY());
    		playerDataConfig.set("map"+i+".redspawn.z", mapRegions.get(i).getRedSpawn().getBlockZ());
    		playerDataConfig.set("map"+i+".bluespawn.x", mapRegions.get(i).getBlueSpawn().getBlockX());
    		playerDataConfig.set("map"+i+".bluespawn.y", mapRegions.get(i).getBlueSpawn().getBlockY());
    		playerDataConfig.set("map"+i+".bluespawn.z", mapRegions.get(i).getBlueSpawn().getBlockZ());
    		playerDataConfig.set("map"+i+".redcore.x", mapRegions.get(i).getRedCoreLoc().getBlockX());
    		playerDataConfig.set("map"+i+".redcore.y", mapRegions.get(i).getRedCoreLoc().getBlockY());
    		playerDataConfig.set("map"+i+".redcore.z", mapRegions.get(i).getRedCoreLoc().getBlockZ());
    		playerDataConfig.set("map"+i+".redouttercore.x", mapRegions.get(i).getRedOutterCoreLoc().getBlockX());
    		playerDataConfig.set("map"+i+".redouttercore.y", mapRegions.get(i).getRedOutterCoreLoc().getBlockY());
    		playerDataConfig.set("map"+i+".redouttercore.z", mapRegions.get(i).getRedOutterCoreLoc().getBlockZ());
    		playerDataConfig.set("map"+i+".bluecore.x", mapRegions.get(i).getBlueCoreLoc().getBlockX());
    		playerDataConfig.set("map"+i+".bluecore.y", mapRegions.get(i).getBlueCoreLoc().getBlockY());
    		playerDataConfig.set("map"+i+".bluecore.z", mapRegions.get(i).getBlueCoreLoc().getBlockZ());
    		playerDataConfig.set("map"+i+".blueouttercore.x", mapRegions.get(i).getBlueOutterCoreLoc().getBlockX());
    		playerDataConfig.set("map"+i+".blueouttercore.y", mapRegions.get(i).getBlueOutterCoreLoc().getBlockY());
    		playerDataConfig.set("map"+i+".blueouttercore.z", mapRegions.get(i).getBlueOutterCoreLoc().getBlockZ());
    		playerDataConfig.set("map"+i+".classselect.x", mapRegions.get(i).getClassSelectLoc().getBlockX());
    		playerDataConfig.set("map"+i+".classselect.y", mapRegions.get(i).getClassSelectLoc().getBlockY());
    		playerDataConfig.set("map"+i+".classselect.z", mapRegions.get(i).getClassSelectLoc().getBlockZ());
        }
              
        try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadStatSignsData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "StatSigns.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Loading StatSigns");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }

        //kills
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("kills"+i) != null){
        		StatsManager.getInstance().addTopKillsSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("kills"+i+".x"), playerDataConfig.getInt("kills"+i+".y"), playerDataConfig.getInt("kills"+i+".z")), i+1);
        	}
        }
        
        //deaths
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("deaths"+i) != null){
        		StatsManager.getInstance().addTopDeathsSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("deaths"+i+".x"), playerDataConfig.getInt("deaths"+i+".y"), playerDataConfig.getInt("deaths"+i+".z")), i+1);
        	}
        }
        
        //wins
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("wins"+i) != null){
        		StatsManager.getInstance().addTopWinsSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("wins"+i+".x"), playerDataConfig.getInt("wins"+i+".y"), playerDataConfig.getInt("wins"+i+".z")), i+1);
        	}
        }
        
        //loses
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("loses"+i) != null){
        		StatsManager.getInstance().addTopLosesSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("loses"+i+".x"), playerDataConfig.getInt("loses"+i+".y"), playerDataConfig.getInt("loses"+i+".z")), i+1);
        	}
        }
        
        //kdr
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("kdr"+i) != null){
        		StatsManager.getInstance().addTopKDRSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("kdr"+i+".x"), playerDataConfig.getInt("kdr"+i+".y"), playerDataConfig.getInt("kdr"+i+".z")), i+1);
        	}
        }
        
        //core
        for(int i = 0; i < 20; i++)
        {
        	if(playerDataConfig.get("core"+i) != null){
        		StatsManager.getInstance().addTopCoreSign(new Location(Bukkit.getWorld("world"), playerDataConfig.getInt("core"+i+".x"), playerDataConfig.getInt("core"+i+".y"), playerDataConfig.getInt("core"+i+".z")), i+1);
        	}
        }
        
       	try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadPlayerData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Stats.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Loading Stats");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }

        for(int i = 0; i < playerDataConfig.getInt("length"); i++)
        {
        	StatsManager.getInstance().addExistingPlayer(playerDataConfig.getString("player" + i + ".name"), playerDataConfig.getInt("player" + i + ".wins"), playerDataConfig.getInt("player" + i + ".loses"), playerDataConfig.getInt("player" + i + ".kills"), playerDataConfig.getInt("player" + i + ".deaths"), playerDataConfig.getInt("player" + i + ".coredamage"));
        }
        
        StatsManager.getInstance().calculateTopLists();
        
       	try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadCrewData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Crews.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Loading Crews");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }

        for(int i = 0; i < playerDataConfig.getInt("length"); i++)
        {
        	Crew c = new Crew(playerDataConfig.getString("crew"+i+".name"));
        	for(int j = 0; j < playerDataConfig.getInt("memberLength"); j++){
        		c.addMember("crew"+i+".member"+j+".name");
        	}
        	CrewManager.getInstance().addCrew(c);
        }
        
       	try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadMapData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Maps.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Loading Maps");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }
        
        Map tempMap = null;
        
        MapManager.getInstance().setEndSpawnPoint(new Location(Bukkit.getWorld("world"),playerDataConfig.getInt("endspawnpoint.x"),playerDataConfig.getInt("endspawnpoint.y"),playerDataConfig.getInt("endspawnpoint.z")));
        for(int i = 0; i < playerDataConfig.getInt("length"); i++)
        {
    		tempMap = new Map(playerDataConfig.getString("map"+i+".name"),playerDataConfig.getString("map"+i+".mainregion"));
    		tempMap.setRedSpawn(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".redspawn.x"),playerDataConfig.getInt("map"+i+".redspawn.y"),playerDataConfig.getInt("map"+i+".redspawn.z")));
    		tempMap.setBlueSpawn(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".bluespawn.x"),playerDataConfig.getInt("map"+i+".bluespawn.y"),playerDataConfig.getInt("map"+i+".bluespawn.z")));
    		tempMap.setRedCoreLoc(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".redcore.x"),playerDataConfig.getInt("map"+i+".redcore.y"),playerDataConfig.getInt("map"+i+".redcore.z")));
    		tempMap.setRedOutterCoreLoc(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".redouttercore.x"),playerDataConfig.getInt("map"+i+".redouttercore.y"),playerDataConfig.getInt("map"+i+".redouttercore.z")));
    		tempMap.setBlueCoreLoc(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".bluecore.x"),playerDataConfig.getInt("map"+i+".bluecore.y"),playerDataConfig.getInt("map"+i+".bluecore.z")));
    		tempMap.setBlueOutterCoreLoc(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".blueouttercore.x"),playerDataConfig.getInt("map"+i+".blueouttercore.y"),playerDataConfig.getInt("map"+i+".blueouttercore.z")));
    		tempMap.setClassSelect(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("map"+i+".classselect.x"),playerDataConfig.getInt("map"+i+".classselect.y"),playerDataConfig.getInt("map"+i+".classselect.z")));
    		MapManager.getInstance().addMap(tempMap);
        }
        
       	try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadSignData(){
		File pluginFolder;
		File configFile;
		FileConfiguration playerDataConfig;
		pluginFolder = PirateWars.getInstance().getDataFolder();
        configFile = new File(pluginFolder, "Signs.yml");
        playerDataConfig = new YamlConfiguration();
        PirateWars.logger.log(Level.INFO, "Loading Signs");
        if(!pluginFolder.exists()){
            try{
            pluginFolder.mkdir();
            }catch(Exception ex){
            	System.out.println("could not create folder");
            }
        }
        if(!configFile.exists()){
            try{
            configFile.createNewFile();
            }catch(Exception ex){
            	System.out.println("could not create file");
            }
        }
        try{
            playerDataConfig.load(configFile);
        }catch(Exception ex){
        	System.out.println("could not load file");
        }

        for(int i = 0; i < playerDataConfig.getInt("loblength");i++)
        {
        	DataManager.getInstance().getLobbySigns().add(new Location(Bukkit.getWorld("world"),playerDataConfig.getInt("lobby"+i+".x"),playerDataConfig.getInt("lobby"+i+".y"),playerDataConfig.getInt("lobby"+i+".z")));
        }
        
        for(int i = 0; i < playerDataConfig.getInt("classlength");i++)
        {
        	DataManager.getInstance().getClassSigns().add(new Location(Bukkit.getWorld("PirateWars"),playerDataConfig.getInt("class"+i+".x"),playerDataConfig.getInt("class"+i+".y"),playerDataConfig.getInt("class"+i+".z")));
        }
        
       	try {
			playerDataConfig.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
