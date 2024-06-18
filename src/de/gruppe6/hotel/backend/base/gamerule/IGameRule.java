package de.gruppe6.hotel.backend.base.gamerule;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Interface for the GameRules that the Game supports
 * 
 * @author ricow
 *
 */
public interface IGameRule {

	/**
	 * Method that is called, when the GameRule is executed
	 * 
	 * @param gameMain - the GameMain, that uses the Rule
	 * @param player   - Player, who is at turn
	 */
	public void ruleBody(GameMain gameMain, Player player);

}
