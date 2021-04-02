package de.rexituz.bingo.events;

import de.rexituz.bingo.gamestates.EndingState;
import de.rexituz.bingo.gamestates.LobbyState;
import de.rexituz.bingo.gui.IngameItem;
import de.rexituz.bingo.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class LittleListeners implements Listener {
    Main plugin = Main.getPlugin();
    @EventHandler
    private void onDrainEat(FoodLevelChangeEvent e){
        if(e.getEntity() instanceof Player){
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent e){
        if(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState || plugin.getGameStateManager().getCurrentGameState() instanceof EndingState){
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onBreak(BlockBreakEvent e){
        if(plugin.getGameStateManager().getCurrentGameState() instanceof LobbyState || plugin.getGameStateManager().getCurrentGameState() instanceof EndingState){
            e.setCancelled(true);
        }
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            if(e.getEntity() instanceof Player){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onRespawn(PlayerRespawnEvent e){
        e.getPlayer().getInventory().setItem(8, new IngameItem().getIngameItem());
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent e){
        e.getDrops().removeIf(is -> is.getType() == Material.NETHER_STAR);
    }
}
