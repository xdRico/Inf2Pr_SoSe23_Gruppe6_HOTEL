package de.gruppe6.hotel.backend.base.event;

import de.gruppe6.hotel.backend.base.player.Player;

/**
 * Player Bankruptcy event to expell a player from the game.
 * 
 * @author sven
 */
public class PlayerBankruptcyEvent extends PlayerEvent {

	private boolean isGameEnd = false;
	int money;

	public PlayerBankruptcyEvent(Player player, int money) {
		super(player);
		this.money = money;
		if (player.getGame().getPlayers().size() <= 2) {
			Player winner;
			for (Player p : player.getGame().getPlayers()) {
				if (p != player) {
					isGameEnd = true;
					winner = p;
					new FinishGameEvent(winner);
				}
			}
		}
	}

	public void foreclosure() {
		if (isGameEnd)
			return;
//		PlayerAuctionEvent event = new PlayerAuctionEvent(player);
//		while(player.getMoney() < 0) {
//			String input
//			while() {}
//			new PlayerAuctionBidEvent(player, event.getNextToBeAuctioned());
//
//		}
	}

}
