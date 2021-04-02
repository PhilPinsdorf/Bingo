package de.rexituz.bingo.gamestates;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.packets.Titles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndingState extends GameStates
{
	Main plugin = Main.getPlugin();

	@Override
	public void start()
	{
		Bukkit.broadcastMessage("Starting Ending State");
		for(Player p : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable(){
				@Override
				public void run(){
					p.closeInventory();
			}}.runTaskLater(plugin, 1);
			p.getInventory().clear();
			p.playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 100, 1);
			Titles.sendTitle(p, plugin.getWinningTeam().getColor() + "Team " + plugin.getWinningTeam().getName(), ChatColor.GRAY + "hat gewonnen!", 20, 3 * 20, 20);
			if (!(new Location(Bukkit.getWorld("lobby"), 242.5, 53, 1348.5, -90, 0)).getChunk().isLoaded())
			{
				(new Location(Bukkit.getWorld("lobby"), 242.5, 53, 1348.5, -90, 0)).getChunk().load();
			}
			p.teleport(new Location(Bukkit.getWorld("lobby"), 242.5, 53, 1348.5, -90, 0));
		}
	}

	@Override
	public void stop()
	{
		Bukkit.broadcastMessage("Stopping Ending State");
		plugin.getGameStateManager().stopCurrentGameState();
		Main.getPlugin().onDisable();
	}



}
