package hr.fer.zemris.java.tecaj.hw3;

import java.text.DecimalFormat;

/**
 * <p>
 * This class represents an unmodifiable complex number. The complex numbers are
 * constant - their values can not be changed once they're created. The various
 * methods for performing arithmetic on complex numbers are defined.
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ComplexNumber {

	/**
	 * Real part of complex number.
	 */
	private double real;

	/**
	 * Imaginary part of complex number.
	 */
	private double imaginary;

	/**
	 * Constructor.
	 * 
	 * @param real
	 *            Real part of complex number.
	 * @param imaginary
	 *            Imaginary part of complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Returns the real part of complex number.
	 * 
	 * @return The real part of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of complex number.
	 * 
	 * @return The imaginary part of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the magnitude of complex number.
	 * 
	 * @return The magnitude of complex number.
	 */
	public double getMagnitude() {
		// sqrt(real^2 + imaginary^2)
		return Math.hypot(real, imaginary);
	}

	/**
	 * Returns the angle of complex number in the range of -pi to pi.
	 * 
	 * @return The angle of complex number.
	 */
	public double getAngle() {
		// arctan(imaginary/real)
		return Math.atan2(imaginary, real);
	}

	/**
	 * Returns the purely real complex number whose imaginary part is zero and
	 * real part is given as an argument.
	 * 
	 * @param real
	 *            The real part of complex number.
	 * @return The purely real complex number.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Returns the purely imaginary complex number whose real part is zero and
	 * imaginary part is given as an argument.
	 * 
	 * @param imaginary
	 *            The imaginary part of complex number.
	 * @return The purely imaginary complex number.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Returns the complex number created of given magnitude and angle.
	 * 
	 * @param magnitude
	 *            The magnitude of complex number.
	 * @param angle
	 *            The angle of complex number.
	 * @return The ComplexNumber object.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		if(magnitude < 0.0) {
			throw new IllegalArgumentException();
		}
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude
				* Math.sin(angle));
	}

	/**
	 * Adds the given complex number to this and returns the new resulting
	 * complex number.
	 * 
	 * @param c
	 *            The complex number to add to this.
	 * @return The new resulting complex number.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.getReal(), imaginary
				+ c.getImaginary());
	}

	/**
	 * Subtracts the given complex vector from this and returns the new
	 * resulting complex number.
	 * 
	 * @param c
	 *            The complex number that is subtracted from this.
	 * @return The new resulting complex number.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.getReal(), imaginary
				- c.getImaginary());
	}

	/**
	 * Multiplies the given complex number and this and returns a new resulting
	 * complex number.
	 * 
	 * @param c
	 *            The complex number to multiply with this.
	 * @return The new resulting complex number.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double realPart;
		double imaginaryPart;
		// (ac-bd) + (ad+bc)i
		realPart = real * c.getReal() - imaginary * c.getImaginary();
		imaginaryPart = real * c.getImaginary() + imaginary * c.getReal();
		return new ComplexNumber(realPart, imaginaryPart);
	}

	/**
	 * Divides this and given complex number and returns new resulting complex
	 * number.
	 * 
	 * @param c
	 *            The divisor.
	 * @return The new resulting complex number.
	 * @throws IllegalArgumentException
	 *             - if dividing by zero.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c.getReal() == 0.0 && c.getImaginary() == 0.0) {
			throw new IllegalArgumentException("Can not divide by zero.");
		}
		// (ac+bd)/(c^2+d^2) + (ad+bc)/(c^2+d^2)i
		ComplexNumber numerator = this.mul(new ComplexNumber(c.getReal(), -c
				.getImaginary()));
		double denominator = Math.pow(c.getReal(), 2)
				+ Math.pow(c.getImaginary(), 2);
		return new ComplexNumber(numerator.getReal() / denominator,
				numerator.getImaginary() / denominator);
	}

	/**
	 * Calculates n-th power of this complex number.
	 * 
	 * @param n
	 *            The power.
	 * @return The new resulting complex number.
	 * @throws IllegalArgumentException
	 *             - if power is negative.
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Calculates n roots of this complex number.
	 * 
	 * @param n
	 *            Required root to calculate.
	 * @return An array of roots.
	 * @throws IllegalArgumentException
	 *             - if the root is less than one.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			double magnitude = Math.pow(getMagnitude(), 1.0 / n);
			double angle = getAngle() + 2 * i * Math.PI;
			roots[i] = fromMagnitudeAndAngle(magnitude, angle);
		}
		return roots;
	}

	/**
	 * This method accepts strings such as: "3.51", "-3.17", "-2.71i", "i", "1",
	 * "-2.71-3.15i" and creates the ComplexNumber object of it.
	 * 
	 * @param string
	 *            String representation of the complex number.
	 * @return The ComplexNumber object.
	 * @throws NumberFormatException
	 *             - if the given string can not be parsed into the
	 *             ComplexNumber object.
	 */
	public static ComplexNumber parse(String string) {
		String parseString;
		// Remove all whitespaces.
		parseString = string.trim().replaceAll("\\s+", "");
		// Split the string by "+" or "-", including.
		String[] parts = parseString.split("(?<=[+-])|(?=[+-])");
		
		double realPart = 0;
		double imaginaryPart = 0;
		boolean negative = false;

		// Ako je samo i stavi imagonary = 1
		for (String part : parts) {
			if (part.matches("[+]")) {
				negative = false;
			} else if (part.matches("[-]")) {
				negative = true;
			} else if (part.endsWith("i")) {
				// Imaginary part.
				part = part.replace("i", "");
				try {
					imaginaryPart = Double.parseDouble(part);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Can not parse \"" + string
							+ "\" to ComplexNumber.");
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
					throw new NumberFormatException("Can not parse \"" + string
							+ "\" to ComplexNumber.");
				}
			}
		}

		return new ComplexNumber(realPart, imaginaryPart);

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
		DecimalFormat formatterLeft = new DecimalFormat("0.##;-0.##");
		DecimalFormat formatterRight = new DecimalFormat("+ 0.##;- 0.##");
		return formatterLeft.format(real) + " "
				+ formatterRight.format(imaginary) + "i";
	}

}
