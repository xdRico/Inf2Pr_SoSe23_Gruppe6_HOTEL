package de.gruppe6.hotel.backend.base.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Roll dice class to handle dice rolls in the current game
 * 
 * @author sudin
 */
public class RollDice<T extends Enum<?>> {

	private List<Side> sides = new ArrayList<Side>();
	private double accumulatedWeight;
	private Random rand = new Random();

	private class Side {
		double accumulatedWeight;
		T side;
	}

	/**
	 * Method to add weighted sides to a dice
	 * 
	 * @param side   - the value of the side
	 * @param weight - the weight of the side
	 */
	public void addWeightedSide(T side, double weight) {
		accumulatedWeight += weight;
		Side s = new Side();
		s.side = side;
		s.accumulatedWeight = accumulatedWeight;
		sides.add(s);
	}

	/**
	 * Method to initiate a dice roll and return one side of the dice
	 * 
	 * @returns side - returns one side the dice has rolled on
	 */
	public T getRandomSide() {
		double r = rand.nextDouble() * accumulatedWeight;

		for (Side side : sides) {
			if (side.accumulatedWeight >= r) {
				return side.side;
			}
		}

		return null;
	}

}
