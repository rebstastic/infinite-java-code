package hr.fer.zemris.java.custom.collections;

/**
 * <p>
 * This class contains various methods for manipulating a resizable array-backed
 * collection of objects. General contract of this collection is: duplicate
 * elements are allowed; null references are not allowed. When passing the null
 * reference to the method, an IllegalArgumentException is thrown. When trying
 * to modify an array on the position that is greater than array size, an
 * IndexOutOfBoundException is thrown.
 * </p>
 * 
 * <p>
 * The documentation for the methods contained in this class includes briefs
 * description of the implementations, such as time complexity.
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @version 1.0
 *
 */
public class ArrayBackedIndexedCollection {

	/**
	 * Number of elements that are currently stored in the collection.
	 */
	private int size;

	/**
	 * Current capacity of allocated array of object references.
	 */
	public int capacity;

	/**
	 * An array of object references which length is determined by capacity
	 * variable.
	 */
	private Object[] elements;

	/**
	 * The constructor sets the capacity to the given value and preallocates the
	 * elements array to that size.
	 * 
	 * @param initialCapacity
	 *            The capacity of allocated elements array.
	 * @throws IllegalArgumentException
	 *             - if the initial capacity is less then 1.
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * The default constructor that sets the capacity to 16 and preallocates the
	 * elements array to that size.
	 */
	public ArrayBackedIndexedCollection() {
		this(16);
	}

	/**
	 * Returns true if and only if the collection contains no objects, false
	 * otherwise.
	 * 
	 * @return True if the collection contains no objects, false otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of currently stored objects in collections.
	 * 
	 * @return The number of currently stored objects in collections.
	 */
	public int size() {
		return size;
	}

	/**
	 * <p>
	 * Adds the given object into the collection into the first empty place in
	 * the elements array. If the elements array is full, the array is
	 * reallocated by doubling its size. The method refuses to add null as an
	 * element.
	 * </p>
	 * 
	 * <p>
	 * The time complexity is O(n).
	 * </p>
	 * 
	 * @param value
	 *            The value to add at the end of the elements array.
	 * @throws IllegalArgumentException
	 *             - if the given value is null.
	 */
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (size >= capacity) {
			// Reallocation to the double sized array.
			reallocate();
		}
		// Add the value to the size + 1 position.
		elements[size++] = value;
	}

	/**
	 * Returns the object that is stored in the array at position index. Valid
	 * indexes are 0 to size-1.
	 * 
	 * @param index
	 *            The index of an object to get.
	 * @return The value of the indexed object.
	 * @throws IndexOutOfBoundsException
	 *             - if the index is greater than or equal to the size of the
	 *             specified array.
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * <p>
	 * Removes the object that is stored in the elements array at position
	 * index. Since the collection must not hold null references, the content of
	 * the elements array which is at positions greater than index should be
	 * shifted one position down.
	 * </p>
	 * 
	 * <p>
	 * If the given index is greater than or equal to the size of the specified
	 * array, nothing happens. Time complexity is O(n).
	 * </p>
	 * 
	 * @param index
	 *            The position of an object to remove.
	 */
	public void remove(int index) {
		elements[index] = null;
		// There is no need to shift the elements if the last element is
		// removed.
		if (index < size - 1) {
			for (int i = index + 1; i < size; i++) {
				elements[i - 1] = elements[i];
			}
		}
		size--;
	}

	/**
	 * <p>
	 * Inserts (does not overwrite) the given value at the given position in the
	 * array. Before actual insertion elements at position and at greater
	 * positions are shifted one place toward the end, so that an empty place is
	 * created at position. The legal positions are 0 to size.
	 * </p>
	 * 
	 * <p>
	 * Time complexity is O(n).
	 * </p>
	 * 
	 * @param value
	 *            The object to add at the given position in the array.
	 * @param position
	 *            The position of the value to add.
	 * @throws IndexOutOfBoundsException
	 *             - if the index is greater than the size of the specified
	 *             array.
	 */
	public void insert(Object value, int position) {
		if (position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (size >= capacity) {
			reallocate();
		}
		if (position < size) {
			for (int i = 0; i < size; i++) {
				elements[i + 1] = elements[i];
			}
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found.
	 * 
	 * @param value
	 *            The value to search in the array.
	 * @return The index of the first occurrence of the given value or -1 if the
	 *         value is not found.
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns returns true only if the collection contains given value.
	 * 
	 * @param value
	 *            The value to search in the array.
	 * @return True if the collection contains given value, false otherwise.
	 */
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}

	/**
	 * Removes all elements from the collection. The allocated array is left at
	 * current capacity.
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Realloacates the array by doubling its size.
	 */
	private void reallocate() {
		capacity = 2 * size;
		Object[] newArray = new Object[capacity];
		for (int i = 0; i < size; i++) {
			newArray[i] = elements[i];
		}
		elements = newArray;
	}

	public Object[] toArray() {
		Object[] array = new Object[size];
		for(int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		return array;
	}
}
