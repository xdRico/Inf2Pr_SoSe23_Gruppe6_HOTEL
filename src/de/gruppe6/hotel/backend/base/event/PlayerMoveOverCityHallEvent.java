package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;

/**
 * Player move over city hall class event to handle event from player moving
 * over the game field
 * 
 * @author sven
 */

public class PlayerMoveOverCityHallEvent extends PlayerEvent {

	/**
	 * Method for stepping over the game city hall field
	 * 
	 * @param player - current player moving over the game map field
	 */
	public PlayerMoveOverCityHallEvent(Player player) {
		super(player);
	}

	/**
	 * Method to trigger the stair building event from stepping over the bank map
	 * field
	 */
	public void buildEntrance() {
		player.getGame().announce(player + " ist über die Stadthalle gelaufen und darf einen Eingang bauen!");
		try {
			PlayerBuyStairEvent event = new PlayerBuyStairEvent(player);
			event.buildEntrance();
			event.pay();
		} catch (NoEntranceToBuild e) {
			Log.sendMessage("No Entrance built!");
		}
	}
}