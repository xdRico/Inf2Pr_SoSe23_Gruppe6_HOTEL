package de.gruppe6.hotel.frontend.base.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import de.gruppe6.hotel.backend.base.io.GraphicGameInput;
import de.gruppe6.hotel.frontend.base.io.IInput;

public class GraphicInputManager extends InputManager implements MouseListener {

	public GraphicInputManager(IInput input) {
		super(input);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (iInput instanceof GraphicGameInput)

			((GraphicGameInput) iInput).setLast(e.getComponent().toString());
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
