package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player roll movement dice class to handle the movement dice rolls from the
 * current player
 * 
 * @author sudin
 */
public class PlayerRollSixDiceEvent extends PlayerDiceEvent {

	private RollDice<RegularDice> dice;

	/**
	 * Method to create a six sided dice with equal weighting for the player
	 * 
	 * @param player - the current player requesting a movement dice roll
	 */
	public PlayerRollSixDiceEvent(Player player) {
		super(player, DiceType.Movement);
		dice = new RollDice<RegularDice>();
		dice.addWeightedSide(RegularDice.ONE, 10);
		dice.addWeightedSide(RegularDice.TWO, 10);
		dice.addWeightedSide(RegularDice.THREE, 10);
		dice.addWeightedSide(RegularDice.FOUR, 10);
		dice.addWeightedSide(RegularDice.FIVE, 10);
		dice.addWeightedSide(RegularDice.SIX, 10);
	}

	/**
	 * Method to roll the dice for the current player
	 * 
	 * @returns dice - returns the integer value of the side the dice has landed on
	 */
	public int getSixRolled() {
		return player.getGame().getOutput().rollDice(player, dice);
	}
}
