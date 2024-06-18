package de.gruppe6.hotel.frontend.base.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.base.core.ProgramMain;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerInfo;
import de.gruppe6.hotel.backend.extended.network.NetworkMode;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.util.Log;

public class CreateMenu {

	public CreateMenu(ProgramMain program, MainMenu menu) {

		program.clear();
		Gruppe6JPanel menuPanel = new Gruppe6JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
		GamesManager manager = program.gamesManager();
		Gruppe6JPanel panel = new Gruppe6JPanel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setPreferredSize(new Dimension(program.getWidth(), 300));
		Gruppe6JPanel networkPanel = new Gruppe6JPanel();
		menuPanel.add(networkPanel);
		PlayerAddMenu playerAddMenu = new PlayerAddMenu(menuPanel);
		NetworkModeMenu networkModeMenu= new NetworkModeMenu (networkPanel, playerAddMenu);
		
		
		// Create Button
		JButton buttonStart = new JButton("Spielstart");
		JButton buttonBack = new JButton("Zurück zum Hauptmenü");

		buttonStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (networkModeMenu.getNetworkMode() == NetworkMode.LOCALPC && playerAddMenu.getPlayers().size() < 2) {
						buttonStart.setForeground(Color.RED);
						return;
					}	
					
					InetAddress ipAdress = InetAddress.getLocalHost();
					int port = 17171;
							
					if (networkModeMenu.getNetworkMode() == NetworkMode.ONLINE
							|| networkModeMenu.getNetworkMode() == NetworkMode.LOCALNET){
						
						ipAdress =	networkModeMenu.getIpAdress();
						
					}else if (networkModeMenu.getNetworkMode() != NetworkMode.LOCALPC){
					
						port = networkModeMenu.getPort();
					
					}
					NetworkSetting setting = new NetworkSetting (networkModeMenu.getNetworkMode(),ipAdress, port);
					GameMain main = manager.createGame(setting);
					
					if(networkModeMenu.getNetworkMode() == NetworkMode.LOCALPC) {
						for(PlayerInfo info : playerAddMenu.getPlayers())
							main.addPlayer(new Player(main, info));
						
					}else if(networkModeMenu.getNetworkMode() == NetworkMode.LOCALNET 
							|| networkModeMenu.getNetworkMode() == NetworkMode.ONLINE) {
						main.addPlayer(new Player(main, playerAddMenu.getCurrent(), setting.getClient()));

					}
					setting.init(main);
					program.refreshAll();	
					if(networkModeMenu.getNetworkMode() == NetworkMode.LOCALPC)
						manager.start(main);
					else if(networkModeMenu.getNetworkMode() != NetworkMode.SERVER)
						manager.startWaiting(main);
					menu.open();
					} 
				catch (Exception e1) {
					Log.sendException(e1);
					e1.printStackTrace();
				}
			}
		});

		buttonBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menu.open();
			}
		});
		
		panel.add(buttonStart);
		
		panel.add(buttonBack);
		
		menuPanel.add(panel);
		program.add(menuPanel);
		program.refreshAll();
	}
}
