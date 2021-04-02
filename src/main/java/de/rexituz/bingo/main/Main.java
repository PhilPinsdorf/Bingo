package de.rexituz.bingo.main;

import de.rexituz.bingo.commands.StartCommand;
import de.rexituz.bingo.countdowns.InGameCountdown;
import de.rexituz.bingo.countdowns.LobbyCountdown;
import de.rexituz.bingo.events.JoinEvent;
import de.rexituz.bingo.events.LittleListeners;
import de.rexituz.bingo.gamestates.GameStateManager;
import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.gui.IngameGui;
import de.rexituz.bingo.gui.IngameItem;
import de.rexituz.bingo.gui.TeamGui;
import de.rexituz.bingo.random.RandomItems;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    static Main plugin;
    public static String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Bingo" + ChatColor.DARK_GRAY + "] ";
    RandomItems randomItems;
    IngameGui ingameGui;
    TeamsDefinition teamsDefinition;
    TeamGui teamGui;
    LobbyCountdown lobbyCountdown;
    InGameCountdown inGameCountdown;
    GameStateManager gameStateManager;
    HashMap<Player, Teams> teamAssignment = new HashMap<Player, Teams>();
    Teams winningTeam;

    @Override
    public void onEnable() {
        plugin = this;
        registerEvents();
        commandRegistration();
        getServer().createWorld(new WorldCreator("lobby"));
        gamestateRegistration();
        getRandomItems().randomizeItems();
    }

    @Override
    public void onDisable() {
        reset(Bukkit.getWorld("world"));
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new IngameItem(), this);
        pluginManager.registerEvents(new IngameGui(), this);
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new TeamGui(), this);
        pluginManager.registerEvents(new LittleListeners(), this);
    }

    private void commandRegistration() {
        getCommand("start").setExecutor(new StartCommand());
    }

    private void gamestateRegistration(){
        getGameStateManager().setGameState(GameStates.LOBBY_STATE);
    }

    public void reset(World world) { // Please modify to your needs
        Bukkit.getServer().unloadWorld(world, false); // False = Not Save
        final File worldFolder = new File(world.getName()); // World folder name
        deleteFolder(worldFolder); // Delete old folder
        recursiveDelete(worldFolder);
        //final WorldCreator w = new WorldCreator(world.getName()); // This starts the world load
    }

    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }

    public static void recursiveDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                recursiveDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
    }

    public static Main getPlugin() {
        return plugin;
    }

    public RandomItems getRandomItems() {
        if(randomItems == null){
            randomItems = new RandomItems();
        }
        return randomItems;
    }

    public IngameGui getIngameGui() {
        if(ingameGui == null){
            ingameGui = new IngameGui();
        }
        return ingameGui;
    }

    public TeamsDefinition getTeamsDefinition() {
        if(teamsDefinition == null){
            teamsDefinition = new TeamsDefinition();
        }
        return teamsDefinition;
    }

    public InGameCountdown getInGameCountdown() {
        if(inGameCountdown == null){
            inGameCountdown = new InGameCountdown();
        }
        return inGameCountdown;
    }

    public LobbyCountdown getLobbyCountdown() {
        if(lobbyCountdown == null){
            lobbyCountdown = new LobbyCountdown();
        }
        return lobbyCountdown;
    }

    public TeamGui getTeamGui() {
        if(teamGui == null){
            teamGui = new TeamGui();
        }
        return teamGui;
    }

    public void setTeamAssignment(Player player, Teams team)
    {
        teamAssignment.put(player, team);
    }

    public void removeTeamAssignment(Player player)
    {
        teamAssignment.remove(player);
    }

    public Teams getTeamAssignment(Player player) {
        return teamAssignment.get(player);
    }

    public GameStateManager getGameStateManager()
    {
        if(gameStateManager == null){
            gameStateManager = new GameStateManager();
        }
        return gameStateManager;
    }

    public Teams getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(Teams winningTeam) {
        this.winningTeam = winningTeam;
    }
}
