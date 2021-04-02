package de.rexituz.bingo.commands;

import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
	private final Main plugin = Main.getPlugin();
	private static final int START_SECONDS = 5;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(player.hasPermission("bingo.start"))
			{
				if(args.length == 0) 
				{
					if(plugin.getGameStateManager().getCurrentGameState() != null) {
						if (plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
							if (plugin.getLobbyCountdown().getSeconds() > START_SECONDS) {
								plugin.getLobbyCountdown().start();
								plugin.getLobbyCountdown().
								plugin.getLobbyCountdown().setSeconds(START_SECONDS);
								for (Player target : Bukkit.getOnlinePlayers())
									target.sendMessage(Main.PREFIX + ChatColor.GREEN + "Der Spieler " + ChatColor.GOLD + player.getName() + ChatColor.GREEN + " hat einen Force-Start benutzt!");
								return true;
							} else {
								player.sendMessage(Main.PREFIX + ChatColor.RED + "Das Spiel ist bereits gestartet.");
							}
						} else {
							player.sendMessage(Main.PREFIX + ChatColor.RED + "Das Spiel ist bereits gestartet.");
						}
					} else {
						player.sendMessage(Main.PREFIX + ChatColor.RED + "null");
					}
				} else {
					player.sendMessage(Main.PREFIX + ChatColor.RED + "Bitte benutze " + ChatColor.GOLD + "/start" + ChatColor.RED + "!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Du hast keine Berechtigung dazu!");
			}
			
		}
		return false;
	}
}
