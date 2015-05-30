package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.filters.QueryFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * A simple database emulator. Attributes are: <i>jmbag</i>, <i>lastName</i>,
 * <i>firstName</i>, <i>finalGrade</i>.
 * </p>
 * <p>
 * All queries must start with keyword <code>query</code>. When done use the
 * <code>quit</code> keyword.
 * </p>
 * <p>
 * Supported queries have the form:
 * <code>fieldName operator stringLiteral</code><br>
 * Field name has only three forms: <code>jmbag</code>, <code>lastName</code>,
 * <code>firstName</code><br>
 * Supported operators are: =, !=, <, <=, >, >=.<br>
 * String literals must be written in quotes, and quote can not be written in
 * string.<br>
 * The asterisk character, if present, can occur at most once, but it can be at
 * the beginning, at the end or somewhere in the middle).<br>
 * Only <code>and</code> is allowed for expression combining
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class StudentDB {

	/**
	 * <p>
	 * This method is called once the program is run.
	 * </p>
	 * <p>
	 * When started, program reads the data from current directory from file
	 * database.txt.
	 * </p>
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {

		String databaseName = "./database.txt";

		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(databaseName),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println("Can not open a file: " + databaseName);
			System.exit(1);
		}

		StudentDatabase database = null;
		try {
			database = new StudentDatabase(lines);
		} catch (DatabaseException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String line = scanner.nextLine().trim();
			if (line.equalsIgnoreCase("quit")) {
				scanner.close();
				break;
			}
			int spliter = line.indexOf(" ");
			String command = line.substring(0, spliter);
			if (!command.equalsIgnoreCase("query")) {
				System.err
						.println("Invalid command. Start command with \"query\".");
				continue;
			}

			String query = line.substring(spliter + 1);
			QueryFilter queryFilter = null;
			try {
				queryFilter = new QueryFilter(query);
			} catch (DatabaseException e) {
				System.err.println(e.getMessage());
				continue;
			}

			List<StudentRecord> validRecords = new ArrayList<>();
			if (queryFilter.getJMBAG().isPresent()) {
				StudentRecord record = database.forJMBAG(queryFilter.getJMBAG()
						.get());
				if (record != null) {
					if (queryFilter.accepts(record)) {
						validRecords.add(record);
						System.out.println("Using index for fast retrieval.");
					} else {
						continue;
					}
				}
			} else {
				validRecords = database.filter(queryFilter);
			}
			printResults(validRecords);
		}
	}

	/**
	 * Prints query results in table form.
	 * 
	 * @param records
	 *            Query results.
	 */
	private static void printResults(List<StudentRecord> records) {
		int[] max = maxLastAndFirstName(records);
		String boarder = printTableBorder(max);

		if (records.size() > 0) {
			StringBuilder sb = new StringBuilder();

			sb.append(boarder);

			for (StudentRecord record : records) {
				sb.append("\n");
				sb.append("| ");
				sb.append(record.getJmbag());
				sb.append(" | ");
				sb.append(record.getLastName());
				sb.append(printEmptySpaces(record.getLastName(), max[0]));
				sb.append(" | ");
				sb.append(record.getFirstName());
				sb.append(printEmptySpaces(record.getFirstName(), max[1]));
				sb.append(" | ");
				sb.append(record.getFinalGrade());
				sb.append(" |");
				sb.append("\n");
				sb.append(boarder);
			}

			System.out.println(sb.toString());
		}
		System.out.println("Records selected: " + records.size());

	}

	/**
	 * Appends white spaces at the end of the last name or first name in order
	 * to properly align records.
	 * 
	 * @param name
	 *            The name to align using white spaces.
	 * @param max
	 *            Maximum length of a name in the query results.
	 * @return Needed white spaces.
	 */
	private static String printEmptySpaces(String name, int max) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, length = max - name.length(); i < length; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Constructs the table border.
	 * 
	 * @param max
	 *            Maximum length of a table field.
	 * @return String representation of table border.
	 */
	private static String printTableBorder(int[] max) {
		StringBuilder sb = new StringBuilder();

		// JMBAG size.
		sb.append("+============+");

		// Last name size.
		for (int i = 0; i < max[0] + 2; i++) {
			sb.append("=");
		}

		sb.append("+");

		// First name size.
		for (int i = 0; i < max[1] + 2; i++) {
			sb.append("=");
		}

		sb.append("+");

		// Final grade size.
		sb.append("===+");

		return sb.toString();

	}

	/**
	 * Calculates the maximum last name length and first name length in query
	 * results.
	 * 
	 * @param records
	 *            Query results.
	 * @return Maximum last name length and first name length in query results.
	 */
	private static int[] maxLastAndFirstName(List<StudentRecord> records) {
		int[] max = new int[2];

		for (StudentRecord record : records) {
			int lastNameLenght = record.getLastName().length();
			int firstNameLength = record.getFirstName().length();
			if (lastNameLenght > max[0]) {
				max[0] = lastNameLenght;
			}
			if (firstNameLength > max[1]) {
				max[1] = firstNameLength;
			}
		}
		return max;
	}
}
