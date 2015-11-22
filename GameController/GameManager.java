package com.piraterevenge.PirateWars.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.piraterevenge.PirateWars.Arena.Map;
import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.GameController.Game.State;
import com.piraterevenge.PirateWars.GameController.Game.Type;

public class GameManager {
	public static GameManager instance = new GameManager();
	
	private static List<Game> activeMatches = new ArrayList<Game>();
	private static ConcurrentLinkedQueue<Game> LFGMatches = new ConcurrentLinkedQueue<Game>();
	private static ConcurrentLinkedQueue<Player> soloQueue = new ConcurrentLinkedQueue<Player>();
	private static ConcurrentLinkedQueue<Player> crewQueue = new ConcurrentLinkedQueue<Player>();
	
	public GameManager()
	{
		instance = this;
	}
	
    public static GameManager getInstance()
    {
    	return instance;
    }
    
    public List<Game> getActiveMatches()
    {
    	return activeMatches;
    }

    public ConcurrentLinkedQueue<Game> getLFGMatches()
    {
    	return LFGMatches;
    }
    
    public ConcurrentLinkedQueue<Player> getSoloQueue()
    {
    	return soloQueue;
    }
    
    public ConcurrentLinkedQueue<Player> getCrewQueue()
    {
    	return crewQueue;
    }
    
    public void checkQueues()
    {
    	if(soloQueue.size() >= 1)
    	{
    		createNewGame(Type.Solo);
    	}
    	if(crewQueue.size() >= 1)
    	{
    		createNewGame(Type.Crew);
    	}
    	
    	if(LFGMatches.size() >= 1)
    	{
    		tryToStartLFGMatches();
    	}
    	
    	checkMatchSizes();
    }
    
    public void checkMatchSizes()
    {
    	if(!LFGMatches.isEmpty())
    	{
	    	for(Game g: LFGMatches)
	    	{
	    		g.checkSize();
	    	}
    	}
    }
    
    public void tryToStartLFGMatches()
    {
    	for(Game g: LFGMatches)
    	{
    		if(g.tryToStartGame())
    		{
    			activeMatches.add(g);
    			LFGMatches.remove(g);
    		}
    	}
    }
    
    public boolean isPlayerInGame(Player p)
    {
    	boolean flag = false;
    	for(Game g: activeMatches)
    	{
    		if(g.isPlayerPlaying(p))
    		{
    			flag = true;
    			return flag;
    		}
    	}

    	for(Game g: LFGMatches)
    	{
    		if(g.isPlayerPlaying(p))
    		{
    			flag = true;
    			return flag;
    		}
    	}
    	
    	return flag;
    }
    
    public Game getGameofPlayer(Player p)
    {
    	Game game = null;
    	for(Game g: activeMatches)
    	{
    		if(g.isPlayerPlaying(p))
    		{
    			game = g;
    			return game;
    		}
    	}
    	
    	for(Game g: LFGMatches)
    	{
    		if(g.isPlayerPlaying(p))
    		{
    			game = g;
    			return game;
    		}
    	}
    	
    	return game;
    }
    
    public void teleportPlayerToSpawn(Player p){
    	Game g = getGameofPlayer(p);
    	if(g.getTeamOfPlayer(p) == 1){
    		p.teleport(g.getMap().getRedSpawn());
    	} else if(g.getTeamOfPlayer(p) == 2){
    		p.teleport(g.getMap().getBlueSpawn());
    	}
    }
    
    public void endActiveGame(Game g){
    	if(g.getState() == State.Finished){
    		activeMatches.remove(g);
    		MapManager.getInstance().getAvaliableMapList().add(g.getMap().getName());
    	}else{
    		System.out.println("Cant remove game! Game is not finished.");
    	}
    }
    
    public void endLFGGame(Game g){
    	if(g.getState() == State.LookingForPlayers){
    		LFGMatches.remove(g);
    		MapManager.getInstance().getAvaliableMapList().add(g.getMap().getName());
    	}else{
    		System.out.println("Cant remove game! Game is not finished.");
    	}
    }
    
    public void createNewGame(Game.Type type)
    {
    	Game curGame = null;
    	
    	if(LFGMatches.isEmpty())
    	{
        	if(!(MapManager.getInstance().getAvaliableMapList().size() > 0)){
        		return;
        	}
	    	Game game = new Game(type);
	    	game.setState(State.LookingForPlayers);
	    	LFGMatches.add(game);
    	}
    	if(type == Type.Solo)
    	{
			while(LFGMatches.iterator().hasNext()){
				curGame = LFGMatches.iterator().next();
				if(curGame.getSize() < 10){
					curGame.addPlayer(soloQueue.remove());
					break;
				}
			}
    	}
    	else if(type == Type.Crew)
    	{
        	LFGMatches.peek().addPlayer(crewQueue.remove());
    	}
    }
    
    public void removePlayerFromAll(Player p)
    {
    	removePlayerFromCrewQueue(p);
    	removePlayerFromSoloQueue(p);
    }
    
    public void removePlayerFromCrewQueue(Player p)
    {
    	if(crewQueue.contains(p))
    	{
	    	p.sendMessage(ChatColor.RED + "You have been removed from Crew Queue.");
	    	crewQueue.remove(p);
    	}
    }
    
    public void removePlayerFromSoloQueue(Player p)
    {
    	if(soloQueue.contains(p))
    	{
	    	p.sendMessage(ChatColor.RED + "You have been removed from Solo Queue.");
	    	soloQueue.remove(p);
    	}
    }
    
    public void addPlayerToSoloQueue(Player p)
    {
    	if(crewQueue.contains(p))
    	{
    		removePlayerFromCrewQueue(p);
    	}
    	
    	if(!soloQueue.contains(p) && !isPlayerInGame(p))
    	{
    		p.sendMessage(ChatColor.GREEN + "You have been added to the Solo Queue.");
    		soloQueue.add(p);
    	}
    }
    
    public void addPlayerToCrewQueue(Player p)
    {
    	if(soloQueue.contains(p))
    	{
    		removePlayerFromSoloQueue(p);
    	}
    	
    	if(!crewQueue.contains(p) && !isPlayerInGame(p))
    	{
    		p.sendMessage(ChatColor.GREEN + "You have been added to the Crew Queue.");
    		crewQueue.add(p);
    	}
    }
}
