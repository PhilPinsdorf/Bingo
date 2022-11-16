package de.rexituz.bingo.countdowns;

import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
									case 0:
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

