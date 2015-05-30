package hr.fer.zemris.java.complex;

import java.text.DecimalFormat;

/**
 * This class represents immutable model of complex number. The complex numbers
 * are constant - their values can not be changed once they're created. The
 * various methods for performing arithmetics with complex numbers are defined.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Complex {

	/**
	 * 0 + 0i
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * 1 + 0i
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * -1 + 0i
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * 0 + i
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * 0 - i
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of complex number.
	 */
	private double real;

	/**
	 * Imaginary part of complex number.
	 */
	private double imaginary;

	/**
	 * Constuructor that initializes the complex number to zero.
	 */
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param real
	 *            Real part of complex number.
	 * @param imaginary
	 *            Imaginary part of complex number.
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns real part of complex number.
	 * 
	 * @return Real part of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns imaginary part of complex number.
	 * 
	 * @return Imaginary part of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns module of complex number.
	 * 
	 * @return module of complex number.
	 */
	public double module() {
		// sqrt(real^2 + imaginary^2)
		return Math.hypot(real, imaginary);
	}

	/**
	 * Multiplies {@code this} rith given complex number and returns a new
	 * resulting complex number.
	 * 
	 * @param other
	 *            The complex number to multiply {@code this} with.
	 * @return The new resulting complex number.
	 */
	public Complex multiply(Complex other) {
		double newReal = this.real * other.real - this.imaginary
				* other.imaginary;
		double newImaginary = this.real * other.imaginary + this.imaginary
				* other.real;
		return new Complex(newReal, newImaginary);
	}

	/**
	 * Divides {@code this} with given complex number and returns new resulting
	 * complex number.
	 * 
	 * @param other
	 *            The divisor.
	 * @return The new resulting complex number.
	 * @throws IllegalArgumentException
	 *             - if dividing by zero.
	 */
	public Complex divide(Complex other) {
		if (other.real == 0.0 && other.imaginary == 0.0) {
			throw new IllegalArgumentException("Can not divide by zero.");
		}
		// (ac+bd)/(c^2+d^2) + (ad+bc)i/(c^2+d^2)
		Complex numerator = this.multiply(new Complex(other.real,
				-other.imaginary));
		double denominator = other.real * other.real + other.imaginary
				* other.imaginary;
		return new Complex(numerator.real / denominator, numerator.imaginary
				/ denominator);
	}

	/**
	 * Adds the given complex number to {@code this} and returns the new
	 * resulting complex number.
	 * 
	 * @param other
	 *            The complex number to add to {@code this}.
	 * @return The new resulting complex number.
	 */
	public Complex add(Complex other) {
		return new Complex(real + other.real, imaginary + other.imaginary);
	}

	/**
	 * Subtracts the given complex vector from {@code this} and returns the new
	 * resulting complex number.
	 * 
	 * @param other
	 *            The complex number that is subtracted from {@code this}.
	 * @return The new resulting complex number.
	 */
	public Complex sub(Complex other) {
		return new Complex(real - other.real, imaginary - other.imaginary);
	}

	/**
	 * Returns negative {@code this}.
	 * 
	 * @return Negative {@code this}.
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}

	/**
	 * This method accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1",
	 * "-2.71-3.15i" and creates the Complex object of it.
	 * 
	 * @param line
	 *            String representation of the complex number.
	 * @return The Complex object.
	 * @throws NumberFormatException
	 *             - if the given string can not be parsed into the Complex
	 *             object.
	 */
	public static Complex parse(String line) {
		// Split the string by "+" or "-", including.
		String[] parts = line.replaceAll("\\s+", "")
				.split("(?<=[+-])|(?=[+-])");

		double realPart = 0;
		double imaginaryPart = 0;
		boolean negative = false;

		for (String part : parts) {
			if (part.matches("[+]")) {
				negative = false;
			} else if (part.matches("[-]")) {
				negative = true;
			} else if (part.contains("i")) {
				// Imaginary part.
				part = part.replace("i", "");
				if (part.isEmpty()) {
					// If imaginary part is "i"
					part = "1";
				}
				try {
					imaginaryPart = Double.parseDouble(part);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Can not parse \"" + line
							+ "\" to complex number.");
				}
				if (negative) {
					imaginaryPart = -imaginaryPart;
					negative = false;
				}
			} else {
				try {
					// Real part.
					realPart = Double.parseDouble(part);
					if (negative) {
						realPart = -realPart;
						negative = false;
					}
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Can not parse \"" + line
							+ "\" to complex number.");
				}
			}
		}

		return new Complex(realPart, imaginaryPart);
	}

	/**
	 * Returns the string representation of the complex number in the "a + bi"
	 * form, where "a" is the real part, "b" is the imaginary part and "i" is
	 * the imaginary unit.
	 *
	 * @return String representation of the complex number.
	 */
	@Override
	public String toString() {
		DecimalFormat formatterLeft = new DecimalFormat("0.#####;-0.#####");
		DecimalFormat formatterRight = new DecimalFormat("+ 0.#####;- 0.#####");
		return formatterLeft.format(real) + " "
				+ formatterRight.format(imaginary) + "i";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Complex)) {
			return false;
		}
		Complex other = (Complex) obj;
		boolean realEquals = Math.abs(this.real - other.real) < 1e-6;
		boolean imaginaryEquals = Math.abs(this.imaginary - other.imaginary) < 1e-6;
		if (realEquals && imaginaryEquals) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Double.valueOf(real + imaginary).hashCode();
	}
}
