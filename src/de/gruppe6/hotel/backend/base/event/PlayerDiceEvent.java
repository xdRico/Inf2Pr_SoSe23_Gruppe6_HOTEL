package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player dice event to select the dice that is to be rolled
 * 
 * @author sven
 */
public class PlayerDiceEvent extends PlayerEvent {

	DiceType type;

	/**
	 * Method to assign the dice type
	 * 
	 * @param player - the current player rolling the dice
	 * @param type   - the type of dice that is to be rolled
	 */
	public PlayerDiceEvent(Player player, DiceType type) {
		super(player);
		this.type = type;
	}

	/**
	 * Method to request the dice type
	 * 
	 * @return type - returns the type of the current dice
	 */
	public DiceType getType() {
		return type;
	}
}
