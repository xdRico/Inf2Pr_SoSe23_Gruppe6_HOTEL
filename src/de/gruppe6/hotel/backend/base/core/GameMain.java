package de.gruppe6.hotel.backend.base.core;

import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.enviroment.ClassicGameMap;
import de.gruppe6.hotel.backend.base.enviroment.GameMap;
import de.gruppe6.hotel.backend.base.event.NextPlayerOnMove;
import de.gruppe6.hotel.backend.base.event.StartGameEvent;
import de.gruppe6.hotel.backend.base.exception.GameMapLockedException;
import de.gruppe6.hotel.backend.base.exception.GameRunningException;
import de.gruppe6.hotel.backend.base.exception.NotEnoughPlayersException;
import de.gruppe6.hotel.backend.base.exception.TooManyPlayersException;
import de.gruppe6.hotel.backend.base.gamerule.IGameRule;
import de.gruppe6.hotel.backend.base.gamerule.RuleBook;
import de.gruppe6.hotel.backend.base.io.IOutput;
import de.gruppe6.hotel.backend.base.io.GraphicGameInput;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.backend.base.player.PlayerInfo;
import de.gruppe6.hotel.backend.extended.network.NetworkMode;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.io.CustomGameOutput;
import de.gruppe6.hotel.frontend.base.io.GraphicGameOutput;
import de.gruppe6.hotel.frontend.base.io.IInput;
import de.gruppe6.hotel.frontend.base.io.GameOutput;
import de.gruppe6.hotel.util.Gruppe6Runnable;
import de.gruppe6.hotel.util.Gruppe6Thread;
import de.gruppe6.hotel.util.Log;

/**
 * Game main class for a game instance
 * 
 * @implements Runnable
 * @author ricow
 *
 */
public class GameMain implements Gruppe6Runnable, Serializable {

	@java.io.Serial
	private static final long serialVersionUID = -907411660732717860L;

	private boolean isRunning;
	private NetworkSetting netSettings;
	private Thread thread;
	private RuleBook ruleBook;
	private GameMap gameMap;
	private Player playerAtTurn;
	private IOutput output;
	private IInput input;
	private GamesManager gamesManager;

	private List<Player> gamePlayers = new ArrayList<Player>();

	private boolean waitForStart = false;

	private boolean isLoaded = false;

	/**
	 * Game main class for a game instance; Implements Runnable
	 * 
	 * @param gamesManager - the GamesManager used to create the GameMain
	 * @param netSettings - NetworkSetting to be used for the game
	 */
	protected GameMain(GamesManager gamesManager, NetworkSetting netSettings) {
		this.gamesManager = gamesManager;
		this.input = gamesManager.getInput();
		this.netSettings = netSettings;
		netSettings.init(this);
		if (this.netSettings.getMode() == NetworkMode.SERVER) {
			this.output = new GameOutput();
			Gruppe6Thread.create(netSettings).start();
			
		} else {
			setIOByType(this.gamesManager);
		}
		
		this.ruleBook = new RuleBook(this);
		this.output.addGame(this);
		Log.sendMessage("GameMain created with NetworkSetting: " + this.netSettings.toString() + "!");

		this.thread = Gruppe6Thread.create(this);
	}

	/**
	 * Method to be called when a new Thread of GameMain is created
	 */
	@Override
	public void run() {
		if(!isLoaded) {
			if (!isRunning 
					|| ((netSettings.getMode() == NetworkMode.LOCALPC || netSettings.getMode() == NetworkMode.SERVER) 
							&& gamePlayers.size() < 2))
				return;
			
			if(netSettings.getMode() == NetworkMode.SERVER) {
				netSettings.write("start");
			} else if(waitForStart == true) {
				String next = "";
				while(!next.contains("start"))
					next = netSettings.readNext();
			}
				
			output.startGame(this);
			output.loadMap(gameMap);
			output.startMap(gameMap);
			gameMap.start();
			ruleBook.start();
			switchPlayer();
		}
		Log.sendMessage("GameMain started!");
		Log.sendMessage();
		while (isRunning && playerAtTurn != null) {
			try {	
				
				if(netSettings.getMode() == NetworkMode.LOCALPC || netSettings.getMode() == NetworkMode.SERVER) {
					
					output.updateGUI(this);
					ruleBook.playRule(this, playerAtTurn);
	
					Thread.sleep(500);
				
				} else {
				
					String next = netSettings.readNext();
					handleNetworkInput(next);
				}
			} catch (NextPlayerOnMove e) {
				switchPlayer();
			} catch (Throwable e) {
				Log.sendException(e);
			}
		}
		Log.sendMessage("GameMain exited!");
	}

