package de.gruppe6.hotel.backend.base.exception;

/**
 * Exception class, thrown when a GameMap is locked but is tried to be edited
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class GameMapLockedException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = -7285986004093562172L;

	/**
	 * Exception class, thrown when a GameMap is locked but is tried to be edited
	 */
	public GameMapLockedException() {

		super("GameMapLockedException");
	}

}
