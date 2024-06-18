package de.gruppe6.hotel.frontend.base.menu;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.ProgramMain;
import de.gruppe6.hotel.backend.extended.gamesavemanager.GameState;
import de.gruppe6.hotel.backend.extended.saveload.JSONConverter;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;


public class MainMenu extends Gruppe6JPanel {

	private static final long serialVersionUID = 2926131037369423489L;
	private ProgramMain program;

	public MainMenu(ProgramMain program) {

		this.program = program;
	}

	public void open() {

		program.clear();
		Gruppe6JPanel panel = new Gruppe6JPanel();
		MainMenu menu = this;

		// Creates the Buttons
		JButton buttonNG = new JButton("New Game");
		JButton buttonSetting = new JButton("Load Game");
		JButton buttonExit = new JButton("Exit");

		buttonNG.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateMenu(program, menu);
			}
		});

		buttonSetting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//JSONConverter converter = new JSONConverter();
				//converter.JSONReader();
				GameState.load(program, menu);
			}
		});

		buttonExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		    	program.close();
			}

		});

		// Adds the Buttons to the panel
		panel.add(buttonNG);
		panel.add(buttonSetting);
		panel.add(buttonExit);

		program.add(panel);
		program.refreshAll();
	}
}
