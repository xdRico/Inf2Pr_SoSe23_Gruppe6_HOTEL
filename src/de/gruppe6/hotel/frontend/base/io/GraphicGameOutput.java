package de.gruppe6.hotel.frontend.base.io;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.GameMap;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.event.RegularDice;
import de.gruppe6.hotel.backend.base.event.RollDice;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.frontend.base.core.GraphicInputManager;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JFrame;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;
import de.gruppe6.hotel.frontend.base.element.SizeMode;
import de.gruppe6.hotel.frontend.base.exception.NothingToBuildException;
import de.gruppe6.hotel.frontend.base.interactions.BuildableInteraction;
import de.gruppe6.hotel.frontend.base.interactions.DiceInteraction;
import de.gruppe6.hotel.frontend.base.interactions.PropertyInteraction;
import de.gruppe6.hotel.frontend.base.interactions.StairInteraction;
import de.gruppe6.hotel.frontend.base.menu.IngameMenu;
import de.gruppe6.hotel.frontend.base.ui.UIPlayerState;
import de.gruppe6.hotel.util.Log;

public class GraphicGameOutput extends GameOutput {

	@java.io.Serial
	private static final long serialVersionUID = 5012291124968511921L;

	private GamesManager manager;
	
	private GameMain main;

	private UIPlayerState ui;

	private Gruppe6JPanel uiPanel;

	private Gruppe6JPanel menuPanel;
	
	private Gruppe6JPanel layerPanel;

	private Gruppe6JPanel interactionPanel;

	private PropertyInteraction buyInteract;
	
	private BuildableInteraction buildInteract;
	
	private StairInteraction stairInteract;

	private Gruppe6JPanel stairPanel;

	private Gruppe6JPanel playerPanel;

	private Gruppe6JFrame frame;

	private MouseListener mouseListener;

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	
	public GraphicGameOutput(GamesManager manager) {
		this.manager = manager;
	}
	
	public PropertyInteraction getBuyInteraction() {
		return buyInteract;
	}

	public BuildableInteraction getBuildInteraction() {
		return buildInteract;
	}

	public StairInteraction getStairInteraction() {
		return stairInteract;
	}

	@Override
	public void startGame(GameMain main) {
		mouseListener = new GraphicInputManager(main.getInput());
		this.main = main;
		frame = new Gruppe6JFrame();
		frame.addWindowListener(new WindowAdapter(){
		    @Override
		    public void windowClosing(WindowEvent e)
		    {
		    	Log.sendMessage("GameMain exited (Window closed)!");
		    	main.stop();
		    	manager.remove(main);
		    }
		});
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		frame.init();
		frame.setTitle("Gruppe6 HOTEL");
		// device.setFullScreenWindow(frame);
		frame.setVisible(false);
		frame.refreshAll();
		layerPanel = new Gruppe6JPanel();
		ui = new UIPlayerState(main);
		interactionPanel = new Gruppe6JPanel();
		playerPanel = new Gruppe6JPanel();
		menuPanel = new Gruppe6JPanel();
		uiPanel = ui.getPanel();
		frame.add(layerPanel);
		frame.getContentPane().setBackground(Color.YELLOW);

		frame.refreshAll();
		frame.setVisible(true);
		graphic = frame.getGraphics();
		frame.setAlwaysOnTop(main.isRunning());
	}

