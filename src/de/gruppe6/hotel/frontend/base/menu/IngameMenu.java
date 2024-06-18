package de.gruppe6.hotel.frontend.base.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.extended.gamesavemanager.GameState;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.util.Log;

public class IngameMenu extends Gruppe6JPanel {

	private static final long serialVersionUID = -4876033949411158180L;


	public IngameMenu(GameMain main, Gruppe6JPanel panel) {
		Gruppe6JPanel menuPanel = new Gruppe6JPanel();
		JButton buttonResume = new JButton("Weiter");
		JButton buttonBackToMainMenu = new JButton("Zurück zum Hauptmenü");
		JButton buttonSettings = new JButton("Einstellungen");
		JButton buttonRestart = new JButton("Neue Runde starten");
		JButton buttonSave = new JButton("Speichern");

		
		buttonResume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(menuPanel);
				panel.refresh();
			}

		});
		
		buttonBackToMainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				main.stop();
			}

		});

		buttonSettings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Function not implemented right now - Add calling settings in Package 3
			}

		});

		buttonRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				manager.start(main);
			}

		});
		
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GameState.save(main);
					buttonSave.setForeground(Color.GREEN);
				}catch(IOException ex) {
					buttonSave.setForeground(Color.RED);
					Log.sendException(ex);
				}
			}
		});
		menuPanel.removeAll();
		menuPanel.add(buttonResume);
//		menuPanel.add(buttonSettings);
		menuPanel.add(buttonSave);
		//interactionPanel.add(buttonRestart);
		menuPanel.add(buttonBackToMainMenu);
		menuPanel.setOpaque(false);
		panel.add(menuPanel);
		panel.setOpaque(false);
		panel.setVisible(true);

		
	}

}
