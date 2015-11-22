package com.piraterevenge.PirateWars;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import com.piraterevenge.PirateWars.Classes.ClassManager;
import com.piraterevenge.PirateWars.Data.SaveLoad;
import com.piraterevenge.PirateWars.GameController.CrewManager;
import com.piraterevenge.PirateWars.GameController.GameTask;
import com.piraterevenge.PirateWars.Listeners.*;
import com.piraterevenge.PirateWars.Stats.StatsManager;
 
public final class PirateWars extends JavaPlugin 
{ 
	public static PirateWars instance;
	public static Logger logger = Logger.getLogger("Minecraft");

    @Override
    public void onEnable()
    {
    	instance = this;
    	Bukkit.getServer().createWorld(new WorldCreator("PirateWars"));
    	
    	ClassManager classM = new ClassManager();
    	StatsManager statsM = new StatsManager();
    	CrewManager crewM = new CrewManager();
    	
    	getCommand("PirateWars").setExecutor(new CommandListener());
    	getServer().getPluginManager().registerEvents(new BlockListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    	getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    	
    	getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameTask(), 0L, 20L*15L);
    	
    	SaveLoad.getInstance().loadAll();
    }
 
    @Override
    public void onDisable() 
    {
    	SaveLoad.getInstance().saveAll();
    	//SaveLoad.getInstance().resetAll();
    }
    
    public static PirateWars getInstance()
    {
    	return instance;
    }
 
}