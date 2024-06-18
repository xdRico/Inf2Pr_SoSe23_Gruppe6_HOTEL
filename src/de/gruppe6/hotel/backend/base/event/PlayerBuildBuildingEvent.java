package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.Park;
import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player building class to assign a building to the current players property
 * 
 * @author sudin
 */
public class PlayerBuildBuildingEvent extends PlayerEvent {

	private IBuildable building;
	private PlanningDice dice;

	/**
	 * Method to build a building on a players property
	 * 
	 * @param player   - the current player building a building on his property
	 * @param building - the building the player chooses to build
	 */
	public PlayerBuildBuildingEvent(Player player, IBuildable building) {
		super(player);
		this.building = building;
	}

	/**
	 * Method to assign a building to a players property
	 * 
	 * @throws IsNotAllowedToBuy
	 */
	public void buildBuilding(boolean isFree) throws IsNotAllowedToBuy {
		int price = building.getBuyPrice();
		if(isFree) {
			price = 0;
		} else {
			if (!(building instanceof Park)) {
				if(dice == null)
					dice = new PlayerRollPlanningDiceEvent(player).getPlanningRolled();
				switch (dice) {
				case DENIED:
					throw new IsNotAllowedToBuy();
				case FREE:
					price = 0;
					break;
				case DOUBLE:
					price *= 2;
					break;
				default:
					break;
				}
			}
		}
		building.buy(price);
		player.getGame().announce("Du musstest " + price + " für " + building.getName() + " abdrücken!");
	}
	
	/**
	 * Method to assign a building to a players property
	 * 
	 * @throws IsNotAllowedToBuy
	 */
	public void buildBuilding(PlanningDice dice) throws IsNotAllowedToBuy {
		this.dice = dice;
		buildBuilding(false);
	}
}