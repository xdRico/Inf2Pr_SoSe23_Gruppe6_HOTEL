package de.gruppe6.hotel.backend.extended.exception;

import de.gruppe6.hotel.backend.extended.network.NetworkSetting;

/**
 * Exception class; Thrown, when the Client Socket is tried to be called, 
 * but the NetworkSetting is configured as Server
 * 
 * @author ricow
 *
 */
public class IsNotAClientException extends NetworkException {

	@java.io.Serial
	private static final long serialVersionUID = -1699876180212032206L;
	
	/**
	 * Exception class; Thrown, when the Client Socket is tried to be called, 
	 * but the NetworkSetting is configured as Server
	 * 
	 * @param setting - the NetworkSetting causing the Exception
	 */
	public IsNotAClientException(NetworkSetting setting) {
		super(setting);
	}
	
	/**
	 * Exception class; Thrown, when the Client Socket is tried to be called, 
	 * but the NetworkSetting is configured as Server
	 * 
	 * @param setting - the NetworkSetting causing the Exception
	 * @param message - the Message to be used for the Exception
	 */
	public IsNotAClientException(NetworkSetting setting, String message) {
		super(setting, message);
	}
}
