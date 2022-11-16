package de.rexituz.bingo.gui;

import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class IngameItem implements Listener {
    Main plugin = Main.getPlugin();
    Material itemMaterial = Material.NETHER_STAR;
    String itemName = ChatColor.GREEN + "Bingo " + ChatColor.GRAY + "(Rechtsklick)";

    public ItemStack getIngameItem() {
        return new ItemBuilder().getItem(itemName, itemMaterial, 1, false, new ArrayList<String>(Arrays.asList("")), true, false);
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e){
        if(e.getItemDrop() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(itemName) || e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(plugin.getTeamGui().getTeamItem().getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName().equals(itemName) || e.getCurrentItem().getItemMeta().getDisplayName().equals(plugin.getTeamGui().getTeamItem().getItemMeta().getDisplayName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getItem() == null) return;
            if(e.getItem().getItemMeta() == null) return;
            if(e.getItem().getItemMeta().getDisplayName() == null) return;
            if(!e.getItem().getItemMeta().getDisplayName().equals(itemName)) return;
            plugin.getIngameGui().openInventory(e.getPlayer());
        }
    }
}
