package de.gruppe6.hotel.backend.base.core;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;

import de.gruppe6.hotel.backend.base.io.GameInput;
import de.gruppe6.hotel.frontend.base.core.InputManager;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JFrame;
import de.gruppe6.hotel.frontend.base.io.IInput;
import de.gruppe6.hotel.frontend.base.menu.MainMenu;
import de.gruppe6.hotel.util.Gruppe6Thread;
import de.gruppe6.hotel.util.Log;

/**
 * Main Class of the program, handles the program itself
 * 
 * @author ricow
 *
 */
public class ProgramMain extends Gruppe6JFrame {

	private static final long serialVersionUID = 4056063015779491009L;
	private static ProgramMain main;
	private GamesManager gamesManager;
	private int exitReason = -1;
	private IInput inputListener;

	private boolean windowClosed = false;
	private boolean isRunning = true;

	/**
	 * Main entry point of program, starts a new Thread for getting console input
	 * 
	 * @param argument - start arguments of program, setting debug+log mode
	 */
	public static void main(String[] arguments) {

		if (main != null)
			return;
		StartArguments.setArguments(arguments);

		main = new ProgramMain();
		main.gameMainLoop();

	}

	/**
	 * Main of program, starts a new GamesManager Thread
	 */
	private ProgramMain() {

		gamesManager = new GamesManager(this);

		if (StartArguments.getOutputType() == OutputType.GRAPHIC) {
			init();
			new MainMenu(this).open();
		}

		inputListener = new GameInput(this, gamesManager);
		Gruppe6Thread.start(new InputManager(inputListener));
	}

	/**
	 * Method used to initialize a graphic Game Window
	 */
	public void init() {
		super.init();
		setTitle("Gruppe6 Hotel");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		addWindowListener(new WindowAdapter(){
		    @Override
		    public void windowClosing(WindowEvent e)
		    {
		    	Log.sendMessage("Main Menu exited (Window closed)!");
		    	close();
		    }
		});
	}

	/**
	 * Method to close the MainMenu
	 */
	public void close() {
		windowClosed = true;
    	dispose();
    	tryClose();
	}
	
	/**
	 * Method to get the state of the JFrame
	 * 
	 * @return boolean - if the window (JFrame) is closed
	 */
	public boolean windowClosed() {
		return windowClosed;
	}
	
	/**
	 * Method to get the IInput of the current ProgramMain
	 * 
	 * @return IInput - the IInput used by the current ProgramMain
	 */
	public IInput getInput() {
		return inputListener;
	}

	/**
	 * Method to get the GamesManager of ProgramMain
	 * 
	 * @return GamesManager - the GamesManager of the ProgramMain
	 */
	public GamesManager gamesManager() {
		return gamesManager;
	}

	/**
	 * Method to run while the program is opened
	 */
	private void gameMainLoop() {

		while (isRunning) {
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				Log.sendException(e);
			}
		}
		Gruppe6Thread.killThreads(4, 2);
		Log.sendMessage("End of Program");
		System.exit(exitReason);
	}

	/**
	 * Method to try to exit the Program, if all GameMains are closed
	 */
	public void tryClose() {
		if(windowClosed() && gamesManager.size() == 0) 
			exitProgram((int) Thread.currentThread().threadId());
	}
	
	/**
	 * Private Method to exit the main loop of ProgramMain
	 * 
	 * @param reason
	 */
	public void exitProgram(int reason) {
		exitReason = reason;
		isRunning = false;
	}
}
