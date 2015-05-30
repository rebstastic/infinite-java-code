package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class calculates and prints the decomposition of a number onto prime
 * factors. User provides a number as a single command line argument. Result is
 * shown to the user on the standard output stream.
 * 
 * @author Petra
 *
 */
public class NumberDecomposition {

	/**
	 * Demonstration of calculating the number decomposition prime factors. User
	 * provides a number. This method is called once the program is run.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}

		int number = Integer.parseInt(args[0]);
		if (number < 1) {
			System.err.println("The number must be greater than zero.");
			System.exit(1);
		}

		System.out.println("You requested decomposition of number " + number
				+ " onto prime factors. Here they are:");
		int copy = number;
		int step = 1;
		for (int i = 2; i <= copy; i++) {
			if (copy % i == 0) {
				System.out.println(step + ". " + i);
				copy /= i;
				i--;
				step++;
			}
		}
	}

}
