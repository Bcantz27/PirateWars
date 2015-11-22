package com.piraterevenge.PirateWars.Listeners;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.piraterevenge.PirateWars.Arena.Map;
import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.Data.SaveLoad;
import com.piraterevenge.PirateWars.GameController.Crew;
import com.piraterevenge.PirateWars.GameController.CrewManager;
import com.piraterevenge.PirateWars.GameController.GameManager;
import com.piraterevenge.PirateWars.Stats.StatsManager;

public class CommandListener implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
	{
		boolean invalid = false;
		Player p = null;
		
		if(sender instanceof Player)
		{
			p = (Player) sender;
		}
		
		if(args[0].equalsIgnoreCase("leave"))
		{
			GameManager.getInstance().removePlayerFromSoloQueue(p);
			return true;
		}
		else if(args[0].equalsIgnoreCase("crew"))
		{
			if(args[1].equalsIgnoreCase("create"))
			{
				String crewName = args[2];
				if(!CrewManager.getInstance().isCrew(crewName)){
					Crew c = new Crew(crewName);
					c.addMember(p.getName());
					CrewManager.getInstance().addCrew(new Crew(crewName));
				}else{
					p.sendMessage(ChatColor.RED + "Crew name already taken.");
				}
			}
			else if(args[1].equalsIgnoreCase("create"))
			{
				String crewName = args[2];
				if(!CrewManager.getInstance().isCrew(crewName)){
					CrewManager.getInstance().addCrew(new Crew(crewName));
					p.sendMessage(ChatColor.GREEN + "Crew Created.");
				}else{
					p.sendMessage(ChatColor.RED + "Crew name already taken.");
				}
				return false;
			}
			else if(args[1].equalsIgnoreCase("leave"))
			{
				if(CrewManager.getInstance().isPlayerInCrew(p.getName())){
					CrewManager.getInstance().getPlayersCrew(p.getName()).removeMember(p.getName());
					p.sendMessage(ChatColor.GREEN + "You have left your crew.");
				}else{
					p.sendMessage(ChatColor.RED + "You are not in a crew.");
				}
				return false;
			}
			else if(args[1].equalsIgnoreCase("addmember"))
			{
				String member = args[2];
				if(CrewManager.getInstance().isPlayerInCrew(p.getName())){
					if(CrewManager.getInstance().getPlayersCrew(p.getName()).isPlayerLeader(p.getName())){
						CrewManager.getInstance().getPlayersCrew(p.getName()).addMember(member);
						p.sendMessage(ChatColor.GREEN + "Member added.");
					}else{
						p.sendMessage(ChatColor.RED + "You are not the leader of your crew.");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You are not in a crew.");
				}
				return false;
			}
			else if(args[1].equalsIgnoreCase("removemember"))
			{
				String member = args[2];
				if(CrewManager.getInstance().isPlayerInCrew(p.getName())){
					if(CrewManager.getInstance().getPlayersCrew(p.getName()).isPlayerLeader(p.getName())){
						CrewManager.getInstance().getPlayersCrew(p.getName()).removeMember(member);;
						p.sendMessage(ChatColor.GREEN + "Member Removed.");
					}else{
						p.sendMessage(ChatColor.RED + "You are not the leader of your crew.");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You are not in a crew.");
				}
				return false;
			}
			else if(args[1].equalsIgnoreCase("passleader"))
			{
				String member = args[2];
				if(CrewManager.getInstance().isPlayerInCrew(p.getName())){
					if(CrewManager.getInstance().getPlayersCrew(p.getName()).isPlayerLeader(p.getName())){
						if(CrewManager.getInstance().getPlayersCrew(p.getName()).isPlayerInCrew(member)){
							CrewManager.getInstance().getPlayersCrew(p.getName()).setLeader(member);
							p.sendMessage(ChatColor.GREEN + "Leadership passed.");
						}else{
							p.sendMessage(ChatColor.RED + "Player is not in crew.");
						}
					}else{
						p.sendMessage(ChatColor.RED + "You are not the leader of your crew.");
					}
				}else{
					p.sendMessage(ChatColor.RED + "You are not in a crew.");
				}
				return false;
			}else{
				if(CrewManager.getInstance().isPlayerInCrew(p.getName())){
					CrewManager.getInstance().getPlayersCrew(p.getName()).displayCrew(p);
				}else{
					p.sendMessage(ChatColor.RED + "You are not in a crew.");
				}
			}
			return false;
		}
		
		if(!p.hasPermission("piratewars.admin"))
			return false;
		
		if(args[0].equalsIgnoreCase("reload"))
		{
			SaveLoad.getInstance().saveAll();
			SaveLoad.getInstance().resetAll();
			SaveLoad.getInstance().loadAll();
		}
		else if(args[0].equalsIgnoreCase("top"))
		{
			if(args[1].equalsIgnoreCase("kills"))
			{
				StatsManager.getInstance().displayTopKills(p);
			}
			else if(args[1].equalsIgnoreCase("deaths"))
			{
				StatsManager.getInstance().displayTopDeaths(p);
			}
			else if(args[1].equalsIgnoreCase("wins"))
			{
				StatsManager.getInstance().displayTopWins(p);
			}
			else if(args[1].equalsIgnoreCase("loses"))
			{
				StatsManager.getInstance().displayTopLoses(p);
			}
			else if(args[1].equalsIgnoreCase("kdr"))
			{
				StatsManager.getInstance().displayTopKDR(p);
			}
			else
			{
				invalid = true;
			}
		}
		else if(args[0].equalsIgnoreCase("set"))
		{
			if(args[1].equalsIgnoreCase("endspawn"))
			{
				MapManager.getInstance().setEndSpawnPoint(p.getLocation());
			}
			else
			{
				invalid = true;
			}
			
			if(!invalid)
				sender.sendMessage(ChatColor.GOLD+"End point has been editted.");
		}
		else if(args[0].equalsIgnoreCase("map"))
		{
			if(args[1].equalsIgnoreCase("add"))
			{
				if(args.length > 3)
				{
					MapManager.getInstance().getMapList().add(new Map(args[2],args[3]));
					sender.sendMessage(ChatColor.GREEN + "" + args[2] + " has been added.");
				}
				else
				{
					invalid = true;
				}
			}
			else if(args[1].equalsIgnoreCase("remove"))
			{
				if(args.length > 2)
				{
					MapManager.getInstance().removeMap(args[2]);
					sender.sendMessage(ChatColor.GREEN+""+ args[2]+ " has been removed.");
				}
			}
			else if(args[1].equalsIgnoreCase("list"))
			{
				MapManager.getInstance().printMapList(p);
			}
			else if(args[1].equalsIgnoreCase("store"))
			{
				if(args.length > 2)
				{
					MapManager.getInstance().getMap(args[2]).store();
				}
			}
			else if(args[1].equalsIgnoreCase("restore"))
			{
				if(args.length > 2)
				{
					MapManager.getInstance().getMap(args[2]).regenerate();
				}
			}
			else if(args[1].equalsIgnoreCase("set"))
			{
				if(args.length > 2)
				{
					MapManager.getInstance().getMap(args[2]);
					if(args[3].equalsIgnoreCase("mainregion"))
					{
						if(args.length > 3)
						{
							MapManager.getInstance().getMap(args[2]).setMapRegion(args[4]);
						}
						else
						{
							invalid = true;
						}
					}
					else if(args[3].equalsIgnoreCase("class"))
					{
						if(args.length > 3)
						{
							MapManager.getInstance().getMap(args[2]).setClassSelect(p.getLocation());
						}
						else
						{
							invalid = true;
						}
					}
					else if(args[3].equalsIgnoreCase("red"))
					{
						if(args[4].equalsIgnoreCase("core"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setRedCoreLoc(p.getTargetBlock((HashSet<Byte>) null, 5).getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else if(args[4].equalsIgnoreCase("outterCore"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setRedOutterCoreLoc(p.getTargetBlock((HashSet<Byte>) null, 5).getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else if(args[4].equalsIgnoreCase("spawn"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setRedSpawn(p.getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else
						{
							invalid = true;
						}
					}
					else if(args[3].equalsIgnoreCase("blue"))
					{
						if(args[4].equalsIgnoreCase("core"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setBlueCoreLoc(p.getTargetBlock((HashSet<Byte>) null, 5).getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else if(args[4].equalsIgnoreCase("outterCore"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setBlueOutterCoreLoc(p.getTargetBlock((HashSet<Byte>) null, 5).getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else if(args[4].equalsIgnoreCase("spawn"))
						{
							if(args.length > 4)
							{
								MapManager.getInstance().getMap(args[2]).setBlueSpawn(p.getLocation());
							}
							else
							{
								invalid = true;
							}
						}
						else
						{
							invalid = true;
						}
					}
					else
					{
						invalid = true;
					}
					
					if(!invalid)
						sender.sendMessage(ChatColor.GOLD+""+ args[2]+ " has been editted.");
				}
				else
				{
					invalid = true;
				}
			}
			else
			{
				invalid = true;
			}
		}
		
		if(invalid)
		{
			sender.sendMessage(ChatColor.RED+"Invalid arguments.");
		}
		return false;
	}
}
