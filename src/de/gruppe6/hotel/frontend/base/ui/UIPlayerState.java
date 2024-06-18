package de.gruppe6.hotel.frontend.base.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JTextArea;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;

public class UIPlayerState {

	@java.io.Serial
	private static final long serialVersionUID = -4393495151170474087L;

	private GameMain main;

	private Gruppe6JPanel uiPanel = new Gruppe6JPanel();

	private Gruppe6JPanel propertyPanel = new Gruppe6JPanel();

	private JTextArea money = new JTextArea();

	public UIPlayerState(GameMain main) {
		this.main = main;
	}

	public Gruppe6JPanel getPanel() {
		return uiPanel;
	}

	public void init() {

		uiPanel.setLayout(new BorderLayout());
		uiPanel.setOpaque(false);
		uiPanel.add(money, BorderLayout.NORTH);
		uiPanel.add(propertyPanel);
		money.setBackground(new Color(0, 0, 0, 255));
		money.setEditable(false);
		money.setFont(new Font("Arial", Font.BOLD, 20));
		money.setFocusable(false);
		money.setAutoscrolls(false);
		money.setOpaque(false);
		money.setForeground(Color.WHITE);
		money.setPreferredSize(new Dimension(150, 50));
		money.setMaximumSize(new Dimension(150, 50));
		money.setMinimumSize(new Dimension(150, 50));
		propertyPanel.setLayout(new BoxLayout(propertyPanel, BoxLayout.LINE_AXIS));
		propertyPanel.setAlignmentX(0);
		propertyPanel.setAlignmentY(0);
		propertyPanel.setOpaque(false);
		propertyPanel.setPreferredSize(new Dimension(uiPanel.getWidth() / 2, uiPanel.getHeight() / 5));
		propertyPanel.setLocation(0, 0);
		updateGUI();
		uiPanel.refresh();

	}

	public void updateGUI() {

		if (main == null)
			return;
		Player player = main.getCurrentPlayer();
		if (player == null)
			return;

		money.setText("Spieler: " + player + "\nGeld: " + player.getMoney());
		propertyPanel.removeAll();
		if (main.getCurrentPlayer() != null) {
			/*for (Property property : main.getCurrentPlayer().getProperties()) {
				Gruppe6JPictureBox propCard = new Gruppe6JPictureBox(
						"/de/gruppe6/hotel/assets/cards/" + property.getName().toLowerCase() + "_front.png");
				propCard.setPreferredSize(
						new Dimension(propertyPanel.getWidth() / 5, propertyPanel.getHeight() / 5));
				propCard.setSizeMode(SizeMode.ZOOM);
				propertyPanel.add(propCard);
			}*/
		}
		uiPanel.refresh();
	}
}
