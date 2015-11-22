package com.piraterevenge.PirateWars.Listeners;

import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.Data.DataManager;
import com.piraterevenge.PirateWars.GameController.GameManager;
import com.piraterevenge.PirateWars.Stats.StatsManager;

public class BlockListener implements Listener 
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		boolean isCore = MapManager.getInstance().isCoreLoc(e.getBlock().getLocation());
		boolean isOutterCore = MapManager.getInstance().isOutterCoreLoc(e.getBlock().getLocation());
		
		if(DataManager.getInstance().getLobbySigns().contains(e.getBlock().getLocation()))
		{
			e.getPlayer().sendMessage("Lobby Sign removed.");
			DataManager.getInstance().getLobbySigns().remove(e.getBlock().getLocation());
		}else if(DataManager.getInstance().getClassSigns().contains(e.getBlock().getLocation()))
		{
			e.getPlayer().sendMessage("Class Sign removed.");
			DataManager.getInstance().getClassSigns().remove(e.getBlock().getLocation());
		}else if(isCore || isOutterCore){
			GameManager.getInstance().getGameofPlayer(e.getPlayer()).attackCore(e.getPlayer(), e, e.getBlock().getLocation());
		}
	}
	
	@EventHandler
	public void onBlockExplode(BlockExplodeEvent e)
	{
		
		if(e.getBlock().getLocation().getWorld().getName() != "PirateWars"){
			return;
		}
		
		boolean isCore = MapManager.getInstance().isNearCore(e.getBlock().getLocation());
		
		if(isCore){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent sign) {
		Player player = sign.getPlayer();

		if(sign.getLine(0).equalsIgnoreCase("lobby"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			DataManager.getInstance().getLobbySigns().add(sign.getBlock().getLocation());
			sign.setLine(0, ChatColor.DARK_RED + "Pirate Wars");
			sign.setLine(3, ChatColor.GREEN + "Click to Join!");
			if(sign.getLine(1).equalsIgnoreCase("solo"))
			{
				sign.setLine(1,"Solo Queue");
			}
			else if(sign.getLine(1).equalsIgnoreCase("crew"))
			{
				sign.setLine(1,"Crew Queue");
			}
			else
			{
				sign.setLine(1,"Solo Queue");
			}
			sign.getBlock().getState().update();
		} 
		else if(sign.getLine(0).equalsIgnoreCase("selectclass"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			DataManager.getInstance().getClassSigns().add(sign.getBlock().getLocation());
			sign.setLine(0, ChatColor.GREEN + "Select Class");
			sign.setLine(3, ChatColor.ITALIC + "1 per team!");

			if(sign.getLine(1).equalsIgnoreCase("engineer"))
			{
				sign.setLine(1,"Engineer");
			}
			else if(sign.getLine(1).equalsIgnoreCase("demolition"))
			{
				sign.setLine(1,"Demolition");
			}
			else if(sign.getLine(1).equalsIgnoreCase("knight"))
			{
				sign.setLine(1,"Knight");
			}
			else if(sign.getLine(1).equalsIgnoreCase("musketeer"))
			{
				sign.setLine(1,"Musketeer");
			}
			else if(sign.getLine(1).equalsIgnoreCase("scout"))
			{
				sign.setLine(1,"Scout");
			}
			else
			{
				sign.setLine(1,"Engineer");
			}
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("pwleave"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			sign.setLine(0, ChatColor.DARK_RED + "Pirate Wars");
			sign.setLine(1, ChatColor.ITALIC + "Leave Queue");
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("pwstats"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			sign.setLine(0, ChatColor.DARK_RED + "Pirate Wars");
			sign.setLine(1, ChatColor.ITALIC + "Get Stats");
			sign.setLine(3, ChatColor.GREEN + "Click for Stats!");
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("tkills"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopKillsName(rank);
			int kills = StatsManager.getInstance().getTopKillsVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopKillsSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". Most Kills");
			sign.setLine(1, name);
			sign.setLine(2, "Kills: " + kills);
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("tdeaths"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopDeathsName(rank);
			int deaths = StatsManager.getInstance().getTopDeathsVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopDeathsSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". Most Deaths");
			sign.setLine(1, name);
			sign.setLine(2, "Deaths: " + deaths);
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("twins"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopWinsName(rank);
			int wins = StatsManager.getInstance().getTopWinsVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopWinsSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". Most Wins");
			sign.setLine(1, name);
			sign.setLine(2, "Wins: " + wins);
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("tloses"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopLosesName(rank);
			int loses = StatsManager.getInstance().getTopLosesVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopLosesSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". Most Loses");
			sign.setLine(1, name);
			sign.setLine(2, "Loses: " + loses);
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("tkdr"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopKDRName(rank);
			float kdr = StatsManager.getInstance().getTopKDRVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopKDRSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". KDR");
			sign.setLine(1, name);
			sign.setLine(2, "KDR: " + kdr);
			sign.getBlock().getState().update();
		}
		else if(sign.getLine(0).equalsIgnoreCase("tcore"))
		{
			if(!player.hasPermission("piratewars.admin"))
			{
				player.sendMessage("You dont have permission to do that.");
				return;
			}
			
			if(sign.getLine(1) == null){
				player.sendMessage("rank not specified.");
				return;
			}
			
			int rank = Integer.parseInt(sign.getLine(1));
			String name = StatsManager.getInstance().getTopCoreName(rank);
			int coredam = StatsManager.getInstance().getTopCoreVal(rank);
			
			if(name == null){
				name = "None";
			}
			
			StatsManager.getInstance().addTopCoreSign(sign.getBlock().getLocation(), rank);
			
			sign.setLine(0, ChatColor.GREEN + "#" + rank + ". Core Damage");
			sign.setLine(1, name);
			sign.setLine(2, "CoreDam: " + coredam);
			sign.getBlock().getState().update();
		}
	}
}
