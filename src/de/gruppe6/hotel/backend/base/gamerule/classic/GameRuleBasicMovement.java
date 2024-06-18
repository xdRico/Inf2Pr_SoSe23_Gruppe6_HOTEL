package de.gruppe6.hotel.backend.base.gamerule.classic;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.event.PlayerMoveEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRollSixDiceEvent;
import de.gruppe6.hotel.backend.base.gamerule.IGameRule;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.extended.network.NetworkMode;


/**
 * Classic Game Rule, that defines the classic movement process
 * 
 * @implements IGameRule
 * @author ricow
 *
 */
public class GameRuleBasicMovement implements IGameRule {
	
	

		@Override
		public void ruleBody(GameMain gameMain, Player player) {
			
			if (gameMain.getNetworkSetting().getMode() == NetworkMode.LOCALPC) {
	
			new PlayerMoveEvent(player, new PlayerRollSixDiceEvent(player).getSixRolled()).move();
			}
			
			else {
				//Add onlineinterface function
			}
			
		
		}
}
