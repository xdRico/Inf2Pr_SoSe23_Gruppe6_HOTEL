package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player move over bank event class to get the payout from moving over bank
 * game field
 * 
 * @author sven
 *
 */
public class PlayerMoveOverBankEvent extends PlayerEvent {

	/**
	 * Method for stepping over the game Bank field
	 * 
	 * @param player - current player moving over the game map field
	 */
	public PlayerMoveOverBankEvent(Player player) {
		super(player);
	}

	/**
	 * Method to collect the payout from stepping over the bank map field
	 */
	public void payout() {
		player.payoutAtBank();
		player.getGame().announce(player + " ist über die Bank gelaufen und hat 2000 einkassiert!");
	}
}
