package de.gruppe6.hotel.backend.extended.network.io;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.event.Event;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;

public class ServerOutput implements INetServer {

	GameMain gameMain;
	NetworkSetting netSettings;
	
	public ServerOutput(GameMain gameMain, NetworkSetting netSettings) {
		this.gameMain = gameMain;
		this.netSettings = netSettings;
	}
	
	
	@Override
	public void InitGame(GameMain main) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changedSettings(GameMain main) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame(GameMain main) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendStarted(boolean running) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePlayer(Player player, int fields) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void throwEvent(Event event) {
		// TODO Auto-generated method stub

	}
	

}
