package de.gruppe6.hotel.frontend.base.interactions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;

import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;
import de.gruppe6.hotel.util.Debug;

/**
 * 
 * This class is responsible for letting the player view cards in the game on
 * screen.
 * 
 * @author Uros Tumaric
 *
 */
public class PropertyInteraction implements ActionListener {

	private Gruppe6JPanel panel;

	private Property activeCard;
	private CardSide side = CardSide.front;
	private List<Property> properties;
	private MouseListener listener;
	private boolean decision;
	private Property wannaBuy = null;

	JButton button;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5;

	/**
	 * 
	 * The constructor. This constructor creates a layout and a button.
	 * 
	 * @param panel
	 */
	public PropertyInteraction(Gruppe6JPanel panel, List<Property> properties, MouseListener listener) {
		this.listener = listener;
		this.panel = panel;
		this.properties = properties;
		activeCard = properties.get(0);
		displayCard();
	}

	/**
	 * 
	 * This method creates new buttons. It adds the button for switching between
	 * cards, sides of cards and removing the cards from the screen. It is also
	 * responsible for putting the cards on the screen.
	 * 
	 */
	private void displayCard() {
		panel.removeAll();
		if (properties.size() > 1) {
			button1 = new JButton();
			button1.setPreferredSize(new Dimension(120, 35));
			button1.addActionListener(this);
			button1.setText("Change Property");
			panel.add(button1);

		}
		panel.setLayout(new FlowLayout());
		panel.setVisible(true);

		button2 = new JButton();
		button2.setPreferredSize(new Dimension(120, 35));
		button2.setText("Turn Card");
		button2.addActionListener(this);

		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		panel.add(button2);

		button3 = new JButton();
		button3.setPreferredSize(new Dimension(120, 35));
		button3.setText("Don't buy");
		button3.setForeground(Color.RED);
		button3.addActionListener(this);

		button4 = new JButton();
		button4.setPreferredSize(new Dimension(120, 35));
		button4.setText("Buy");
		button4.setForeground(Color.GREEN);
		button4.addActionListener(this);

		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		panel.add(button4);
		panel.add(button3);
		Gruppe6JPictureBox cardBox = new Gruppe6JPictureBox(
				"/de/gruppe6/hotel/assets/cards/" + activeCard.getName().toLowerCase() + "_" + side + ".png");
		panel.add(cardBox);
		cardBox.setPreferredSize(
				new Dimension((int) Math.round(panel.getWidth() * 0.7), (int) Math.round(panel.getHeight() * 0.7)));
		cardBox.addMouseListener(listener);
		Debug.sendMessage("Aktuelle Bild: " + activeCard.getName() + "_" + side + ".png");

		panel.refresh();
	}

	private Property getActiveCard() {
		return activeCard;
	}

	public boolean getDecision() {
		return decision;
	}

	/**
	 * 
	 * This method switches between individual cards.
	 * 
	 * @return
	 */
	private void switchCard() {
		for (int i = 0; i < properties.size(); i++) {
			if (properties.get(i) == activeCard) {
				if (i == properties.size() - 1)
					activeCard = properties.get(0);
				else
					activeCard = properties.get(i + 1);
				displayCard();
				break;
			}
		}
	}

	/**
	 * 
	 * This method turns the cards and shows their fronts and backs.
	 * 
	 * @return
	 */
	private void turnCard() {
		side = side.turn();
		Debug.sendMessage("Nächstes Bild: " + activeCard.getName().toLowerCase() + "_" + side + ".png");
		displayCard();
	}

	/**
	 * 
	 * This method removes the cards from the screen.
	 * 
	 */
	private void removeCard() {
		panel.removeAll();
		panel.refresh();
	}

	/**
	 * 
	 * A method which makes button do specific things once the buttons are pressed.
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) {
			switchCard();
		}
		if (e.getSource() == button2) {
			turnCard();
		}
		if (e.getSource() == button3) {
			removeCard();
			decision = true;
		}
		if (e.getSource() == button4) {
			wannaBuy = getActiveCard();
			removeCard();
			decision = true;
		}
	}

	/**
	 * Method to get the Property to buy, or null
	 * 
	 * @return
	 */
	public Property getToBuy() {
		return wannaBuy;
	}

}
