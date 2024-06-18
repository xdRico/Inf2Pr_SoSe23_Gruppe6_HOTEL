package de.gruppe6.hotel.backend.base.exception;

/**
 * Exception class, thrown when a Game has not enough Players to start
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class NotEnoughPlayersException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = 8228389859339765835L;

	/**
	 * Exception class, thrown when a Game has not enough Players to start
	 */
	public NotEnoughPlayersException() {

		super("NotEnoughPlayersException");
	}
}
