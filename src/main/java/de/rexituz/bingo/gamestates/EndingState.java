package de.rexituz.bingo.gamestates;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import de.rexituz.bingo.main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EndingState extends GameStates
{
	Main plugin = Main.getPlugin();

	@Override
	public void start()
	{
		plugin.getEndingCountdown().start();
		plugin.getInGameScoreboard().stopTimer();

		for (Player p : Bukkit.getOnlinePlayers()){
			p.setScoreboard(plugin.getEndingScoreboard().getBoard());
		}

		World world = Bukkit.getWorld("world");
		world.setTime(1000);
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		world.setStorm(false);
		world.setDifficulty(Difficulty.PEACEFUL);
		createLobby();
		Location spawnLoc = Bukkit.getWorld("world").getSpawnLocation();
		for(Player p : Bukkit.getOnlinePlayers()) {
			new BukkitRunnable(){
				@Override
				public void run(){
					p.closeInventory();
			}}.runTaskLater(plugin, 1);
			p.getInventory().clear();
			p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 100, 1);
			p.sendTitle(plugin.getWinningTeam().getColor() + "Team " + plugin.getWinningTeam().getName(), ChatColor.GRAY + "hat gewonnen!", 20, 3 * 20, 20);
			p.teleport(new Location(Bukkit.getWorld("world"), spawnLoc.getBlockX() + .5, spawnLoc.getBlockY() + 51, spawnLoc.getBlockZ() + .5, -90, 0));
		}
	}

	@Override
	public void stop()
	{
		plugin.getServer().shutdown();
	}

	private void createLobby(){
		Clipboard clipboard = null;
		WorldEditPlugin worldEditPlugin = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
		WorldEdit we = worldEditPlugin.getWorldEdit();
		World world = Bukkit.getWorld("world");
		com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
		Location spawnLoc = Bukkit.getWorld("world").getSpawnLocation();
		File file = new File(plugin.getDataFolder() + File.separator + "/schematics/lobby.schematic");
		ClipboardFormat format = ClipboardFormats.findByFile(file);
		try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
			clipboard = reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (EditSession editSession = we.newEditSession(weWorld)) {
			Operation operation = new ClipboardHolder(clipboard)
					.createPaste(editSession)
					.to(BlockVector3.at(spawnLoc.getBlockX(), spawnLoc.getBlockY() + 50, spawnLoc.getBlockZ()))
					.build();
			Operations.complete(operation);
		} catch (WorldEditException e){
			e.printStackTrace();
		}
	}
}
