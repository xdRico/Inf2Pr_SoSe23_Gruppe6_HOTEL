package de.gruppe6.hotel.frontend.base.io;

import java.io.InputStream;
import java.util.List;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.gamerule.RuleBook;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.util.Gruppe6Runnable;

public interface IInput extends Gruppe6Runnable {

	public InputStream getInputStream();

	public String waitForNext();

	public boolean createGame();

	public boolean createGame(NetworkSetting netSetting);

	public boolean startGame(GameMain main);

	public boolean quitGame(GameMain main);

	public GameMain getGameByIndex(int gameIndex);

	public boolean createPlayer(GameMain main);

	public boolean addPlayer(GameMain main, Player player);

	public boolean removePlayer(GameMain main, Player player);

	public Player getPlayerByIndex(GameMain main, int index);

	public boolean setClassic(RuleBook rulebook);

	public boolean setClassic(GameMain main);

	public boolean exitProgram(int reason);

	public void rollDice(Player player);

	public Property getToBuy(List<Property> properties);
	
	public List<IBuildable> getToBuild(List<IBuildable> buildables);
	
	public Entrance getStair(Player player, List<MapField> mapFields);
}
