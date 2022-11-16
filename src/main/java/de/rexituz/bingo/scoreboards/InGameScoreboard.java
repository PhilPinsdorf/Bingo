package de.rexituz.bingo.scoreboards;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.time.LocalTime;

public class InGameScoreboard {
    Main plugin = Main.getPlugin();
    Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective obj = board.registerNewObjective("InGameBoard", "dummy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Bingo");
    BukkitTask bukkitTask;
    int time = 0;

    public InGameScoreboard(){
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        initScoreboard();
    }

    public void setAll(){
        for(Player p : Bukkit.getOnlinePlayers()){
            //-----Setting Scoreboard-----//
            p.setScoreboard(board);
        }
    }

    private void initScoreboard() {
        //-----Initialize Teams-----//
        Team teamRed = board.registerNewTeam("teamRed");
        Team teamBlue = board.registerNewTeam("teamBlue");
        Team teamGreen = board.registerNewTeam("teamGreen");
        Team teamYellow = board.registerNewTeam("teamYellow");
        Team teamTime = board.registerNewTeam("teamTime");

        //-----Initialize Teams on Scoreboard-----//
        initializeTeam(teamRed, TeamsDefinition.getTeamRed(), obj, ChatColor.RED + "Team Rot", 15);
        initializeTeam(teamBlue, TeamsDefinition.getTeamBlue(), obj, ChatColor.BLUE + "Team Blau", 14);
        initializeTeam(teamGreen, TeamsDefinition.getTeamGreen(), obj, ChatColor.GREEN + "Team Grün", 13);
        initializeTeam(teamYellow, TeamsDefinition.getTeamYellow(), obj, ChatColor.YELLOW + "Team Gelb", 12);

        //-----Placeholder-----//
        Score free = obj.getScore(" ");
        free.setScore(11);

        //-----Time-----//
        initializeTime(teamTime, obj, ChatColor.GREEN + "Time: ", 10);
    }

    private void updateScoreboard(){
        updatePresence(board, TeamsDefinition.getTeamRed(), "teamRed");
        updatePresence(board, TeamsDefinition.getTeamBlue(), "teamBlue");
        updatePresence(board, TeamsDefinition.getTeamGreen(), "teamGreen");
        updatePresence(board, TeamsDefinition.getTeamYellow(), "teamYellow");

        updateItems(board, TeamsDefinition.getTeamRed(), "teamRed");
        updateItems(board, TeamsDefinition.getTeamBlue(), "teamBlue");
        updateItems(board, TeamsDefinition.getTeamGreen(), "teamGreen");
        updateItems(board, TeamsDefinition.getTeamYellow(), "teamYellow");

        updateTime(board, "teamTime");
    }

    private void initializeTeam(Team team, Teams t, Objective obj, String text, int score){
        team.addEntry(text);
        if(t.getPlayer().size() >= 1){
            team.setPrefix(ChatColor.BOLD + "" + ChatColor.GREEN + "✔ ");
        } else {
            team.setPrefix(ChatColor.BOLD + "" + ChatColor.RED + "✘ ");
        }
        team.setSuffix(" 0/9");
        obj.getScore(text).setScore(score);
    }

    private void initializeTime(Team team, Objective obj, String text, int score){
        team.addEntry(text);
        team.setSuffix(ChatColor.YELLOW + " 00:00:00");
        obj.getScore(text).setScore(score);
    }

    private void updatePresence(Scoreboard board, Teams t, String tName){
        if(t.getPlayer().size() >= 1){
            board.getTeam(tName).setPrefix(ChatColor.BOLD + "" + ChatColor.GREEN + "✔ ");
        } else {
            board.getTeam(tName).setPrefix(ChatColor.BOLD + "" + ChatColor.RED + "✘ ");
        }
    }

    private void updateItems(Scoreboard board, Teams t, String tName){
        int i = 0;
        for (Boolean bool : t.getTeamsDelivery().getAllDeliverys()){
            if(bool){
                i++;
            }
        }
        ChatColor cc;
        switch (i){
            case 0: case 1: case 2: case 3: case 4:
                cc = ChatColor.GREEN;
                break;
            case 5: case 6: case 7:
                cc = ChatColor.YELLOW;
                break;
            case 8: case 9:
                cc = ChatColor.RED;
                break;
            default:
                cc = ChatColor.WHITE;
                break;
        }
        board.getTeam(tName).setSuffix(cc + " " + i + ChatColor.GRAY + "/" + ChatColor.RED + "9");
    }

    private void updateTime(Scoreboard board, String tName){
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(time);
        String timeText = timeOfDay.toString();

        board.getTeam(tName).setSuffix(ChatColor.YELLOW + timeText);
    }

    public void startTimer(){
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                time++;
                updateScoreboard();
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void stopTimer(){
        bukkitTask.cancel();
    }

    public int getTime() {
        return time;
    }
}
