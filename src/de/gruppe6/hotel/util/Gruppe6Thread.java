package de.gruppe6.hotel.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to make Thread-Handling more easy
 * 
 * @author ricow
 *
 */
public class Gruppe6Thread {

	private Thread thread;
	private Gruppe6Runnable run;

	private static List<Gruppe6Thread> runnables = new ArrayList<Gruppe6Thread>();

	/**
	 * Creates a new Gruppe6Thread containing the Thread and the Gruppe6Runnable
	 * 
	 * @param thread - the Thread, the Runnable runs on
	 * @param run    - the Gruppe6Runnable running
	 */
	private Gruppe6Thread(Thread thread, Gruppe6Runnable run) {
		runnables.add(this);
		this.thread = thread;
		this.run = run;
	}

	/**
	 * Method overwriting the standard toString method to make outputs more readable
	 */
	public String toString() {
		return "[#" + thread.threadId() + " " + thread.getName() + "] " + run.getClass().getSimpleName();
	}

	/**
	 * Method to simply start a Runnable as new Thread
	 * 
	 * @param run - the Runnable that has to be started as new thread
	 * @return Thread - the Thread that has been started
	 */
	public static Thread start(Gruppe6Runnable run) {

		Thread thread = create(run);

		thread.start();

		return thread;
	}

	/**
	 * Method to simply create and rename a new Thread
	 * 
	 * @param run - the Runnable that has to be created as new Thread
	 * @return Thread - the Thread that has been created
	 */
	public static Thread create(Gruppe6Runnable run) {

		Thread thread = new Thread(run);

		thread.setName(run.getClass().getSimpleName().toString());
		return thread;
	}

	/**
	 * Method to wait for all Threads to end, else stop them
	 * 
	 * @param seconds - the seconds to be waited for the Threads to end
	 */
	@SuppressWarnings("removal")
	public static void killThreads(int secondsWait, int secondsKill) {
		List<Gruppe6Thread> running = new ArrayList<Gruppe6Thread>();
		for (int i = 0; i < secondsWait * 2; i++) {
			running = new ArrayList<Gruppe6Thread>();
			for (Gruppe6Thread gr6thread : runnables) {
				Thread thread = gr6thread.thread;
				if (thread.getState() != Thread.State.TERMINATED && thread.getState() != Thread.State.NEW) {
					running.add(gr6thread);
					break;
				}
			}
			if (running.size() == 0)
				return;
			Log.sendMessage("Waiting for Threads: ");
			for (Gruppe6Thread gr6thread : running) {
				Log.sendMessage(gr6thread.toString() + " is Running in " + gr6thread.thread.getState() + "-state!");
				Log.sendMessage("Waiting 0.5 secs..");
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Log.sendException(e);
			}
		}
		if (running.size() == 0)
			return;

		for (int i = 0; i < secondsKill * 2; i++) {
			running = new ArrayList<Gruppe6Thread>();
			for (Gruppe6Thread gr6thread : runnables) {
				Thread thread = gr6thread.thread;
				if (thread.getState() != Thread.State.TERMINATED && thread.getState() != Thread.State.NEW) {
					running.add(gr6thread);
					break;
				}
			}
			if (running.size() == 0)
				return;
			Log.sendMessage("Stopping Threads soft: ");
			for (Gruppe6Thread gr6thread : running) {
				try {
					Log.sendMessage(gr6thread.toString() + " is Running in " + gr6thread.thread.getState() + "-state!");
					gr6thread.run.stop();
					gr6thread.thread.interrupt();
				} catch (Exception e) {
					Log.sendException(e);
				}
			}
			Log.sendMessage("Waiting 0.5 secs..");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Log.sendException(e);
			}
		}

		if (running.size() == 0)
			return;
		Log.sendMessage("Stopping all threads hard..");
		for (Gruppe6Thread gr6thread : running) {
			gr6thread.thread.stop();
		}
	}
}
