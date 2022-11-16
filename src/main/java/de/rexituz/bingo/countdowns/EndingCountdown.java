package de.rexituz.bingo.countdowns;

import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EndingCountdown extends Countdown{
    Main plugin = Main.getPlugin();
    private static final int COUNTDOWN_TIME = 20;
    private boolean isRunning;
    private boolean isFinished;
    private int seconds;

    public EndingCountdown()
    {
        this.seconds = COUNTDOWN_TIME;
        this.isRunning = false;
        this.isFinished = false;
    }

    @Override
    public void start()
    {
        isRunning = true;
        endingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                new Runnable() {
                    @Override
                    public void run() {
                            switch (seconds) {
                                case 20: case 10: case 5: case 4: case 3: case 2:
                                    Bukkit.broadcastMessage(Main.PREFIX + ChatColor.RED + "Der Server stoppt in " + seconds + " Sekunden!");
                                    break;
                                case 1:
                                    Bukkit.broadcastMessage(Main.PREFIX + ChatColor.RED + "Der Server stoppt in einer Sekunde!");
                                    break;
                                case 0:
                                    stop();
                                    Bukkit.broadcastMessage(Main.PREFIX + ChatColor.RED + "Der Server stoppt jetzt!");
                                    plugin.getGameStateManager().stopCurrentGameState();
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
            Bukkit.getScheduler().cancelTask(endingID);
            isRunning = false;
            seconds = 0;
            isFinished = true;
        }

    }
}
