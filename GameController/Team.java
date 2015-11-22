package com.piraterevenge.PirateWars.GameController;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.piraterevenge.PirateWars.GameController.Game.State;

public class Team {
	public List<Player> team = new ArrayList<Player>();
	
	public int number;
	
	public State state;
	
	public Team(int num)
	{
		number = num;
	}
	
	public List<Player> getTeam()
	{
		return team;
	}
	
	public State getState()
	{
		return state;
	}
}
