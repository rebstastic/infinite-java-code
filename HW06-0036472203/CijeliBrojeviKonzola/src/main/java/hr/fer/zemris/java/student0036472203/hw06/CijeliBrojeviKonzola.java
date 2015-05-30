package hr.fer.zemris.java.student0036472203.hw06;

import java.util.Scanner;
import java.lang.NumberFormatException;

/**
 * Razred koji simulira konzolu za ispitivanje svojstava cijelih
 * brojeva. Moguće je ispitati je li predani broj paran, neparan
 * ili prost. Korisnik unosi brojeve u konzolu sve dok ne unese 
 * naredbu <code>quit</code>.
 */
public class CijeliBrojeviKonzola {

	/**
	 * Ova metoda se poziva kada je program pokrenut.
	 * @param args Argumenti komandne linije.
	 */
    public static void main( String[] args ) {
		CijeliBrojevi cijeliBrojevi = new CijeliBrojevi();
        Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.print("Unesite broj> ");
			String unos = scanner.next();
			int broj = 0;
			try {
				broj = Integer.parseInt(unos);
			} catch (NumberFormatException e) {
				if(unos.equalsIgnoreCase("quit")) {
					System.out.println("Hvala na druženju.");
					break;
				} else {
					System.out.println("Neispravan unos.");
				}
			}
			boolean paran = cijeliBrojevi.jeParan(broj);
			boolean neparan = cijeliBrojevi.jeNeparan(broj);
			boolean prim = cijeliBrojevi.jeProst(broj);
			System.out.println("Paran: " + (paran ? "DA" : "NE") + 
								", neparan: " + (neparan ? "DA" : "NE") +
								", prim: " + (prim ? "DA" : "NE"));
		}
		scanner.close();
    }
}
