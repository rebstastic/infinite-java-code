package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class computes and prints first n prime numbers. User provides n as a single
 * command line argument. The result is shown to the user on the standard output
 * stream.
 * 
 * @author Petra
 *
 */
public class PrimeNumbers {

	/**
	 * Demonstration of calculating first n prime numbers. User provides n. 
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments.");
			System.exit(1);
		}

		int n = Integer.parseInt(args[0]);
		if (n <= 0) {
			System.err.println("Number n must be greater than 0.");
			System.exit(1);
		}

		int i = 2;
		int number = 2;
		boolean isPrime = true;

		System.out.println("You requested calculation of " + n
				+ " prime numbers. Here they are:");
		System.out.println("1. " + number++);

		while (i <= n) {
			for (int divider = 2; divider <= Math.sqrt(number); divider++) {
				if (number % divider == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				System.out.println(i + ". " + number);
				i++;
			}
			isPrime = true;
			number++;
		}
	}

}