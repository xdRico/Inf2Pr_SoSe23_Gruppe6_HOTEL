package de.gruppe6.hotel.backend.base.gamerule.classic;

import java.util.List;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Park;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.event.IsNotAllowedToBuy;
import de.gruppe6.hotel.backend.base.event.NoEntranceToBuild;
import de.gruppe6.hotel.backend.base.event.PlanningDice;
import de.gruppe6.hotel.backend.base.event.PlayerBuildBuildingEvent;
import de.gruppe6.hotel.backend.base.event.PlayerBuyPropertyEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRequestFreeBuildingEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRollPlanningDiceEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRequestBuildEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRequestBuyEvent;
import de.gruppe6.hotel.backend.base.event.PlayerStairEvent;
import de.gruppe6.hotel.backend.base.event.PlayerStayNightEvent;
import de.gruppe6.hotel.backend.base.gamerule.IGameRule;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.util.Log;

/**
 * Classic Game Rule, that defines the Actions of the MapField the player stays
 * on
 * 
 * @implements IGameRule
 * @author ricow
 * 
 */
public class GameRuleMapFieldAction implements IGameRule {

	@Override
	public void ruleBody(GameMain gameMain, Player player) {

		fieldActions(player);
		fieldTypeActions(player);
	}

	/**
	 * Method to handle the Entrances that are on the MapField
	 * 
	 * @param player - Player, who is at turn
	 */
	private void fieldActions(Player player) {

		MapField field = player.getMapField();
		Entrance stair = field.getEntrance();
		if (stair != null && stair.getProperty().getPlayer() != player) {
			new PlayerStayNightEvent(player, stair);
		}
	}

	/**
	 * Method to handle the MapFieldType of the MapField the player stands on
	 * 
	 * @param player - Player, who is at turn
	 */
	private void fieldTypeActions(Player player) {

		MapField field = player.getMapField();

		switch (field.getType()) {
		case StartField:
			player.getGame().announce("Hier kannst du nichts tun!");
			break;
		case BuyField:
			Property buy = new PlayerRequestBuyEvent(player).getBought();
			if (buy != null)
				new PlayerBuyPropertyEvent(player, buy).buyProperty();
			break;
		case FreeStairField:
			try {
				new PlayerStairEvent(player).buildEntrance();
			} catch (NoEntranceToBuild e) {
				Log.sendMessage("No Entrance built!");
			}
			break;
		case FreeBuildingField:
			IBuildable freeBuilding = new PlayerRequestFreeBuildingEvent(player).getBuilding();
			if (freeBuilding == null)
				break;
			try {
				new PlayerBuildBuildingEvent(player, freeBuilding).buildBuilding(true);
			} catch (IsNotAllowedToBuy e) {
				Log.sendMessage("Player is not allowed to buy.");
			}
			break;
		default: // BuildField
			List<IBuildable> buildings = new PlayerRequestBuildEvent(player).getBuildings();
			if (buildings.size() == 0)
				break;
			try {
				PlanningDice dice = null;
				for (IBuildable building : buildings) {
					if(!(building instanceof Park)) {
						dice = new PlayerRollPlanningDiceEvent(player).getPlanningRolled();
						break;
					}
				}
				for(IBuildable building : buildings)
					new PlayerBuildBuildingEvent(player, building).buildBuilding(dice);
				
			} catch (IsNotAllowedToBuy e) {
				Log.sendMessage("Player is not allowed to buy.");
				break;
			}
			break;
		}
	}
}
