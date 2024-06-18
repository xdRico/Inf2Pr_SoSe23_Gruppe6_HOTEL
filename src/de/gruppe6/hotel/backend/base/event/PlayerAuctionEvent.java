package de.gruppe6.hotel.backend.base.event;

import java.util.List;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.enviroment.Property;

/**
 * Player auction event to handle auctions from a player
 * 
 * @author sudin
 */
public class PlayerAuctionEvent extends PlayerEvent {

	Property next;

	/**
	 * Method to set the Player creating the auction
	 * 
	 * @param player - current player for the auction
	 */
	public PlayerAuctionEvent(Player player) {
		super(player);
	}

	/**
	 * Method to return the properties the current player owns
	 * 
	 * @returns properties - properties owned by the player creating the auction
	 */
	public List<Property> getSellableProperties() {
		return player.getProperties();
	}

	/**
	 * Method to ask for the next Property to be auctioned
	 * 
	 * @return Property - the next Property to be auctioned
	 * @throws NothingToBeAuctioned - thrown, when the player owns no (more)
	 *                              Properties
	 */
	public Property getNextToBeAuctioned() throws NothingToBeAuctioned {
		if (getSellableProperties().size() == 0) {
			player.getGame().announce("Nothing can be sold!");
			throw new NothingToBeAuctioned();
		}
		player.getGame().getOutput().buyable(getSellableProperties());
		next = player.getGame().getInput().getToBuy(getSellableProperties());
		return next;
	}
}
