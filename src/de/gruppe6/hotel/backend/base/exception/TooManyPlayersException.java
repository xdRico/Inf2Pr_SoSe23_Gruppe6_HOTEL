package de.gruppe6.hotel.backend.base.exception;

/**
 * Exception class, thrown when a Game already has the maximum amount of Players
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class TooManyPlayersException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = -2732278354626861955L;

	/**
	 * Exception class, thrown when a Game already has the maximum amount of Players
	 */
	public TooManyPlayersException() {

		super("TooManyPlayersException");
	}
}
