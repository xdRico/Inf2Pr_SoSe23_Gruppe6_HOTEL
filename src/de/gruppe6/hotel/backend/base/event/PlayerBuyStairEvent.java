package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerBankruptcy;
import de.gruppe6.hotel.util.Log;

/**
 * Player buy stair event to handle
 * 
 * @author sven
 */
public class PlayerBuyStairEvent extends PlayerStairEvent {

	/**
	 * Method to assign stairs to the property of a player
	 * 
	 * @param player - the current player buying the stairs
	 */
	public PlayerBuyStairEvent(Player player) {
		super(player);
	}

	/**
	 * Method to let the Player pay for the stair
	 */
	public void pay() {
		try {
			player.removeMoney(getPrice());
		} catch (NoEntranceFoundException e) {
			Log.sendMessage("No Entrance that should be built!");
		} catch (PlayerBankruptcy e) {
			player.getGame().announce("Spieler ist pleite!");
			Log.sendException(e);
		}
	}
}
