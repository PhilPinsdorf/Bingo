package de.rexituz.bingo.countdowns;

import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LobbyCountdown extends Countdown
{
	Main plugin = Main.getPlugin();
	private static final int IDLE_TIME = 15;
	private static final int COUNTDOWN_TIME = 60;
	
	private boolean isRunning;
	private int seconds;
	private int idleID;
	private boolean isIdeling;
	
	public LobbyCountdown()
	{
		this.seconds = COUNTDOWN_TIME;
		this.isRunning = false;		
	}
	

	@Override
	public void start()
	{
		for(Player target : Bukkit.getOnlinePlayers()) {
			target.setExp(1);
			target.setLevel(seconds);
		}

		isRunning = true;
		lobbyID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					@Override
					public void run()
					{
						for(Player player : Bukkit.getOnlinePlayers())
						{
							float startExp = 1;
							float exp = player.getExp();
							float remExp = (float)1/COUNTDOWN_TIME;
							float newExp = (COUNTDOWN_TIME - seconds) * remExp;
							float newNewExp = startExp - newExp;
							player.setLevel(seconds);
							player.setExp(newNewExp);
						}
						
						switch(seconds) 
						{
						case 60: case 30: case 20: case 10: case 4: case 3: case 2:
							for(Player player : Bukkit.getOnlinePlayers())
							{
								player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 1);
								player.sendMessage(Main.PREFIX + ChatColor.GRAY + "Das Spiel startet in " + ChatColor.GREEN + seconds + ChatColor.GRAY + " Sekunden.");
							}
							break;
							
						case 5:
							Bukkit.broadcastMessage(Main.PREFIX + ChatColor.GRAY + "Das Spiel startet in " + ChatColor.GREEN + seconds + ChatColor.GRAY + " Sekunden.");
							for(Player player : Bukkit.getOnlinePlayers())
							{
								player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 1);
							}
							break;
							
						case 1:
							Bukkit.broadcastMessage(Main.PREFIX + "§7Das Spiel startet in §aeiner Sekunde§7.");
							for(Player player : Bukkit.getOnlinePlayers())
							{
								player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 1);
								player.setExp(player.getExp() + (float)1/COUNTDOWN_TIME);
							}
							break;
							
						case 0:
							plugin.getGameStateManager().setGameState(GameStates.INGAME_STATE);
							for(Player player: Bukkit.getOnlinePlayers())
							{
								player.playSound(player.getLocation(), Sound.NOTE_BASS, 100, 2);
								player.setLevel(0);
								player.setExp(0);
								stop();
							}
							break;							
						default:
							break;
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
			Bukkit.getScheduler().cancelTask(lobbyID);
			isRunning = false;
			seconds = COUNTDOWN_TIME;
		}
		
	}
	
	public void startIdle() 
	{
		isIdeling = true;
		idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run()
			{
				Bukkit.broadcastMessage(Main.PREFIX + ChatColor.GRAY +"Bis zum Spielstart fehlen noch " + ChatColor.GOLD +
										(LobbyState.MIN_PLAYERS - Bukkit.getOnlinePlayers().size()) +
										" Spieler" + ChatColor.GRAY + ".");
				
			}
		}, 0, 20 * IDLE_TIME);
	}
	
	public void stopIdle() 
	{
		if(isIdeling)
		{
			Bukkit.getScheduler().cancelTask(idleID);
			isIdeling = false;
		}
	}
	
	public int getSeconds()
	{
		return seconds;
	}
	
	public void setSeconds(int seconds)
	{
		this.seconds = seconds;
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}
}
