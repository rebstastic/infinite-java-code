package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents a value wrapper and allows work with objects if any
 * type, but supports arithmetic operations for instances of classes
 * {@code Integer}, {@code Double}, {@code String} representation of mentioned
 * classes, and {@code null} which is numerically interpreted as zero. When
 * trying to apply those operations on any other types, {@code RuntimeException}
 * is thrown. Supported arithmetic operations are: {@code increment(Object)},
 * {@code decrement(Object)}, {@code multiply(Object)}, {@code divide(Object)};
 * and number comparison: {@code numCompare(Object)}.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ValueWrapper {

	/**
	 * Arithmetic operation enums.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private enum Operation {
		INCREMENT, DECREMENT, MULTIPLY, DIVIDE, COMPARE
	};

	/**
	 * Currently invoked arithmetic operation.
	 */
	private Operation operation;

	/**
	 * The object value.
	 */
	private Object value;

	/**
	 * Constructor that initializes the objects value.
	 * 
	 * @param value
	 *            The value.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Returns objects value.
	 * 
	 * @return Objects value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets objects value to given {@code value}.
	 * 
	 * @param value
	 *            The value to set.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Arithmetic operation that increments this objects value by given
	 * {@code value}.
	 * 
	 * @param incValue
	 *            The value to increment this objects value with.
	 */
	public void increment(Object incValue) {
		operation = Operation.INCREMENT;
		value = performOperation(incValue);
	}

	/**
	 * Arithmetic operation that decrements this objects value by given
	 * {@code value}.
	 * 
	 * @param decValue
	 *            The value to decrement this objects value with.
	 */
	public void decrement(Object decValue) {
		operation = Operation.DECREMENT;
		value = performOperation(decValue);
	}

	/**
	 * Arithmetic operation that multiplies this objects value by given
	 * {@code value}.
	 * 
	 * @param mulValue
	 *            The value to multiply this objects value with.
	 */
	public void multiply(Object mulValue) {
		operation = Operation.MULTIPLY;
		value = performOperation(mulValue);
	}

	/**
	 * Arithmetic operation that divides this objects value by given
	 * {@code value}.
	 * 
	 * @param divValue
	 *            The value to divide this objects value with.
	 */
	public void divide(Object divValue) {
		operation = Operation.DIVIDE;
		value = performOperation(divValue);
	}

	/**
	 * Compares this objects value by given {@code value}.
	 * 
	 * @param withValue
	 *            The value to compare this objects value with.
	 * @return 0 if values are equal, -1 if this value is less then given
	 *         {@code value}, 1 if this value is greater than given
	 *         {@code value}.
	 */
	public int numCompare(Object withValue) {
		operation = Operation.COMPARE;
		return (int) performOperation(withValue);

	}

	/**
	 * Performs currently invoked operation with this objects value and given
	 * {@code value} if possible. If not, {@code RuntimeException} is thrown.
	 * 
	 * @param otherValue
	 *            The value to perform the operation with this objects value.
	 * @return The result of the currently invoked operation.
	 * @throws RuntimeException
	 *             - if operation can not be performed with this objects value
	 *             and given {@code value}.
	 */
	private Object performOperation(Object otherValue) {
		// Parse from string if needed, convert to zero if null.
		this.value = getProperFormat(this.value);
		otherValue = getProperFormat(otherValue);

		// Determine the results type.
		boolean isResultDouble = false;
		if (isDouble(this.value) || isDouble(otherValue)) {
			isResultDouble = true;
		}

		// Convert to double before applying operation.
		this.value = toDouble(this.value);
		otherValue = toDouble(otherValue);

		// Apply operation.
		Object result = null;
		switch (operation) {
		case INCREMENT:
			result = (Double) this.value + (Double) otherValue;
			break;
		case DECREMENT:
			result = (Double) this.value - (Double) otherValue;
			break;
		case MULTIPLY:
			result = (Double) this.value * (Double) otherValue;
			break;
		case DIVIDE:
			result = (Double) this.value / (Double) otherValue;
			break;
		case COMPARE:
			result = toDouble(((Double) this.value).compareTo((Double) otherValue));
			break;
		default:
			break;
		}

		// If the result is integer.
		if (!isResultDouble || operation.equals(Operation.COMPARE)) {
			return (Integer) ((Double) result).intValue();
		}
		// Result is double.
		return result;
	}

	/**
	 * Specifying the values type. If {@code null}, the value is treated as
	 * zero. If {@code String}, try to parse it to the number.
	 * 
	 * @param value
	 *            The value to specify.
	 * @return The more specified values type.
	 */
	private Object getProperFormat(Object value) {
		Object ret = value;
		if (value == null) {
			ret = Integer.valueOf(0);
		} else if (value instanceof String) {
			ret = parse((String) value);
		}

		return ret;
	}

	/**
	 * Parses the string value to it's number equivalent if possible. If not,
	 * {@code RuntimeException} is thrown.
	 * 
	 * @param value
	 *            The value to parse.
	 * @return The number representation of given {@code value}.
	 * @throws RuntimeException
	 *             - if given string {@code value} does not represent any
	 *             number.
	 */
	private static Object parse(String value) {
		Object retValue = null;
		try {
			retValue = Integer.valueOf(value);
		} catch (NumberFormatException e1) {
			try {
				retValue = Double.valueOf(value);
			} catch (NumberFormatException e2) {
				throw new RuntimeException("Can not parse to the number: "
						+ value);
			}
		}
		return retValue;
	}

	/**
	 * Returns {@code true} if given {@code value} is instance of {@code Double}
	 * , {@code false} otherwise.
	 * 
	 * @param value
	 *            The value to check.
	 * @return {@code true} if given {@code value} is instance of {@code Double}
	 *         , {@code false} otherwise.
	 */
	private static boolean isDouble(Object value) {
		if (value instanceof Double) {
			return true;
		}
		return false;
	}

	/**
	 * Casts given {@code value} to {@code Double}.
	 * 
	 * @param value
	 *            The value to cast.
	 * @return The given {@code value} wrapped in {@code Double}.
	 */
	private static Object toDouble(Object value) {
		if (value instanceof Integer) {
			return (Double) ((Integer) value * 1.0);
		}
		return value;
	}

}
