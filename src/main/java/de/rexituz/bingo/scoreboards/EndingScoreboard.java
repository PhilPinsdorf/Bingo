package de.rexituz.bingo.scoreboards;

import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

import java.time.LocalTime;

public class EndingScoreboard {
    Main plugin = Main.getPlugin();
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective obj = board.registerNewObjective("EndingBoard", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Bingo");

    public EndingScoreboard(){
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        initScoreboard();
    }

    private void initScoreboard() {
        Score winning = obj.getScore(ChatColor.YELLOW + "Gewonnen hat:");
        winning.setScore(15);

        Score winningTeam = obj.getScore(plugin.getWinningTeam().getColor() + "Team " + plugin.getWinningTeam().getName());
        winningTeam.setScore(14);

        Score placeholder = obj.getScore(" ");
        placeholder.setScore(13);

        Score time = obj.getScore(ChatColor.YELLOW + "Gebrauchte Zeit:");
        time.setScore(12);

        LocalTime timeOfDay = LocalTime.ofSecondOfDay(plugin.getInGameScoreboard().getTime());
        String timeText = timeOfDay.toString();
        Score timeTime = obj.getScore(ChatColor.WHITE + timeText);
        timeTime.setScore(11);
    }

    public Scoreboard getBoard() {
        return board;
    }
}
