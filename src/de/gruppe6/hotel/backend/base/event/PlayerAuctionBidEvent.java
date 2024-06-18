package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.enviroment.Property;

/**
 * Player auction bid event class to handle the bids in an ongoing Auction
 * 
 * @author sudin
 */
public class PlayerAuctionBidEvent extends PlayerAuctionEvent {

	private int currentBid = 0;
	private Player lastPlayerBid;

	/**
	 * Method to collect player bids
	 * 
	 * @param player - the current player doing a bid
	 * @param bid    - the value of the bid
	 */
	public PlayerAuctionBidEvent(Player player, int bid) {
		super(player);
		if (player.getMoney() >= currentBid) {
			currentBid += bid;
			lastPlayerBid = this.player;
		} else {
			return;
		}

	}

	/**
	 * Method to end auction and transfer properties and money between players
	 * 
	 * @param player     - the current players auction
	 * @param currentBid - the bid amount to pay
	 * @param property   - the current Property that is being auctioned
	 */
	public void PlayerAuctionEndEvent(Property property) {
		property.getPlayer().sellProperty(lastPlayerBid, property, currentBid);
		return;
	}

	/**
	 * Method to return the current bid value
	 * 
	 * @returns currentBid - the value of the Bid ongoing in the current auction
	 */
	public int getCurrentBid() {
		return currentBid;
	}

	/**
	 * Method to request the last player to bid in an auction
	 * 
	 * @returns lastPlayerBid - the last player placing a bid in an auction
	 */
	public Player getLastPlayerBid() {
		return lastPlayerBid;
	}
}
