package de.gruppe6.hotel.backend.extended.gamesavemanager;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.base.core.ProgramMain;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.backend.extended.network.NetworkMode;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.menu.MainMenu;

/**
 * Main class to handle saving and loading games from a file.
 * 
 * @author sudin
 */

public final class GameState {
	
	/**
	 * Method to save the current game to a file
	 * 
	 * @param GameMain - The gameMain from the current played game.
	 */
	
    public static void save(GameMain gameMain) throws IOException {
        ArrayList<PlayerState> playerStates = new ArrayList<PlayerState>();
        for(Player player: gameMain.getPlayers()) {
        	PlayerState state = new PlayerState();
        	state.name = player.getName();
        	state.money = player.getMoney();
        	state.playerColor = player.getColor().toString();
        	//state.mapField = player.getMapField();
        	state.properties = new ArrayList<PropertyState>();
        	for(Property property: player.getProperties()) {
        		PropertyState propertyState = new PropertyState();
        		propertyState.name = property.getName();
        		propertyState.stairPrice = property.getStairPrice();
        		propertyState.price = property.getPrice();
        		state.properties.add(propertyState);
        	}
        	playerStates.add(state);
        	
        }
        State state = new State();
        state.playerState = playerStates;
        String path = System.getProperty("user.dir");
    	FileOutputStream fileOut = new FileOutputStream(path + "/bin/gamestate.hotel");
    	ObjectOutputStream out = new ObjectOutputStream(fileOut);
    	out.writeObject(state);
    	out.close();
    	fileOut.close();
	
    }

	/**
	 * Method to load the game from a file
	 * 
	 * @param ProgramMain - creates a new game from the main class.
	 * @param MainMenu - creates a new window instance for the loaded game.
	 */
    
    public static State load(ProgramMain program, MainMenu menu) {
    	State state = null;
    	 
    	try {
    		String path = System.getProperty("user.dir");
			FileInputStream fileIn = new FileInputStream(path + "/bin/gamestate.hotel");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			state = (State) in.readObject();
			in.close();
			fileIn.close();
			InetAddress ipAdress = InetAddress.getLocalHost();
			int port = 17171;
			NetworkSetting setting = new NetworkSetting (NetworkMode.LOCALPC,ipAdress, port);
			GamesManager manager = program.gamesManager();
			GameMain gameMain = manager.createGame(setting);
			for(PlayerState playerState: state.playerState) {
				Player player = new Player(gameMain, PlayerColor.valueOf(playerState.playerColor), playerState.name, null);
				for(PropertyState propertyState: playerState.properties) {
					Property property = new Property(propertyState.name, propertyState.price, propertyState.stairPrice, null);
					player.addProperty(property);
				}
				gameMain.addPlayer(player);
			}
	    	program.refreshAll();	
			manager.start(gameMain);
			menu.open();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        return state;
    }
}
class State implements java.io.Serializable{

	private static final long serialVersionUID = 8802400768151602339L;
	public ArrayList<PlayerState> playerState;
}

class MapFieldState implements java.io.Serializable {

	private static final long serialVersionUID = -5114776235372812892L;
	
}

class PlayerState implements java.io.Serializable{

	private static final long serialVersionUID = -1205162850003338818L;
	public String playerColor;
	public String name;
	public ArrayList<PropertyState> properties;
	public int money = 10000;
	//public MapField mapField;
}

class PropertyState implements java.io.Serializable{
	
	private static final long serialVersionUID = 7546329452732874345L;
	public int price;
	public int stairPrice;
	public String name;
	
}