package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Square value observer. Every time <code>IntegerStorage</code> subject changes
 * state, this object represents its square value.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints the square value of new subject value.
	 */
	@Override
	public void valueChanged(IntegerStorageChange change) {
		int squareValue = (int) Math.pow(change.getCurrentValue(), 2);
		System.out.println("Provided new value: " + change.getCurrentValue()
				+ ", square is " + squareValue);
	}

}
