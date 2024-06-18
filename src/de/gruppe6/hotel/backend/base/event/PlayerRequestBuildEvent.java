package de.gruppe6.hotel.backend.base.event;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapFieldType;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.frontend.base.exception.NothingToBuildException;

/**
 * Player request building event class to request the buildings a player can
 * build on his properties
 * 
 * @author sudin
 */
public class PlayerRequestBuildEvent extends PlayerEvent {

	private List<IBuildable> buildings = new ArrayList<IBuildable>();
	protected List<IBuildable> unBuilt = new ArrayList<IBuildable>();

	/**
	 * Method to create a list of the buildings a player can choose from to build on
	 * his property
	 * 
	 * @param player - the current player requesting the build event
	 */
	public PlayerRequestBuildEvent(Player player) {
		super(player);
		for (Property property : player.getProperties()) {
			unBuilt.addAll(property.getUnBuiltBuildings());
		}
		try {
			if (unBuilt.size() == 0)
				throw new NothingToBuildException();
			if (!player.canBuild() && player.getMapField().getType() != MapFieldType.FreeBuildingField)
				throw new NothingToBuildException();
			player.getGame().getOutput().buildable(unBuilt);
			buildings = player.getGame().getInput().getToBuild(unBuilt);
		} catch (NothingToBuildException e) {
			player.getGame().announce("Kein Gebäude, das gebaut werden kann!");
		}
	}

	/**
	 * Method to request the buildings available to be built
	 * 
	 * @returns buildings - list of all available buildings the player can build
	 */
	public List<IBuildable> getBuildings() {
		return buildings;
	}
}
