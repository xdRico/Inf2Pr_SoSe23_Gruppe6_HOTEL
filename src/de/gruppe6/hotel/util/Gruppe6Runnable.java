package de.gruppe6.hotel.util;

/**
 * Interface to make handle of Threads easier
 * 
 * @extends Runnable
 * @author ricow
 *
 */
public interface Gruppe6Runnable extends Runnable {

	/**
	 * Method to get if a Gruppe6Runnable is running
	 * 
	 * @return boolean - the boolean, if the threads loop is still running
	 */
	public boolean isRunning();

	/**
	 * Method to stop a Gruppe6Runnable
	 */
	public void stop();

}
