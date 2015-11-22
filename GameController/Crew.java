package com.piraterevenge.PirateWars.GameController;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Crew {

	private String name;
	private List<String> members = new ArrayList<String>();
	
	public Crew(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public List<String> getMembers() {
		return members;
	}
	
	public void addMember(String name){
		if(!members.contains(name)){
			members.add(name);
		}
	}
	
	public void removeMember(String name){
		if(members.indexOf(name) >= 0){
			if(members.indexOf(name) == 0){
				CrewManager.getInstance().removeCrew(this.getName());
			}else{
				members.remove(members.indexOf(name));
			}
		}
	}
	
	public boolean isPlayerInCrew(String name){
		boolean flag = false;
		
		for(String n: members){
			if(n.equals(name)){
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	public boolean isPlayerLeader(String name){
		if(members.get(0).equals(name)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void displayCrew(Player p){
		p.sendMessage(ChatColor.GREEN + name);
		p.sendMessage(ChatColor.GREEN + "Crew List:");
		for(int i = 0; i < members.size(); i++){
			if(i == 0){
				p.sendMessage(ChatColor.GREEN + "L:" + members.get(i));
			}else{
				p.sendMessage(ChatColor.GREEN + "M:" + members.get(i));
			}
		}
	}
	
	public void setLeader(String newLeader){
		if(isPlayerInCrew(newLeader)){
			int newLeaderIndex = members.indexOf(newLeader);
			members.set(newLeaderIndex, members.get(0));
			members.set(0, newLeader);
		}
	}
	
	
	
}
