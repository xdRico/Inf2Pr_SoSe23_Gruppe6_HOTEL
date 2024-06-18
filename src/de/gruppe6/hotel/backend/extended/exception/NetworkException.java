package de.gruppe6.hotel.backend.extended.exception;

import de.gruppe6.hotel.backend.extended.network.NetworkSetting;

/**
 * Exception class; Thrown at Networking problems/failures
 * @author ricow
 *
 */
public class NetworkException extends Exception {

	@java.io.Serial
	private static final long serialVersionUID = -3355768538068553748L;

	protected NetworkSetting netSettings;
	
	/**
	 * Exception class; Thrown at Networking problems/failures
	 * 
	 * @param netSetting - the NetworkSetting causing the Exception
	 */
	public NetworkException(NetworkSetting netSetting) {
		this.netSettings = netSetting;
	}
	
	/**
	 * Exception class; Thrown at Networking problems/failures
	 * 
	 * @param netSetting - the NetworkSetting causing the Exception
	 * @param message - the Message to be used for the Exception
	 */
	public NetworkException(NetworkSetting netSetting, String message) {
		super(message);
		this.netSettings = netSetting;
	}
	
	/**
	 * Method to get the NetworkSetting causing the Exception
	 * 
	 * @return NetworkSetting - the NetworkSetting causing the Exception
	 */
	public NetworkSetting getNetworkSetting() {
		return netSettings;
	}
	
}
