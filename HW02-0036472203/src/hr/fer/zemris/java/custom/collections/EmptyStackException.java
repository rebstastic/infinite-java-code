package hr.fer.zemris.java.custom.collections;

/**
 * This class represents an exception that's thrown when the user tries to pop
 * an object out of the empty stack.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException() {

	}

	public EmptyStackException(String message) {
		super(message);
	}

	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
