package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a special kind of map that allows the user to store
 * multiple values for same key. A stack-like abstraction is provided for these
 * values. Keys are instances of {@code String} class, and values are instances
 * of {@code ValueWrapper} class. In a way, this collection is a map that
 * associates strings with stacks. Virtual stacks for two different strings are
 * completely isolated from each other. Provided stack-like methods are:
 * {@code pop(String)}, {@code peek(String)}, {@code push(String, ValueWrapper)}
 * .
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ObjectMultistack {
	
	/**
	 * This inner static class acts as a node of a single linked-list.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class MultistackEntry {
		/**
		 * The value stored in the entry.
		 */
		private ValueWrapper value;
		
		/**
		 * The reference to the next {@code MultistackEntry} object.
		 */
		private MultistackEntry next;
	}

	/**
	 * A map that associates strings with stacks.
	 */
	private Map<String, MultistackEntry> stackMap;

	/**
	 * Constructor that initializes the map.
	 */
	public ObjectMultistack() {
		stackMap = new HashMap<>();
	}

	/**
	 * Pushes the given {@code value} onto the top of the stack associated with
	 * given {@code key}.
	 * 
	 * @param key
	 *            The key.
	 * @param value
	 *            The value to be pushed.
	 * @throws IllegalArgumentException
	 *             - if given {@code key} is {@code null}
	 */
	public void push(String key, ValueWrapper value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null: " + key);
		}

		MultistackEntry stack = null;
		if (!stackMap.containsKey(key)) {
			stack = new MultistackEntry();
			stack.value = value;
			stackMap.put(key, stack);
		}

		stack = stackMap.get(key);
		MultistackEntry newEntry = new MultistackEntry();
		newEntry.value = value;
		newEntry.next = stack.next;
		stack.next = newEntry;

	}

	/**
	 * Removes the object at the top of the stack associated with the given
	 * {@code key} and returns the value stored in that object.
	 * 
	 * @param key
	 *            The key.
	 * @return The value at the top of the stack associated with the given
	 *         {@code key}.
	 * @throws IllegalArgumentException
	 *             - if given {@code key} is {@code null} or does not exists in
	 *             the map.
	 * @throws RuntimeException
	 *             - if the stack associated with the given {@code key} is
	 *             empty.
	 */
	public ValueWrapper pop(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null: " + key);
		}
		if (!stackMap.containsKey(key)) {
			throw new IllegalArgumentException("Key does not exists: " + key);
		}
		if (isEmpty(key)) {
			throw new RuntimeException("Can not pop from empty stack.");
		}
		MultistackEntry stack = stackMap.get(key);
		ValueWrapper value = stack.next.value;
		stack.next = stack.next.next;
		return value;
	}

	/**
	 * Looks at the object at the top of the stack associated with the given
	 * {@code key} without removing it from the stack. Returns the objects
	 * value.
	 * 
	 * @param key
	 *            The key.
	 * 
	 * @return The value at the top of the stack associated with the given
	 *         {@code key}.
	 * @throws IllegalArgumentException
	 *             - if given {@code key} is {@code null} or does not exists in
	 *             the map.
	 * @throws RuntimeException
	 *             - if the stack associated with the given {@code key} is
	 *             empty.
	 */
	public ValueWrapper peek(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null: " + key);
		}
		if (!stackMap.containsKey(key)) {
			throw new IllegalArgumentException("Key does not exists: " + key);
		}
		if (isEmpty(key)) {
			throw new RuntimeException("Can not peek from empty stack.");
		}

		return stackMap.get(key).next.value;
	}

	/**
	 * Returns {@code true} if stack associated with the given {@code key} is
	 * empty, {@code false} otherwise.
	 * 
	 * @param key
	 *            The key.
	 * @return {@code true} if stack associated with the given {@code key} is
	 *         empty, {@code false} otherwise.
	 * @throws IllegalArgumentException
	 *             - if given {@code key} is {@code null} or does not exists in
	 *             the map.
	 */
	public boolean isEmpty(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can not be null: " + key);
		}
		if (!stackMap.containsKey(key)) {
			throw new IllegalArgumentException("Key does not exists: " + key);
		}
		MultistackEntry stack = stackMap.get(key);
		if (stack.next == null) {
			return true;
		}
		return false;
	}

}
