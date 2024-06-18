package de.gruppe6.hotel.frontend.base.core;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.extended.network.NetworkMode;
import de.gruppe6.hotel.backend.extended.network.NetworkSetting;
import de.gruppe6.hotel.frontend.base.exception.GameNotFoundException;
import de.gruppe6.hotel.frontend.base.io.IInput;
import de.gruppe6.hotel.util.Debug;
import de.gruppe6.hotel.util.Gruppe6Runnable;
import de.gruppe6.hotel.util.Gruppe6Thread;
import de.gruppe6.hotel.util.Log;

/**
 * Input Manager, that gets the console Inputs and handles the Actions
 * 
 * @implements Runnable
 * @author ricow
 *
 */
public class InputManager implements Gruppe6Runnable {

	protected IInput iInput;

	protected boolean isRunning = true;

	/**
	 * InputManager handles Inputs in Console; Implements Runnable
	 */
	public InputManager(IInput input) {
		this.iInput = input;
		Gruppe6Thread.start(input);
		Log.sendMessage("InputListener created!");
	}

	/**
	 * Method to be called when a new Instance (Threaded) of GameMain is created
	 * 
	 * (* is required);
	 * 
	 * @create [online|server|net/lan] [ipAdress/Port] [Port] - creates a new GameMain-instance;
	 * @start [*int] - starts the GameMain-Thread of the GameMain with the given
	 *        Index (>0);
	 * @quit/exit/stop [int] - stops the program or the GameMain-Thread of the
	 *                 GameMain with the given Index (>0);
	 */
	@Override
	public void run() {

		Log.sendMessage("InputListener started!");

		while (isRunning) {

			String input = iInput.waitForNext();
			input = input.toLowerCase();
			String[] inputs = input.split(" ");
			List<String> args = new ArrayList<String>();

			for (int i = 1; i < inputs.length; i++)
				args.add(inputs[i]);

			Log.sendMessage();
			Log.sendMessage("Input: " + input + "; Arguments as ArrayList: " + args.toString());
			handleInput(input, args);
		}
		try {

			Log.sendMessage("InputListener exited!");
			iInput.exitProgram((int) Thread.currentThread().threadId());

		} catch (Exception e) {
			Debug.sendException(e);
		}
	}

	/**
	 * Method to handle the given String inputs
	 * 
	 * @param input - the command given
	 * @param args  - the parameters given
	 */
	private void handleInput(String input, List<String> args) {
		if (input.contains("create")) {
			try {
				if (input.contains("online") && args.size() >= 1)
					iInput.createGame(new NetworkSetting(
									NetworkMode.ONLINE, 
									InetAddress.getByName(args.get(0)), 
									(int) (args.size() > 1 ? args.get(1) : 17171)));
				else if(args.size() > 1 && args.get(0).equals("online"))
					iInput.createGame(new NetworkSetting(
							NetworkMode.ONLINE, 
							InetAddress.getByName(args.get(1)), 
							(int) (args.size() > 1 ? args.get(1) : 17171)));
				else if (input.contains("server") || (args.size() > 0 && args.get(0).equals("server")))
					iInput.createGame(new NetworkSetting(
							NetworkMode.SERVER, 
							(int) (args.size() > 1 ? args.get(1) : 17171)));
	
				else if ((input.contains("net") || input.contains("lan"))
						|| (args.size() > 0 && (args.get(0).contains("net") || args.get(0).contains("lan")))
						|| (args.size() > 1 && (args.get(1).equals("net") || args.get(1).equals("lan"))))
					iInput.createGame(new NetworkSetting());
				else
					iInput.createGame(new NetworkSetting());
			} catch (Exception e) {
				Log.sendException(e);
			}
		}
		else if (input.contains("exit") || input.contains("quit") || input.contains("stop")) {

			if (args.size() >= 1) {
				try {
					int gameToStop = Integer.parseInt(args.get(0));
					iInput.quitGame(iInput.getGameByIndex(gameToStop - 1));

				} catch (Exception e) {
					Log.sendException(new GameNotFoundException());
				}
			} else {
				stop();
			}
		}

		else if (input.contains("start")) {
			try {
				int gameToStart = 1;

				if (args.size() > 0)
					gameToStart = Integer.parseInt(args.get(0));

				iInput.startGame(iInput.getGameByIndex(gameToStart - 1));

			} catch (Exception e) {
				Log.sendException(new GameNotFoundException());
			}
		}

		else if (input.contains("player")) {
			try {
				if (input.contains("players")) {
					int gameToAdd = 1;

					if (args.size() > 0)
						gameToAdd = Integer.parseInt(args.get(0));
					iInput.createPlayer(iInput.getGameByIndex(gameToAdd - 1));
					iInput.createPlayer(iInput.getGameByIndex(gameToAdd - 1));
				} else {
					int gameToStart = 1;

					if (args.size() > 0)
						gameToStart = Integer.parseInt(args.get(0));
					iInput.createPlayer(iInput.getGameByIndex(gameToStart - 1));
				}
			} catch (Exception e) {
				Log.sendException(e);
			}
		}

		else if (input.contains("classic")) {
			int gameToSet = 1;

			if (args.size() > 0)
				gameToSet = Integer.parseInt(args.get(0));
			iInput.setClassic(iInput.getGameByIndex(gameToSet - 1));
		}
	}

	/**
	 * Method to get the isRunning-state of the current InputManager-instance
	 * 
	 * @return boolean - if the InputManager is in loop
	 */
	public boolean isRunning() {

		return isRunning;
	}

	/**
	 * Method to stop the current InputManager-instance
	 */
	public void stop() {

		isRunning = false;
	}
}
