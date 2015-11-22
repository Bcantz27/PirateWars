package com.piraterevenge.PirateWars.Data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class DataManager {
	public static DataManager instance = new DataManager();
	
	private static List<Location> lobbySigns = new ArrayList<Location>();
	private static List<Location> classSigns = new ArrayList<Location>();
	
	public DataManager()
	{
		instance = this;
	}
	
    public static DataManager getInstance()
    {
    	return instance;
    }
    
    public List<Location> getLobbySigns()
    {
    	return lobbySigns;
    }
    
    public List<Location> getClassSigns()
    {
    	return classSigns;
    }
}
