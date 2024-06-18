package de.gruppe6.hotel.backend.base.exception;

import de.gruppe6.hotel.backend.base.core.GameMain;

/**
 * Exception class, thrown when a Game already started is tried to be started
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class GameRunningException extends IllegalStateException {

	private GameMain game;

	@java.io.Serial
	private static final long serialVersionUID = 3638137344682324415L;

	/**
	 * Exception class, thrown when a Game already started is tried to be started
	 * 
	 * @param game - the GameMain that is tried to be started
	 */
	public GameRunningException(GameMain game) {

		super("GameRunningException " + game.toString());
		this.game = game;
	}

	/**
	 * Method to get the GameMain, that was tried to be started
	 * 
	 * @return String - GameMain name
	 */
	public String getGame() {

		return game.toString();
	}
}
