package de.gruppe6.hotel.backend.base.enviroment;

import de.gruppe6.hotel.backend.base.exception.IsNotBuyableException;
import de.gruppe6.hotel.backend.base.player.PlayerBankruptcy;
import de.gruppe6.hotel.frontend.base.element.SizeMode;
import de.gruppe6.hotel.util.Log;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;

import java.io.IOException;

/**
 * class Building is for the buildings
 * 
 * @implements IBuildable
 */

public class Building extends Gruppe6JPictureBox implements IBuildable {

	@java.io.Serial
	private static final long serialVersionUID = 1640520278099961569L;

	private Property property;

	private int price;
	private int stayprice;
	private String name;
	private boolean isBought = false;

	/**
	 * a method with information about the building
	 * 
	 * @param name-     name of the property
	 * @param price
	 * @param stayprice
	 * @throws IOException
	 */
	public Building(Property property, String name, int price, int stayprice, GameMap map) {
		this.property = property;
		this.name = name;
		this.price = price;
		this.stayprice = stayprice;

		try {
			map.add(this);
			setPreferredSize(getParent().getPreferredSize());
			setImage("/de/gruppe6/hotel/assets/enviroment/" + name.toLowerCase() + ".png");

			setVisible(isBought);
			getParent().validate();
			getParent().repaint();
		} catch (Exception e) {
			Log.sendMessage("/de/gruppe6/hotel/assets/enviroment/" + name.toLowerCase() + ".png");
			Log.sendException(e);
		}

	}

	@Override
	public int getBuyPrice() {
		return price;
	}

	@Override
	public Property getProperty() {
		return property;
	}

	@Override
	public int getStayPrice() {
		return stayprice;
	}

	/**
	 * if it is possible to buy the property
	 * 
	 * @throws IsNotBuyableException
	 */

	@Override
	public void buy(int price) throws IsNotBuyableException {
		if (isBought) {
			throw new IsNotBuyableException();
		}
		try {

			property.getPlayer().removeMoney(price);
			isBought = true;
			setSizeMode(SizeMode.ZOOM);
			setVisible(isBought);
			getParent().validate();
			getParent().repaint();

		} catch (PlayerBankruptcy e) {
			property.getPlayer().getGame().announce("Spieler ist pleite!");
			Log.sendException(e);
		}

	}

	@Override
	public boolean isBuilt() {
		return isBought;
	}

	@Override
	public String getName() {
		return name;
	}
}
