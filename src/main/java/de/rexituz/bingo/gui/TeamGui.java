package de.rexituz.bingo.gui;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import de.rexituz.bingo.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TeamGui implements Listener {
    Main plugin = Main.getPlugin();
    private final Material TEAM_GUI_MATERIAL = Material.RED_BED;
    private final String TEAM_GUI_NAME = ChatColor.GOLD + "Team Auswahl";
    private final Inventory inventory = Bukkit.createInventory(null, 9, TEAM_GUI_NAME);

    @EventHandler
    public void handleTeamGuiOpener(PlayerInteractEvent event)
    {
        if(event.getItem() == null) return;
        if(event.getItem().getType() != TEAM_GUI_MATERIAL) return;
        if(!Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(TEAM_GUI_NAME)) return;
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            openTeamGUI(event.getPlayer());
    }

    public void openTeamGUI(Player player)
    {
        inventory.setItem(1, TeamsDefinition.getTeamRed().getIcon());
        inventory.setItem(3, TeamsDefinition.getTeamBlue().getIcon());
        inventory.setItem(5, TeamsDefinition.getTeamGreen().getIcon());
        inventory.setItem(7, TeamsDefinition.getTeamYellow().getIcon());
        player.openInventory(inventory);
    }

    @EventHandler
    public void handleTeamGUIClick(InventoryClickEvent event)
    {
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(event.getClickedInventory() == null) return;
        if(event.getCurrentItem() == null) return;
        if(!event.getView().getTitle().equals(TEAM_GUI_NAME)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem().isSimilar(TeamsDefinition.getTeamRed().getIcon()))
        {
            addToTeam(player, TeamsDefinition.getTeamRed());
        }
        else if(event.getCurrentItem().isSimilar(TeamsDefinition.getTeamBlue().getIcon()))
        {
            addToTeam(player, TeamsDefinition.getTeamBlue());
        }
        else if(event.getCurrentItem().isSimilar(TeamsDefinition.getTeamGreen().getIcon()))
        {
            addToTeam(player, TeamsDefinition.getTeamGreen());
        }
        else if(event.getCurrentItem().isSimilar(TeamsDefinition.getTeamYellow().getIcon()))
        {
            addToTeam(player, TeamsDefinition.getTeamYellow());
        }
        player.closeInventory();
    }

    private void addToTeam(Player player, Teams newteam)
    {
        if(newteam.getMAX_PLAYER() > newteam.getPlayer().size())
        {
            if(newteam.isInTeam(player))
            {
                player.sendMessage(Main.PREFIX + ChatColor.DARK_GRAY + "Du bist bereits in Team " + newteam.getColor() + newteam.getName() + ChatColor.DARK_GRAY + "!");
                return;
            }

            Main plugin = Main.getPlugin();
            for(Teams t: TeamsDefinition.getAllTeams())
            {
                if(t.isInTeam(player))
                {
                    t.getPlayer().remove(player.getName());
                    plugin.removeTeamAssignment(player);
                    player.sendMessage(Main.PREFIX + ChatColor.DARK_GRAY + "Du hast das Team " + t.getColor() + t.getName() + ChatColor.RED + " verlassen" + ChatColor.DARK_GRAY + "!");
                }
            }

            newteam.getPlayer().add(player.getName());
            plugin.setTeamAssignment(player, newteam);
            player.setPlayerListName(plugin.getTeamAssignment(player).getColor() + player.getName());
            player.sendMessage(Main.PREFIX + ChatColor.DARK_GRAY + "Du hast das Team " + newteam.getColor() + newteam.getName() + ChatColor.GREEN + " betreten" + ChatColor.DARK_GRAY + "!");
        }
        else
        {
            player.sendMessage(Main.PREFIX + ChatColor.DARK_GRAY + "Das Team " + newteam.getColor() + newteam.getName() + ChatColor.DARK_GRAY + " ist " + ChatColor.YELLOW + "schon voll" + ChatColor.DARK_GRAY + "!");
        }
    }

    public ItemStack getTeamItem()
    {
        return new ItemBuilder().getItem(TEAM_GUI_NAME, TEAM_GUI_MATERIAL, 1, false, new ArrayList<String>(Arrays.asList("")), true, false);
    }
}
