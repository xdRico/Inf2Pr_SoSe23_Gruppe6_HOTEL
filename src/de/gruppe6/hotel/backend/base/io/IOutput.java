package de.gruppe6.hotel.backend.base.io;

import java.io.PrintStream;
import java.util.List;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.GameMap;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.event.RegularDice;
import de.gruppe6.hotel.backend.base.event.RollDice;
import de.gruppe6.hotel.backend.base.player.Player;

public interface IOutput {

	public void println(String message);

	public PrintStream getStream();

	public void stop();

	public default void buildable(List<IBuildable> buildables) {
		for (IBuildable buildable : buildables)
			println(buildable.getName());
	}

	public default void buyable(List<Property> properties) {
		for (Property property : properties)
			println(property.getBuyInformation().toString());
	}

	public default void buildableStairs(Player player, List<MapField> mapFields) {
//		println("Felder, auf denen Eingänge gebaut werden können: ");
		for (int i = 0; i < mapFields.size(); i++) {
			println("[" + (i + 1) + "]" + mapFields.get(i).toString());
		}
	}

	public default void addGame(GameMain main) {
		println("GameMain created!");
	}

	public default void startGame(GameMain main) {
		println("Started Game!\n\n");
	}

	public default void addGamePlayer(GameMain main, Player player) {
		println("Added Player with color " + player.getColor() + "!");
	}

	public default void loadMap(GameMap map) {
		println("Created GameMap!");
	}

	public default void startMap(GameMap map) {
		println("Starting Game on Map " + map.getClass().getTypeName());
	}

	public default void switchPlayer(GameMain main) {
		println("Switching player to: " + main.getCurrentPlayer());
	}

	public default void updateGUI(GameMain main) {

	}

	public default void movePlayer(Player player, int fields) {
		println(player + " ist " + fields + " gelaufen und auf einem " + player.getMapField().getType() + " gelandet!");
	}

	public default void buildEntrance(Entrance stair) {
		println("Neuen Eingang zu Grundstück " + stair.getProperty().getName() + " bei Feld "
				+ stair.getProperty().getMap().indexOf(stair.getMapField()) + " gesetzt!");
	}

	public default int rollDice(Player player, RollDice<RegularDice> dice) {
		println("Würfeln! (Enter)");
		player.getGame().getInput().waitForNext();
		int idice = dice.getRandomSide().ordinal() + 1;
		println("Du hast eine " + idice + " gewürfelt.");
		return idice;
	}

}
