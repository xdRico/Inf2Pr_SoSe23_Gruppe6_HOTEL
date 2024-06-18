package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;

/**
 * Create Game Event Class to create Game in a game instance
 * 
 * @author sudin
 * 
 */

public class CreateGameEvent extends GameEvent {


	private NetworkSetting netSetting;

	/**
	 * Main method to create a Game in a game instance
	 * 
	 * @param gamesManager - the gamesManager of the current game instance
	 * @param netMode      - the network mode of the current game instance
	 */
	public CreateGameEvent(GamesManager gamesManager, NetworkSetting netSettings) {
		super(gamesManager, null);
		this.netSetting = netSettings;
		gameMain = gamesManager.createGame(netSettings);
	}

	/**
	 * Method to return current Game Main
	 * 
	 * @returns gameMain - returns the Game Main of the current Game instance
	 */
	public GameMain getGameMain() {
		return gameMain;
	}

	/**
	 * Method to return current Network mode
	 * 
	 * @return netMode - returns the network mode of current Game instance
	 */
	public NetworkSetting getNetworkSetting() {
		return netSetting;
	}

}
