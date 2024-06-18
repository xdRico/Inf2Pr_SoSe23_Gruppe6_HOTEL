package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;

/**
 * Game Event class to handle Events in a game instance
 * 
 * @author sudin
 */
public class GameEvent extends Event {

	protected GamesManager gamesManager;
	protected GameMain gameMain;


	/**
	 * Method to set a Game Event to the current Game instance
	 * 
	 * @param gamesManager - parameter for the current Game instance
	 */
	public GameEvent(GamesManager gamesManager, GameMain gameMain) {

		this.gamesManager = gamesManager;
		this.gameMain = gameMain;
	}

	/**
	 * Method to request the current Game Manager for Game instance
	 * 
	 * @returns gamesManager - returns current Game Manager
	 */
	protected GamesManager getGamesManager() {
		return gamesManager;
	}
	
	/**
	 * Method to get the current Game
	 * 
	 * @returns main - the current GameMain
	 */
	protected GameMain getGame() {
		return gameMain;
	}
}
