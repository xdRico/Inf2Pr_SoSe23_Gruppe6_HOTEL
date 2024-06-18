package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.core.GameMain;

/**
 * Start game event class to start a game in the game main
 * 
 * @author sudin
 */
public class StartGameEvent {

	private GameMain main;

	/**
	 * Method to start the current game
	 * 
	 * @param main - current game main
	 */
	public StartGameEvent(GameMain main) {
		this.main = main;
	}

	/**
	 * Method to request the game main
	 * 
	 * @returns main - returns the current game
	 */
	public GameMain getMain() {
		return main;
	}
}
