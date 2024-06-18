package de.gruppe6.hotel.backend.base.io;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.base.core.ProgramMain;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.event.CreateGameEvent;
import de.gruppe6.hotel.backend.base.event.PlayerRollSixDiceEvent;
import de.gruppe6.hotel.backend.base.event.QuitGameEvent;
import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;
import de.gruppe6.hotel.backend.base.gamerule.RuleBook;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.exception.NothingToBuildException;
import de.gruppe6.hotel.frontend.base.io.IInput;
import de.gruppe6.hotel.util.Log;

/**
 * The Classic IInput using a Console input stream
 * 
 * @author ricow
 *
 */
public class GameInput implements IInput {

	protected GamesManager gamesManager;
	protected ProgramMain programMain;
	protected GameMain gameMain;
	private Scanner scanner;
	protected String last;
	protected boolean isRunning = false;

	/**
	 * 
	 * The Classic IInput using a Console input stream
	 *  
	 * @param gamesManager - the GamesManager using the GameInput
	 * @param gameMain - the GameMain using the GameInput
	 */
	public GameInput(GamesManager gamesManager, GameMain gameMain) {
		this.gamesManager = gamesManager;
		this.gameMain = gameMain;
		setScanner(getInputStream());
	}
	
	/**
	 * 
	 * The Classic IInput using a Console input stream
	 *  
	 * @param programMain - the ProgramMain using the GameInput
	 * @param gamesManager - the GamesManager using the GameInput
	 */
	public GameInput(ProgramMain programMain, GamesManager gamesManager) {
		this.gamesManager = gamesManager;
		this.programMain = programMain;
		setScanner(getInputStream());
	}

	/**
	 * Method to set the Input scanner for the GameInput
	 * 
	 * @param stream - the InputStream to be scanned
	 */
	public void setScanner(InputStream stream) {
		scanner = new Scanner(stream);
	}

	@Override
	public InputStream getInputStream() {
		return System.in;
	}

	@Override
	public boolean createGame() {
		if (gameMain == null) {

			try {
				gameMain = new CreateGameEvent(gamesManager, new NetworkSetting()).getGameMain();
			} catch (UnknownHostException e)  {
				Log.sendException(e);
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean createGame(NetworkSetting netSetting) {
		if (gameMain == null) {
			gameMain = new CreateGameEvent(gamesManager, netSetting).getGameMain();
			return true;
		}
		return false;
	}

	@Override
	public boolean startGame(GameMain main) {
		return gamesManager.start(main);
	}

	@Override
	public boolean quitGame(GameMain main) {
		new QuitGameEvent(gamesManager, main);
		return true;
	}

	@Override
	public GameMain getGameByIndex(int gameIndex) {
		return gamesManager.get(gameIndex);
	}

	@Override
	public boolean createPlayer(GameMain main) {
		return gamesManager.addPlayer(main, new Player(main));
	}

	@Override
	public boolean addPlayer(GameMain main, Player player) {
		return gamesManager.addPlayer(main, player);
	}

	@Override
	public boolean removePlayer(GameMain main, Player player) {
		return gamesManager.removePlayer(main, player);
	}

	@Override
	public Player getPlayerByIndex(GameMain main, int index) {
		return main.getPlayer(index);
	}

	@Override
	public boolean setClassic(GameMain main) {
		gamesManager.setClassic(main);
		return true;
	}

	@Override
	public boolean setClassic(RuleBook ruleBook) {
		ruleBook.createClassic();
		return true;
	}

	@Override
	public boolean exitProgram(int reason) {
		if (programMain != null) {
			programMain.exitProgram(reason);
			return true;
		}
		return false;
	}

	@Override
	public void rollDice(Player player) {
		new PlayerRollSixDiceEvent(player);
	}

	@Override
	public String waitForNext() {
		String start = last;
		while (last == start) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				Log.sendException(e);
			}
		}

		return last.replace(last.split("=")[0] + "=", "");

	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public void run() {
		if (scanner == null)
			return;
		if (isRunning)
			return;
		isRunning = true;
		int i = 0;
		while (isRunning) {
			String input = "[" + i + "]=" + scanner.nextLine();
			last = input;
			if (i == Integer.MAX_VALUE - 1)
				i = 0;
		}
	}

	@Override
	public Property getToBuy(List<Property> properties) {
		
		gameMain.getOutput().println("Möchtest du kaufen? (Maximal ein Grundstück akzeptiert!)");

		String next = waitForNext();

		for (Property property : properties) {

			if (property.getName().equalsIgnoreCase(next.replace(" ", ""))) {
				return property;
			}
		}
		return null;
	}

	@Override
	public List<IBuildable> getToBuild(List<IBuildable> buildables) {
		
		gameMain.getOutput().println("Möchtest du kaufen? (Name !jedes! Gebäudes, dass du kaufen möchtest, eingeben! "
				+ "(Durch Leerzeichen getrennt))");
		
		String next = waitForNext();
		
		List<IBuildable> toBuild = new ArrayList<IBuildable>();

		for (String buildingName : next.split(" ")) {

			for (IBuildable buildable : buildables) {

				if (buildable.getName().equalsIgnoreCase(buildingName)) {
					toBuild.add(buildable);
					continue;
				}
			}
		}
		if (toBuild.size() == 0)
			throw new NothingToBuildException();
		return toBuild;
	}
	
	@Override
	public Entrance getStair(Player player, List<MapField> mapFields) {
		String next = waitForNext();
		try {
			int input = Integer.parseInt(next.split(" ")[0]);
			
			MapField mapField =  mapFields.get(input - 1);
			Property property = null;

			List<Property> p = new ArrayList<Property>();
			for (Property prop : mapField.getProperties()) {
				try {
					if (prop.getPlayer() == player && prop.isBuilt()) {
						p.add(prop);
					}
				} catch (IllegalPropertyException e) {
					Log.sendException(e);
				}
			}
			if (p.size() > 1) {
				try {
					int prop = Integer.parseInt(next.split(" ")[1]);
					if(prop >= 0 && prop < p.size())
						property = p.get(prop);
				}catch(NumberFormatException e) {
					Log.sendException(e);
				}
			} else {
				property = p.get(0);
			}
			if (property != null) {
				return new Entrance(property, mapField);
				
			}
			

		} catch (Exception e) {
			Log.sendException(e);
		}

		return null;
	}

}
