package de.gruppe6.hotel.backend.base.enviroment;

import de.gruppe6.hotel.frontend.base.element.Gruppe6JPictureBox;
import de.gruppe6.hotel.util.Log;

/**
 * this class is about the entrances to the property
 */
public class Entrance extends Gruppe6JPictureBox {

	@java.io.Serial
	private static final long serialVersionUID = 6612101287922953481L;

	private final Property property;
	private final MapField mapField;

	/**
	 * method entrance based on property
	 * 
	 * @param property-
	 */
	public Entrance(Property property, MapField mapField) {
		this.property = property;
		this.mapField = mapField;
		String name = property.getName().toLowerCase() + "stair_" + mapField.getIndex();
		try {
			if (mapField.getProperties()[0] == property) {
				name += "_O";
			} else {
				name += "_I";
			}
			setImage("/de/gruppe6/hotel/assets/enviroment/" + name + ".png");

		} catch (Exception e) {
			Log.sendMessage("/de/gruppe6/hotel/assets/enviroment/" + name + ".png");
			Log.sendException(e);
		}

	}

	/**
	 * method property
	 * 
	 * @return
	 */
	public Property getProperty() {
		return property;
	}

	/**
	 * getter for mapField
	 * 
	 * @return
	 */
	public MapField getMapField() {
		return mapField;
	}

}
