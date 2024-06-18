package de.gruppe6.hotel.backend.base.enviroment;

import java.util.ArrayList;
import java.util.List;

import de.gruppe6.hotel.backend.base.exception.IllegalPropertyException;
import de.gruppe6.hotel.backend.base.exception.IsNotBuyableException;
import de.gruppe6.hotel.backend.base.player.Player;

/**
 * this class is the property class and contains every information about the
 * property
 */
public class Property {

	private int price;
	private int stairPrice;
	private String name;
	private Player player;
	private GameMap map;

	private List<IBuildable> buildings;

	/**
	 * methode generates a property with these parameters
	 * 
	 * @param name-name        of the property
	 * @param price-price      of the property
	 * @param stairPrice-price of the stair
	 */

	public Property(String name, int price, int stairPrice, GameMap map) {

		this.name = name;
		this.price = price;
		this.stairPrice = stairPrice;
		this.map = map;
		buildings = new ArrayList<IBuildable>();
	}

	/**
	 * add a player
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	public GameMap getMap() {
		return map;
	}

	/**
	 * the name of the property
	 * 
	 * @return
	 */

	public String getName() {
		return name;
	}

	/**
	 * the price
	 * 
	 * @return
	 */

	public int getPrice() {
		return price;
	}

	/**
	 * and the stair price for the property
	 * 
	 * @return
	 */

	public int getStairPrice() {
		return stairPrice;
	}

	/**
	 * this method gives you back the current Stay price that means you have to pay
	 * to stay price in the currently built building
	 * 
	 * @return - stay price
	 */
	public int getStayPrice() {

		int stayPrice = 0;
		for (int i = 0; i < buildings.size(); i++) {
			IBuildable building = buildings.get(i);
			if (building == null)
				break;
			if (!building.isBuilt())
				break;
			stayPrice = building.getStayPrice();
		}
		return stayPrice;
	}

	/**
	 * this method returns you all Stay prices of the property in a list
	 * 
	 * @return - int list prices
	 */
	private List<Integer> getStayPrices() {
		List<Integer> prices = new ArrayList<Integer>();
		for (IBuildable building : buildings)
			prices.add(building.getStayPrice());
		return prices;

	}

	/**
	 * this method returns you all Buildable prices of the property in a list
	 * 
	 * @return - int list prices
	 */

	public List<Integer> getBuildablePrices() {
		List<Integer> prices = new ArrayList<Integer>();
		for (IBuildable building : buildings)
			prices.add(building.getBuyPrice());
		return prices;
	}

	/**
	 * you get all buy Information for the property
	 * 
	 * @return get Name method
	 */

	public String getBuyInformation() {

		return "\n" + getName() + "\nBesitzer: 							" + (getPlayer() == null ? "--" : getPlayer())
				+ "\nPreis: 							" + getPrice() + "\nEingangspreis: 					"
				+ getStairPrice() + "\nGebäudepreise: 					" + getBuildablePrices()
				+ "\nÜbernachtungspreise (* Würfelzahl):" + getStayPrices();
	}

	/**
	 * getter for all Buildings/Parks of the Property
	 * 
	 * @return List - list of all buildings of this Property
	 */
	public List<IBuildable> getBuildings(){
		return buildings;
	}
	
	/**
	 * you get here a list with all unbuilt Buildings
	 * 
	 * @return- the list
	 */

	public List<IBuildable> getUnBuiltBuildings() {
		List<IBuildable> unBuilt = new ArrayList<IBuildable>();
		for (IBuildable building : buildings) {
			if (!building.isBuilt() && !(building instanceof Park)
					|| (!building.isBuilt() && building instanceof Park && unBuilt.size() == 0))
				unBuilt.add(building);
		}
		return unBuilt;
	}

	/**
	 * is on the property a building?
	 * 
	 * @return
	 * @throws IllegalPropertyException
	 */

	public boolean isBuilt() throws IllegalPropertyException {
		if (buildings.size() == 0)
			throw new IllegalPropertyException();
		return (buildings.get(0).isBuilt());
	}

	/**
	 * method to buy a property hand over the player a property
	 * 
	 * @param player
	 * @return
	 * @throws IsNotBuyableException
	 */
	public int buy(Player player) throws IsNotBuyableException {

		if (this.player != null && this.player != player) {
			if (!buildings.get(0).isBuilt()) {
				this.player = player;
				return getPrice();
			}
			throw new IsNotBuyableException();
		} else if (this.player == player) {
			throw new IsNotBuyableException();
		} else {
			int price = this.price;

			this.price /= 2;
			this.player = player;
			return price;
		}
	}

	/**
	 * method to add a hotel building
	 * 
	 * @param name
	 * @param price
	 * @param stayPrice
	 */

	public void addBuilding(String name, int price, int stayPrice) {
		buildings.add(new Building(this, name, price, stayPrice, map));

	}

	/**
	 * a method to add a park
	 * 
	 * @param name
	 * @param price
	 * @param stayprice
	 */
	public void addPark(String name, int price, int stayprice) {
		buildings.add(new Park(this, name, price, stayprice, map));
	}

	/**
	 * this method resets the owner of the property
	 * 
	 * @param player
	 */
	public void setPlayer(Player player) {

		this.player = player;

	}
}
