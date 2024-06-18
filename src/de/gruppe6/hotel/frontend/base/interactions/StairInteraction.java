package de.gruppe6.hotel.frontend.base.interactions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.util.Log;

public class StairInteraction implements ActionListener{

	private boolean decision = false;
	private Entrance activeStair;
	private Entrance toBuild = null;
	private Gruppe6JPanel panel;
	private Gruppe6JPanel stairPanel;
	private List<Entrance> buildable = new ArrayList<Entrance>();
	
	
	public StairInteraction(Gruppe6JPanel panel, Gruppe6JPanel stairPanel, Player player, List<MapField> mapFields) {
		this.panel = panel;
		this.stairPanel = stairPanel;
		if(mapFields == null || mapFields.size() == 0) {
			decision = true;
			return;
		}
		for(MapField valid : mapFields) {
			if(valid.getEntrance() != null)
				continue;
			for(Property prop : valid.getProperties()) {
				try {
					if(prop == null)
						continue;
					if(prop.getPlayer() == player && prop.isBuilt()) {
						buildable.add(new Entrance(prop, valid));
					}
				}catch(IllegalPropertyException e) {
					Log.sendException(e);
				}
				
			}
		}
		if(buildable.size() == 0){
			decision = true;
			return;
		}
		activeStair = buildable.get(0);
		displayMenu();
	}
	
	public boolean getDecision() {
		return decision;
	}
	
	public Entrance getToBuild() {
		return toBuild;
	}
	
	private void displayMenu(){
		
		panel.removeAll();
		
		JButton bPrev = new JButton();
		bPrev.setPreferredSize(new Dimension(120, 35));
		bPrev.setText("Previous Stair");
		bPrev.addActionListener(this);

		JButton bCancel = new JButton();
		bCancel.setPreferredSize(new Dimension(120, 35));
		bCancel.setText("Cancel");
		bCancel.setForeground(Color.RED);
		bCancel.addActionListener(this);
		
		JButton bBuy = new JButton();
		bBuy.setPreferredSize(new Dimension(120, 35));
		bBuy.setText("Buy Stair");
		bBuy.setForeground(Color.GREEN);
		bBuy.addActionListener(this);
		
		JButton bNext = new JButton();
		bNext.setPreferredSize(new Dimension(120, 35));
		bNext.setText("Next Stair");
		bNext.addActionListener(this);
		
		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		panel.add(bPrev);
		panel.add(bCancel);
		panel.add(bBuy);
		panel.add(bNext);
		
		showStair();
		panel.refresh();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton button = (JButton) e.getSource();
			switch(button.getText()) {
				case "Previous Stair":
					prevStair();
					break;
				case "Buy Stair":
					toBuild = activeStair;
					removeMenu();
					decision = true;
					break;
				case "Cancel":
					removeMenu();
					decision = true;
					break;
				case "Next Stair":
					nextStair();
					break;
			}	
		}
	}
	
	private void nextStair() {
		removeStair();
		int index = buildable.indexOf(activeStair) + 1;
		if(index >= buildable.size()) {
			activeStair = buildable.get(0);
		}else {
			activeStair = buildable.get(index);
		}
		showStair();
	}

	private void prevStair() {
		removeStair();
		int index = buildable.indexOf(activeStair) - 1;
		if(index < 0) {
			activeStair = buildable.get(buildable.size() - 1);
		}else {
			activeStair = buildable.get(index);
		}
		showStair();
	}

	private void showStair() {
		stairPanel.add(activeStair);
		activeStair.setPreferredSize(stairPanel.getSize());
		activeStair.setVisible(true);
		activeStair.setOpaque(false);
	}
	
	private void removeStair() {
		activeStair.setVisible(false);
		stairPanel.remove(activeStair);
	}

	private void removeMenu() {
		removeStair();
		panel.removeAll();
		panel.refresh();		
	}
	
	
}
