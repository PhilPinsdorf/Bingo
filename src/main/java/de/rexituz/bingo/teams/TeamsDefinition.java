package de.rexituz.bingo.teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamsDefinition {
    static {
        boolean[] allFalse = new boolean[9];
        Arrays.fill(allFalse, false);

        TeamsDelivery td1 = new TeamsDelivery(allFalse);
        TeamsDelivery td2 = new TeamsDelivery(allFalse);
        TeamsDelivery td3 = new TeamsDelivery(allFalse);
        TeamsDelivery td4 = new TeamsDelivery(allFalse);

        teamRed = new Teams("Rot", ChatColor.RED, new ArrayList<String>(), td1, Material.RED_WOOL, Bukkit.createInventory(null, 9, "Team Inventar"));
        teamBlue = new Teams("Blau", ChatColor.BLUE, new ArrayList<String>(), td2, Material.BLUE_WOOL, Bukkit.createInventory(null, 9, "Team Inventar"));
        teamGreen = new Teams("Grün", ChatColor.GREEN, new ArrayList<String>(), td3, Material.LIME_WOOL, Bukkit.createInventory(null, 9, "Team Inventar"));
        teamYellow = new Teams("Gelb", ChatColor.YELLOW, new ArrayList<String>(), td4, Material.YELLOW_WOOL, Bukkit.createInventory(null, 9, "Team Inventar"));
    }

    static Teams teamRed;
    static Teams teamBlue;
    static Teams teamGreen;
    static Teams teamYellow;

    public static Teams getTeamRed() {
        return teamRed;
    }

    public static Teams getTeamBlue() {
        return teamBlue;
    }

    public static Teams getTeamGreen() {
        return teamGreen;
    }

    public static Teams getTeamYellow() {
        return teamYellow;
    }

    public static ArrayList<Teams> getAllTeams() {
        return new ArrayList<Teams>(Arrays.asList(teamBlue, teamGreen, teamRed, teamYellow));
    }
}
