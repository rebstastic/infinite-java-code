package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * This class implements a simple hash table, which maps keys to values. Any
 * non-null object can be used as a key.
 * </p>
 * 
 * <p>
 * To successfully store and retrieve objects from a hash table, the objects
 * used as keys must implement the hashCode method and the equals method.
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {

	/**
	 * A measure of how full the hash table is allowed to get before its
	 * capacity is automatically increased.
	 */
	private static final double LOAD_FACTOR = 0.75;

	/**
	 * The number of times this hash table has been modified. The methods that
	 * increase this field are <code>put</code>, <code>rehash</code>,
	 * <code>remove</code> and <code>clean</code>.
	 */
	private int modificationCount;

	/**
	 * The hash table represented by the array of slots. Each element is the
	 * first element in the i-th slot, where i is the element index.
	 */
	private TableEntry[] table;

	/**
	 * Number of stored key-value pairs in the hash table.
	 */
	private int size = 0;

	/**
	 * Default constructor. Constructs a new, empty hash table with default
	 * initial capacity of 16 slots.
	 */
	public SimpleHashtable() {
		this(16);
	}

	/**
	 * Constructor. Constructs a new, empty the hash table with specified
	 * initial capacity. Number of slots is a power of two that is greater or
	 * equal to given parameter.
	 * 
	 * @param initialCapacity
	 *            The initial capacity of the hash table.
	 * @throws IllegalArgumentException
	 *             - if the given initialCapacity is less than zero.
	 */
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Capacity can't be negative!");
		}
		int newCapacity = 1;
		while (newCapacity < initialCapacity) {
			newCapacity *= 2;
		}
		modificationCount = 0;
		table = new TableEntry[newCapacity];
	}

	/**
	 * Returns the number of the slot suitable for the given key. The key-value
	 * pair will be stored in the calculated slot.
	 * 
	 * @param key
	 *            The key to hash.
	 * @return The number of slot suitable for the given key.
	 */
	private int hash(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * Maps the specified key to the specified value in this hash table. The key
	 * can not be null.
	 * 
	 * @param key
	 *            The hash table key.
	 * @param value
	 *            The value.
	 * @throws IllegalArgumentException
	 *             - if the key is null
	 */
	public void put(Object key, Object value) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}
		int slot = hash(key);
		if (table[slot] == null) {
			// The first element in the slot.
			table[slot] = new TableEntry(key, value, null);
		} else {
			TableEntry entry = table[slot];
			// Check if the given key is already in the hash table.
			while (entry != null) {
				if (entry.getKey().equals(key)) {
					entry.setValue(value);
					return;
				}
				entry = entry.next;
			}
			// Get the last entry.
			TableEntry lastEntry = table[slot];
			for (; lastEntry.next != null; lastEntry = lastEntry.next)
				;
			lastEntry.next = new TableEntry(key, value, null);
		}
		modificationCount++;
		size++;
		if (size >= table.length * LOAD_FACTOR) {
			rehash();
		}
	}

	/**
	 * Increases the capacity of and internally reorganizes this hash table, in
	 * order to accommodate and access its entries more efficiently. This method
	 * is called automatically when the number of keys in the hash table exceeds
	 * this hash table's capacity and load factor.
	 */
	private void rehash() {
		int newLength = table.length * 2;
		TableEntry[] newTable = new TableEntry[newLength];
		// Iterate through old table.
		for (int slot = 0; slot < table.length; slot++) {
			// Iterate through old slot.
			for (TableEntry oldEntry = table[slot]; oldEntry != null; oldEntry = oldEntry.next) {
				// Calculate new slot.
				int newSlot = Math.abs(oldEntry.getKey().hashCode())
						% newLength;
				TableEntry newEntry = new TableEntry(oldEntry.getKey(),
						oldEntry.getValue(), null);
				if (newTable[newSlot] == null) {
					// The first element in the slot.
					newTable[newSlot] = newEntry;
				} else {
					// Get the last entry.
					TableEntry lastEntry = newTable[newSlot];
					for (; lastEntry.next != null; lastEntry = lastEntry.next)
						;
					lastEntry.next = newEntry;
				}
			}
		}
		// Make this object remember new table reference.
		table = newTable;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 * 
	 * @param key
	 *            The key whose associated value is to be returned.
	 * @return The value to which the specified key is mapped, or null if this
	 *         map contains no mapping for the key.
	 * @throws IllegalArgumentException
	 *             - if the key is null
	 * 
	 */
	public Object get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}
		int slot = hash(key);
		if (table[slot] != null) {
			for (TableEntry entry = table[slot]; entry != null; entry = entry.next) {
				if (entry.getKey().equals(key))
					return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * Returns the number of elements in this hash table.
	 * 
	 * @return The number of elements in this hash table.
	 * 
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests if some key maps into the specified value in this hash table. This
	 * operation is more expensive than the containsKey method.
	 * 
	 * @param value
	 *            A value to search for.
	 * @return True if and only if some key maps to the value argument in this
	 *         hash table as determined by the equals method, false otherwise.
	 * 
	 */
	public boolean containsValue(Object value) {
		for (int slot = 0; slot < table.length; slot++) {
			for (TableEntry entry = table[slot]; entry != null; entry = entry.next) {
				if (entry.getValue().equals(value))
					return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the specified object is a key in this hash table.
	 * 
	 * @param key
	 *            Possible key.
	 * @return True if and only if the specified object is a key in this hash
	 *         table, as determined by the equals method, false otherwise.
	 * @throws IllegalArgumentException
	 *             - if the key is null
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}
		int slot = hash(key);
		for (TableEntry entry = table[slot]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key))
				return true;
		}
		return false;
	}

	/**
	 * Removes the key (and its corresponding value) from this hash table. This
	 * method does nothing if the key is not in the hash table.
	 * 
	 * @param key
	 *            The key that needs to be removed.
	 * @throws IllegalArgumentException
	 *             - if the key is null
	 * 
	 */
	public void remove(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}

		int slot = hash(key);
		for (TableEntry entry = table[slot], previous = null; entry != null; previous = entry, entry = entry.next) {
			if (entry.getKey().equals(key)) {
				if (previous != null) {
					previous.next = entry.next;
				} else {
					table[slot] = entry.next;
				}
				modificationCount++;
				size--;
				return;
			}
		}
	}

	/**
	 * Returns the number of keys in this hash table.
	 * 
	 * @return True if this hash table maps no keys to values, false otherwise.
	 * 
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clears the hash table so that it contains no keys. The capacity remains
	 * unchanged.
	 */
	public void clear() {
		for (int slot = 0; slot < table.length; slot++) {
			table[slot] = null;
		}
		modificationCount++;
		size = 0;
	}

	/**
	 * Returns a string representation of this SimpleHashtable object in the
	 * form of a set of entries, enclosed in braces and separated by the ASCII
	 * characters ", " (comma and space). Each entry is rendered as the key, a
	 * comma ",", and the associated element, where the toString method is used
	 * to convert the key and element to strings.
	 * 
	 * @return A string representation of the hash table.
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int slot = 0; slot < table.length; slot++) {
			TableEntry entry = table[slot];
			if (entry == null) {
				continue;
			} else {
				while (entry != null) {
					sb.append(entry.toString());
					sb.append(", ");
					entry = entry.next;
				}
			}
		}
		if (sb.length() > 1) {
			// Delete the last comma and space.
			sb.delete(sb.length() - 2, sb.length());
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * <p>
	 * This class is static nested class in SimpleHashtable class. It implements
	 * an entry intended to be stored in the hash table. Any non-null object can
	 * be used as a key.
	 * </p>
	 * <p>
	 * It is common to use static nested classes as a helper data structures. In
	 * effect, a static nested class is behaviorally a top-level class that has
	 * been nested in another top-level class for packaging convenience.
	 * </p>
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	public static class TableEntry {

		/**
		 * The table entry key. Can not be null.
		 */
		private Object key;

		/**
		 * The table entry value.
		 */
		private Object value;

		/**
		 * The reference to the next table entry in the same slot.
		 */
		private TableEntry next;

		/**
		 * Constructs a new TableEntry object and initializes it with given
		 * arguments.
		 * 
		 * @param key
		 *            The table entry key. Can not be null.
		 * @param value
		 *            The table entry value.
		 * @param lastEntry
		 *            The reference to the last entry in the slot.
		 * @throws IllegalArgumentException
		 *             - if the key is null
		 * 
		 */
		public TableEntry(Object key, Object value, TableEntry next) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null!");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the key corresponding to this entry.
		 * 
		 * @return The key corresponding to this entry.
		 * 
		 */
		public Object getKey() {
			return this.key;
		}

		/**
		 * Returns the value corresponding to this entry.
		 * 
		 * @return The value corresponding to this entry.
		 * 
		 */
		public Object getValue() {
			return this.value;
		}

		/**
		 * Sets the value in this entry on a given value.
		 * 
		 * @param value
		 *            The value to be set in this table entry.
		 * 
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return ("" + this.key + "=" + this.value);
		}
	}

	@Override
	public Iterator<SimpleHashtable.TableEntry> iterator() {
		return new IteratorImpl();
	}

	/**
	 * A hash table iterator class.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry> {

		/**
		 * Local reference to the backing SimpleHashtable table array.
		 */
		private TableEntry[] t;

		/**
		 * The number of elements left to iterate over.
		 */
		private int remaining;

		/**
		 * The current entry.
		 */
		private TableEntry currentEntry;

		/**
		 * The current slot.
		 */
		private int currentSlot;

		/**
		 * The last returned entry.
		 */
		private TableEntry lastReturned;

		/**
		 * The modCount value that the iterator believes that the backing
		 * SimpleHashtbale should have. If this expectation is violated, the
		 * iterator has detected concurrent modification.
		 */
		private int expectedModificationCount;

		/**
		 * Constructor. Finds the first entry in the hash table.
		 */
		public IteratorImpl() {
			t = table;
			remaining = size;
			currentSlot = -1;
			lastReturned = null;
			expectedModificationCount = modificationCount;
			getNextHead();
		}

		/**
		 * Finds the first non-empty slot and the first element in it.
		 */
		private void getNextHead() {
			while (++currentSlot < t.length) {
				if (t[currentSlot] != null) {
					currentEntry = t[currentSlot];
					break;
				}
			}
		}

		@Override
		public boolean hasNext() {
			if (modificationCount != expectedModificationCount) {
				throw new ConcurrentModificationException();
			}
			return remaining > 0;
		}

		@Override
		public TableEntry next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Iteration over.");
			}
			remaining--;
			if (currentEntry == null) {
				getNextHead();
			}

			lastReturned = currentEntry;
			currentEntry = currentEntry.next;
			return lastReturned;
		}

		@Override
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException(
						"Invalid call of remove() method.");
			}
			if (modificationCount != expectedModificationCount) {
				throw new ConcurrentModificationException();
			}
			int slot = hash(lastReturned.getKey());
			for (TableEntry entry = t[slot], prev = null; entry != null; prev = entry, entry = entry.next) {
				if (entry.getKey().equals(lastReturned.getKey())) {
					modificationCount++;
					expectedModificationCount++;
					if (prev == null) {
						t[slot] = entry.next;
					} else {
						prev.next = entry.next;
					}
					size--;
					lastReturned = null;
					return;
				}
			}
		}
	}
}