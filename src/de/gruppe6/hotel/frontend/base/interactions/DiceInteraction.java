package de.gruppe6.hotel.frontend.base.interactions;

import java.awt.FlowLayout;

import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;

/**
 * 
 * Class responsible for the rolling of a dice being displayed visually on
 * screen.
 * 
 * @author Uros Tumaric
 *
 */
public class DiceInteraction {

	private Gruppe6JPanel panel;

	/**
	 * 
	 * The constructor of the class. Adds a layout and a mouselistener.
	 * 
	 * @param panel
	 */
	public DiceInteraction(Gruppe6JPanel panel) {

		this.panel = panel;

		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
	}

	/**
	 * 
	 * Method dice. This method gets a picture according to the number that the dice
	 * rolled.
	 * 
	 * @param dice
	 */
	public void dice(int dice) {
		panel.removeAll();
		panel.add(new Gruppe6JPictureBox("/de/gruppe6/hotel/assets/mechanic/sixdice_" + dice + ".png"));
		panel.refresh();
	}

}