package de.gruppe6.hotel.frontend.base.io;

import java.awt.Graphics;
import java.io.*;

import de.gruppe6.hotel.backend.base.io.IOutput;
import de.gruppe6.hotel.util.Log;

public class GameOutput implements IOutput {

	protected PrintStream stream = System.out;
	protected Graphics graphic;

	@java.io.Serial
	private static final long serialVersionUID = 7352687024673973904L;

	@Override
	public void println(String message) {
		stream.println(message);
		Log.sendMessage(message);
	}

	public void stop() {

		if (stream != System.out)
			stream.close();
	}

	@Override
	public PrintStream getStream() {
		return stream;
	}
}
