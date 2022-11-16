package de.rexituz.bingo.events;

import de.rexituz.bingo.countdowns.LobbyCountdown;
import de.rexituz.bingo.gamestates.EndingState;
import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.gamestates.InGameState;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class QuitListener implements Listener {
    Main plugin = Main.getPlugin();
    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(plugin.getTeamAssignment(p) != null){
            plugin.getTeamAssignment(p).getPlayer().remove(p.getName());
            plugin.removeTeamAssignment(p);
        }

        //-----Lobby-----//
        if(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState){
            e.setQuitMessage(Main.PREFIX + ChatColor.RED + "-" + ChatColor.GRAY + " " + p.getName() + " [" +
                    ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + LobbyState.MAX_PLAYERS + ChatColor.GRAY + "]");

            LobbyCountdown countdown = plugin.getLobbyCountdown();
            if(Bukkit.getOnlinePlayers().size() < LobbyState.MIN_PLAYERS) {
                if (countdown.isRunning()) {
                    countdown.stop();
                    countdown.startIdle();
                }
            }
            return;
        }

        e.setQuitMessage(Main.PREFIX + ChatColor.RED + "-" + ChatColor.GRAY + " " + p.getName());
        //-----Ingame-----//
        if(plugin.getGameStateManager().getCurrentGameState() instanceof InGameState){
            //-----Check for Win-----//
            ArrayList<Teams> teamsAlive = new ArrayList<>();
            for(Teams t : TeamsDefinition.getAllTeams()){
                if(t.getPlayer().size() >= 1){
                    teamsAlive.add(t);
                }
            }
            if(teamsAlive.size() == 1){
                plugin.setWinningTeam(teamsAlive.get(0));
                if(!plugin.getInGameCountdown().isFinished()){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            plugin.getGameStateManager().setGameState(GameStates.ENDING_STATE);
                        }
                    }.runTaskLater(plugin, 10);
                }
                plugin.getGameStateManager().setGameState(GameStates.ENDING_STATE);
            }
            if(teamsAlive.size() == 0){
                plugin.getServer().shutdown();
            }
        }

        if(plugin.getGameStateManager().getCurrentGameState() instanceof EndingState){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(Bukkit.getOnlinePlayers().size() == 0){
                        plugin.getServer().shutdown();
                    }
                }
            }.runTaskLater(plugin, 10);
        }
    }
}
