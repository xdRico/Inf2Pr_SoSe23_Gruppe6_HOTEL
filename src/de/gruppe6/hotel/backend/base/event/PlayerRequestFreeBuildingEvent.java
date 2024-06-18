package de.gruppe6.hotel.backend.base.event;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.frontend.base.exception.NothingToBuildException;

/**
 * Player request free building event class to request the buildings a player an
 * build on his properties
 * 
 * @author sven
 */
public class PlayerRequestFreeBuildingEvent extends PlayerEvent {

	private IBuildable building;
	private List<IBuildable> unBuilt = new ArrayList<IBuildable>();

	/**
	 * Method to create an event to handle the building on the property of a player
	 * 
	 * @param player - the current player requesting the building event
	 */
	public PlayerRequestFreeBuildingEvent(Player player) {
		super(player);
		for (Property property : player.getProperties()) {
			unBuilt.addAll(property.getUnBuiltBuildings());
		}
		try {
			if (unBuilt.size() == 0)
				throw new NothingToBuildException();
			player.getGame().getOutput().buildable(unBuilt);
			building = player.getGame().getInput().getToBuild(unBuilt).get(0);
		} catch (NothingToBuildException e) {
			player.getGame().announce("Kein Grunstück, um ein Gebäude zu bauen!");
		}
	}

	/**
	 * Method to request the building the player can build
	 * 
	 * @return building - the building available to be built by the player
	 */
	public IBuildable getBuilding() {
		return building;
	}

}
