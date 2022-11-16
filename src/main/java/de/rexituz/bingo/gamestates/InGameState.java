package de.rexituz.bingo.gamestates;

import de.rexituz.bingo.gui.IngameItem;
import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class InGameState extends GameStates 
{
    Main plugin = Main.getPlugin();

	@Override
	public void start()
	{
		plugin.getInGameCountdown().start();

		for(Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.getInventory().setItem(8, new IngameItem().getIngameItem());
		}

		plugin.getInGameScoreboard().setAll();
		plugin.getInGameScoreboard().startTimer();

		//-----Check for Win-----//
		ArrayList<Teams> teamsAlive = new ArrayList<>();
		for(Teams t : TeamsDefinition.getAllTeams()){
			if(t.getPlayer().size() >= 1){
				teamsAlive.add(t);
			}
		}
		if(teamsAlive.size() == 1){
			plugin.setWinningTeam(teamsAlive.get(0));
			new BukkitRunnable() {
				@Override
				public void run() {
					plugin.getGameStateManager().setGameState(GameStates.ENDING_STATE);
				}
			}.runTaskLater(plugin, 10);
		}
		if(teamsAlive.size() == 0){
			plugin.getServer().shutdown();
		}

		World world = Bukkit.getWorld("world");
		world.setTime(1000);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
		world.setStorm(false);
		world.setDifficulty(Difficulty.NORMAL);
	}

	@Override
	public void stop()
	{
		//Empty
	}
}
