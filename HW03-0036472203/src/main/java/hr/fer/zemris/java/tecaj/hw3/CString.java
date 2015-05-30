package hr.fer.zemris.java.tecaj.hw3;

/**
 * <p>
 * The {@code CString} class represents character strings. Strings are constant
 * - their values cannot be changed after they are created.
 * </p>
 * 
 * <p>
 * The class {@code CString} includes methods for examining individual
 * characters of the sequence, for searching strings, for extracting substrings,
 * for concatenating strings, and for replacing sequences in the string.
 * </p>
 * 
 * <p>
 * The implementation allows performing operations such as substring and similar
 * in constant time (independent on the substring length).
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class CString {

	/**
	 * The array that stores all the object data.
	 */
	private final char[] data;

	/**
	 * The start position of a string in data array.
	 */
	private final int offset;

	/**
	 * The number of elements of a string.
	 */
	private final int count;

	/**
	 * Constructor. Allocates a new {@code CString} that contains characters
	 * from a subarray of the character array argument. The {@code offset}
	 * argument is the index of the first character of the subarray and the
	 * {@code count} argument specifies the length of the subarray. The contents
	 * of the subarray are copied; subsequent modification of the character
	 * array does not affect the newly created string.
	 * 
	 * @param data
	 *            The array that is the source of data of an object.
	 * @param offset
	 *            The initial start position.
	 * @param count
	 *            The length.
	 */
	public CString(char[] data, int offset, int count) {
		this(data, offset, count, true);
	}

	/**
	 * Constructor. Allocates a new {@code CString} so that it represents the
	 * sequence of characters currently contained in the character array
	 * argument. The contents of the character array are copied; subsequent
	 * modification of the character array does not affect the newly created
	 * string.
	 * 
	 * @param data
	 *            The initial data of an object.
	 */
	public CString(char[] data) {
		this(data, 0, data.length, true);
	}

	/**
	 * Constructor. Initializes a newly created {@code CString} object so that
	 * it represents the same sequence of characters as the argument; in other
	 * words, the newly created string is a copy of the argument string.
	 * 
	 * @param string
	 *            The initial data of an object.
	 */
	public CString(String string) {
		this(string.toCharArray(), 0, string.length(), true);
	}

	/**
	 * Constructor. Initializes a newly created {@code CString} object so that
	 * it represents the same sequence of characters as the argument. If the
	 * length of the data array is equal to the length of <code>original</code>
	 * string, the data array is shared, otherwise, the newly created string is
	 * a copy of the argument string.
	 * 
	 * @param original
	 *            The original object.
	 */
	public CString(CString original) {
		char[] newData;
		if (original.data.length > original.count) {
			// Allocate new char array with min size.
			newData = copyOfRange(original.data, original.offset,
					original.offset + original.count);
		} else {
			// Pass the reference to the char array.
			newData = original.data;
		}
		this.offset = 0;
		this.count = newData.length;
		this.data = newData;
	}

	/**
	 * Additional constructor. Private constructor which shares data array if
	 * the copy flag is set to {@code false} for speed. A separate constructor
	 * is needed because we already have a public String(char[], int, int)
	 * constructor that makes a copy of the given char[].
	 * 
	 * @param data
	 *            The array that is the source of data of an object.
	 * @param offset
	 *            The initial start position.
	 * @param count
	 *            The length.
	 * @param copy
	 *            The variable with value true if char array copying is needed,
	 *            false otherwise.
	 * @throws StringIndexOutOfBoundsException
	 *             - if the offset or count are out of data length limits.
	 */
	private CString(char[] data, int offset, int count, boolean copy) {
		if (offset < 0) {
			throw new StringIndexOutOfBoundsException(offset);
		}

		if (count < 0) {
			throw new StringIndexOutOfBoundsException(count);
		}

		if (data.length < offset + count) {
			throw new StringIndexOutOfBoundsException(offset + count);
		}

		if (copy) {
			// Creates new array on the heap.
			// If user sends larger array than needed, take only what needed.
			this.offset = 0;
			this.count = count;
			this.data = copyOfRange(data, offset, offset + count);
		} else {
			// The substring of the same array on the heap.
			this.offset = offset;
			this.count = count;
			this.data = data;
		}
	}

	/**
	 * Returns the length of a string.
	 * 
	 * @return The length of a string.
	 */
	public int length() {
		return count;
	}

	/**
	 * Returns the <code>char</code> value at the specified index. An index
	 * ranges from <code>0</code> to <code>length() - 1</code>. The first
	 * <code>char</code> value of the sequence is at index <code>0</code>, the
	 * next at index <code>1</code>, and so on, as for array indexing.
	 * 
	 * @param index
	 *            The index of wanted character.
	 * @return The character at the specified index of this string. The first
	 *         <code>char</code> value is at index <code>0</code>.
	 * @throws StringIndexOutOfBoundsException
	 *             - if given index is not in the string limits.
	 */
	public char charAt(int index) {
		if (index < 0 || index > offset + count) {
			throw new StringIndexOutOfBoundsException();
		}
		return data[index+offset];
	}

	/**
	 * Returns the character array that represents a string.
	 * 
	 * @return The character array that represents a string.
	 */
	public char[] toCharArray() {
		return copyOfRange(data, offset, offset + count);
	}

	/**
	 * Returns the index within this string of the first occurrence of the
	 * specified character.
	 * 
	 * @param c
	 *            The character.
	 * @return Index of first occurrence of the <code>c</code> or -1.
	 */
	public int indexOf(char c) {
		for (int i = offset; i < offset + count; i++) {
			if (c == data[i]) {
				return i-offset;
			}
		}
		return -1;
	}

	/**
	 * Tests if this string starts with the specified prefix.
	 * 
	 * @param s
	 *            The prefix.
	 * @return True if this string starts with prefix, false otherwise.
	 * @throws StringIndexOutOfBoundsException
	 *             - if the given string is greater than this string.
	 */
	public boolean startsWith(CString s) {
		if (this.count < s.count) {
			throw new StringIndexOutOfBoundsException();
		}
		int sSize = s.count;
		int sIndex = s.offset;
		int thisIndex = offset;
		while (sSize-- > 0) {
			if (data[thisIndex++] != s.data[sIndex++]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Tests if this string ends with the specified suffix.
	 * 
	 * @param s
	 *            The suffix.
	 * @return True if this string ends with suffix, false otherwise.
	 * @throws StringIndexOutOfBoundsException
	 *             - if the given string is greater than this string.
	 */
	public boolean endsWith(CString s) {
		if (this.count < s.count) {
			throw new StringIndexOutOfBoundsException();
		}
		int sSize = s.count;
		int sIndex = s.offset + s.count - 1;
		int thisIndex = offset + count - 1;
		while (sSize-- > 0) {
			if (this.data[thisIndex--] != s.data[sIndex--]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if and only if this string contains the specified sequence
	 * of char values.
	 *
	 * @param s
	 *            The sequence to search for.
	 * @return True if the string contains given string at any position, false
	 *         otherwise.
	 */
	public boolean contains(CString s) {
		return indexOf(s.data, 0) != -1;
	}

	/**
	 * Returns new <code>CString</code> which represents a part of original
	 * string; position <code>endIndex</code> does not belong to the substring.
	 * The substring begins with the character at the specified index and
	 * extends to the end of this string. The data array is shared with this
	 * object. Time complexity: O(1).
	 * 
	 * @param startIndex
	 *            The start index, inclusive.
	 * @param endIndex
	 *            The end index, exclusive.
	 * @return The instance of a substring.
	 * @throws StringIndexOutOfBoundsException
	 *             - if the start index is greater than end index.
	 */
	public CString substring(int startIndex, int endIndex) {
		if (startIndex > endIndex) {
			throw new StringIndexOutOfBoundsException();
		}
		return new CString(data, offset + startIndex, endIndex - startIndex,
				false);
	}

	/**
	 * Returns new <code>CString</code> which represents starting part of
	 * original string and is of length <code>n</code>. Throws an exception if
	 * this can not be constructed.
	 * 
	 * @param n
	 *            The length of the substring.
	 * @return The substring that represents starting part of original string
	 *         and is of length <code>n</code>.
	 * 
	 * @throws StringIndexOutOfBoundsException
	 *             - if <code>n</code> is out of this string limits.
	 */
	public CString left(int n) {
		if (n < 0 || n > count) {
			throw new StringIndexOutOfBoundsException();
		}
		return substring(0, n);
	}

	/**
	 * Returns new <code>CString</code> which represents ending part of original
	 * string and is of length <code>n</code>. Throws an exception if this can
	 * not be constructed.
	 * 
	 * @param n
	 *            The length of the substring.
	 * @return The substring that represents ending part of original string and
	 *         is of length <code>n</code>.
	 * 
	 * @throws StringIndexOutOfBoundsException
	 *             - if <code>n</code> is out of this string limits.
	 */
	public CString right(int n) {
		if (n < 0 || n > count) {
			throw new StringIndexOutOfBoundsException();
		}
		return substring(count-n, n);
	}

	/**
	 * Concatenates the specified string to the end of this string. If the
	 * length of the argument string is <code>0</code>, then this
	 * <code>CString</code> object is returned.
	 * 
	 * @param s
	 *            the <code>CString</code> that is concatenated to the end of
	 *            this <code>CString</code>.
	 * @return a string that represents the concatenation of this object's
	 *         characters followed by the string argument's characters.
	 */
	public CString add(CString s) {
		if (s.count == 0) {
			return this;
		}
		char[] newData = new char[count + s.count];
		int thisOffset = offset;
		int sOffset = s.offset;
		int i = 0;
		for (; i < count; i++) {
			newData[i] = data[thisOffset++];
		}
		for (; i < newData.length; i++) {
			newData[i] = s.data[sOffset++];
		}

		return new CString(newData, 0, newData.length, true);
	}

	/**
	 * Returns a new string resulting from replacing all occurrences of
	 * <code>oldChar</code> in this string with <code>newChar</code>.
	 * <p>
	 * If the character <code>oldChar</code> does not occur in the character
	 * sequence represented by this <code>CString</code> object, then a
	 * reference to this <code>CString</code> object is returned.
	 * 
	 * @param oldChar
	 *            The old character.
	 * @param newChar
	 *            The new character.
	 * @return An object derived from this string by replacing every occurrence
	 *         of <code>oldChar</code> with <code>newChar</code>.
	 */
	public CString replaceAll(char oldChar, char newChar) {
		if (oldChar == newChar) {
			return this;
		}
		char[] newData = new char[count];
		int i = count;
		while (--i >= 0) {
			if (data[offset + i] == oldChar) {
				newData[i] = newChar;
			} else {
				newData[i] = data[offset + i];
			}
		}
		return new CString(newData, 0, newData.length, true);
	}

	/**
	 * * Returns a new string resulting from replacing all occurrences of
	 * <code>oldStr</code> in this string with <code>newStr</code>.
	 * <p>
	 * If the character <code>oldStr</code> does not occur in the character
	 * sequence represented by this <code>CString</code> object, then a
	 * reference to this <code>CString</code> object is returned.
	 * 
	 * @param oldStr
	 *            The old substring.
	 * @param newStr
	 *            The new substring.
	 * @return An object derived from this string by replacing every occurrence
	 *         of <code>oldStr</code> with <code>newStr</code>.
	 */
	public CString replaceAll(CString oldStr, CString newStr) {
		if (oldStr.equals(newStr) || !contains(oldStr)) {
			return this;
		}

		char[] oldData = oldStr.data;
		char[] newData = newStr.data;

		int numberOfChanges = countOccurrence(oldData);
		int[] indexes = new int[numberOfChanges];

		int start = 0;
		for (int i = 0; i < numberOfChanges; i++) {
			indexes[i] = indexOf(oldData, start);
			start = indexes[i] + oldData.length;
		}

		char[] buffer = new char[count + (newData.length - oldData.length)
				* numberOfChanges];

		int i = 0; // index of indexes array
		int dataIndex = offset; // index of data array
		int bufferIndex = 0; // index of buffer array
		while (i < numberOfChanges) {
			while (dataIndex != indexes[i]) {
				buffer[bufferIndex++] = data[dataIndex++];
			}
			int j = 0; // index of newData array
			while (j < newData.length) {
				buffer[bufferIndex++] = newData[j++];
			}
			i++;
			dataIndex += oldData.length;
		}

		return new CString(buffer, 0, buffer.length, true);
	}

	/**
	 * Returns the index within this string of the first occurrence of the
	 * specified substring starting from given index.
	 * 
	 * @param seq
	 *            The substring.
	 * @param start
	 *            The starting search index.
	 * @return The index of the first occurrence of the specified substring
	 *         starting from given index.
	 */
	private int indexOf(char[] seq, int start) {
		int dataIndex = offset + start;
		int dataEnd = offset + count - seq.length;

		while (dataIndex <= dataEnd) {
			for (int seqIndex = 0; seq[seqIndex++] == data[dataIndex++];) {
				if (seqIndex == seq.length) {
					return dataIndex - seq.length;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the number of occurrences of given substring in this string.
	 * 
	 * @param seq
	 *            The substring to search for.
	 * @return The number of occurrences of given substring.
	 */
	private int countOccurrence(char[] seq) {
		int i = 0;
		int counter = 0;
		while (i < count) {
			int index = indexOf(seq, i);
			if (index != -1) {
				counter++;
				i = index + seq.length;
			} else {
				break;
			}
		}
		return counter;
	}

	/**
	 * Returns a newly allocated character array which is a copy of given
	 * <code>data</code>. The first element is the one indexed with
	 * <code>start</code>, and the length is <code>end -
	 * start</code>.
	 *
	 * @param data
	 *            The array to copy.
	 * @param start
	 *            The starting point
	 * @param end
	 *            The ending point.
	 * @return A newly allocated character array.
	 */
	private static char[] copyOfRange(char[] data, int start, int end) {
		// End is not included.
		int size = end - start;
		char[] newData = new char[size];
		for (int i = 0; i < size; i++) {
			newData[i] = data[i + start];
		}
		return newData;
	}

	@Override
	public String toString() {
		char[] array = copyOfRange(data, offset, offset + count);
		String string = "";
		for (int i = 0; i < array.length; i++) {
			string += array[i];
		}
		return string;
	}
}
