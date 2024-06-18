package de.gruppe6.hotel.backend.base.exception;

/**
 * Exception class, thrown when a IBuildable is not buyable but is tried to be
 * bought
 * 
 * @extends IllegalStateException
 * @author ricow
 *
 */
public class IsNotBuyableException extends IllegalStateException {

	@java.io.Serial
	private static final long serialVersionUID = -8934031803980193912L;

	/**
	 * Exception class, thrown when a IBuildable is not buyable but is tried to be
	 * bought
	 */
	public IsNotBuyableException() {
		super("IsNotBuyableException");
	}

}
