package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player stay night event for when a player has to stay at a different players
 * building
 * 
 * @author sudin
 */
public class PlayerStayNightEvent extends PlayerEvent {

	Property property;
	Entrance stair;

	/**
	 * Method to create a night event for the player that stays at a different
	 * players property
	 * 
	 * @param player - the current player at turn
	 * @param stair  - the stair that goes to a different players building
	 */
	public PlayerStayNightEvent(Player player, Entrance stair) {
		super(player);
		this.stair = stair;
		this.property = stair.getProperty();
		int dice = new PlayerRollSixDiceEvent(player).getSixRolled();
		int price = property.getStayPrice() * dice;
		player.getGame()
				.announce("Du hast " + dice + " Nächte für " + price + "€ im " + property.getName() + "übernachtet!");
		player.transactionToPlayer(price, property.getPlayer());
	}

	/**
	 * Method to request the current stair the player stays on on the game map
	 * 
	 * @returns stair - current stairs on the game map
	 */
	public Entrance getEntrance() {
		return stair;
	}

}
