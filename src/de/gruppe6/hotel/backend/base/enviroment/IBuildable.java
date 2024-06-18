package de.gruppe6.hotel.backend.base.enviroment;

import de.gruppe6.hotel.backend.base.exception.IsNotBuyableException;

/**
 * this Interface is Important for the classes Park and Building this to classes
 * should be treated the same way
 */

public interface IBuildable {
	/**
	 * a method that you can buy the property or not
	 * 
	 * @throws IsNotBuyableException
	 */

	public void buy(int price) throws IsNotBuyableException;

	/**
	 * the name of the property
	 * 
	 * @return -String - a name
	 */
	public String getName();

	/**
	 * a method if its built or not
	 * 
	 * @return - boolean true or false
	 */

	public boolean isBuilt();

	/**
	 * the stay price for the current building amount/park
	 * 
	 * @return - int stay price
	 */

	public int getStayPrice();

	/**
	 * the buy price of the building/park
	 * 
	 * @return - int buy price
	 */

	public int getBuyPrice();

	/**
	 * get property methode
	 * 
	 * @return
	 */

	public Property getProperty();
}
