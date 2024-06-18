package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;

/**
 * Quit game event to end the current game instance
 * 
 * @author sudin
 */
public class QuitGameEvent extends GameEvent{


	/**
	 * Method to remove a game instance from the game manager
	 * 
	 * @param manager - the current game manager
	 * @param main    - the current game instance
	 */
	public QuitGameEvent(GamesManager manager, GameMain gameMain) {
		super(manager, gameMain);
		manager.remove(gameMain);
	}

}
