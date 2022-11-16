package de.rexituz.bingo.events;

import de.rexituz.bingo.countdowns.LobbyCountdown;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    Main plugin = Main.getPlugin();

    @EventHandler
    private void onPreJoin(AsyncPlayerPreLoginEvent e){
        if(!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Spiel bereits gestartet!");
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.getInventory().setItem(0, plugin.getTeamGui().getTeamItem());
        p.setFoodLevel(20);
        p.setGameMode(GameMode.SURVIVAL);
        p.setScoreboard(plugin.getLobbyScoreboard().getBoard());

        Location spawnLoc = Bukkit.getWorld("world").getSpawnLocation();
        p.teleport(new Location(Bukkit.getWorld("world"), spawnLoc.getBlockX() + .5, spawnLoc.getBlockY() + 51, spawnLoc.getBlockZ() + .5, -90, 0));

        e.setJoinMessage(Main.PREFIX + ChatColor.GREEN + "+ " + ChatColor.GRAY + p.getName() + " [" +
                ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + LobbyState.MAX_PLAYERS + ChatColor.GRAY + "]");

        LobbyCountdown countdown = plugin.getLobbyCountdown();
        if(Bukkit.getOnlinePlayers().size() >= LobbyState.MIN_PLAYERS)
        {
            if(!(countdown.isRunning()))
            {
                countdown.stopIdle();
                countdown.start();
            }
        }
    }
}
