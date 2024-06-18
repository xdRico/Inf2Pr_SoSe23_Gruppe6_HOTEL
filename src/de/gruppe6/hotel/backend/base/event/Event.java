package de.gruppe6.hotel.backend.base.event;

/**
 * Main Event class to handle events
 * 
 * @author sudin
 */

public class Event {

	private boolean isCancelled = false;

	/**
	 * Method to cancel an Event
	 * 
	 * @param isCancelled - parameter to set to cancel an ongoing event
	 */
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	/**
	 * Method to check if the event is cancelled
	 * 
	 * @returns isCancelled - returns the status of the current event
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

}
