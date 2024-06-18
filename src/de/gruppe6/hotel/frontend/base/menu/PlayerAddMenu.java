package de.gruppe6.hotel.frontend.base.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.backend.base.player.PlayerInfo;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;

public class PlayerAddMenu {

	private Gruppe6JPanel panel;
	private Gruppe6JPanel playerPanel;
	private JButton buttonAddPlayer;
	private JTextField tfPlayerName;
	private JComboBox<PlayerColor> colorList;

	
	private List<PlayerInfo> players = new ArrayList<PlayerInfo>();
	
	public PlayerAddMenu(Gruppe6JPanel panel) {
		this.panel = panel;
		playerPanel = new Gruppe6JPanel(); 
		Gruppe6JPanel panelCreatePlayer = new Gruppe6JPanel();
		colorList = new JComboBox<PlayerColor>(PlayerColor.values());
		colorList.setSelectedIndex(0);
		JLabel label = new JLabel("Spielername: ");
		label.setForeground(Color.WHITE);
		panelCreatePlayer.add(label);

		tfPlayerName = new JTextField("", 20);
		// Set Fontcolor
		tfPlayerName.setForeground(Color.WHITE);
		tfPlayerName.setCaretColor(Color.BLACK);
		// Set backgroundcolor
		tfPlayerName.setBackground(new Color(40, 40, 40));
		// add textfield to the pannel
		panelCreatePlayer.add(tfPlayerName);

		buttonAddPlayer = new JButton("Spieler hinzufügen");
		Gruppe6JPanel panelPlayers = new Gruppe6JPanel();
		panelPlayers.setLayout(new BoxLayout(panelPlayers, BoxLayout.PAGE_AXIS));
		panelPlayers.setPreferredSize(new Dimension(500, 200));

		buttonAddPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerColor color = (PlayerColor) colorList.getSelectedItem();
				if(players.size() >= 4)
					return;
				players.add(new PlayerInfo(tfPlayerName.getText(), color));
				JLabel labelPlayer = new JLabel(
						"Name: " + tfPlayerName.getText() + ", Farbe: " + colorList.getSelectedItem());
				panelPlayers.add(labelPlayer);
				labelPlayer.setForeground(Color.WHITE);

				panel.refresh();

				tfPlayerName.setText("");
				colorList.removeItem(color);
				colorList.setSelectedIndex(0);
				if(players.size() == 4)
					buttonAddPlayer.setForeground(Color.RED);
			}
		});
		JLabel labelColor = new JLabel("Spielerfarbe: ");
		labelColor.setForeground(Color.WHITE);
		panelCreatePlayer.add(labelColor);

		panelCreatePlayer.add(colorList);
		panelCreatePlayer.add(buttonAddPlayer);
		playerPanel.add(panelPlayers);
		playerPanel.add(panelCreatePlayer);
		playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
		panel.add(playerPanel);
		panel.refresh();
	}
	
	public List<PlayerInfo> getPlayers(){
		return players;
	}

	public void setOnlyOne() {
		playerPanel.setVisible(true);
		buttonAddPlayer.setVisible(false);
		panel.refresh();
	}

	public void setNone() {
		playerPanel.setVisible(false);
		panel.refresh();
	}

	public void setNormal() {
		playerPanel.setVisible(true);
		buttonAddPlayer.setVisible(true);
		panel.refresh();
	}
	
	public PlayerInfo getCurrent() {
		return new PlayerInfo(tfPlayerName.getText(), (PlayerColor) colorList.getSelectedItem());
	}
}
