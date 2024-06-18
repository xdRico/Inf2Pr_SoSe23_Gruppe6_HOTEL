package de.gruppe6.hotel.backend.base.player;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.event.PlayerBankruptcyEvent;
import de.gruppe6.hotel.backend.base.exception.IsNotBuyableException;
import de.gruppe6.hotel.util.Log;

/**
 * Class for the player. This class takes care of player behavior such as
 * transaction and moving on the field.
 * 
 * @author Uros Tumaric
 *
 */
public class Player {
	private boolean canBuy;
	private GameMain main;
	private PlayerColor playerColor;
	private String name;
	private List<Property> properties = new ArrayList<Property>();
	private int money = 10000;
	private MapField mapField;
	private Socket clientSocket;

	/**
	 * First constructor used for main, the color an name of the player and the client socket.
	 * 
	 * @param main
	 * @param playerColor
	 */
	public Player(GameMain main, PlayerColor playerColor, String name, Socket clientSocket) {
		this.main = main;
		this.playerColor = playerColor;
		this.name = name;
		this.clientSocket = clientSocket;
	}
	
	/**
	 * Second constructor which is also used for main and the color of the player.
	 * 
	 * @param gameMain
	 */
	public Player(GameMain gameMain) {
		this(gameMain, null, null, null);
		this.playerColor = PlayerColor.random();
		boolean needNew = true;
		while (needNew && main.getPlayers().size() <= PlayerColor.values().length) {
			needNew = false;
			for (Player p : main.getPlayers()) {
				if (p != this) {
					if (p.getColor() == getColor()) {
						playerColor = playerColor.swap();
						needNew = true;
					}
				}
			}
		}
	}

	/**
	 * Third constructor used for main and the color of the player.
	 * 
	 * @param main
	 * @param playerColor
	 */
	public Player(GameMain main, PlayerColor playerColor) {
		this(main, playerColor, null, null);
	}

	/**
	 * Fourth constructor used for main, info and clientSocket of the player.
	 * @param main
	 * @param info
	 * @param clientSocket
	 */
	public Player(GameMain main, PlayerInfo info, Socket clientSocket) {
		this(main, info.getColor(), info.getName(), clientSocket);
	}

	/**
	 * Fifth constructor used for main and PlayerInfo of the player.
	 * @param main
	 * @param info
	 */
	public Player(GameMain main, PlayerInfo info) {
		this(main, info.getColor(), info.getName(), null);
	}

	
	/**
	 * Set method for the color of the player, can only be used if the game is
	 * running.
	 * 
	 * @param playerColor
	 */
	public void setPlayerColor(PlayerColor playerColor) {
		if (!main.isRunning())
			this.playerColor = playerColor;
	}

	/**
	 * Get method for the color of the player.
	 * 
	 * @return
	 */
	public PlayerColor getColor() {
		return playerColor;
	}

	/**
	 * A method for adding a certain sum of money to a player.
	 * 
	 * @param money
	 */
	public void addMoney(int money) {
		this.money += money;
		main.getOutput().updateGUI(main);
	}

	/**
	 * Set method for the name of the player, can only be used if the game is
	 * running.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		if (!main.isRunning())
			this.name = name;
	}

	/**
	 * Get method for the name of the player.
	 * 
	 * @return
	 */
	public String getName() {
		if(name.isBlank())
			return playerColor.toString();
		return name;
	}

	/**
	 * Get method for the properties.
	 * 
	 * @return
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/**
	 * A method for adding a property for a player.
	 * 
	 * @param property
	 */
	public void addProperty(Property property) {
		properties.add(property);
	}

	/**
	 * A method for selling a property to another player.
	 * 
	 * @param buyer
	 * @param property
	 * @param money
	 */
	public void sellProperty(Player buyer, Property property, int money) {
		try {
			buyer.removeMoney(money);
			buyer.addProperty(property);
			addMoney(money);
			properties.remove(property);
			property.buy(buyer);
			main.announce("Spieler " + buyer + " hat das Grundstück " + property.getName() + " gekauft!");
		} catch (PlayerBankruptcy e) {
			main.announce("Spieler ist pleite!");
			Log.sendException(e);
		}

	}

	/**
	 * Method to get the Socket of the Online Player (if the Player is an Online Player)
	 * @return Socket - the Socket of the online Player
	 */
	public Socket getSocket() {
		return clientSocket;
	}

	/**
	 * A method for taking away a certain sum of money from the player.
	 * 
	 * @param money
	 * @throws PlayerBankruptcy
	 */
	public void removeMoney(int money) throws PlayerBankruptcy {
		this.money -= money;
		if (this.money < 0)
			new PlayerBankruptcyEvent(this, money).foreclosure();
		if (this.money < 0)
			throw new PlayerBankruptcy();
		main.getOutput().updateGUI(main);
	}

	/**
	 * Method to set the player able to buy buildings
	 */
	public void setCanBuild() {
		canBuy = true;
	}

	/**
	 * Method to get if the player can already buy buildings
	 * 
	 * @return
	 */
	public boolean canBuild() {
		return canBuy;
	}

	/**
	 * Set method for the money, can only be used if the game is running.
	 * 
	 * @param money
	 */
	@SuppressWarnings("unused")
	private void setMoney(int money) {
		if (!main.isRunning())
			this.money = money;
		main.getOutput().updateGUI(main);
	}

	/**
	 * Get method for the money.
	 * 
	 * @return
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Get method for the map field.
	 * 
	 * @return
	 */
	public MapField getMapField() {
		return mapField;
	}

	/**
	 * A method which is reponsible for the movement of player on the field.
	 * 
	 * @param fields
	 */
	public void move(MapField field) {

		this.mapField = field;
	}

	/**
	 * A method for giving player 2000 euros.
	 * 
	 */
	public void payoutAtBank() {
		money += 2000;
		main.getOutput().updateGUI(main);
	}

	/**
	 * A method for transaction between players. Player gives a certain amount of
	 * money to another player.
	 * 
	 * @param money
	 * @param player
	 */
	public void transactionToPlayer(int money, Player player) {
		try {
			removeMoney(money);
			player.addMoney(money);
		} catch (PlayerBankruptcy e) {
			main.announce("Spieler ist pleite!");
			Log.sendException(e);
		}
		main.getOutput().updateGUI(main);
	}

	/**
	 * A method for buying a property.
	 * 
	 * @param property
	 */
	public void buyProperty(Property property) {
		try {
			removeMoney(property.buy(this));
			properties.add(property);
			main.announce("Spieler " + this + " hat das Grundstück " + property.getName() + " gekauft!");
		} catch (IsNotBuyableException e) {
			Log.sendException(e);
		} catch (PlayerBankruptcy e) {
			main.announce("Spieler ist pleite!");
			Log.sendException(e);
		}
		main.getOutput().updateGUI(main);
	}

	/**
	 * Get method for the main.
	 * 
	 * @return
	 */
	public GameMain getGame() {
		return main;
	}

	/**
	 * method to get the player as String
	 */
	public String toString() {
		String name = getColor().toString();
		if (!getName().isBlank())
			name = getName() + " (" + getColor() + ")";
		return name;
	}    
}