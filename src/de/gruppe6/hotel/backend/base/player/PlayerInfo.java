package de.gruppe6.hotel.backend.base.player;

import java.net.Socket;

/**
 * Class containing the basic info of a Player
 * 
 * @author ricow
 *
 */
public class PlayerInfo {

	private String name;
	private PlayerColor color;
	private Socket socket;
	
	/**
	 * Class containing the basic info of a Player
	 * @param name
	 * @param color
	 */
	public PlayerInfo(String name, PlayerColor color) {
		this.name = name;
		this.color = color;
	}
	
	public PlayerInfo(String name, PlayerColor color, Socket client) {
		this(name,color);
		this.socket = client;
	}
	
	/**
	 * Method to get the Name of the PlayerInfo
	 * 
	 * @return String - the Name set to this PlayerInfo
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Method to get the PlayerColor of the PlayerInfo
	 * 
	 * @return PlayerColor - the PlayerColor set to this PlayerInfo
	 */
	public PlayerColor getColor() {
		return color;
	}
	
	/**
	 * Method to get the Socket used for the connection
	 */
	public Socket getClient() {
		return socket;
	}
}
