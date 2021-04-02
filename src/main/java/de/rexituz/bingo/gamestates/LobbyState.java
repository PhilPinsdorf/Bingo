package de.rexituz.bingo.gamestates;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LobbyState extends GameStates
{
	Main plugin = Main.getPlugin();
	public static final int MIN_PLAYERS = 8;
	public static final int MAX_PLAYERS = TeamsDefinition.getAllTeams().size() * 2;

	@Override
	public void start()
	{
		Bukkit.broadcastMessage("Starting Lobby State");
		plugin.getLobbyCountdown().startIdle();
	}

	@Override
	public void stop()
	{
		for(Player p : Bukkit.getOnlinePlayers()){
			if(plugin.getTeamAssignment(p) == null){
				Teams tFinal = null;
				for(Teams t : TeamsDefinition.getAllTeams()) {
					if(tFinal == null){
						tFinal = t;
					}
					if(t.getPlayer().size() < tFinal.getPlayer().size()) {
						tFinal = t;
					}
				}
				tFinal.getPlayer().add(p.getName());
				plugin.setTeamAssignment(p, tFinal);
			}
			p.setPlayerListName(plugin.getTeamAssignment(p).getColor() + p.getName());
		}
		Bukkit.broadcastMessage("Stopping Lobby State");
	}
}
