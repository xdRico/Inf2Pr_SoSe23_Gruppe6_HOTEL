package de.gruppe6.hotel.backend.extended.exception;

import de.gruppe6.hotel.backend.extended.network.NetworkSetting;

/**
 * Exception class; Thrown, when the ServerSocket is tried to be called, 
 * but the NetworkSetting is configured as Client
 * 
 * @author ricow
 *
 */
public class IsNotAServerException extends NetworkException {

	@java.io.Serial
	private static final long serialVersionUID = -6261120209084238370L;
	
	/**
	 * Exception class; Thrown, when the ServerSocket is tried to be called, 
	 * but the NetworkSetting is configured as Client
	 * 
	 * @param setting - the NetworkSetting causing the Exception
	 */
	public IsNotAServerException(NetworkSetting setting) {
		super(setting);
	}
	
	/**
	 * Exception class; Thrown, when the ServerSocket is tried to be called, 
	 * but the NetworkSetting is configured as Client
	 * 
	 * @param setting - the NetworkSetting causing the Exception
	 * @param message - the Message to be used for the Exception
	 */
	public IsNotAServerException(NetworkSetting setting, String message) {
		super(setting, message);
	}
}
