package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.enviroment.MapField;
//import de.gruppe6.hotel.backend.base.enviroment.MapField;
//import de.gruppe6.hotel.backend.base.enviroment.MapFieldType;
import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player move event class to handle the players movement on the game map
 * 
 * @author sudin
 */
public class PlayerMoveEvent extends PlayerEvent {

	// private MapField mapField;
	private int fieldsToMove;
	private MapField getFrom;

	/**
	 * Method to create a move event for the current player
	 * 
	 * @param player       - the current player on turn to move on the game map
	 * @param fieldsToMove - the amount of fields the player can move on the game
	 *                     map
	 */
	public PlayerMoveEvent(Player player, int fieldsToMove) {
		super(player);
		this.fieldsToMove = fieldsToMove;
		this.getFrom = player.getMapField();
	}

	/**
	 * Method to move the player to a new position
	 */
	public void move() {
		for (int i = 1; i <= fieldsToMove; i++) {
			int field = player.getGame().getMap().indexOf(player.getMapField()) + i;
			switch (player.getGame().getMap().getField(field).getType()) {
			case CityHallField:
				new PlayerMoveOverCityHallEvent(player).buildEntrance();
				break;
			case BankField:
				new PlayerMoveOverBankEvent(player).payout();
				break;
			case StartField:
				if (getFrom != null)
					player.setCanBuild();
				break;
			default:
				break;
			}
			if (player.getGame().getMap().getField(field).getType().isOversteppable() == true) {
				fieldsToMove++;
			}
			if (i == fieldsToMove) {
				boolean isFree = true;
				for (Player p : player.getGame().getPlayers()) {
					if (player == p)
						continue;
					if (p.getMapField() == null)
						continue;
					if (player.getGame().getMap().getField(field) == p.getMapField()) {
						fieldsToMove++;
						isFree = false;
					}
				}
				if (isFree) {
					player.move(player.getGame().getMap().getField(field));
					break;
				}
			}
		}
		player.getGame().getOutput().movePlayer(player, fieldsToMove);
	}

	/**
	 * Method to request the amount of fields the player can move
	 * 
	 * @returns fieldsToMove - amount of fields a player can move
	 */
	public int getFieldsToMove() {
		return fieldsToMove;
	}
}
