package com.piraterevenge.PirateWars.GameController;

import java.util.ArrayList;
import java.util.List;

public class CrewManager {

	private static CrewManager instance;
	
	private List<Crew> crewList = new ArrayList<Crew>();
	
	public CrewManager(){
		instance = this;
	}
	
	public static CrewManager getInstance(){
		return instance;
	}
	
    public List<Crew> getCrewList(){
    	return crewList;
    }
    
    public void addCrew(Crew newCrew){
    	if(!isCrew(newCrew.getName())){
    		crewList.add(newCrew);
    	}
    }
    
    public void removeCrew(String name){
    	Crew c = getCrew(name);
    	
    	if(c != null){
    		crewList.remove(crewList.indexOf(name));
    	}
    }
    
    public boolean isCrew(String name){
    	boolean flag = false;
    	
    	for(Crew crew: crewList){
    		if(crew.getName().equals(name)){
    			flag = true;
    			break;
    		}
    	}
    	
    	return flag;
    }
    
    public Crew getCrew(String name){
    	Crew crew = null;
    	
    	if(isCrew(name)){
        	for(Crew c: crewList){
        		if(c.getName().equals(name)){
        			crew = c;
        			break;
        		}
        	}
    	}
    	
    	return crew;
    }
    
    public boolean isPlayerInCrew(String name){
    	boolean flag = true;
    	
    	for(Crew c:crewList)
    	{
    		if(c.isPlayerInCrew(name)){
    			flag = true;
    			break;
    		}
    	}
    	
    	return flag;
    }
    
    public Crew getPlayersCrew(String name){
    	Crew crew = null;
    	
    	if(!isPlayerInCrew(name)){
    		return crew;
    	}
    	
    	for(Crew c:crewList)
    	{
    		if(c.isPlayerInCrew(name)){
    			crew = c;
    			break;
    		}
    	}
    	
    	return crew;
    }
    
}
