package hr.fer.zemris.java.tecaj.hw5.observer1;

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
	public void valueChanged(IntegerStorage integerStorage) {
		int squareValue = (int) Math.pow(integerStorage.getValue(), 2);
		System.out.println("Provided new value: " + integerStorage.getValue()
				+ ", square is " + squareValue);
	}

}
