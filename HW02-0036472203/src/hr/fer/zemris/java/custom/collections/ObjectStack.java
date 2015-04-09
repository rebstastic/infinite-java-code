package hr.fer.zemris.java.custom.collections;

/**
 * <p>
 * This class represents a last-in-first-out (LIFO) stack of objects and
 * contains various methods for manipulating it. The usual push and pop
 * operations are provided, as well as a method to peek at the top item on the
 * stack,a method that returns size of a stack, a method to test for whether the
 * stack is empty and a method that clears the stack. When a stack is first
 * created, it contains no items.
 * </p>
 * 
 * <p>
 * This class plays the role of an Adaptor in the Adapter design pattern. The
 * Adaptee is the {@link ArrayBackedIndexedCollection} class. This class
 * provides to user the methods which are natural for a stack and hide
 * everything else. This way, the methods of ObjectStack are the methods user
 * expects to exist in stack, and those methods will implement its functionality
 * by calling (i.e. delegating) methods of its internal collection of type
 * {@link ArrayBackedIndexedCollection}.
 * </p>
 * 
 * <p>
 * For more informations about Adaptor design pattern please see: {@link http://en.wikipedia.org/wiki/Adapter_pattern}
 * </p>
 * 
 * 
 * @author Petra
 * @version 1.0
 * @see {@link ArrayBackedIndexedCollection}
 */
public class ObjectStack {

	/**
	 * <p>
	 * This instance is used for actual element storage and for delegation. It
	 * is used to avoid code duplication.
	 * </p>
	 * 
	 * <p>
	 * The Adaptee role in Adaptor design pattern.
	 * </p>
	 */
	private ArrayBackedIndexedCollection adaptee;

	/**
	 * Default constructor that creates the private instance
	 * {@link ArrayBackedIndexedCollection}.
	 */
	public ObjectStack() {
		adaptee = new ArrayBackedIndexedCollection();
	}

	/**
	 * Returns true if and only if the stack contains no objects, false
	 * otherwise.
	 * 
	 * @return True if the stack contains no objects, false otherwise.
	 */
	public boolean isEmpty() {
		return adaptee.isEmpty();
	}

	/**
	 * Returns the size of the stack.
	 * 
	 * @return The size of the stack.
	 */
	public int size() {
		return adaptee.size();
	}

	/**
	 * Pushes an item onto the top of this stack. If the array that represents
	 * stack needs to be reallocated, time complexity is O(n). Otherwise, time
	 * complexity is O(1).
	 * 
	 * @param value
	 *            The value to be pushed onto this stack.
	 */
	public void push(Object value) {
		adaptee.add(value);
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value. Time complexity of this method is O(1);
	 * 
	 * @return The object at the top of this stack.
	 * @throws EmptyStackException
	 *             - if the stack is empty.
	 */
	public Object pop() {
		if (adaptee.isEmpty()) {
			throw new EmptyStackException();
		}

		Object object = adaptee.get(adaptee.size() - 1);
		adaptee.remove(adaptee.size() - 1);
		return object;
	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return The object at the top of this stack.
	 * @throws EmptyStackException
	 *             - if the stack is empty.
	 */
	public Object peek() {
		if (adaptee.isEmpty()) {
			throw new EmptyStackException();
		}

		Object object = adaptee.get(adaptee.size() - 1);
		return object;
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		adaptee.clear();
	}
}
