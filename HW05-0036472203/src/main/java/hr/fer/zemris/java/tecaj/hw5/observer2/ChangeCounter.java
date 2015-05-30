package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Change counter observer. Every time <code>IntegerStorage</code> subject
 * changes state, this object increments by one.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * The number of times <code>IntegerStorage</code> subject changed state
	 * since this object is subscribed to it.
	 */
	private int count;
	
	/**
	 * Constructor.
	 */
	public ChangeCounter() {
		this.count = 0;
	}
	
	/**
	 * Prints the number of times <code>IntegerStorage</code> subject changed state
	 * since this object is subscribed to it.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		count++;
		System.out.println("Number of value changes since tracking: " + count);
	}

}
