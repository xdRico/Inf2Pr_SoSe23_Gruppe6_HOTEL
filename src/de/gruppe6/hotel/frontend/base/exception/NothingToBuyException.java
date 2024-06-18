package de.gruppe6.hotel.frontend.base.exception;

/**
 * Exception class, thrown when a Player has nothing to buy
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class NothingToBuyException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = 5218076635849238704L;

	/**
	 * Exception class, thrown when a Player has nothing to buy
	 */
	public NothingToBuyException() {
		super("NothingToBuyException");
	}
}
