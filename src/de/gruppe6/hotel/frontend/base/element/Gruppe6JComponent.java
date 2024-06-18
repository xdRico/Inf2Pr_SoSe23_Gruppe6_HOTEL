package de.gruppe6.hotel.frontend.base.element;

import javax.swing.JComponent;

public class Gruppe6JComponent extends JComponent implements IGruppe6JObject {

	@java.io.Serial
	private static final long serialVersionUID = 700629653624681589L;

	@Override
	public void refresh() {

		validate();
		repaint();
	}
}
