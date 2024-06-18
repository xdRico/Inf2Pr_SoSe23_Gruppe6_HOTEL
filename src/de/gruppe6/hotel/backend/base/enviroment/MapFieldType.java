package de.gruppe6.hotel.backend.base.enviroment;

/**
 * this enum has the types of the fields
 */

public enum MapFieldType {

	BuyField, BuildField, BankField(true), CityHallField(true), FreeStairField, FreeBuildingField, StartField;

	private boolean oversteppable = false;

	/**
	 * the method map field type
	 */

	MapFieldType() {

	}

	/**
	 * if the field is oversteppable
	 * 
	 * @param oversteppable
	 */

	MapFieldType(boolean oversteppable) {
		this.oversteppable = oversteppable;
	}

	public boolean isOversteppable() {
		return oversteppable;
	}
}
