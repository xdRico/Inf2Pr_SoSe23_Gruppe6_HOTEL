package de.gruppe6.hotel.frontend.base.interactions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;
import de.gruppe6.hotel.util.Debug;



public class BuildableInteraction implements ActionListener {
	
	private boolean decision = false;
	private boolean wannaBuy = false;
	
	private CardSide side = CardSide.front;
	private Gruppe6JPanel panel;
	private List<Property> properties = new ArrayList<Property>();
	@SuppressWarnings("unused")
	private List<IBuildable> buildables;
	private List<IBuildable> toBuild = new ArrayList<IBuildable>();
	private MouseListener listener;
	private Property activeCard;

	public BuildableInteraction(Gruppe6JPanel panel, List<IBuildable> buildables, MouseListener listener) {
		this.panel = panel;
		this.buildables = buildables;
		this.listener = listener;
		if(buildables.size() == 0) {
			decision = true;
			return;
		}
		activeCard = buildables.get(0).getProperty();
		for(IBuildable building : buildables) {
			if(!properties.contains(building.getProperty())) {
				properties.add(building.getProperty());
			}
		}
		displayCard();
	}
	
	public boolean getDecision() {
		return decision;
	}

	public List<IBuildable> getToBuild(){
		if(wannaBuy)
			return toBuild;
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			switch(button.getText()) {
				case "Turn Card":
					turnCard();
					break;
				case "Change Property":
					switchCard();
					break;
				case "Buy":
					wannaBuy = true;
					removeMenu();
					decision = true;
					break;
				case "Cancel":
					removeMenu();
					decision = true;
					break;
			}	
		}
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
		panel.refresh();
		JButton button1;
		if (properties.size() > 1) {
			button1 = new JButton();
			button1.setPreferredSize(new Dimension(120, 35));
			button1.addActionListener(this);
			button1.setText("Change Property");
			panel.add(button1);

		}

		JButton button2 = new JButton();
		button2.setPreferredSize(new Dimension(120, 35));
		button2.setText("Turn Card");
		button2.addActionListener(this);

		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		panel.add(button2);

		Gruppe6JPictureBox cardBox = new Gruppe6JPictureBox(
				"/de/gruppe6/hotel/assets/cards/" + activeCard.getName().toLowerCase() + "_" + side + ".png");
		panel.add(cardBox);
		cardBox.setPreferredSize(
				new Dimension((int) Math.round(panel.getWidth() * 0.7), (int) Math.round(panel.getHeight() * 0.7)));
		cardBox.addMouseListener(listener);
		Debug.sendMessage("Aktuelle Bild: " + activeCard.getName() + "_" + side + ".png");
		
		Gruppe6JPanel panelCheck = new Gruppe6JPanel();
		panelCheck.setBackground(null);
		panelCheck.setOpaque(false);
		panelCheck.setLayout(new BoxLayout(panelCheck, BoxLayout.Y_AXIS));
		panel.add(panelCheck);
		JTextArea priceText = new JTextArea("Gesamtkosten: " + updatePrice());
		priceText.setPreferredSize(new Dimension(160, 40));
		for(IBuildable buildable : activeCard.getBuildings()) {
			JCheckBox check = new JCheckBox(buildable.getName());
			check.setEnabled(activeCard.getUnBuiltBuildings().contains(buildable));
			check.setPreferredSize(new Dimension(160, 40));
			check.setSelected(buildable.isBuilt() || toBuild.contains(buildable));
			check.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(decision)
						return;
					JCheckBox box = (JCheckBox) e.getSource();
					if(box.isSelected())
						toBuild.add(buildable);
					else toBuild.remove(buildable);
					priceText.setText("Gesamtkosten: " + updatePrice());
					panelCheck.refresh();
				}
			});
			panelCheck.add(check);
			
		}
		
		
		panelCheck.add(priceText);
		panelCheck.setPreferredSize(new Dimension(160, 360));
		JButton buttonBuy = new JButton();
		buttonBuy.setPreferredSize(new Dimension(120, 35));
		buttonBuy.setText("Buy");
		buttonBuy.setForeground(Color.GREEN);
		buttonBuy.addActionListener(this);
		panel.add(buttonBuy);
		
		JButton buttonCancel = new JButton();
		buttonCancel.setPreferredSize(new Dimension(120, 35));
		buttonCancel.setText("Cancel");
		buttonCancel.setForeground(Color.RED);
		buttonCancel.addActionListener(this);
		panel.add(buttonCancel);
//		panelCheck.setPreferredSize(panelCheck.getSize());
		panel.refresh();
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

	private int updatePrice() {
		int price = 0;
		for(IBuildable toBuild : this.toBuild) {
			price += toBuild.getBuyPrice();
		}
		return price;
	}

	/**
	 * 
	 * This method removes the cards from the screen.
	 * 
	 */
	private void removeMenu() {
		panel.removeAll();
		panel.refresh();
	}
	
}
