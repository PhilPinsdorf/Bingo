package de.rexituz.bingo.gamestates;

import de.rexituz.bingo.gui.IngameItem;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InGameState extends GameStates 
{
    Main plugin = Main.getPlugin();

	@Override
	public void start()
	{
		Bukkit.broadcastMessage("Starting Ingame State");
		plugin.getInGameCountdown().start();
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getInventory().setItem(8, new IngameItem().getIngameItem());
			p.teleport(Bukkit.getWorld("world").getSpawnLocation());
		}
	}

	@Override
	public void stop()
	{
		Bukkit.broadcastMessage("Stopping Ingame State");
	}
}
