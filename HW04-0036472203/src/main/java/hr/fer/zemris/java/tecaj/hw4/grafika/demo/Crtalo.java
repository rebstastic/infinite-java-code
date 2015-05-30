package hr.fer.zemris.java.tecaj.hw4.grafika.demo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;
import hr.fer.zemris.java.tecaj.hw4.grafika.Elipsa;
import hr.fer.zemris.java.tecaj.hw4.grafika.GeometrijskiLik;
import hr.fer.zemris.java.tecaj.hw4.grafika.Krug;
import hr.fer.zemris.java.tecaj.hw4.grafika.Kvadrat;
import hr.fer.zemris.java.tecaj.hw4.grafika.Linija;
import hr.fer.zemris.java.tecaj.hw4.grafika.Pravokutnik;
import hr.fer.zemris.java.tecaj.hw4.grafika.StvarateljLika;
import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * <p>
 * Demonstracija crtanja geometrijskih likova na slici. Korisnik preko komandne
 * linije predaje 3 argumenta - putanja do tekstualne datoteke koja sadrži
 * definiciju geometrijskih likova, širinu slike i visinu slike. Geometrijski
 * likovi se iscrtavaju u novom prozoru pomoću razreda {@link PrikaznikSlike}
 * ili na proizvoljno predanom izlaznom toku podataka.
 * </p>
 * 
 * 
 * <p>
 * Ukoliko korisnik želi dodati novi geometrijski lik, potrebno je to naglasiti
 * metodi <code>podesi</code> kako bi program znao dohvatiti stvaratelja tog
 * razreda.
 * </p>
 * 
 * <p>
 * Linija je zadana početnom i završnom točkom. Pravokutnik je zadan
 * koordinatama gornjeg-lijevog ugla, širinom i visinom. Kvadrat je zadan
 * koordinatama gornjeg-lijevog ugla te duljinom stranice. Elipsa je zadana
 * koordinatama centra, vodoravnim te okomitim radijusom. Kružnica je zadana
 * koordinatama centra te radijusom.<br>
 * Primjer datoteke:<br>
 * LINIJA 5 5 10 10<br><br>
 * PRAVOKUTNIK 0 0 10 20<br>
 * KVADRAT 50 50 100<br>
 * ELIPSA 100 400 80 40<br>
 * KRUG 400 400 20<br>
 * </p>
 * 
 * @author Petra Rebernjak - 0036472203
 * @see Slika
 * @see PrikaznikSlike
 */
public class Crtalo {

	/**
	 * Metoda koja se poziva čim je program pokrenut.
	 * 
	 * @param args
	 *            Argumenti komandne linije.
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Neispravan broj predanih argumenata.");
			System.exit(1);
		}

		SimpleHashtable stvaratelji = podesi(Linija.class, Pravokutnik.class,
				Elipsa.class, Kvadrat.class, Krug.class);
		String[] definicije = null;
		try {
			definicije = Files.readAllLines(Paths.get(args[0]),
					StandardCharsets.UTF_8).toArray(new String[0]);
		} catch (IOException e) {
			System.err.println("File nije pronađen :" + args[0]);
			System.exit(1);
		}
		GeometrijskiLik[] likovi = new GeometrijskiLik[definicije.length];
		int index = 0;
		for (String definicija : definicije) {
			String lik = definicija.substring(0, definicija.indexOf(' '))
					.trim();
			String parametri = definicija.substring(definicija.indexOf(' '))
					.trim();
			StvarateljLika stvaratelj = (StvarateljLika) stvaratelji.get(lik);
			try {
				likovi[index++] = stvaratelj.stvoriIzStringa(parametri);
			} catch (IllegalArgumentException ex) {
				System.err.println("Nemoguće nacrtati sliku. "
						+ ex.getMessage());
				System.exit(1);
			}
		}

		int sirina = 0;
		int visina = 0;
		try {
			sirina = Integer.parseInt(args[1]);
			visina = Integer.parseInt(args[2]);
		} catch (NumberFormatException ex) {
			System.err.println("Neispravne dimenzije slike: " + args[1] + ", "
					+ args[2]);
			System.exit(1);
		}

		Slika slika = new Slika(sirina, visina);
		for (GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		PrikaznikSlike.prikaziSliku(slika);
	}

	/**
	 * Metoda zadužena za popunjavanje kolekcije stvarateljima geometrijskih
	 * likova. Podržani razredi predaju se kao argumenti. Ukoliko predani razred
	 * ne postoji ili ne sadrži stvaratelja geometrijskog lika
	 * {@link RuntimeException} iznimka je bačena.
	 * 
	 * @param razredi
	 *            Razredi geometrijskih likova koji su podržani za crtanje na
	 *            slici.
	 * @return Kolekciju stvaratelja predanih razreda.
	 * @throws RuntimeException
	 *             - ako razred ne postoji ili ne sadrži vlastitog stvaratelja.
	 */
	private static SimpleHashtable podesi(Class<?>... razredi) {
		SimpleHashtable stvaratelji = new SimpleHashtable();
		for (Class<?> razred : razredi) {
			try {
				Field field = razred.getDeclaredField("STVARATELJ");
				StvarateljLika stvaratelj = (StvarateljLika) field.get(null);
				stvaratelji.put(stvaratelj.nazivLika(), stvaratelj);
			} catch (Exception ex) {
				throw new RuntimeException(
						"Nije moguće doći do stvaratelja za razred "
								+ razred.getName() + ".", ex);
			}
		}
		return stvaratelji;
	}
}