	@Override
	public void loadMap(GameMap map) {
		if (layerPanel == null)
			throw new IllegalStateException("Unable to create resources: no panel set!");
		layerPanel.setLocation(0, 0);
		layerPanel.setLayout(new OverlayLayout(layerPanel));
		layerPanel.setBackground(new Color(0, 0, 0, 0));
		layerPanel.setPreferredSize(frame.getSize());
		layerPanel.setMinimumSize(frame.getSize());
		
		JButton ingameMenu = new JButton();
		ingameMenu.setText("Menu");
		ingameMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPanel.removeAll();
				new IngameMenu(main, menuPanel);
				menuPanel.setVisible(true);
				menuPanel.refresh();
			}
		});
		menuPanel.setOpaque(false);
		menuPanel.setVisible(false);

		layerPanel.add(menuPanel);
		layerPanel.add(ingameMenu);
		layerPanel.add(interactionPanel);
		layerPanel.add(uiPanel);
		layerPanel.add(map);

		uiPanel.setPreferredSize(layerPanel.getSize());
		uiPanel.setLayout(new OverlayLayout(uiPanel));
		uiPanel.setLocation(0, 0);
		uiPanel.setAlignmentX(0);
		uiPanel.setAlignmentY(0);

		map.setLayout(new OverlayLayout(map));
		map.setPreferredSize(layerPanel.getSize());
		map.setMinimumSize(layerPanel.getSize());
		map.setBackground(new Color(100, 100, 100, 255));
		map.setLocation(0, 0);
		map.setAlignmentX(0);
		map.setAlignmentY(0);

		interactionPanel
				.setPreferredSize(new Dimension((int) Math.round(layerPanel.getWidth() / 2), layerPanel.getHeight()));
		// interactionPanel.setPreferredSize(layerPanel.getSize());
		interactionPanel.setLayout(new GridBagLayout());
		interactionPanel.setOpaque(false);
		// interactionPanel.setLocation((int) Math.round(layerPanel.getWidth() / 4), 0);
		interactionPanel.setBackground(Color.YELLOW);
		interactionPanel.setAlignmentX(0.5f);
		interactionPanel.addMouseListener(mouseListener);
		interactionPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		Gruppe6JPanel interactionField = new Gruppe6JPanel();
		interactionPanel.add(interactionField);
		
		interactionField.setPreferredSize(interactionPanel.getSize());
		interactionField.setBackground(new Color(100, 100, 100, 200));
		interactionField.setAlignmentX(0);
		interactionField.setAlignmentY(0);
		interactionField.addMouseListener(mouseListener);
		
		frame.refreshAll();
	}

	@Override
	public void startMap(GameMap map) {

		Gruppe6JPictureBox mapPicture = map.getPictureBox();

		stairPanel = new Gruppe6JPanel();

		map.add(playerPanel);
		map.add(stairPanel);

		for (int i = 1; i <= map.maxPlayers(); i++) {
			PlayerColor c = PlayerColor.random();
			boolean isPlayer = false;
			if (main.getPlayer(i - 1) != null) {
				isPlayer = true;
				c = main.getPlayer(i - 1).getColor();
			}

			Gruppe6JPictureBox start = new Gruppe6JPictureBox();
			start.setColored("/de/gruppe6/hotel/assets/player/start_" + i + ".png", c.getColor());
			start.setPreferredSize(map.getPreferredSize());
			start.setOpaque(false);
			start.setVisible(true);

			Gruppe6JPictureBox pl = new Gruppe6JPictureBox();
			pl.setColored("/de/gruppe6/hotel/assets/player/startfield_" + i + "_null.png", c.getColor());
			pl.setPreferredSize(map.getPreferredSize());
			pl.setOpaque(false);
			pl.setVisible(true);
			Gruppe6JPictureBox pl1 = new Gruppe6JPictureBox();
			try {
				pl1.setImage("/de/gruppe6/hotel/assets/player/startfield_" + i + "_null.png");
			}catch(IOException e) {
				Log.sendException(e);
			}
			pl1.setPreferredSize(map.getPreferredSize());
			pl1.setOpaque(false);
			pl1.setVisible(true);

			if (!isPlayer) {
				map.add(pl);
				map.add(pl1);

			}
			else {
				playerPanel.add(pl);
				playerPanel.add(pl1);

			}
			map.add(start);
//			start.paintComponent(graphic);
//			pl.paintComponent(graphic);
			playerPanel.refresh();
			map.refresh();
		}
		
		
		playerPanel.setLayout(new OverlayLayout(playerPanel));
		playerPanel.setPreferredSize(map.getSize());
		playerPanel.setOpaque(false);
		playerPanel.setLocation(0, 0);
		playerPanel.setAlignmentX(0);
		playerPanel.setAlignmentY(0);

		stairPanel.setLayout(new OverlayLayout(stairPanel));
		stairPanel.setOpaque(false);
		stairPanel.setPreferredSize(map.getSize());
		stairPanel.setMinimumSize(map.getSize());
		stairPanel.setLocation(0, 0);
		stairPanel.setAlignmentX(0);
		stairPanel.setAlignmentY(0);

		ui.init();
		map.add(mapPicture);
		mapPicture.setPreferredSize(map.getSize());
		mapPicture.setSizeMode(SizeMode.ZOOM);
		mapPicture.paintComponent(frame.getGraphics());
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		interactionPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	@Override
	public void switchPlayer(GameMain main) {

		updateGUI(main);
		println("Der nächste Spieler: " + main.getCurrentPlayer());
	}

	@Override
	public void movePlayer(Player p, int fields) {
		playerPanel.removeAll();
		for (Player player : main.getPlayers()) {
			Gruppe6JPictureBox picBox = new Gruppe6JPictureBox();
			Gruppe6JPictureBox picBox2 = new Gruppe6JPictureBox();
			String mapField = "startfield_" + (main.getPlayers().indexOf(player) + 1) + "_null";
			if (player.getMapField() != null)
				mapField = "playerfield_" + player.getMapField().getIndex();
			picBox.setPreferredSize(playerPanel.getSize());
			picBox.setColored("/de/gruppe6/hotel/assets/player/" + mapField + ".png", player.getColor().getColor());
			try {
				picBox2.setImage("/de/gruppe6/hotel/assets/player/" + mapField + ".png");
			}catch(IOException e) {
				Log.sendException(e);
			}
			playerPanel.add(picBox);
			playerPanel.add(picBox2);
			picBox.setPreferredSize(playerPanel.getSize());
			picBox.paintComponent(graphic);
			picBox2.paintComponent(graphic);

		}
		playerPanel.refresh();
	}

	@Override
	public void updateGUI(GameMain main) {

		ui.updateGUI();
	}

	@Override
	public void stop() {
		super.stop();
		if(frame == null)
			return;
		frame.setEnabled(false);
		frame.setVisible(false);
		frame.dispose();
	}

	@Override
	public void println(String message) {
		if (interactionPanel == null) {
			super.println(message);
			return;
		}
		JTextArea print = new JTextArea();
		print.setText(message);
		print.setBackground(new Color(0, 0, 0, 255));
		print.setEditable(false);
		print.setFont(new Font("Arial", Font.BOLD, 20));
		print.setFocusable(false);
		print.setAutoscrolls(false);
		print.setOpaque(false);
		print.setForeground(Color.WHITE);
		print.setPreferredSize(new Dimension((int) Math.round(interactionPanel.getSize().getWidth() / 2),
				(int) interactionPanel.getSize().getHeight()));
		print.setMinimumSize(interactionPanel.getSize());
		print.addMouseListener(mouseListener);
		interactionPanel.removeAll();
		interactionPanel.add(print);
		interactionPanel.refresh();
		main.getInput().waitForNext();
		interactionPanel.removeAll();
		interactionPanel.refresh();

	}

	@Override
	public int rollDice(Player player, RollDice<RegularDice> dice) {
		DiceInteraction di = new DiceInteraction(interactionPanel);
		int idice = dice.getRandomSide().ordinal() + 1;
		di.dice(idice);
		main.getInput().waitForNext();
		interactionPanel.removeAll();
		interactionPanel.refresh();
		return idice;
	}

	@Override
	public void buildEntrance(Entrance stair) {
		stairPanel.add(stair);
		stair.setPreferredSize(stairPanel.getSize());
		stair.setVisible(true);
		stair.setOpaque(false);
	}

	@Override
	public void buyable(List<Property> properties) {
		buyInteract = new PropertyInteraction(interactionPanel, properties, mouseListener);
	}

	@Override 
	public void buildable(List<IBuildable> buildables) throws NothingToBuildException {
		buildInteract = new BuildableInteraction(interactionPanel, buildables, mouseListener);
	}
	
	@Override
	public void buildableStairs(Player player, List<MapField> mapFields) {
		stairInteract = new StairInteraction(interactionPanel, stairPanel, player, mapFields);
	}
}
