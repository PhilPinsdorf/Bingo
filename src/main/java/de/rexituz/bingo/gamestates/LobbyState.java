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
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class LobbyState extends GameStates
{
	Main plugin = Main.getPlugin();
	public static final int MIN_PLAYERS = 8;
	public static final int MAX_PLAYERS = TeamsDefinition.getAllTeams().size() * 2;

	@Override
	public void start()
	{
		createLobby();
		plugin.getLobbyCountdown().startIdle();
		plugin.getLobbyScoreboard().startTimer();
	}

	@Override
	public void stop()
	{
		plugin.getLobbyCountdown().stopIdle();
		plugin.getLobbyScoreboard().stopTimer();

		HashMap<Integer, Teams> teamSizes = new HashMap<>();
		for(Teams t : TeamsDefinition.getAllTeams()) {
			teamSizes.put(t.getPlayer().size(), t);
		}

		//-----Random Team Assignment-----//
		for(Player p : Bukkit.getOnlinePlayers()){
			if(plugin.getTeamAssignment(p) == null){
				Teams tFinal = null;
				for(Teams t : TeamsDefinition.getAllTeams()) {
					if(t.getPlayer().size() == 2){
						continue;
					}
					if(tFinal == null){
						tFinal = t;
					}
					if(tFinal == t){
						continue;
					}
					if(t.getPlayer().size() < tFinal.getPlayer().size()) {
						tFinal = t;
					}
				}
				tFinal.getPlayer().add(p.getName());
				plugin.setTeamAssignment(p, tFinal);
				p.setPlayerListName(plugin.getTeamAssignment(p).getColor() + p.getName());
			}
		}
	}

	private void createLobby() {
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
