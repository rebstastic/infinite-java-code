package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Double value observer. Every time <code>IntegerStorage</code> subject changes
 * state, this object represents its double value. It unsubscribes itself after subject has been changed two times.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Number of times <code>IntegerStorage</code> subject changed state since
	 * this object is subscribed to it.
	 */
	private int count;

	/**
	 * Constructor.
	 */
	public DoubleValue() {
		this.count = 0;
	}

	/**
	 * Prints the double value of new integer storage value. 
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		count++;
		if (count <= 2) {
			int doubleValue = 2 * change.getCurrentValue();
			System.out.println("Double value: " + doubleValue);
			if (count == 2) {
				change.getIntegerStorage().removeObserver(this);
			}
		}
	}

}
