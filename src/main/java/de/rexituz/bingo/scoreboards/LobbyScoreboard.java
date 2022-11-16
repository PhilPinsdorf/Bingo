package de.rexituz.bingo.scoreboards;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

public class LobbyScoreboard {
    Main plugin = Main.getPlugin();
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective obj = board.registerNewObjective("LobbyBoard", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Bingo");
    BukkitTask bukkitTask;

    public LobbyScoreboard(){
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        initScoreboard();
    }

    private void initScoreboard() {
        //-----Initialize Teams-----//
        Team teamRed = board.registerNewTeam("teamRed");
        Team teamBlue = board.registerNewTeam("teamBlue");
        Team teamGreen = board.registerNewTeam("teamGreen");
        Team teamYellow = board.registerNewTeam("teamYellow");

        //-----Initialize Teams on Scoreboard-----//
        initializeTeam(teamRed, TeamsDefinition.getTeamRed(), obj, ChatColor.RED + "Team Rot" + ChatColor.WHITE + ":", 15);
        initializeTeam(teamBlue, TeamsDefinition.getTeamBlue(), obj, ChatColor.BLUE + "Team Blau" + ChatColor.WHITE + ":", 14);
        initializeTeam(teamGreen, TeamsDefinition.getTeamGreen(), obj, ChatColor.GREEN + "Team Grün" + ChatColor.WHITE + ":", 13);
        initializeTeam(teamYellow, TeamsDefinition.getTeamYellow(), obj, ChatColor.YELLOW + "Team Gelb" + ChatColor.WHITE + ":", 12);
    }

    private void updateScoreboard(){
        updateTeamSize(board, TeamsDefinition.getTeamRed(), "teamRed");
        updateTeamSize(board, TeamsDefinition.getTeamBlue(), "teamBlue");
        updateTeamSize(board, TeamsDefinition.getTeamGreen(), "teamGreen");
        updateTeamSize(board, TeamsDefinition.getTeamYellow(), "teamYellow");
    }

    private void initializeTeam(Team team, Teams t, Objective obj, String text, int score){
        team.addEntry(text);
        team.setSuffix(" " + t.getPlayer().size() + "/2");
        obj.getScore(text).setScore(score);
    }

    private void updateTeamSize(Scoreboard board, Teams t, String tName){
        board.getTeam(tName).setSuffix(" " + t.getPlayer().size() + "/2");
    }

    public void startTimer(){
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                updateScoreboard();
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void stopTimer(){
        bukkitTask.cancel();
    }

    public Scoreboard getBoard() {
        return board;
    }
}
