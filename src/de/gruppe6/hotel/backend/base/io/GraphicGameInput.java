package de.gruppe6.hotel.backend.base.io;

import java.util.List;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.core.GamesManager;
import de.gruppe6.hotel.backend.base.enviroment.Entrance;
import de.gruppe6.hotel.backend.base.enviroment.IBuildable;
import de.gruppe6.hotel.backend.base.enviroment.MapField;
import de.gruppe6.hotel.backend.base.enviroment.Property;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.frontend.base.exception.NothingToBuildException;
import de.gruppe6.hotel.frontend.base.interactions.BuildableInteraction;
import de.gruppe6.hotel.frontend.base.interactions.PropertyInteraction;
import de.gruppe6.hotel.frontend.base.interactions.StairInteraction;
import de.gruppe6.hotel.frontend.base.io.GraphicGameOutput;
import de.gruppe6.hotel.util.Log;

/**
 * The Graphic IInput using a graphic surface
 * 
 * @author ricow
 *
 */
public class GraphicGameInput extends GameInput {

	private String last;

	private int count = 0;

	/**
	 * The Graphic IInput using a graphic surface
	 * 
	 * @param gamesManager - the GamesManager using the GameInput
	 * @param gameMain - the GameMain using the GameInput
	 */
	public GraphicGameInput(GamesManager gamesManager, GameMain gameMain) {
		super(gamesManager, gameMain);

	}

	/**
	 * Method to set the last Input and making it identifiable
	 * 
	 * @param scanner - the String that was read last
	 */
	public void setLast(String scanner) {
		if (isRunning) {
			String input = "[" + count + "]=" + scanner;
			last = input;
			if (count == Integer.MAX_VALUE - 1)
				count = 0;
		}
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
	public Property getToBuy(List<Property> properties) {

		if (gameMain.getOutput() instanceof GraphicGameOutput && ((GraphicGameOutput) gameMain.getOutput()).getBuyInteraction() != null) {
			PropertyInteraction buyInteract = ((GraphicGameOutput) gameMain.getOutput()).getBuyInteraction();
			while (!buyInteract.getDecision())
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					Log.sendException(e);
				}
			return buyInteract.getToBuy();
			
		} else {
			return super.getToBuy(properties);
		}
	}
	
	@Override
	public List<IBuildable> getToBuild(List<IBuildable> buildables) throws NothingToBuildException {
		
		if (gameMain.getOutput() instanceof GraphicGameOutput
				&& ((GraphicGameOutput) gameMain.getOutput()).getBuildInteraction() != null) {
			BuildableInteraction buildInteract = ((GraphicGameOutput) gameMain.getOutput()).getBuildInteraction();
			try {
				while (!buildInteract.getDecision())
					Thread.sleep(100);
				if(buildInteract.getToBuild() == null || buildInteract.getToBuild().size() == 0)
					throw new NothingToBuildException();
				return buildInteract.getToBuild();
			} catch (InterruptedException e) {
				Log.sendException(e);
				return null;
			}
		} else {
			return super.getToBuild(buildables);
		}
	}
	
	@Override
	public Entrance getStair(Player player, List<MapField> mapFields) {

		if (gameMain.getOutput() instanceof GraphicGameOutput
				&& ((GraphicGameOutput) gameMain.getOutput()).getStairInteraction() != null) {
			StairInteraction stairInteract = ((GraphicGameOutput) gameMain.getOutput()).getStairInteraction();
			try {
				while (!stairInteract.getDecision())
					Thread.sleep(100);
				if(stairInteract.getToBuild() == null)
					throw new NothingToBuildException();
				return stairInteract.getToBuild();
			} catch (InterruptedException e) {
				Log.sendException(e);
				if(stairInteract.getToBuild() != null)
					return stairInteract.getToBuild();
				return null;
			}
		} else {
			return super.getStair(player, mapFields);
		}		
		
	}
}
