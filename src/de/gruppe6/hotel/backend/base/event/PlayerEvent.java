package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player event class to handle the events from the current player on turn
 * 
 * @author sven
 */
public class PlayerEvent extends Event {

	protected Player player;

	/**
	 * Method to assign the current player on turn
	 * 
	 * @param player - current player on turn
	 */
	public PlayerEvent(Player player) {
		this.player = player;
	}

	/**
	 * Method to request the current player on turn
	 * 
	 * @return player - returns the current player
	 */
	public final Player getPlayer() {
		return player;
	}

}
