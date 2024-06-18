package de.gruppe6.hotel.backend.extended.network.io;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.event.Event;
import de.gruppe6.hotel.backend.base.player.Player;

public interface INetServer {

	public void InitGame(GameMain main);
	
	public void changedSettings(GameMain main);
	
	public void startGame(GameMain main);
	
	public void sendStarted(boolean running);
	
	public void switchPlayer(Player player);
	
	public void movePlayer(Player player, int fields);
	
	public void updatePlayer(Player player);
	
	public void throwEvent(Event event);
	
}
