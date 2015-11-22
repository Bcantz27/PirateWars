package com.piraterevenge.PirateWars.PlayerController;

import org.bukkit.entity.Player;

import com.piraterevenge.PirateWars.PirateWars;
import com.piraterevenge.PirateWars.Classes.ClassManager;
import com.piraterevenge.PirateWars.Classes.PlayerClass;

public class GamePlayer {
	
	public String name;
	public PlayerClass pc;
	public int kills;
	public int deaths;
	
	public GamePlayer(String name)
	{
		this.name = name;
		pc = ClassManager.Type.Musketeer.getClassType();
		kills = 0;
		deaths = 0;
	}
	
	public GamePlayer(String name, ClassManager.Type type)
	{
		this.name = name;
		pc = type.getClassType();
		kills = 0;
		deaths = 0;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Player getPlayer()
	{
		return PirateWars.getInstance().getServer().getPlayer(name);
	}
}