	/**
	 * Method to get the Player currently at turn
	 * 
	 * @return Player - the Player at turn
	 */
	public Player getCurrentPlayer() {
		return playerAtTurn;
	}

	/**
	 * Method to get the IInput, that should be used for the current GameMain
	 * 
	 * @return IInput - the IInput to be used for Inputting information
	 */
	public IInput getInput() {
		return input;
	}

	/**
	 * Method to get the current played GameMap
	 * 
	 * @return GameMap - the GameMap the current GameMain uses
	 */
	public final GameMap getMap() {
		return gameMap;
	}

	/**
	 * Method to get the NetworkSetting of the current GameMain-instance
	 * 
	 * @return NetworkSetting - the NetworkSetting the current GameMain is set to
	 */
	public final NetworkSetting getNetworkSetting() {
	
		return netSettings;
	}
	

	/**
	 * Method to get the IOutput, that should be used for the current GameMain
	 * 
	 * @return IOutput - the IOutput to be used for Outputting information
	 */
	public IOutput getOutput() {
		return output;
	}
	

	/**
	 * Method to get the Player with the specified Index in the current GameMain
	 * 
	 * @param index - the index of the Player to get
	 * @return Player - the Player on the specified Index
	 */
	public Player getPlayer(int index) {
		if (index >= gamePlayers.size() || index < 0)
			return null;
		return gamePlayers.get(index);
	}

	/**
	 * Method to get the Players of the game
	 * 
	 * @return List<Player> - the players in the current game
	 */
	public List<Player> getPlayers() {
		return gamePlayers;
	}

	/**
	 * Method to get the OutputStream, that should be used for the current GameMain
	 * 
	 * @return PrintStream - the PrintStream to be used for Outputting information
	 */
	public PrintStream getStream() {
		return output.getStream();
	}

	/**
	 * Method to get the isRunning-state of the current GameMain-instance
	 * 
	 * @return boolean - if the current GameMain-instance is in loop
	 */
	public final boolean isRunning() {

		return isRunning;
	}

	/**
	 * Method to automatically set the Output by the OutputType set in
	 * StartArguments
	 * 
	 * @param GamesManager - the GamesManager to be used
	 */
	private void setIOByType(GamesManager manager) {
		switch (StartArguments.getOutputType()) {
		case CUSTOM_CONSOLE:
			output = new CustomGameOutput();
			break;
		case GRAPHIC:
			output = new GraphicGameOutput(manager);
			input = new GraphicGameInput(manager, this);
			break;
		default:
			output = new GameOutput();

			break;
		}
	}

	private void handleNetworkInput(String input) {
		int todo;
	}
	

	/**
	 * Method to simply announce a message in the game
	 * 
	 * @param message - the String to be announced
	 */
	public void announce(String message) {
		output.println(message);
	}

	/**
	 * Method to add a Player to a created Game
	 * 
	 * @param color - PlayerColor, that the Player should use
	 * @throws TooManyPlayersException, GameRunningException, IllegalStateException
	 */
	public final void addPlayer(PlayerColor color) throws TooManyPlayersException, GameRunningException, IllegalStateException {

		if (isRunning)
			throw new GameRunningException(this);
		if ((gameMap == null && gamePlayers.size() >= 4) || (gameMap != null && gamePlayers.size() >= gameMap.maxPlayers()))
			throw new TooManyPlayersException();
		if((netSettings.getMode() == NetworkMode.ONLINE || netSettings.getMode() == NetworkMode.LOCALNET))
			throw new IllegalStateException("Illegal method for Online-/Local-Network-Game");
		Player player = new Player(this, color);
		gamePlayers.add(player);
		output.addGamePlayer(this, player);
		Log.sendMessage("Player with color " + color + " added to Game!");
	}

