package de.gruppe6.hotel.backend.base.exception;

/**
 * Exception class, thrown when a Property has no IBuildable added
 * 
 * @extends Exception
 * @author ricow
 *
 */
public class IllegalPropertyException extends Exception {

	@java.io.Serial
	private static final long serialVersionUID = 5155436351546061583L;

	/**
	 * Exception class, thrown when a Property has no IBuildable added
	 */
	public IllegalPropertyException() {
		super("IllegalPropertyException");
	}
}
