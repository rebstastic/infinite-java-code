package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Example of Observer pattern usage.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ObserverExample {

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);

	}
}
