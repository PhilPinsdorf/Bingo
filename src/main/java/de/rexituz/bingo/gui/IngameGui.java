package de.rexituz.bingo.gui;

import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.main.Main;
import de.rexituz.bingo.util.ItemBuilder;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class IngameGui implements Listener {
    Main plugin = Main.getPlugin();
    private final ArrayList<Integer> CLOSED_SLOTS = new ArrayList<Integer>(Arrays.asList(0,4,5,6,8,9,13,15,17,18,22,23,24,26));
    private final Material CLOSED_SLOTS_MATERIAL = Material.STAINED_GLASS_PANE;
    private final ArrayList<Integer> MATERIAL_SLOT = new ArrayList<Integer>(Arrays.asList(1,2,3,10,11,12,19,20,21));
    private final int SKULL_SLOT = 7;
    private final Material SKULL_SLOT_MATERIAL = Material.SKULL_ITEM;
    private final int GRASS_SLOT = 16;
    private final Material GRASS_SLOT_MATERIAL = Material.GRASS;
    private final int CHEST_SLOT = 25;
    private final Material CHEST_SLOT_MATERIAL = Material.CHEST;
    private final int HOPPER_SLOT = 14;
    private final Material HOPPER_SLOT_MATERIAL = Material.HOPPER;
    private final String INVENTORY_NAME = ChatColor.DARK_PURPLE + "Bingo";

    public void openInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 3*9, INVENTORY_NAME);
        for(int i : CLOSED_SLOTS){
            inv.setItem(i, new ItemBuilder().getItem(" ", CLOSED_SLOTS_MATERIAL, 1, false, new ArrayList<String>(Arrays.asList("")), false, true, 15));
        }
        for(int i = 0; i < 9; i++){
            ChatColor cc = ChatColor.WHITE;
            switch(i){
                case 0: case 1: case 2: case 3: case 4: case 5:
                    cc = ChatColor.GREEN;
                    break;
                case 6: case 7:
                    cc = ChatColor.YELLOW;
                    break;
                case 8:
                    cc = ChatColor.RED;
                    break;
            }
            if(plugin.getTeamAssignment(p).getTeamsDelivery().getAllDeliverys().get(i)) {
                inv.setItem(MATERIAL_SLOT.get(i), new ItemBuilder().getItem(ChatColor.RESET + "" + cc + "" + WordUtils.capitalize(plugin.getRandomItems().getFinalMaterials().get(i).name().replace("_", " ").toLowerCase()), plugin.getRandomItems().getFinalMaterials().get(i), 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Finde oder Crafte dieses Item und gebe es ab!")), true, false, 0));
            } else {
                inv.setItem(MATERIAL_SLOT.get(i), new ItemBuilder().getItem(ChatColor.RESET + "" + cc + "" + WordUtils.capitalize(plugin.getRandomItems().getFinalMaterials().get(i).name().replace("_", " ").toLowerCase()), plugin.getRandomItems().getFinalMaterials().get(i), 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Finde oder Crafte dieses Item und gebe es ab!")), false, false, 0));
            }
        }
        inv.setItem(SKULL_SLOT, new ItemBuilder().getItem(ChatColor.AQUA + "Team Tp", SKULL_SLOT_MATERIAL, 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Teleportiere dich zu deinem Team Mate!")), false, false, 0));
        inv.setItem(GRASS_SLOT, new ItemBuilder().getItem(ChatColor.AQUA + "Top Tp", GRASS_SLOT_MATERIAL, 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Teleportiere dich an die Oberfläche!")), false, false, 0));
        inv.setItem(CHEST_SLOT, new ItemBuilder().getItem(ChatColor.AQUA + "Team Chest", CHEST_SLOT_MATERIAL, 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Teile dir ein Inventar mit deinem Team Mate!")), false, false, 0));
        inv.setItem(HOPPER_SLOT, new ItemBuilder().getItem(ChatColor.GOLD + "Abgabe", HOPPER_SLOT_MATERIAL, 1, true, new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Lege dein Item hier ab um es abzugeben!")), false, false, 0));
        p.openInventory(inv);
    }

    @EventHandler
    private void onClick(InventoryClickEvent e){
        if(e.getClickedInventory() != null) {
            if (e.getClickedInventory().getName().equals(INVENTORY_NAME)) {
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                switch(e.getClickedInventory().getContents()[e.getSlot()].getType()) {
                    case GRASS:
                        Location loc = player.getLocation();
                        for(int y = 256; y > 0; y--){
                            Block b = new Location(Bukkit.getWorld("world"), loc.getBlockX(), y, loc.getBlockZ()).getBlock();
                            if(!b.isEmpty()){
                                player.teleport(new Location(b.getLocation().getWorld(), player.getLocation().getX(), b.getLocation().getY() + 1, player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
                                break;
                            }
                        }
                        break;
                    case SKULL_ITEM:
                        ArrayList<String> tap = plugin.getTeamAssignment(player).getPlayer();
                        if(tap.size() > 1){
                            for(String s : tap){
                                if(s.equalsIgnoreCase(player.getName())){
                                    player.teleport(Bukkit.getPlayer(s).getLocation());
                                    break;
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Du hast kein Teammate!");
                        }
                        player.closeInventory();
                       break;
                    case CHEST:
                        player.openInventory(plugin.getTeamAssignment(player).getInv());
                        break;
                    case HOPPER:
                        if(e.getCursor() != null){
                            if(plugin.getRandomItems().getFinalMaterials().contains(e.getCursor().getType())){
                                if(!plugin.getTeamAssignment((Player) e.getWhoClicked()).getTeamsDelivery().getAllDeliverys().get(plugin.getRandomItems().getFinalMaterials().indexOf(e.getCursor().getType()))) {
                                    updateMaterialBools(e.getCursor().getType(), (Player) e.getWhoClicked());
                                    ChatColor cc = ChatColor.WHITE;
                                    switch(plugin.getRandomItems().getFinalMaterials().indexOf(e.getCursor().getType())){
                                        case 0: case 1: case 2: case 3: case 4: case 5:
                                            cc = ChatColor.GREEN;
                                            break;
                                        case 6: case 7:
                                            cc = ChatColor.YELLOW;
                                            break;
                                        case 8:
                                            cc = ChatColor.RED;
                                            break;
                                    }
                                    for(Player p : Bukkit.getOnlinePlayers()){
                                        if(p == e.getWhoClicked()){
                                            p.sendMessage(Main.PREFIX + ChatColor.DARK_GRAY + "Du hast " + cc + WordUtils.capitalize(e.getCursor().getType().name().replace("_", " ").toLowerCase()) + ChatColor.DARK_GRAY + " abgegeben!");
                                        } else {
                                            p.sendMessage(Main.PREFIX + plugin.getTeamAssignment((Player) e.getWhoClicked()).getColor() + e.getWhoClicked().getName() + " hat " + cc + WordUtils.capitalize(e.getCursor().getType().name().replace("_", " ").toLowerCase()) + ChatColor.DARK_GRAY + " abgegeben!");
                                        }
                                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 100, 1);
                                    }
                                    if (e.getCursor().getAmount() > 1) {
                                        e.getCursor().setAmount(e.getCursor().getAmount() - 1);
                                    } else {
                                        e.getWhoClicked().setItemOnCursor(null);
                                    }
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                             openInventory((Player) e.getWhoClicked());
                                        }
                                    }.runTaskLater(plugin, 1);

                                } else {
                                    e.getWhoClicked().sendMessage(Main.PREFIX + ChatColor.RED + "Du hast dieses Item bereits abgegeben!");
                                }
                            } else {
                                e.getWhoClicked().sendMessage(Main.PREFIX + ChatColor.RED + "Dieses Item musst du nicht abgeben!");
                            }
                        }
                        break;
                }
            }
        }
    }

    private void updateMaterialBools(Material material, Player p){
        plugin.getTeamAssignment(p).getTeamsDelivery().getAllDeliverys().set(plugin.getRandomItems().getFinalMaterials().indexOf(material), true);
        checkForWin(p);
    }

    private void checkForWin(Player p){
        if(areAllTrue(plugin.getTeamAssignment(p).getTeamsDelivery().getAllDeliverys())){
            plugin.setWinningTeam(plugin.getTeamAssignment(p));
            plugin.getGameStateManager().setGameState(GameStates.ENDING_STATE);
        }
    }

    public static boolean areAllTrue(ArrayList<Boolean> arraylist)
    {
        for (Boolean b : arraylist) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
