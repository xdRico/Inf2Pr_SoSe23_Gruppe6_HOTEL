package de.gruppe6.hotel.frontend.base.io;

import java.awt.*;
import java.io.*;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.gruppe6.hotel.frontend.base.element.Gruppe6JFrame;
import de.gruppe6.hotel.util.Log;

public class CustomGameOutput extends GameOutput {

	@java.io.Serial
	private static final long serialVersionUID = 7356949206737138292L;

	private Gruppe6JFrame frame = new Gruppe6JFrame();

	public CustomGameOutput() {

		JTextArea textArea = new JTextArea();
		textArea.getInsets().set(0, 0, 10, 0);
		;
		textArea.setMaximumSize(frame.getSize());
		textArea.setAutoscrolls(true);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.GREEN);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.scrollRectToVisible(frame.getBounds());
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JScrollBar scrollBar = scroll.getVerticalScrollBar();

		stream = new PrintStream(new BufferedOutputStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				textArea.append(String.valueOf((char) b));
				scrollBar.setValue(scrollBar.getMaximum());
			}
		})) {
			@Override
			public void println(String x) {
				textArea.append(x + "\n");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					Log.sendException(e);
				}
				scrollBar.setValue(scrollBar.getMaximum());
			}
		};
		frame.add(scroll);
		frame.init();

	}
}