	/**
	 * Method to add a Player to a created Game
	 * 
	 * @param color - PlayerColor, that the Player should use
	 * @throws TooManyPlayersException, GameRunningException, IllegalStateException
	 */
	public final void addPlayer(Player player) throws TooManyPlayersException, GameRunningException, IllegalStateException {

		if (isRunning)
			throw new GameRunningException(this);

		if ((gameMap == null && gamePlayers.size() >= 4) || (gameMap != null && gamePlayers.size() >= gameMap.maxPlayers()))
			throw new TooManyPlayersException();
		if((netSettings.getMode() == NetworkMode.ONLINE || netSettings.getMode() == NetworkMode.LOCALNET) && player.getSocket() == null)
			throw new IllegalStateException("Missing Player Socket");
		gamePlayers.add(player);
		output.addGamePlayer(this, player);
		;
		Log.sendMessage("Player " + player + " added to Game!");
	}

	/**
	 * Method to add a online Player to the GameMain instance
	 * 
	 * @param accept - the Socked used to the client
	 * @param info - the PlayerInfo of the Player
	 */
	public void addClient(Socket accept, PlayerInfo info) {
		addPlayer(new Player(this, info.getColor(), info.getName(), accept));
	}

	/**
	 * Method to add a online Player to the GameMain instance
	 * 
	 * @param accept - the Socked used to the client
	 * @param name - the name of the Player
	 * @param color - the PlayerColor of the Player
	 */
	public void addClient(Socket accept, String name, PlayerColor color) {
		addPlayer(new Player(this, color, name, accept));
	}

	
	/**
	 * Method to add a GameRule to the current GameMain
	 * 
	 * @param rule - the IGameRule to be added to the game
	 */
	protected final void addGameRule(IGameRule rule) {

		ruleBook.addRule(rule);
	}

	/**
	 * Method to remove a Player from the current GameMain
	 * 
	 * @param player - the Player to be removed
	 * @return boolean - true, if the player was removed
	 */
	public boolean removePlayer(Player player) {
		return gamePlayers.remove(player);
	}

	/**
	 * Method to set a GameMain to a classic game with classic RuleBook and GameMap
	 * 
	 * @param player - the Player running this method
	 */
	protected final void createClassic() {
		try {
			ruleBook.createClassic();
			gameMap = new ClassicGameMap(output);
		} catch (GameMapLockedException e) {
			Log.sendException(e);
		}
	}

	/**
	 * Method to start the current GameMain's Thread
	 * 
	 * @throws GameRunningException, GameMapLockedException
	 */
	protected final void startGame() throws GameRunningException, GameMapLockedException {
		if (isRunning)
			throw new GameRunningException(this);

		if (gamePlayers.size() < 2)
			throw new NotEnoughPlayersException();

		Log.sendMessage("Starting GameMain..!");

		isRunning = true;
		netSettings.stop();

		if (gameMap == null || gameMap.getFields().size() == 0) {
			gameMap = new ClassicGameMap(output);
		}

		
		thread.start();

		new StartGameEvent(this);
	}
	
	public boolean load() {
		isRunning = true;
		isLoaded  = true;
		thread.start();
		return true;
	}

	/**
	 * Method to switch the Player at turn
	 */
	protected final void switchPlayer() {

		if (playerAtTurn == null) {
			playerAtTurn = gamePlayers.get(0);
		}else {
			for (int i = 0; i < gamePlayers.size(); i++) {
				if (gamePlayers.get(i).equals(playerAtTurn)) {
					if (i >= gamePlayers.size() - 1)
						i = 0;
					else
						i++;
					playerAtTurn = gamePlayers.get(i);
					break;
				}
			}
		}
		output.switchPlayer(this);
	}

	/**
	 * Method to stop the current GameMain-instance
	 */
	public void stop() {
		isRunning = false;
    	int to_player_quit_online;

		output.stop();
		gamesManager.mainStopped(this);
	}

	/**
	 * Method to let the current GameMain wait for starting of the Game (online only)
	 */
	public void startWaitingGame() {
		if (isRunning)
			throw new GameRunningException(this);

		Log.sendMessage("Starting GameMain..!");
		waitForStart  = true;
		isRunning = true;
		netSettings.stop();

		if (gameMap == null || gameMap.getFields().size() == 0) {
			gameMap = new ClassicGameMap(output);
		}

		
		thread.start();

		new StartGameEvent(this);		
	}
}
