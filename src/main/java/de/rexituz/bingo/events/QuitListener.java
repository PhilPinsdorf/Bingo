package de.rexituz.bingo.events;

import de.rexituz.bingo.countdowns.LobbyCountdown;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    Main plugin = Main.getPlugin();
    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
    if(!(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState))return;
    Player p = e.getPlayer();
    plugin.getTeamAssignment(p).getPlayer().remove(p.getName());
    plugin.removeTeamAssignment(p);
    e.setQuitMessage(Main.PREFIX + ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.RED + p.getName() + " [" +
            ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + "/" + ChatColor.GREEN + LobbyState.MAX_PLAYERS + "]");

    LobbyCountdown countdown = plugin.getLobbyCountdown();
    if(Bukkit.getOnlinePlayers().size() < LobbyState.MIN_PLAYERS) {
        if (countdown.isRunning()) {
            countdown.stop();
            countdown.startIdle();
        }
    }
}
}
