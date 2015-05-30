package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * The observers interface which encapsulates all the observers that wants to
 * subscribe to <code>IntegerStorage</code> subject.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface IntegerStorageObserver {
	/**
	 * A method which executes when <code>IntegerStorage</code> subject changes
	 * state.
	 * 
	 * @param integerStorage
	 *            A <code>IntegerStorage</code> subject reference.
	 */
	public void valueChanged(IntegerStorage integerStorage);
}
