package de.gruppe6.hotel.backend.base.core;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.exception.GameRunningException;
import de.gruppe6.hotel.backend.base.exception.TooManyPlayersException;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.exception.GameNotFoundException;
import de.gruppe6.hotel.frontend.base.io.IInput;
import de.gruppe6.hotel.util.Log;

/**
 * Game Manager Class to run and handle multiple GameMain instances
 * 
 * @author ricow
 *
 */
public class GamesManager {

	private ProgramMain main;

	private List<GameMain> gameMains = new ArrayList<GameMain>();

	/**
	 * Game Manager Class to run and handle multiple GameMain instances
	 * 
	 * @author ricow
	 *
	 */
	protected GamesManager(ProgramMain main) {
		this.main = main;
	}

	/**
	 * Method to get the IInput used for the current GamesManager
	 * 
	 * @return IInput - the IInput used by the current GamesManager
	 */
	public IInput getInput() {
		return main.getInput();
	}


	/**
	 * Method to get the GameMain at a specified index
	 * 
	 * @param i - the index of the GameMain to get
	 * @return GameMain - the GameMain at the index
	 */
	public GameMain get(int i) {
		return gameMains.get(i);
	}
	
	/**
	 * Method to get the current size of the gameMains-List
	 * 
	 * @return int - size of gameMains
	 */
	public int size() {
		return gameMains.size();
	}

	/**
	 * Method to create a GameMain with the specified NetworkSetting
	 * 
	 * @param netSettings - the NetworkSetting to be used at the GameMain
	 * @return GameMain - the GameMain created
	 */
	public GameMain createGame(NetworkSetting netSettings) {

		GameMain main = new GameMain(this, netSettings);

		gameMains.add(main);

		return main;
	}

	/**
	 * Method to add a Player to the Game at the specified position from existing
	 * GameMain's
	 * 
	 * @param gameIndex   - the position of the GameMain to add a Player
	 * @param color - the PlayerColor of the Player to be added
	 * @throws TooManyPlayersException, GameRunningException
	 * @returns boolean - true, if the Player was successfully added
	 */
	public boolean addPlayer(int gameIndex, PlayerColor color) throws TooManyPlayersException, GameRunningException {
		try {
			gameMains.get(gameIndex).addPlayer(color);
			return true;
		} catch (Exception e) {
			Log.sendException(e);
			return false;
		}
	}

	/**
	 * Method to add a Player to a GameMain
	 * 
	 * @param main   - the GameMain to add a Player
	 * @param player - the Player to be added
	 */
	public boolean addPlayer(GameMain main, Player player) {
		try {
			main.addPlayer(player);
			return true;
		} catch (Exception e) {
			Log.sendException(e);
			return false;
		}

	}

	/**
	 * Method to remove a Player from the specified GameMain
	 * 
	 * @param main   - the GameMain the Player has to be removed from
	 * @param player - the Player to be removed
	 * @return boolean - true, if the player was removed
	 */
	public boolean removePlayer(GameMain main, Player player) {
		return main.removePlayer(player);
	}

	/**
	 * Method to set a GameMain to a classic Game (classic RuleBook and GameMap)
	 * 
	 * @param game - the index of the game to be set to classic
	 */
	public void setClassic(int gameIndex) {
		gameMains.get(gameIndex).createClassic();
	}

	/**
	 * Method to set a GameMain to a classic Game (classic RuleBook and GameMap)
	 * 
	 * @param main - the GameMain to be set to classic
	 */
	public void setClassic(GameMain main) {
		main.createClassic();
	}

	/**
	 * Method to start the GameMain at the specified position
	 * 
	 * @param gameIndex - the position of the game to be started
	 * @return boolean - true, if the game has been successfully started
	 */
	public boolean start(int gameIndex) {

		try {
			gameMains.get(gameIndex).startGame();
			Log.sendMessage("Requested starting GameMain..!");

			return true;

		} catch (Exception e) {
			Log.sendException(e);

			return false;
		}
	}

	/**
	 * Method to start the specified GameMain
	 * 
	 * @param main - the GameMain to be started
	 * @return boolean - true, if the game has been successfully started
	 */
	public boolean start(GameMain main) {

		try {
			main.startGame();
			Log.sendMessage("Requested starting GameMain..!");

			return true;

		} catch (Exception e) {
			Log.sendException(e);

			return false;
		}
	}

	/**
	 * Method to be called when a GameMain is closed, tries to close the ProgramMain
	 * @param main - the GameMain that is being closed
	 */
	public void mainStopped(GameMain main) {
		gameMains.remove(main);
		this.main.tryClose();
	}
	
	/**
	 * Method to remove the Game at the specified position from the running
	 * GameMain's
	 * 
	 * @param gameIndex - the position of the GameMain to be removed
	 * @return boolean - true, if the GameMain has been removed
	 */
	public boolean remove(int gameIndex) {

		return remove(gameMains.get(gameIndex));
	}

	/**
	 * Method to remove the specified GameMain from the running GameMain's
	 * 
	 * @param main - the GameMain to be removed
	 * @return boolean - true, if the GameMain has been removed
	 */
	public boolean remove(GameMain main) {

		try {
			main.stop();
			gameMains.remove(main);
			this.main.tryClose();
			return true;

		} catch (Exception e) {
			Log.sendException(new GameNotFoundException());
		}
		return false;
	}

	/**
	 * Method to remove all GameMain's from the running Games
	 */
	public void removeAll() {

		for (GameMain main : gameMains) {
			main.stop();
		}

		gameMains = new ArrayList<GameMain>();
	}

	
	private int todo;
	/**
	 * Method to load a specified saved GameMain
	 * 
	 * @param main - the GameMain to be loaded
	 * @return boolean - true, if the game has been successfully loaded
	 */
	public boolean loadGame(GameMain main) {

		gameMains.add(main);
		return main.load();
	}

	/**
	 * Method to let a GameMain start waiting for start of the game (online only)
	 * @param main2
	 */
	public boolean startWaiting(GameMain main) {
		try {
			main.startWaitingGame();
			Log.sendMessage("Requested starting GameMain..!");

			return true;

		} catch (Exception e) {
			Log.sendException(e);

			return false;
		}		
	}
}
