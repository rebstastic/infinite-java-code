package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * This class computes and prints all requested roots of given complex number.
 * User provides three arguments: real part of complex number, imaginary part of
 * complex number and required root to calculate. The result is shown to the
 * user through the standard output stream.
 * 
 * @author Petra
 *
 */
public class Roots {

	/**
	 * Demonstration of complex number roots calculation. User provides three
	 * arguments: real part of complex number, imaginary part of complex number
	 * and required root to calculate.
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}

		double real = Double.parseDouble(args[0]);
		double imaginary = Double.parseDouble(args[1]);
		int root = Integer.parseInt(args[2]);

		if (root < 2) {
			System.err.println("Root must be greater than 1.");
			System.exit(1);
		}
		
		// r = sqrt(real*real + imaginary*imaginary)
		double radius = Math.hypot(real, imaginary);
		// fi = arctan(imaginary/real), fi is between -pi and pi
		double fi = Math.atan2(imaginary, real);

		System.out.println("You requested calculation of " + root
				+ ". roots. Solutions are:");

		// n-th root of a radius
		double coef = Math.pow(radius, 1.0 / root);
		for (int i = 0; i < root; i++) {
			System.out.print(i + 1 + ") ");
			printComplexNumber(coef * Math.cos((fi + 2 * Math.PI * i) / root),
					coef * Math.sin((fi + 2 * Math.PI * i) / root));
		}
	}

	/**
	 * Prints complex number in the form (a + bi), where a is the real part, b
	 * is the imaginary part of the complex number and i is the imaginary unit.
	 * 
	 * @param real
	 *            The real part of complex number.
	 * @param imaginary
	 *            The imaginary part of complex number.
	 */
	static void printComplexNumber(double real, double imaginary) {
		DecimalFormat formatterLeft = new DecimalFormat("0.##;-0.##");
		DecimalFormat formatterRight = new DecimalFormat("+ 0.##;- 0.##");
		System.out.println(formatterLeft.format(real) + " "
				+ formatterRight.format(imaginary) + "i");
	}

}
