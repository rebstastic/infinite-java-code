package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Apstraktni razred koji predstavlja geometrijske likove u 2D dimenziji. Svaki
 * razred koji nasljeđuje ovaj razred mora implementirati metodu
 * <code>sadrziTocku</code>. Ponuđena je metoda <code>popuniLik</code> koja na
 * neefikasan način crta ispunjeni geometrijski lik. Preporuča se nadjačati ovu
 * metodu efikasnijom implementacijom.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public abstract class GeometrijskiLik {

	/**
	 * Vraća <code>true</code> ako geometrijski lik sadrži točku,
	 * <code>false</code> inače.
	 * 
	 * @param x
	 *            x koordinata točke.
	 * @param y
	 *            y koordinata točke.
	 * @return <code>true</code> ako geometrijski lik sadrži točku,
	 *         <code>false</code> inače.
	 */
	public abstract boolean sadrziTocku(int x, int y);

	/**
	 * <p>
	 * Crta geometrijski lik paljenjem odgovarajućeg piksela na slici. Prikaz
	 * geometrijskog lika je odgovarajući bijeli oblik na crnoj pozadini.
	 * </p>
	 * 
	 * <p>
	 * Metoda je izrazito neefikasna. Preporučljivo je da svaki razred koji
	 * nasljeđuje ovaj razred ponudi efikasniju implementaciju crtanja
	 * geometrijskog lika.
	 * </p>
	 * 
	 * @param slika
	 *            Slika koja prikazuje nacrtani geometrijski lik.
	 */
	public void popuniLik(Slika slika) {
		for (int x = 0, sirina = slika.getSirina(), visina = slika.getVisina(); x < sirina; x++) {
			for (int y = 0; y < visina; y++) {
				if (this.sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}

}
