package de.gruppe6.hotel.backend.base.enviroment;

import java.util.ArrayList;
import java.util.List;

/**
 * class for Map Fields
 */

public class MapField {
	private int index;

	private boolean isLocked = false;
	private MapFieldType type;
	private Entrance stair;
	private Property[] properties = new Property[2];

	/**
	 * methode map field
	 */

	/**
	 * method MapField is about the type of the field
	 * 
	 * @param type- to say what type has the field
	 */

	public MapField(int index, MapFieldType type) {
		this.type = type;
		this.index = index;
	}

	/**
	 * looks for mistakes in properties array
	 * 
	 * @param type
	 * @param properties
	 * @throws IllegalArgumentException
	 */

	public MapField(int index, MapFieldType type, Property[] properties) throws IllegalArgumentException {
		this(index, type);
		if (properties.length > 2) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < properties.length; i++) {
			this.properties[i] = properties[i];
		}

	}

	/**
	 * method setProperty is for to set the property in the property list
	 * 
	 * @param property-are the properties
	 * @param side-left    and right
	 */

	public void setProperty(Property property, FieldSide side) {
		if (isLocked)
			return;
		if (side == FieldSide.left) {
			properties[0] = property;
		} else {
			properties[1] = property;
		}
	}

	/**
	 * set type methode
	 * 
	 * @param type
	 */

	public void setType(MapFieldType type) {
		if (!isLocked)
			this.type = type;
	}

	/**
	 * is a method to put the stairs on the entrance
	 * 
	 * @return stair
	 */

	public Entrance getEntrance() {
		return stair;
	}

	/**
	 * method to set the entrance
	 * 
	 * @param entrance
	 */
	public void setEntrance(Entrance entrance) {
		this.stair = entrance;

	}

	/**
	 * the type of the fields
	 * 
	 * @return
	 */

	public MapFieldType getType() {
		return type;
	}

	/**
	 * method getProperties
	 * 
	 * @return the array properties
	 */

	public Property[] getProperties() {
		return properties;
	}

	/**
	 * Is a method to output the properties that border on the field as a list So
	 * you create a list and put every element from the array properties in there
	 * 
	 * @return - properties list
	 */

	public List<Property> getPropertyList() {
		List<Property> properties = new ArrayList<>();
		for (Property property : getProperties())
			properties.add(property);
		return properties;
	}

	/**
	 * is a lock method ,when the method is invoked ,the program set isLocked=true
	 */
	public void lock() {
		this.isLocked = true;
	}

	/**
	 * only one method to clearly display the field in the console
	 * 
	 * @return -field
	 */

	public String toString() {
		String propL = "--", propR = "--";
		if (getProperties()[0] != null)
			propL = getProperties()[0].getName();
		if (getProperties()[1] != null)
			propR = getProperties()[1].getName();
		return getType().name() + "		 - L: " + propL + "		 R: " + propR;
	}

	public int getIndex() {
		return index;
	}
}
