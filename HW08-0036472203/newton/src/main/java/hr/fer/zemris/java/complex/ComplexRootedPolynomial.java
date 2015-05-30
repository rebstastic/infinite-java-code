package hr.fer.zemris.java.complex;

/**
 * This class represents a complex polynomial in a form that is obtained by
 * polynomial factorization into factors of degree 1. For example, polynomial
 * x^3-2x^2-x+2 can be represents as complex rooted polynomial equal to
 * (x-2)(x-1)(x+1). Roots of that polynomial are 2, 1, -1.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Polynom roots.
	 */
	private Complex[] roots;

	/**
	 * Constructor.
	 * 
	 * @param roots
	 *            Polynom roots.
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Returns an array of polynom roots.
	 * 
	 * @return An array of polynom roots.
	 */
	public Complex[] getRoots() {
		return roots;
	}

	/**
	 * Returns poynomial value at fiven point {@code z}.
	 * 
	 * @param z
	 *            The point.
	 * @return Polynomial value at given point {@code z}.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for (Complex c : roots) {
			result = result.multiply(z.sub(c));
		}
		return result;
	}

	/**
	 * Converts this representation to {@code ComplexPolynomial} type.
	 * 
	 * @return This representation to {@code ComplexPolynomial} type.
	 */
	public ComplexPolynomial toComplexPolynomial() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		for (Complex c : roots) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, c
					.negate()));
		}
		return result;
	}

	/**
	 * Returns index of closest root for given complex number {@code z} that is
	 * within {@code treshold}. If there is no such root, returns -1.
	 * 
	 * @param z
	 *            The point.
	 * @param treshold
	 *            The treshold.
	 * @return Index of closest root for given complex number {@code z} that is
	 *         within {@code treshold}. If there is no such root, returns -1.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minDivergency = Double.MAX_VALUE;
		for (int i = 0, len = roots.length; i < len; i++) {
			double divergency = z.sub(roots[i]).module();
			if (Double.compare(divergency, treshold) < 0
					&& Double.compare(divergency, minDivergency) < 0) {
				minDivergency = divergency;
				index = i;
			}
		}
		return index;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Complex c : roots) {
			sb.append("[z-(" + c.toString() + ")] * ");
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
		if (!(obj instanceof ComplexRootedPolynomial)) {
			return false;
		}
		ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
		if (this.roots.length != other.roots.length) {
			return false;
		}
		for (int i = 0, len = roots.length; i < len; i++) {
			if (!this.roots[i].equals(other.roots[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return roots.hashCode();
	}

}
