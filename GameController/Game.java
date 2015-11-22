package com.piraterevenge.PirateWars.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.piraterevenge.DevilFruits;
import com.piraterevenge.PirateWars.PirateWars;
import com.piraterevenge.PirateWars.Arena.Map;
import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.Classes.ClassManager;
import com.piraterevenge.PirateWars.Classes.PlayerClass;
import com.piraterevenge.PirateWars.Stats.StatsManager;

public class Game {
	
	public Type type;
	
	public Team team1 = new Team(1);
	public Team team2 = new Team(2);
	
	public int lastSizeCheck;
	
	public static int minPlayers = 6;
	public static int maxPlayers = 10;
	
	public HashMap<String,ArrayList<ItemStack>> playerJoinInventories = new HashMap<String,ArrayList<ItemStack>>();
	private ConcurrentHashMap<String,Integer> fruits = new ConcurrentHashMap<String,Integer>(); // PlayerName, Fruit ID
	
	public HashMap<String,String> playerClasses = new HashMap<String,String>();
	
	public State state;
	
	private Team winner;
	
	DevilFruits df = DevilFruits.instance;
	
	public Map map;
	
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	Scoreboard board = manager.getNewScoreboard();
	
	public Game(Type type)
	{
		this.map = MapManager.getInstance().getRandomMap();
		this.type = type;
		state = State.Idle;
		lastSizeCheck = 0;
	}
	
	public Game(Type type, Map map)
	{
		this.map = map;
		this.type = type;
		state = State.Idle;
		lastSizeCheck = 0;
	}
	
	public HashMap<String,String> getPlayerClasses(){
		return playerClasses;
	}
	
	public void setPlayerClass(Player p, String className){
		int team = getTeamOfPlayer(p);
		Team teamList = null;
		
		if(team == 1){
			teamList = team1;
		}else if(team == 2){
			teamList = team2;
		}
		
		if(playerClasses.get(p.getName()) != null){
			playerClasses.remove(p.getName());
		}
		
		List<Player> players = teamList.getTeam();
		
		for(Player p2: players){
			if(playerClasses.get(p2.getName()) == className){
				p.sendMessage(ChatColor.RED + "" + p2.getName() + " has already selected that class.");
				return;
			}
		}
		
		playerClasses.put(p.getName(), className);
		p.sendMessage(ChatColor.GREEN + "Your class has been set as " + className + ".");
		this.sendTeamMessage(team, ChatColor.GREEN + p.getName() + " has selected " + className + ".");
		
	}
	
