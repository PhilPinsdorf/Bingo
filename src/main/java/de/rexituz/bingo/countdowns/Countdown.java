package de.rexituz.bingo.countdowns;

public abstract class Countdown
{
	protected int lobbyID;
	protected int inGameID;
	protected int roleChooseID;
	
	public abstract void start();
	public abstract void stop();
}
