package hr.fer.zemris.java.tecaj.hw5.observer2;

import java.util.ArrayList;

import java.util.List;
/**
 * The subject that stores the integers and when ever the new integer appears it
 * notifies all of it's observers.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class IntegerStorage {
	
	/**
	 * Current integer value.
	 */
	private int value;

	/**
	 * A list of observers.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor that initializes the current integer value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds given observer to the observers list.
	 * 
	 * @param observer
	 *            Observer that subscribed to this subject.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if(observers == null) {
			observers = new ArrayList<>();
		}
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes given observer from observers list.
	 * 
	 * @param observer
	 *            Observer that unsubscribed from this subject.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Removes all the subscribed observers.
	 */
	public void clearObservers() {
		observers = null;
	}

	/**
	 * Returns current integer value.
	 * 
	 * @return Current integer value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the new integer value and notifies all the subscribed observers that
	 * value has changed.
	 * 
	 * @param value New integer value.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value.
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			// Update current value.
			this.value = value;
			// Notify all registered observers.
			if (observers != null) {
				List<IntegerStorageObserver> observersToIterateOver = new ArrayList<>(observers);
				for (IntegerStorageObserver observer: observersToIterateOver) {
					observer.valueChanged(change);
				}
			}
		}
	}
}
