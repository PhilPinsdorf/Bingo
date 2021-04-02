package de.rexituz.bingo.gamestates;

public abstract class GameStates
{
	public static final int LOBBY_STATE = 0;
	public static final int INGAME_STATE = 1;
	public static final int ENDING_STATE = 2;
	
	public abstract void start();
	public abstract void stop();
}
