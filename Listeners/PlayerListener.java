package com.piraterevenge.PirateWars.Listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.piraterevenge.PirateWars.PirateWars;
import com.piraterevenge.PirateWars.Arena.MapManager;
import com.piraterevenge.PirateWars.Data.DataManager;
import com.piraterevenge.PirateWars.GameController.Game.State;
import com.piraterevenge.PirateWars.GameController.Game;
import com.piraterevenge.PirateWars.GameController.GameManager;
import com.piraterevenge.PirateWars.Stats.StatsManager;

import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener
{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player p = e.getEntity();
		Player killer = p.getKiller();
		int damagerTeam = -1;
		int entTeam = -2;
		
		if(GameManager.getInstance().isPlayerInGame(p)){
			
			Game g = GameManager.getInstance().getGameofPlayer(p);
			
			if(g.getState() != State.LookingForPlayers){
				e.getDrops().clear();
				StatsManager.getInstance().getPlayer(p).incrementDeaths();
			}
			
			if(GameManager.getInstance().isPlayerInGame(killer)){
				
				damagerTeam = GameManager.getInstance().getGameofPlayer((Player) killer).getTeamOfPlayer((Player) killer);
				entTeam = GameManager.getInstance().getGameofPlayer((Player) p).getTeamOfPlayer((Player) p);
				
				if(damagerTeam != entTeam)
					StatsManager.getInstance().getPlayer(killer).incrementKills();
			}
		}

	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{
		Player p = e.getPlayer();

		if(GameManager.getInstance().isPlayerInGame(p)){
			Game g = GameManager.getInstance().getGameofPlayer(p);
			if(g.getState() != State.LookingForPlayers){
				Bukkit.getScheduler().scheduleSyncDelayedTask(PirateWars.getInstance(), new Runnable() {
				    public void run() {
				    	GameManager.getInstance().teleportPlayerToSpawn(p);
				    	GameManager.getInstance().getGameofPlayer(p).givePlayerClassKit(p);
				    	GameManager.getInstance().getGameofPlayer(p).setPlayerScoreboard(p);
				    }
				}, 1L);
			}
		}
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e)
	{
		Player p = e.getPlayer();

		if(GameManager.getInstance().isPlayerInGame(p) && !p.isOp() && GameManager.getInstance().getGameofPlayer(p).getState() != State.LookingForPlayers && e.getMessage() != "release"){
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You cannot do commands while in a Pirate Wars match.");
		}
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e)
	{
		final Entity ent = e.getEntity();
		Entity damager = e.getDamager();
		Game g = null;
		int damagerTeam = -1;
		int entTeam = -2;
		
		if(ent instanceof Player && damager instanceof Player)
		{
			if(GameManager.getInstance().isPlayerInGame((Player) ent) && GameManager.getInstance().isPlayerInGame((Player) damager) ){
				damagerTeam = GameManager.getInstance().getGameofPlayer((Player) damager).getTeamOfPlayer((Player) damager);
				entTeam = GameManager.getInstance().getGameofPlayer((Player) ent).getTeamOfPlayer((Player) ent);
				g = GameManager.getInstance().getGameofPlayer((Player) damager);
				if(entTeam == damagerTeam){
					damager.sendMessage(ChatColor.RED + "Dont attack your own teammates!");
					e.setCancelled(true);
				}
				else if(g.getState() == State.WinnerFound)
				{
					e.setCancelled(true);
				}
			}
		}
		else if(ent instanceof Player && damager instanceof Projectile){
			damager = (Entity) ((Projectile)damager).getShooter();
			
			if(damager instanceof Player){
				if(GameManager.getInstance().isPlayerInGame((Player) ent) && GameManager.getInstance().isPlayerInGame((Player) damager) ){
					damagerTeam = GameManager.getInstance().getGameofPlayer((Player) damager).getTeamOfPlayer((Player) damager);
					entTeam = GameManager.getInstance().getGameofPlayer((Player) ent).getTeamOfPlayer((Player) ent);
					
					if(entTeam == damagerTeam){
						damager.sendMessage(ChatColor.RED + "Dont attack your own teammates!");
						e.setCancelled(true);
					}
					
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(e.getClickedBlock() == null) return;
		if(e.getClickedBlock().getState() instanceof Sign)
		{
			handleSignInteract(e.getPlayer(), e.getClickedBlock());
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		if(GameManager.getInstance().getSoloQueue().contains(p)){
			GameManager.getInstance().removePlayerFromSoloQueue(e.getPlayer());
		}
		else if(GameManager.getInstance().isPlayerInGame(p)){
			GameManager.getInstance().getGameofPlayer(p).playerQuit(p);
		}

	}
	
	private void handleSignInteract(Player p, Block b)
	{
		Sign sign = (Sign) b.getState();
		if(DataManager.getInstance().getLobbySigns().contains(b.getLocation()))
		{
			if(sign.getLine(1).compareToIgnoreCase("Solo Queue") >= 0)
			{
				GameManager.getInstance().addPlayerToSoloQueue(p);
			}
			else if(sign.getLine(1).compareToIgnoreCase("Crew Queue") >= 0)
			{
				GameManager.getInstance().addPlayerToCrewQueue(p);
			}
			else
			{
				PirateWars.logger.log(Level.INFO, "Unhandled Lobby Type Interact: " + sign.getLine(1));
			}	
		}
		else if(DataManager.getInstance().getClassSigns().contains(b.getLocation()))
		{
			if(GameManager.getInstance().isPlayerInGame(p)){
				if(sign.getLine(1).equals("Knight"))
				{
					GameManager.getInstance().getGameofPlayer(p).setPlayerClass(p,"Knight");
				}
				else if(sign.getLine(1).equals("Musketeer"))
				{
					GameManager.getInstance().getGameofPlayer(p).setPlayerClass(p,"Musketeer");
				}
				else if(sign.getLine(1).equals("Demolition"))
				{
					GameManager.getInstance().getGameofPlayer(p).setPlayerClass(p,"Demolition");
				}
				else if(sign.getLine(1).equals("Engineer"))
				{
					GameManager.getInstance().getGameofPlayer(p).setPlayerClass(p,"Engineer");
				}
				else if(sign.getLine(1).equals("Scout"))
				{
					GameManager.getInstance().getGameofPlayer(p).setPlayerClass(p,"Scout");
				}
				else
				{
					PirateWars.logger.log(Level.INFO, "Unhandled Class Type Interact: " + sign.getLine(1));
				}
			}
		}
		else if(sign.getLine(1).equals(ChatColor.ITALIC + "Leave Queue"))
		{
			if(GameManager.getInstance().getSoloQueue().contains(p)){
				GameManager.getInstance().removePlayerFromSoloQueue(p);
			}
			else if(GameManager.getInstance().isPlayerInGame(p))
			{
				GameManager.getInstance().getGameofPlayer(p).playerQuit(p);
			}
		}
		else if(sign.getLine(1).equals(ChatColor.ITALIC + "Get Stats"))
		{
			StatsManager.getInstance().displayPlayerStats(p);
		}
	}
}
