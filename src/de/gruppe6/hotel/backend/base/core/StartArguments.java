package de.gruppe6.hotel.backend.base.core;

import de.gruppe6.hotel.util.Debug;
import de.gruppe6.hotel.util.Log;

/**
 * Class to handle the arguments given at the start of the program
 * 
 * @author ricow
 *
 */
public class StartArguments {

	private static OutputType ioType = OutputType.GRAPHIC;
	private static boolean isLocked = false;

	/**
	 * Method to get the OutputType that should be used for the program
	 * 
	 * @return OutputType - the OutputType to be used
	 */
	public static OutputType getOutputType() {
		return ioType;
	}

	/**
	 * Method to get the Arguments by the Program
	 * 
	 * @param args
	 */
	public static void setArguments(String[] args) {

		if (isLocked)
			return;
		isLocked = true;
		if (args.length == 0)
			return;

		for (String arg : args) {
			if (!arg.contains("-"))
				continue;
			arg = arg.toLowerCase();
			if (arg.equalsIgnoreCase("-debug")) {
				Debug.setDebugMode(true);

			} else if (arg.contains("-log")) {

				Log.setWriteToFile(true);
				if (arg.contains("\""))
					Log.setPath(arg.split("\"")[1]);
				else
					Log.setPath(arg.split("=")[1]);
			} else if (OutputType.valueOf(arg.replace("-", "").toUpperCase()) != null) {
				ioType = OutputType.valueOf(arg.replace("-", "").toUpperCase());
			}
		}
		if (Debug.isDebug())
			Log.sendMessage("Debug-Mode active!");
	}
}
