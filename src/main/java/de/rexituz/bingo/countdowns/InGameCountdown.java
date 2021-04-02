package de.rexituz.bingo.countdowns;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.packets.Titles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class InGameCountdown extends Countdown
{
		Main plugin = Main.getPlugin();
		private static final int COUNTDOWN_TIME = 5;
		private boolean isRunning;
		private boolean isFinished;
		private int seconds;
		
		public InGameCountdown()
		{
			this.seconds = COUNTDOWN_TIME;
			this.isRunning = false;	
			this.isFinished = false;	
		}
		

		@Override
		public void start()
		{
			isRunning = true;
			inGameID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
					new Runnable() {
						@Override
						public void run() {
							for (Player player : Bukkit.getOnlinePlayers()) {
								switch (seconds) {
									case 5:
										//player.sendMessage(Main.PREFIX + "5");
										Titles.sendTitle(player ,ChatColor.DARK_RED + "5", null, 20, 20, 20);
										break;
									case 4:
										//player.sendMessage(Main.PREFIX + "4");
										Titles.sendTitle(player ,ChatColor.RED + "4", null, 20, 20, 20);
										break;
									case 3:
										//player.sendMessage(Main.PREFIX + "3");
										Titles.sendTitle(player ,ChatColor.GOLD + "3", null, 20, 20, 20);
										break;
									case 2:
										//player.sendMessage(Main.PREFIX + "2");
										Titles.sendTitle(player ,ChatColor.YELLOW + "2", null, 20, 20, 20);
										break;
									case 1:
										//player.sendMessage(Main.PREFIX + "1");
										Titles.sendTitle(player ,ChatColor.GREEN + "1", null, 20, 20, 20);
										break;
									case 0:
										//player.sendMessage(Main.PREFIX + "Go!");
										Titles.sendTitle(player ,ChatColor.DARK_GREEN + "Go!", null, 20, 20, 20);
										stop();
										break;
									default:
										break;
								}
							}
							seconds--;
						}
					}, 0, 20);
			
		}

		@Override
		public void stop()
		{
			if(isRunning)
			{
				Bukkit.getScheduler().cancelTask(inGameID);
				isRunning = false;
				seconds = COUNTDOWN_TIME;
				isFinished = true;
			}
			
		}
		
		public int getSeconds()
		{
			return seconds;
		}
		
		public boolean isRunning()
		{
			return isRunning;
		}
		
		public boolean isFinished()
		{
			return isFinished;
		}
	}