	public void checkEmptyClass(){
		ArrayList<String> redClasses = new ArrayList<String>() {{
			add("Knight");
			add("Musketeer");
			add("Demolition");
			add("Engineer");
			add("Scout");
		}};
		ArrayList<String> blueClasses = new ArrayList<String>() {{
			add("Knight");
			add("Musketeer");
			add("Demolition");
			add("Engineer");
			add("Scout");
		}};
		
		ArrayList<String> redNoClass = new ArrayList<String>();
		ArrayList<String> blueNoClass = new ArrayList<String>();
		
		for(Player p: team1.getTeam())
		{
			if(playerClasses.get(p.getName()) != null){
				redClasses.remove(redClasses.indexOf(playerClasses.get(p.getName())));
			}else{
				redNoClass.add(p.getName());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(playerClasses.get(p.getName()) != null){
				blueClasses.remove(blueClasses.indexOf(playerClasses.get(p.getName())));
			}else{
				blueNoClass.add(p.getName());
			}
		}
		
		for(String pName: redNoClass){
			if(playerClasses.get(pName) == null){
				playerClasses.put(pName, redClasses.get(0));
				redClasses.remove(0);
			}
		}
		for(String pName: blueNoClass){
			if(playerClasses.get(pName) == null){
				playerClasses.put(pName, blueClasses.get(0));
				blueClasses.remove(0);
			}
		}
	}
	
	public void addPlayer(Player player)
	{
		if(getSize() < maxPlayers)
		{
			this.sendAllPlayersMessage(ChatColor.GREEN + player.getName() + " has joined the match.");
			
			if(team1.getTeam().size() <= team2.getTeam().size())
			{
				team1.getTeam().add(player);
			}
			else
			{
				team2.getTeam().add(player);
			}
			if((minPlayers - getSize()) > 0){
				player.sendMessage(ChatColor.GREEN + "A match has been found! Waiting for " + (minPlayers - getSize()) +" more players!");
			}else{
				player.sendMessage(ChatColor.GREEN + "A match has been found!");
			}
		}
	}
	
	public void addPlayer(int team, Player player)
	{
		if(getSize() < maxPlayers)
		{

			this.sendAllPlayersMessage(ChatColor.GREEN + player.getName() + " has joined the match.");
			
			if(team == 1)
			{
				team1.getTeam().add(player);
			}
			else if(team == 2)
			{
				team2.getTeam().add(player);
			}
			if((minPlayers - getSize()) > 0){
				player.sendMessage(ChatColor.GREEN + "A match has been found! Waiting for " + (minPlayers - getSize()) +" more players!");
			}else{
				player.sendMessage(ChatColor.GREEN + "A match has been found!");
			}
		}
	}
	
	public void setPlayerScoreboard(Player p){
		p.setScoreboard(board);
	}
	
	public void setupScoreboard(){
		Objective objective = board.registerNewObjective("gameinfo", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.RED + "Pirate Wars");
		Score gamestage = objective.getScore(ChatColor.GREEN + "Stage: " + ChatColor.WHITE + "Building");
		gamestage.setScore(7);
		
		Score redteam = objective.getScore(ChatColor.WHITE + "------" + ChatColor.RED + "RED TEAM"  + ChatColor.WHITE +  "------");
		Score redouttercore = objective.getScore(ChatColor.RED + "OutCore HP: " + ChatColor.GREEN + "||||||||||||||||||||");
		Score redcore = objective.getScore(ChatColor.RED + "InnCore HP: " + ChatColor.GREEN + "||||||||||||||||||||");
		redcore.setScore(5);
		redouttercore.setScore(4);
		redteam.setScore(6);

		Score blueteam = objective.getScore(ChatColor.WHITE + "------" + ChatColor.BLUE + "BLUE TEAM"  + ChatColor.WHITE +  "-----");
		Score blueouttercore = objective.getScore(ChatColor.BLUE + "OutCore HP: " + ChatColor.GREEN + "||||||||||||||||||||");
		Score bluecore = objective.getScore(ChatColor.BLUE + "InnCore HP: " + ChatColor.GREEN + "||||||||||||||||||||");
		bluecore.setScore(2);
		blueouttercore.setScore(1);
		blueteam.setScore(3);
		
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(board);
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(board);
			}
		}
	}
	
	public void updateScoreboard(){
		board.getObjective(DisplaySlot.SIDEBAR).unregister();
		Objective objective = board.registerNewObjective("gameinfo", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.RED + "Pirate Wars");
		
		String stage = state.toString();
		String redoutterhp = ChatColor.RED + "OutCore HP: ";
		String redinnerhp = ChatColor.RED + "InnCore HP: ";
		String blueoutterhp = ChatColor.BLUE + "OutCore HP: ";
		String blueinnerhp = ChatColor.BLUE + "InnCore HP: ";
		
		redoutterhp += ChatColor.GREEN;
		for(int i = 0; i < map.getRedOutterCoreHealth(); i ++){
			redoutterhp += "|";
		}
		redoutterhp += ChatColor.RED;
		for(int i = 0; i < 20-map.getRedOutterCoreHealth(); i ++){
			redoutterhp += "|";
		}
		
		redinnerhp += ChatColor.GREEN;
		for(int i = 0; i < map.getRedCoreHealth(); i ++){
			redinnerhp += "|";
		}
		redinnerhp += ChatColor.RED;
		for(int i = 0; i < 20-map.getRedCoreHealth(); i ++){
			redinnerhp += "|";
		}
		
		blueoutterhp += ChatColor.GREEN;
		for(int i = 0; i < map.getBlueOutterCoreHealth(); i ++){
			blueoutterhp += "|";
		}
		blueoutterhp += ChatColor.RED;
		for(int i = 0; i < 20-map.getBlueOutterCoreHealth(); i ++){
			blueoutterhp += "|";
		}
		
		blueinnerhp += ChatColor.GREEN;
		for(int i = 0; i < map.getBlueCoreHealth(); i ++){
			blueinnerhp += "|";
		}
		blueinnerhp += ChatColor.RED;
		for(int i = 0; i < 20-map.getBlueCoreHealth(); i ++){
			blueinnerhp += "|";
		}
		
		Score gamestage = objective.getScore(ChatColor.GREEN + "Stage: " + ChatColor.WHITE + stage);
		gamestage.setScore(7);
		
		Score redteam = objective.getScore(ChatColor.WHITE + "------" + ChatColor.RED + "RED TEAM"  + ChatColor.WHITE +  "------");
		Score redouttercore = objective.getScore(redoutterhp);
		Score redcore = objective.getScore(redinnerhp);
		redcore.setScore(5);
		redouttercore.setScore(4);
		redteam.setScore(6);

		Score blueteam = objective.getScore(ChatColor.WHITE + "------" + ChatColor.BLUE + "BLUE TEAM"  + ChatColor.WHITE +  "-----");
		Score blueouttercore = objective.getScore(blueoutterhp);
		Score bluecore = objective.getScore(blueinnerhp);
		bluecore.setScore(2);
		blueouttercore.setScore(1);
		blueteam.setScore(3);
		
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(board);
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(board);
			}
		}
	}
	
	public void clearScoreboards(){
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(manager.getNewScoreboard());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.setScoreboard(manager.getNewScoreboard());
			}
		}
	}
	
	public State getState()
	{
		return state;
	}
	
	public void setState(State s)
	{
		state = s;
	}
	
	public Team getWinner()
	{
		return winner;
	}
	
	public Team getTeam1()
	{
		return team1;
	}
	
	public Team getTeam2()
	{
		return team2;
	}
	
	public Map getMap(){
		return map;
	}
	
	public void attackCore(Player p, BlockBreakEvent event, Location loc){
		if(map.getRedCoreLoc().equals(loc)){
			map.attackRedCore(p, this, event);
		}else if(map.getBlueCoreLoc().equals(loc)){
			map.attackBlueCore(p, this, event);
		}else if(map.getRedOutterCoreLoc().equals(loc)){
			map.attackRedOutterCore(p, this, event);
		}else if(map.getBlueOutterCoreLoc().equals(loc)){
			map.attackBlueOutterCore(p, this, event);
		}
		updateScoreboard();
	}
	
	public void clearAllPlayersInventories(){
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
				p.getInventory().clear();
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
				p.getInventory().clear();
			}
		}
	}
	
	public void playerQuit(Player p){
		ArrayList<ItemStack> itemList = null;
		ItemStack[] invContents = null;
		int playerTeam = this.getTeamOfPlayer(p);
		
		this.sendAllPlayersMessage(ChatColor.RED + p.getName() + " has left the match.");
		
		if(playerTeam == 1){
			getTeam1().getTeam().remove(p);
			if(getTeam1().getTeam().size() == 0 && (getState() == State.Building || getState() == State.Fighting)){
				StatsManager.getInstance().getPlayer(p).incrementLoses();
				this.sendAllPlayersMessage(ChatColor.RED + "Red Team has no players left!");
				setWinner(2);
			}
		}
		else if(playerTeam == 2)
		{
			getTeam2().getTeam().remove(p);
			if(getTeam2().getTeam().size() == 0&& (getState() == State.Building || getState() == State.Fighting)){
				StatsManager.getInstance().getPlayer(p).incrementLoses();
				this.sendAllPlayersMessage(ChatColor.RED + "Blue Team has no players left!");
				setWinner(1);
			}
		}

		if(getState() != State.LookingForPlayers){
			p.getInventory().setHelmet(null);
			p.getInventory().setChestplate(null);
			p.getInventory().setLeggings(null);
			p.getInventory().setBoots(null);
			p.getInventory().clear();
			
			itemList = playerJoinInventories.get(p.getName());
			invContents = new ItemStack[36];
			if(itemList != null && itemList.size() > 0){
				p.getInventory().setHelmet(itemList.get(0));
				p.getInventory().setChestplate(itemList.get(1));
				p.getInventory().setLeggings(itemList.get(2));
				p.getInventory().setBoots(itemList.get(3));
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);
	
				for(int i = 0; i < 36; i++){
					invContents[i] = itemList.get(i);
				}
	
				p.getInventory().setContents(invContents);
			}
			
			if(fruits.containsKey(p.getName())){
				df.setPlayersFruit(p, fruits.get(p.getName()));
				fruits.remove(p.getName());
			}
			
			p.teleport(MapManager.getInstance().getEndSpawnPoint());
		}
	}
	
	public void storePlayerFruits(){
		
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				if(df.findPlayersFruitId(p) != -1){
					if(!fruits.containsKey(p.getName())){
						fruits.put(p.getName(), df.findPlayersFruitId(p));
						df.removePlayersFruit(p);
					}
					else
					{
						fruits.remove(p.getName());
						fruits.put(p.getName(), df.findPlayersFruitId(p));
						df.removePlayersFruit(p);
					}
				}
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				if(df.findPlayersFruitId(p) != -1){
					if(!fruits.containsKey(p.getName())){
						fruits.put(p.getName(), df.findPlayersFruitId(p));
						df.removePlayersFruit(p);
					}
					else
					{
						fruits.remove(p.getName());
						fruits.put(p.getName(), df.findPlayersFruitId(p));
						df.removePlayersFruit(p);
					}
				}
			}
		}
	}
	
	public void storePlayerInventories(){
		ArrayList<ItemStack> itemList = null;
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				itemList = new ArrayList<ItemStack>();
				itemList.add(p.getInventory().getHelmet());
				itemList.add(p.getInventory().getChestplate());
				itemList.add(p.getInventory().getLeggings());
				itemList.add(p.getInventory().getBoots());
				itemList.addAll(Arrays.asList(p.getInventory().getContents()));
				playerJoinInventories.put(p.getName(), itemList);
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				itemList = new ArrayList<ItemStack>();
				itemList.add(p.getInventory().getHelmet());
				itemList.add(p.getInventory().getChestplate());
				itemList.add(p.getInventory().getLeggings());
				itemList.add(p.getInventory().getBoots());
				itemList.addAll(Arrays.asList(p.getInventory().getContents()));
				playerJoinInventories.put(p.getName(), itemList);
			}
		}
	}
	
	public void restorePlayerFruits(){
		for(Player p: team1.getTeam())
		{
			if(fruits.containsKey(p.getName())){
				df.setPlayersFruit(p, fruits.get(p.getName()));
				fruits.remove(p.getName());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(fruits.containsKey(p.getName())){
				df.setPlayersFruit(p, fruits.get(p.getName()));
				fruits.remove(p.getName());
			}
		}
	}
	
	public void restorePlayerInventories(){
		ArrayList<ItemStack> itemList = null;
		ItemStack[] invContents = null;
		
		for(Player p: team1.getTeam())
		{
			itemList = playerJoinInventories.get(p.getName());
			invContents = new ItemStack[36];
			if(itemList.size() > 0){
				p.getInventory().setHelmet(itemList.get(0));
				p.getInventory().setChestplate(itemList.get(1));
				p.getInventory().setLeggings(itemList.get(2));
				p.getInventory().setBoots(itemList.get(3));
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);

				for(int i = 0; i < 36; i++){
					invContents[i] = itemList.get(i);
				}

				p.getInventory().setContents(invContents);
			}
		}
		for(Player p: team2.getTeam())
		{
			itemList = playerJoinInventories.get(p.getName());
			invContents = new ItemStack[36];
			if(itemList.size() > 0){
				p.getInventory().setHelmet(itemList.get(0));
				p.getInventory().setChestplate(itemList.get(1));
				p.getInventory().setLeggings(itemList.get(2));
				p.getInventory().setBoots(itemList.get(3));
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);
				itemList.remove(0);
				
				for(int i = 0; i < 36; i++){
					invContents[i] = itemList.get(i);
				}
				
				p.getInventory().setContents(invContents);
			}
		}
	}
	
	public boolean tryToStartGame()
	{
		System.out.println("Trying to start game");
		if(getSize() >= minPlayers && getSize() <= maxPlayers)
		{
			if(getSize() != lastSizeCheck)
			{
				return false;
			}
			else
			{
				if(getSize() <= maxPlayers && GameManager.getInstance().getSoloQueue().size() > 0){
					return false;
				}
				sendAllPlayersMessage(ChatColor.GREEN + "Match will begin in 10 secs!");
				Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
				    public void run() {
				    	startClassSelect();
				    }
				}, 20*10L);
				return true;
			}
		}
		else
		{
			sendAllPlayersMessage(ChatColor.GREEN + "Searching for more players. " + getSize() + "/10 Players");
			return false;
		}
	}
	
	public void startClassSelect()
	{
		state = State.ClassSelect;
		storePlayerInventories();
		storePlayerFruits();
		clearAllPlayersInventories();
		teleportAllPlayersToClassSelect();

		sendAllPlayersMessage(ChatColor.RED + "You have 1 min to select a class!");
		sendAllPlayersMessage(ChatColor.RED + "Only 1 of each class per team!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
		    public void run() {
		    	startGame();
		    }
		}, 20*60*1L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
		    public void run() {
		    	sendAllPlayersMessage(ChatColor.RED + "You have 30 secs left.");
		    }
		}, 20*30*1L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
		    public void run() {
		    	sendAllPlayersMessage(ChatColor.RED + "You have 10 secs left.");
		    }
		}, 20*50*1L);
	}
	
	public void givePlayerClassKit(Player p){
		PlayerClass pClass = ClassManager.Type.valueOf(playerClasses.get(p.getName())).getClassType();
		
		List<ItemStack> kit = pClass.getKit(getTeamOfPlayer(p));
		ItemStack[] invContents = new ItemStack[36];
		
		p.getInventory().setHelmet(kit.get(0));
		p.getInventory().setChestplate(kit.get(1));
		p.getInventory().setLeggings(kit.get(2));
		p.getInventory().setBoots(kit.get(3));
		
		for(int i = 4; i < 36; i++){
			if(i <= kit.size()-1){
				invContents[i-4] = kit.get(i);
			}
		}
		
		p.getInventory().setContents(invContents);
	}
	
	public void giveAllPlayersKit(){
		for(Player p: team1.getTeam())
		{
			givePlayerClassKit(p);
		}
		for(Player p: team2.getTeam())
		{
			givePlayerClassKit(p);
		}
	}
	
	public void startGame()
	{
		if(state != State.Building){
			state = State.Building;
			map.putWallUp();
			checkEmptyClass();
			giveAllPlayersKit();
			setupScoreboard();
			teleportAllPlayersToSpawn();
			sendTeamMessage(1,"You are on the " + ChatColor.RED + "Red Team" + ChatColor.WHITE + ".");
			sendTeamMessage(2,"You are on the " + ChatColor.BLUE + "Blue Team" + ChatColor.WHITE + ".");
			sendAllPlayersMessage(ChatColor.RED + "You have 2 mins to prepare for battle!");
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
			    public void run() {
			    	startCombat();
			    }
			}, 20*60*2L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
			    public void run() {
			    	sendAllPlayersMessage(ChatColor.RED + "1 min to prepare for battle!");
			    }
			}, 20*60*1L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
			    public void run() {
			    	sendAllPlayersMessage(ChatColor.RED + "30 secs to prepare for battle!");
			    }
			}, 20*90*1L);
		}
	}
	
	public int getTeamOfPlayer(Player p){
		int team = -1;
		
		if(team1.getTeam().contains(p)){
			team = 1;
		}else if(team2.getTeam().contains(p)){
			team = 2;
		}
		
		return team;
	}
	
	public void setWinner(int team){
		
		if(team == 1){
			sendAllPlayersMessage(ChatColor.RED + "Red Team" + ChatColor.GREEN + " Wins!");
			winner = team1;
			for(Player p: team2.getTeam()){
				StatsManager.getInstance().getPlayer(p).incrementLoses();
			}
		}
		else if(team == 2)
		{
			sendAllPlayersMessage(ChatColor.BLUE + "Blue Team" + ChatColor.GREEN + " Wins!");
			winner = team2;
			for(Player p: team1.getTeam()){
				StatsManager.getInstance().getPlayer(p).incrementLoses();
			}
		}
		state = State.WinnerFound;
		updateScoreboard();
		
		sendAllPlayersMessage(ChatColor.GREEN + "You will be teleported out in 10 secs!");
		Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
		    public void run() {
		    	endGame();
		    }
		}, 20*10L);
	}
	
	public void endGame(){
		state = State.Finished;
		
		clearScoreboards();
		clearAllPlayersInventories();
		teleportAllPlayersToEndSpawn();
		restorePlayerInventories();
		restorePlayerFruits();
		
		for(Player p: winner.getTeam()){
			StatsManager.getInstance().getPlayer(p).incrementWins();
			p.getInventory().addItem(new ItemStack(Material.DIAMOND,10));
			p.getInventory().addItem(new ItemStack(Material.EXP_BOTTLE,32));
			p.sendMessage(ChatColor.GREEN + "Your team WON the match! Your prize is 10 diamonds and 32 xp bottles.");
		}
		
		this.sendAllPlayersMessage(ChatColor.GREEN + "Thank you for participating in the Pirate Wars Beta Testing! Every game you play helps us continue to make it better. If you have any suggestions please visit our website http://www.piraterevenge.net/");
		
		map.resetMap();
		map.regenerate();
		
		StatsManager.getInstance().calculateTopLists();
		GameManager.getInstance().endActiveGame(this);
	}
	
	public void resetGame(){
		team1 = new Team(1);
		team2 = new Team(2);
		winner = null;
		map = null;
	}
	
	private void teleportAllPlayersToSpawn()
	{
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(map.getRedSpawn());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(map.getBlueSpawn());
			}
		}
	}
	
	private void teleportAllPlayersToClassSelect()
	{
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(map.getClassSelectLoc());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(map.getClassSelectLoc());
			}
		}
	}
	
	private void teleportAllPlayersToEndSpawn()
	{
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(MapManager.getInstance().getEndSpawnPoint());
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.teleport(MapManager.getInstance().getEndSpawnPoint());
			}
		}
	}
	
	public void teleportPlayerToSpawn(Player p)
	{
		if(!this.isPlayerPlaying(p)) return;

		if(team1.getTeam().contains(p))
		{
			p.teleport(map.getRedSpawn());
		}
		else if(team2.getTeam().contains(p))
		{
			p.teleport(map.getBlueSpawn());
		}
	}
	
	public void startCombat()
	{
		state = State.Fighting;
		map.putWallDown();
		
		updateScoreboard();
		sendAllPlayersMessage(ChatColor.DARK_RED + "The wall has been dropped - FIGHT!");
		sendAllPlayersMessage(ChatColor.GREEN + "First team to destroy the enemy cores wins!");
	}
	
	public void sendAllPlayersMessage(String message)
	{
		for(Player p: team1.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.sendMessage(message);
			}
		}
		for(Player p: team2.getTeam())
		{
			if(this.isPlayerPlaying(p))
			{
				p.sendMessage(message);
			}
		}
	}
	
	public boolean isPlayerPlaying(Player p)
	{
		boolean flag = false;
		if(team1.getTeam().contains(p) || team2.getTeam().contains(p))
		{
			flag = true;
		}
		return flag;
	}
	
	public void sendTeamMessage(int team, String message)
	{
		if(team == 1)
		{
			for(Player p: team1.getTeam())
			{
				if(this.isPlayerPlaying(p))
				{
					p.sendMessage(message);
				}
			}
		}
		else if(team == 2)
		{
			for(Player p: team2.getTeam())
			{
				if(this.isPlayerPlaying(p))
				{
					p.sendMessage(message);
				}
			}
		}
	}
	
	public void checkSize()
	{
		lastSizeCheck = team1.getTeam().size() + team2.getTeam().size();
	}
	
	public int getSize()
	{
		return team1.getTeam().size() + team2.getTeam().size();
	}
	
	public enum State
	{
		Idle,
		LookingForPlayers,
		ClassSelect,
		Building,
		Fighting,
		WinnerFound,
		Finished;
	}
	
	public static enum Type
	{
		Solo,
		Crew;
	}
}
