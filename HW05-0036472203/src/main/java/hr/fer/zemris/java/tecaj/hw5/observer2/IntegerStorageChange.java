package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * The class that stores the most recent value and new value from integer
 * storage.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class IntegerStorageChange {

	/**
	 * The reference to the subject that stores integer values.
	 */
	private IntegerStorage integerStorage;

	/**
	 * Recent value.
	 */
	private int recentValue;

	/**
	 * Current value.
	 */
	private int currentValue;

	/**
	 * Constructs the integer storage change object.
	 * 
	 * @param integerStorage
	 *            The reference to the subject that stores integer values.
	 * @param recentValue
	 *            Recent value.
	 * @param currentValue
	 *            New value.
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int recentValue,
			int currentValue) {
		this.integerStorage = integerStorage;
		this.recentValue = recentValue;
		this.currentValue = currentValue;
	}

	/**
	 * Returns the integer storage reference.
	 * 
	 * @return The integer storage reference.
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Returns the recent value.
	 * 
	 * @return The recent value.
	 */
	public int getRecentValue() {
		return recentValue;
	}

	/**
	 * Returns the current value.
	 * 
	 * @return The current value.
	 */
	public int getCurrentValue() {
		return currentValue;
	}

}
