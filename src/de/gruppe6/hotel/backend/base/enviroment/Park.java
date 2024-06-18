package de.gruppe6.hotel.backend.base.enviroment;

import de.gruppe6.hotel.backend.base.exception.IsNotBuyableException;
import de.gruppe6.hotel.backend.base.player.PlayerBankruptcy;
import de.gruppe6.hotel.frontend.base.element.SizeMode;
import de.gruppe6.hotel.util.Log;
import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;

/**
 * class Park is for the parks
 *
 * @implements - IBuildable
 */
public class Park extends Gruppe6JPictureBox implements IBuildable {

	@java.io.Serial
	private static final long serialVersionUID = -356380837491414484L;

	private Property property;
	private int price;
	private int stayprice;
	private String name;

	private boolean isBought = false;

	public Park(Property property, String name, int price, int stayprice, GameMap map) {
		this.property = property;
		this.name = name;
		this.price = price;
		this.stayprice = stayprice;

		try {
			map.add(this);
			setPreferredSize(getParent().getPreferredSize());
			setImage("/de/gruppe6/hotel/assets/enviroment/" + name + ".png");
			setVisible(isBought);
			getParent().validate();
			getParent().repaint();

		} catch (Exception e) {
			Log.sendMessage("/de/gruppe6/hotel/assets/enviroment/" + name + ".png");
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
		isBought = true;

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
