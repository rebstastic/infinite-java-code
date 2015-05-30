package hr.fer.zemris.java.complex;

/**
 * This class represents a complex polynomial. Coefficient that is provided
 * first is the one multiplying the variable with biggest order. For example, if
 * given factors are: 1, -1, (2+i), (2-i), the polynomial looks like:
 * z^3-z^2+(2+i)z+(2-i).
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ComplexPolynomial {

	/**
	 * Polynom factors in descending order.
	 */
	private Complex[] factors;

	/**
	 * Constructor.
	 * 
	 * @param factors
	 *            Polynom factors.
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns an array of polynom factors.
	 * 
	 * @return An array of polynom factors.
	 */
	public Complex[] getFactors() {
		return factors;
	}

	/**
	 * Returns the order of this polynom. For example, for (7+2i)z^3+2z^2+5z+1
	 * returns 3.
	 * 
	 * @return The order of this polynom.
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Returns the new polynpmial this*other
	 * 
	 * @param other
	 *            Polynomial to multiply this with.
	 * @return The new polynomial this*other;
	 */
	public ComplexPolynomial multiply(ComplexPolynomial other) {
		Complex[] result = new Complex[this.factors.length
				+ other.factors.length - 1];
		for (int i = 0, len1 = this.factors.length; i < len1; i++) {
			for (int j = 0, len2 = other.factors.length; j < len2; j++) {
				if (result[i + j] == null) {
					result[i + j] = Complex.ZERO;
				}
				result[i + j] = result[i + j].add(this.factors[i]
						.multiply(other.factors[j]));
			}
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * Returns first derivative of this polynomial. For example, for
	 * (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5.
	 * 
	 * @return First derivate of this polynomial.
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[order()];
		for (int i = 0, len = order(); i < len; i++) {
			result[i] = factors[i].multiply(new Complex(order() - i, 0));
		}
		return new ComplexPolynomial(result);
	}

	/**
	 * Returns polynomial value at given point {@code z}.
	 * 
	 * @param z
	 *            The point.
	 * @return Polynomial value at given point {@code z}.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		Complex zN = Complex.ONE;
		for (int i = order(); i >= 0; i--) {
			result = result.add(factors[i].multiply(zN));
			zN = zN.multiply(z);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int exp = order();
		for (Complex c : factors) {
			if (c.equals(Complex.ZERO)) {
				exp--;
				continue;
			}
			sb.append("(" + c.toString() + ")");
			if (exp > 1) {
				sb.append("z^" + exp);
			} else if (exp == 1) {
				sb.append("z");
			}
			exp--;
			sb.append(" + ");
		}
		sb.delete(sb.length() - 3, sb.length());
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ComplexPolynomial)) {
			return false;
		}
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (other.order() != this.order()) {
			return false;
		}
		for (int i = 0, len = factors.length; i < len; i++) {
			if (!this.factors[i].equals(other.factors[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return this.factors.hashCode();
	}

}
