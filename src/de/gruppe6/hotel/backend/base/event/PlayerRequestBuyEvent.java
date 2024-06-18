package de.gruppe6.hotel.backend.base.event;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;

/**
 * Player request buy event to create a list of properties the player can buy
 * 
 * @author sudin
 */
public class PlayerRequestBuyEvent extends PlayerEvent {

	private List<Property> buy = new ArrayList<Property>();
	private Property property;

	/**
	 * Method to create a list of properties available for the player to be bought
	 * 
	 * @param player - current player requesting the buy event
	 */
	public PlayerRequestBuyEvent(Player player) {
		super(player);
		for (Property property : player.getMapField().getProperties()) {
			try {
				if (property == null) {
					continue;
				}
				if (property.getPlayer() != player && !property.isBuilt())
					buy.add(property);
			} catch (IllegalPropertyException e) {
				Log.sendException(e);
			}
		}
		if (buy.size() == 0) {
			player.getGame().announce("Kein Grunstück, das gekauft werden kann!");
			return;
		}
		player.getGame().getOutput().buyable(buy);
		property = player.getGame().getInput().getToBuy(buy);

	}

	/**
	 * Method to request the property that is getting bought by the player
	 * 
	 * @returns property - the property the player has chosen
	 */
	public Property getBought() {
		return property;
	}

}
