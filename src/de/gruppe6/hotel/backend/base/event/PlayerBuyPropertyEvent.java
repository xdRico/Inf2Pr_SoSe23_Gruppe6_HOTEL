package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;

/**
 * Player buy proprety event class to handle the buying of properties from
 * players
 * 
 * @author sven
 */
public class PlayerBuyPropertyEvent extends PlayerEvent {

	private Property property;

	/**
	 * Method to create an event for a player buying a property
	 * 
	 * @param player - the current player buying the property
	 * @param prop   - the current property that is being assigned to a player
	 */
	public PlayerBuyPropertyEvent(Player player, Property property) {
		super(player);
		this.property = property;
	}

	/**
	 * Method to assign the bought property to the current player
	 */
	public void buyProperty() {
		try {
			if (property.getPlayer() != null && !property.isBuilt()) {
				property.getPlayer().sellProperty(player, property, property.getPrice());
			} else {
				player.buyProperty(property);
			}
		} catch (IllegalPropertyException e) {
			Log.sendException(e);
		}
	}

}
