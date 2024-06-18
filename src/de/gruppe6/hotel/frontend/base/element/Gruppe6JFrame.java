package de.gruppe6.hotel.frontend.base.element;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;

public class Gruppe6JFrame extends JFrame implements IGruppe6JObject {

	@java.io.Serial
	private static final long serialVersionUID = -4689142237322080507L;

	public void init() {
		setSize(1920, 1080);
		getContentPane().setBackground(new Color(50, 50, 50));
		setVisible(true);
		// setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void refresh() {

		validate();
		repaint();
	}

	public void refreshAll() {

		for (Component component : super.getComponents()) {
			component.validate();
			component.repaint();
		}
		refresh();
	}

	public void clear() {

		getContentPane().removeAll();
		refreshAll();
	}
}
