package de.gruppe6.hotel.backend.extended.saveload;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import de.gruppe6.hotel.backend.base.player.*;
import de.gruppe6.hotel.backend.base.core.*;
import de.gruppe6.hotel.backend.base.enviroment.*;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * This class creates a JSON Object where it stores all important player related
 * values, writes them into a JSON file to save the progress of the game and
 * then reads the JSON file to load the progress of the previous game.
 * 
 * @author Uros Tumaric
 *
 */

public class JSONConverter {
	private int playerNumber;

	/**
	 * 
	 * Constructor
	 * 
	 */

	public JSONConverter() {

	}

	/**
	 *
	 * Creates a JSON Object and puts all important values into it.
	 * 
	 * @param json1
	 */

	public void JSONInitialize(JSONObject json1, GameMain main, Player player) {
		playerNumber = main.getPlayers().size();

		for (int i = 0; i < playerNumber; i++) {
			json1.put("PlayerName" + i, main.getPlayer(i).getName());
			json1.put("PlayerColor" + i, main.getPlayer(i).getColor());
			json1.put("PlayerMoney" + i, main.getPlayer(i).getMoney());
			json1.put("Position" + i, main.getPlayer(i).getMapField());
			json1.put("Properties" + i, main.getPlayer(i).getProperties());
		}
		json1.put("PlayerAtTurn", main.getCurrentPlayer());
		System.out.println(json1);

	}

	/**
	 * 
	 * Writes the values of a JSON Object into a JSON file.
	 * 
	 * @param json1
	 */

	public void JSONWriter(JSONObject json1) {
		String fileName = "D:\\save files project\\JSONFile.json";
		try (FileWriter file = new FileWriter(fileName)) {

			file.write(json1.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Reads a JSON File and extracts all important values from it.
	 * 
	 */

	public void JSONReader(GameMain main, Player player) {
		String path1 = "D:\\save files project\\JSONFile.json";
		try {
			String read1 = new String(Files.readAllBytes(Paths.get(path1)));
			JSONObject object1 = new JSONObject(read1);
			for (int i = 0; i < playerNumber; i++) {
				String playerName = (String) object1.get("PlayerName" + i);
				PlayerColor playerColor = (PlayerColor) object1.get("PlayerColor" + i);
				int playerMoney = (int) object1.get("PlayerMoney" + i);
				int position = (int) object1.get("Position" + i);
				String properties = (String) object1.get("Properties" + i);
				
				main.getPlayer(i).setName(playerName);
				main.getPlayer(i).setPlayerColor(playerColor);
				//main.getPlayer(i).setMoney(playerMoney);
                //Hier soll eine setProperty Methode kommen
				//Hier soll eine setter Methode fur Position von der Player kommen
			}
			int playerAtTurn = (int) object1.get("PlayerAtTurn");
			
			// Hier soll eine setter Methode kommen, die sagt welche Spieler ist dran
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}