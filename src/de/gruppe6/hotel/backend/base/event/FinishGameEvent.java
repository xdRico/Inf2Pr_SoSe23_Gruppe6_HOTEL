package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Class to be called, when a Game is ended by being won by a Player
 * 
 * @author ricow
 *
 */
public class FinishGameEvent {

	/**
	 * Class to be called, when a Game is ended by being won by a Player
	 * 
	 * @param winner - the winner of the game
	 */
	public FinishGameEvent(Player winner) {
		winner.getGame().announce("Glückwunsch!!\n" + winner + " hat das Spiel gewonnen!");
		winner.getGame().stop();
	}

}
