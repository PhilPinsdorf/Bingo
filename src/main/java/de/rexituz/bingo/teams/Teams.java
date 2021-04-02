package de.rexituz.bingo.teams;

import de.rexituz.bingo.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Teams {
    String name;
    ChatColor color;
    ArrayList<String> players;
    TeamsDelivery teamsDelivery;
    DyeColor dyeColor;
    Inventory inv;
    final int MAX_PLAYER = 2;

    public Teams(String name, ChatColor color, ArrayList<String> players, TeamsDelivery teamsDelivery, DyeColor dyeColor, Inventory inv){
        this.name = name;
        this.color = color;
        this.players = players;
        this.teamsDelivery = teamsDelivery;
        this.dyeColor = dyeColor;
        this.inv = inv;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public ArrayList<String> getPlayer() {
        return players;
    }

    public TeamsDelivery getTeamsDelivery() {
        return teamsDelivery;
    }

    public Inventory getInv() {
        return inv;
    }

    public ItemStack getIcon(){
        ArrayList<String> listOne = new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Hier klicken um Team zu Joinen!"));
        for (int i = 0; i < getMAX_PLAYER(); i++) {
            listOne.add(i + 1, getColor() + "» ");
        }
        for (int i = 0; i < getPlayer().size(); i++) {
            listOne.set(i + 1, getColor() + "» " + getPlayer().get(i));
        }
        return new ItemBuilder().getItem(color + "Team " + name, Material.WOOL, 1, true, listOne, false, true, dyeColor.getData());
    }

    public int getMAX_PLAYER() {
        return MAX_PLAYER;
    }
    public boolean isInTeam(Player player)
    {
        return players.contains(player.getName());
    }

}
