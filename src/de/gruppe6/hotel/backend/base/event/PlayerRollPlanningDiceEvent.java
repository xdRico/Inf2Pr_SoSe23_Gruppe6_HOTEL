package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player roll planning dice event to handle the planning dice rolls from the
 * current player
 * 
 * @author sudin
 */
public class PlayerRollPlanningDiceEvent extends PlayerDiceEvent {

	private RollDice<PlanningDice> dice;

	/**
	 * Method to create a six sided dice with different weighting for the player
	 * 
	 * @param player - the current player requesting a movement dice roll
	 */
	public PlayerRollPlanningDiceEvent(Player player) {
		super(player, DiceType.Planning);
		dice = new RollDice<PlanningDice>();
		dice.addWeightedSide(PlanningDice.ACCEPTED, 30);
		dice.addWeightedSide(PlanningDice.DENIED, 10);
		dice.addWeightedSide(PlanningDice.DOUBLE, 10);
		dice.addWeightedSide(PlanningDice.FREE, 10);
	}

	/**
	 * Method to roll the dice for the current player
	 * 
	 * @returns dice - returns the state of the side the dice has landed on
	 */
	public PlanningDice getPlanningRolled() {
		player.getGame().announce("Würfeln! (Enter)");
		player.getGame().getInput().waitForNext();
		PlanningDice dice = this.dice.getRandomSide();
		player.getGame().announce("Du hast " + dice + " gewürfelt.");
		return dice;
	}
}