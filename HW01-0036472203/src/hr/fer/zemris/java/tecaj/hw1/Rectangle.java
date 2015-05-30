package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class demonstrates a simple rectangle area and perimeter calculator.
 * User specifies a rectangle width and height as command line arguments or
 * through the standard input stream. Both rectangle area and perimeter are
 * shown to the user through the standard output stream.
 * 
 * @author Petra
 *
 */
public class Rectangle {

	/**
	 * Demonstration of a rectangle area and perimeter calculation. User
	 * provides a rectangle width and height. This method is called once the
	 * program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 * @throws IOException
	 *             if an I/O error occurs.
	 */
	public static void main(String[] args) throws IOException {

		double width;
		double height;

		if (args.length < 2) {
			// User didn't provide arguments from command line.
			width = userInputHandling("width");
			height = userInputHandling("height");
		} else {
			// User provided arguments from command line.
			width = Double.parseDouble(args[0]);
			height = Double.parseDouble(args[1]);
			if(width < 0 || height < 0) {
				errorHandling("Both width and height must be positive.");
				System.exit(1);
			}
		}

		System.out.println("You have specified a rectangle with width " + width
				+ " and height " + height + ". Its area is "
				+ Rectangle.areaCalc(width, height) + " and its perimeter is "
				+ Rectangle.perimeterCalc(width, height) + ".");

	}

	/**
	 * Calculates the rectangle area.
	 * 
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 * @return the area of the rectangle.
	 */
	static double areaCalc(double width, double height) {
		return width * height;
	}

	/**
	 * Calculates the rectangle perimeter.
	 * 
	 * @param width
	 *            The width of the rectangle.
	 * @param height
	 *            The height of the rectangle.
	 * @return the perimeter of the rectangle.
	 */
	static double perimeterCalc(double width, double height) {
		return 2 * width + 2 * height;
	}

	/**
	 * Handles user input. This method communicates with the user in order to
	 * ensure validity of given input arguments.
	 * 
	 * @param widthOrHeight
	 * @return the value of given input.
	 * @throws IOException
	 *             if an I/O error occurs.
	 */
	static double userInputHandling(String widthOrHeight) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String line;
		double ret;
		while (true) {
			System.out.printf("Please provide " + widthOrHeight + ": ");
			line = reader.readLine();
			if (line.trim().isEmpty()) {
				errorHandling("Nothing was given!");
				continue;
			} else {
				ret = Double.parseDouble(line);
				if (ret < 0) {
					errorHandling("Given " + widthOrHeight + " is negative.");
					continue;
				}
			}
			break;
		}
		return ret;
	}

	/**
	 * Notifies the user that the error has occurred. The message is usually a
	 * complaint.
	 * 
	 * @param message
	 *            the error message.
	 */
	static void errorHandling(String message) {
		System.out.println(message);
	}

}
