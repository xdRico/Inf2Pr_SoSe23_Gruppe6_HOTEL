package de.gruppe6.hotel.backend.base.player;

import java.awt.Color;

public enum PlayerColor {
	PINK(new Color(255, 50, 255)), GREEN(new Color(0, 255, 0)), BLUE(new Color(0, 0, 255)),
	YELLOW(new Color(0, 255, 255)), RED(new Color(255, 0, 0)), PURPLE(new Color(255, 0, 180)),
	WHITE(new Color(255, 255, 255)), CYAN(new Color(180, 0, 255));

	private Color color;

	private PlayerColor(Color color) {
		this.color = color;
	}

	public PlayerColor swap() {
		if (this.ordinal() >= PlayerColor.values().length - 1)
			return PlayerColor.values()[0];
		return PlayerColor.values()[this.ordinal() + 1];

	}

	int temp;

	public static PlayerColor random() {
		return PlayerColor.values()[(int) Math.round(Math.random() * (PlayerColor.values().length - 1))];
	}

	public Color getColor() {
		return color;
	}

	public Color removeAlpha() {
		return new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), 0);
	}
}
