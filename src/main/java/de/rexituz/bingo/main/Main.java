package de.rexituz.bingo.main;

import de.rexituz.bingo.commands.StartCommand;
import de.rexituz.bingo.config.ConfigFile;
import de.rexituz.bingo.countdowns.EndingCountdown;
import de.rexituz.bingo.countdowns.InGameCountdown;
import de.rexituz.bingo.countdowns.LobbyCountdown;
import de.rexituz.bingo.events.JoinEvent;
import de.rexituz.bingo.events.LittleListeners;
import de.rexituz.bingo.events.QuitListener;
import de.rexituz.bingo.gamestates.GameStateManager;
import de.rexituz.bingo.gamestates.GameStates;
import de.rexituz.bingo.gui.IngameGui;
import de.rexituz.bingo.gui.IngameItem;
import de.rexituz.bingo.gui.TeamGui;
import de.rexituz.bingo.random.RandomItems;
import de.rexituz.bingo.scoreboards.EndingScoreboard;
import de.rexituz.bingo.scoreboards.InGameScoreboard;
import de.rexituz.bingo.scoreboards.LobbyScoreboard;
import de.rexituz.bingo.teams.Teams;
import de.rexituz.bingo.teams.TeamsDefinition;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
    EndingCountdown endingCountdown;
    GameStateManager gameStateManager;
    LobbyScoreboard lobbyScoreboard;
    InGameScoreboard inGameScoreboard;
    EndingScoreboard endingScoreboard;
    HashMap<Player, Teams> teamAssignment = new HashMap<Player, Teams>();
    Teams winningTeam;
    ConfigFile configFile;

    @Override
    public void onEnable() {
        plugin = this;
        registerEvents();
        commandRegistration();
        getServer().createWorld(new WorldCreator("lobby"));
        gamestateRegistration();
        setupGamerules();
        getRandomItems().randomizeItems();
    }

    @Override
    public void onDisable() {

    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new IngameItem(), this);
        pluginManager.registerEvents(new IngameGui(), this);
        pluginManager.registerEvents(new JoinEvent(), this);
        pluginManager.registerEvents(new TeamGui(), this);
        pluginManager.registerEvents(new LittleListeners(), this);
        pluginManager.registerEvents(new QuitListener(), this);
    }

    private void commandRegistration() {
        getCommand("start").setExecutor(new StartCommand());
    }

    private void gamestateRegistration(){
        getGameStateManager().setGameState(GameStates.LOBBY_STATE);
    }

    private void setupGamerules(){
        World world = Bukkit.getWorld("world");
        world.setTime(1000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setStorm(false);
        world.setDifficulty(Difficulty.PEACEFUL);
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

    public EndingCountdown getEndingCountdown() {
        if(endingCountdown == null) {
            endingCountdown = new EndingCountdown();
        }
        return endingCountdown;
    }

    public LobbyScoreboard getLobbyScoreboard() {
        if(lobbyScoreboard == null){
            lobbyScoreboard = new LobbyScoreboard();
        }
        return lobbyScoreboard;
    }

    public InGameScoreboard getInGameScoreboard() {
        if(inGameScoreboard == null){
            inGameScoreboard = new InGameScoreboard();
        }
        return inGameScoreboard;
    }

    public EndingScoreboard getEndingScoreboard() {
        if(endingScoreboard == null){
            endingScoreboard = new EndingScoreboard();
        }
        return endingScoreboard;
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

    public ConfigFile getConfigFile() {
        if(configFile == null){
            configFile = new ConfigFile();
        }
        return configFile;
    }
}
