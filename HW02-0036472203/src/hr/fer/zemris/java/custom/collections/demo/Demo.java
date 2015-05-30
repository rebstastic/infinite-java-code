package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * <p>
 * This class demonstrates a command-line application which accepts a single
 * command-line argument: expression which should be evaluated. Expression must
 * be in postfix representation. It is important to provide an argument as one
 * string. Do not forget to put quotation marks around it.
 * </p>
 * 
 * <p>
 * Example 1: “8 2 /” means apply / on 8 and 2, so 8/2=4. <br>
 * Example 2: “-1 8 2 / +” means apply / on 8 and 2, so 8/2=4, then apply + on
 * -1 and 4, so the result is 3.
 * </p>
 * 
 * 
 * @author Petra
 *
 */
public class Demo {

	/**
	 * <p>
	 * Demonstration of a stack usage in order to calculate simple postfix
	 * mathematical expressions.
	 * </p>
	 * 
	 * <p>
	 * This method is called once the program is run.
	 * </p>
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Expression not provided.");
			System.exit(1);
		} else if (args.length != 1) {
			System.err
					.println("Please surround the expresion with quotation marks.");
			System.exit(1);
		}

		ObjectStack stack = new ObjectStack();
		String[] expression = args[0].trim().split("\\s+");

		boolean notAnOperator = false;
		for (String s : expression) {
			try {
				int operand = Integer.parseInt(s);
				// If the exception is not thrown (if s is really a number),
				// push that number on the stack.
				stack.push(operand);
			} catch (NumberFormatException e) {
				// The exception is thrown which means s is not a number but an
				// operator.
				int rightOperand;
				int leftOperand;
				try {
					rightOperand = (int) stack.pop();
					leftOperand = (int) stack.pop();
				} catch (EmptyStackException ex) {
					// This happens when, for example, "3 8 / + 2" is provided.
					break;
				}

				switch (s) {
				case "+":
					stack.push(leftOperand + rightOperand);
					break;
				case "-":
					stack.push(leftOperand - rightOperand);
					break;
				case "*":
					stack.push(leftOperand * rightOperand);
					break;
				case "/":
					if(rightOperand != 0) {
						stack.push(leftOperand / rightOperand);
					} else {
						System.err.println("Division by zero.");
						System.exit(1);
					}
					break;
				case "%":
					if(rightOperand != 0) {
						stack.push(leftOperand % rightOperand);
					} else {
						System.err.println("Division by zero.");
						System.exit(1);
					}
					break;
				default:
					notAnOperator = true;
					break;
				}

			}
		}

		if (stack.size() != 1 || notAnOperator) {
			System.out.println("The expression can not be evaluated.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}

}
