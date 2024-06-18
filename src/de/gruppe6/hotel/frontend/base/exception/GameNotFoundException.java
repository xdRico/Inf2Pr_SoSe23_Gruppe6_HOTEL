package de.gruppe6.hotel.frontend.base.exception;

/**
 * Exception class, thrown when a Game can't be found but is tried to be started
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class GameNotFoundException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = 6091178348163742404L;

	/**
	 * Exception class, thrown when a Game can't be found but is tried to be started
	 */
	public GameNotFoundException() {

		super("GameNotFoundException");
	}
}
