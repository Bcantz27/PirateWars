package com.piraterevenge.PirateWars.PlayerController;

import java.util.ArrayList;
import java.util.List;

import com.piraterevenge.PirateWars.Classes.ClassManager;

public class PlayerManager {
	
	public static PlayerManager instance;
	private static List<GamePlayer> Players = new ArrayList<GamePlayer>();
	
	public PlayerManager()
	{
		instance = this;
	}
	
	public static PlayerManager getInstance()
	{
		return instance;
	}
	
	public List<GamePlayer> getPlayers()
	{
		return Players;
	}
	
	public void addPlayer(String name, ClassManager.Type type)
	{
		if(isPlayerNew(name))
		{
			Players.add(new GamePlayer(name,type));
		}
	}
	
	public void addPlayer(String name)
	{
		if(isPlayerNew(name))
		{
			Players.add(new GamePlayer(name));
		}
	}
	
	public boolean isPlayerNew(String name)
	{
		boolean flag = false;
		for(GamePlayer gp: Players)
		{
			if(gp.getName().equalsIgnoreCase(name))
			{
				flag = true;
			}
		}
		return flag;
	}
	
}
