package com.piraterevenge.PirateWars.Stats;

public class PWPlayer {
	private String name;
	private int wins;
	private int loses;
	private int kills;
	private int deaths;
	private int coreDamage;
	
	public PWPlayer(String name){
		this.name = name;
		wins = 0;
		loses = 0;
		kills = 0;
		deaths = 0;
		coreDamage = 0;
	}
	
	public PWPlayer(String name,int wins, int loses, int kills, int deaths, int coreDamage){
		this.name = name;
		this.wins = wins;
		this.loses = loses;
		this.kills = kills;
		this.deaths = deaths;
		this.coreDamage = coreDamage;
	}
	
	public String getName(){
		return name;
	}
	
	public int getKills(){
		return kills;
	}
	
	public int getDeaths(){
		return deaths;
	}
	
	public int getWins(){
		return wins;
	}
	
	public float getKDR(){
		float kdr = 0;
		
		if(deaths == 0){
			kdr = kills;
		}else{
			kdr = (float)((float)kills/(float)deaths);
		}
		return kdr;
	}
	
	public int getLoses(){
		return loses;
	}
	
	public int getCoreDamage(){
		return coreDamage;
	}
	
	public void incrementKills(){
		kills++;
	}
	
	public void incrementDeaths(){
		deaths++;
	}
	
	public void incrementWins(){
		wins++;
	}
	
	public void incrementLoses(){
		loses++;
	}
	
	public void incrementCoreDamage(){
		coreDamage++;
	}
	
	
}
