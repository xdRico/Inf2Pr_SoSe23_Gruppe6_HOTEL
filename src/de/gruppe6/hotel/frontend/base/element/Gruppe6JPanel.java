package de.gruppe6.hotel.frontend.base.element;

import java.awt.Color;

import javax.swing.JPanel;

public class Gruppe6JPanel extends JPanel implements IGruppe6JObject {

	@java.io.Serial
	private static final long serialVersionUID = 5280017471478829937L;

	public Gruppe6JPanel() {
		setBackground(new Color(50, 50, 50));
	}

	@Override
	public void refresh() {

		validate();
		repaint();
	}

	@Override
	public boolean isOptimizedDrawingEnabled() {
		return false;
	}

}
