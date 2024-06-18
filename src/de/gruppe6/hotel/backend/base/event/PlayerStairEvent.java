package de.gruppe6.hotel.backend.base.event;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;

/**
 * Player stair event class to handle building entrances to hotels on a players
 * property
 * 
 * @author sven
 */
public class PlayerStairEvent extends PlayerEvent {

	private List<MapField> fields = new ArrayList<MapField>();
	private Entrance stair;
	private Property property;

	/**
	 * Method to create a list of properties to a player can build entrances on
	 * 
	 * @param player - the current player
	 */
	public PlayerStairEvent(Player player) {
		super(player);
		for (MapField mapField : player.getGame().getMap().getFields()) {
			for (Property property : mapField.getProperties()) {
				try {
					if (property == null)
						continue;
					if (property.isBuilt() && property.getPlayer() == player && mapField.getEntrance() == null) {
						fields.add(mapField);
						break;
					}
				} catch (IllegalPropertyException e) {
					Log.sendException(e);
				}
			}
		}
		if (fields.size() != 0) {
			player.getGame().getOutput().buildableStairs(player, fields);
			stair = player.getGame().getInput().getStair(player, fields);
		} else
			player.getGame().announce("Kein Eingang käuflich!");
	}

	/**
	 * Method to return the map field that the player has chosen
	 */
	public Entrance getEntrance() {
		return stair;
	}

	/**
	 * Method to build an entrance on the property of the player
	 * 
	 * @throws NoEntranceToBuild - throws if there is no property where an entrance
	 *                           could be build on
	 */
	public void buildEntrance() throws NoEntranceToBuild {
		if (stair == null) {
			throw new NoEntranceToBuild();
		}
		
		stair.getMapField().setEntrance(stair);
		player.getGame().getOutput().buildEntrance(stair);
		
	}

	/**
	 * method to get the price of the entrance
	 * 
	 * @return price - returns the price of the entrance on that property
	 * @throws NoEntranceFoundException - throws if there is no entrance to be found
	 */
	public int getPrice() throws NoEntranceFoundException {
		if (property != null) {
			return property.getStairPrice();
		} else {
			throw new NoEntranceFoundException();
		}
	}
}