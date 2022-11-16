package de.rexituz.bingo.gamestates;

public class GameStateManager
{
	private GameStates[] gameStates;
	private GameStates currentGameState;
	
	public GameStateManager()
	{
		gameStates = new GameStates[3];
		
		gameStates[GameStates.LOBBY_STATE] = new LobbyState();
		gameStates[GameStates.INGAME_STATE] = new InGameState();
		gameStates[GameStates.ENDING_STATE] = new EndingState();
	}
	
	public void setGameState(int gameStateID) 
	{
		if(currentGameState == gameStates[gameStateID]) return;
		if(currentGameState != null)
			currentGameState.stop();
		currentGameState = gameStates[gameStateID];
		currentGameState.start();
	}
	
	public void stopCurrentGameState() 
	{
		if(currentGameState != null) {
			currentGameState.stop();
			currentGameState = null;
		}
	}
	
	public GameStates getCurrentGameState()
	{
		return currentGameState;
	}
}
