package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred koji predstavlja liniju u 2D prostoru. Sadrži vlastitog stvaratelja te
 * metode za provjeru sadrživosti točke i crtanje linije na slici.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Linija extends GeometrijskiLik {

	/**
	 * Referenca na stvaratelja linije.
	 */
	public static final StvarateljLika STVARATELJ = new LinijaStvaratelj();

	/**
	 * Početna točka linije.
	 */
	private Tocka t1;

	/**
	 * Završna točka linije.
	 */
	private Tocka t2;

	/**
	 * Konstruktor. Inicijalizira vrijednosti početne i završne točke.
	 * 
	 * @param t1
	 *            Početna točka linije.
	 * @param t2
	 *            Završna točka linije.
	 */
	public Linija(Tocka t1, Tocka t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	/**
	 * Vraća referencu na stvaratelja linije.
	 * 
	 * @return Stvaratelj linije.
	 */
	public static StvarateljLika getStvaratelj() {
		return STVARATELJ;
	}

	/**
	 * Vraća početnu točku linije.
	 * 
	 * @return Početna točka linije.
	 */
	public Tocka getT1() {
		return t1;
	}

	/**
	 * Vraća završnu točku linije.
	 * 
	 * @return Završna točka linije.
	 */
	public Tocka getT2() {
		return t2;
	}

	/**
	 * Pomoćna metoda koja računa udaljenost između dvije točke. Ukoliko su te
	 * točke početna i završna točka linije, retultat je duljina linije.
	 * 
	 * @param a
	 *            Prva točka.
	 * @param b
	 *            Druga točka.
	 * @return Udaljenost između točaka a i b.
	 */
	private double udaljenost(Tocka a, Tocka b) {
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		Tocka tocka = new Tocka(x, y);
		return udaljenost(t1, tocka) + udaljenost(tocka, t2) == udaljenost(t1,
				t2);
	}

	@Override
	public void popuniLik(Slika slika) {
		// Počni s dijelom koji je unutar slike.
		int x1 = t1.x < 0 ? 0 : t1.x;
		int x2 = t2.x < 0 ? 0 : t2.x;
		int y1 = t1.y < 0 ? 0 : t1.y;
		int y2 = t2.y < 0 ? 0 : t2.y;

		// Ne dopusti ispadanje iz slike.
		x1 = t1.x > slika.getSirina() ? slika.getSirina() - 1 : t1.x;
		x2 = t2.x > slika.getSirina() ? slika.getSirina() - 1 : t2.x;
		y1 = t1.y > slika.getVisina() ? slika.getVisina() - 1 : t1.y;
		y2 = t2.y > slika.getVisina() ? slika.getVisina() - 1 : t2.y;

		if (x1 <= x2) {
			if (y1 <= y2) {
				bresenham1(slika, x1, y1, x2, y2);
			} else {
				bresenham2(slika, x1, y1, x2, y2);
			}
		} else {
			if (y1 >= y2) {
				bresenham1(slika, x2, y2, x1, y1);
			} else {
				bresenham2(slika, x2, y2, x1, y1);
			}
		}
	}

	/**
	 * Bresenhamov algoritam koji crta linije pod kutevima od 0 do 90 stupnjeva
	 * uz x1 < x2.
	 * 
	 * @param slika
	 *            Slika koja prikazuje nacrtanu liniju.
	 * @param x1
	 *            x koordinata prve točke.
	 * @param y1
	 *            y koordinata prve točke.
	 * @param x2
	 *            x koordinata druge točke.
	 * @param y2
	 *            y koordinata druge točke.
	 */
	private void bresenham1(Slika slika, int x1, int y1, int x2, int y2) {
		int x, yc, korekcija;
		int a, yf;

		// Provjeri je li razlika po y manja od razlike po x.
		if (y2 - y1 <= x2 - x1) {
			a = 2 * (y2 - y1);
			yc = y1;
			yf = -(x2 - x1);
			korekcija = -2 * (x2 - x1);
			// Crtaj.
			for (x = x1; x <= x2; x++) {
				slika.upaliTocku(x, yc);
				yf += a;
				if (yf >= 0) {
					yf += korekcija;
					yc += 1;
				}
			}
		} else {
			// Zamijeni x i y koordinate.
			x = x2;
			x2 = y2;
			y2 = x;
			x = x1;
			x1 = y1;
			y1 = x;
			a = 2 * (y2 - y1);
			yc = y1;
			yf = -(x2 - x1);
			korekcija = -2 * (x2 - x1);
			// Crtaj.
			for (x = x1; x <= x2; x++) {
				slika.upaliTocku(yc, x);
				yf += a;
				if (yf >= 0) {
					yf += korekcija;
					yc += 1;
				}
			}
		}
	}

	/**
	 * Bresenhamov algoritam koji crta linije pod kutevima od 0 do -90 stupnjeva
	 * uz x1 < x2.
	 * 
	 * @param slika
	 *            Slika koja prikazuje nacrtanu liniju.
	 * @param x1
	 *            x koordinata prve točke.
	 * @param y1
	 *            y koordinata prve točke.
	 * @param x2
	 *            x koordinata druge točke.
	 * @param y2
	 *            y koordinata druge točke.
	 */
	private void bresenham2(Slika slika, int x1, int y1, int x2, int y2) {
		int x, yc, korekcija;
		int a, yf;
		if (-(y2 - y1) <= x2 - x1) {
			a = 2 * (y2 - y1);
			yc = y1;
			yf = x2 - x1;
			korekcija = 2 * (x2 - x1);
			// Crtaj.
			for (x = x1; x <= x2; x++) {
				slika.upaliTocku(x, yc);
				yf += a;
				if (yf <= 0) {
					yf += korekcija;
					yc -= 1;
				}
			}
		} else {
			// Zamijeni x i y koordinate.
			x = x2;
			x2 = y1;
			y1 = x;
			x = x1;
			x1 = y2;
			y2 = x;
			a = 2 * (y2 - y1);
			yc = y1;
			yf = x2 - x1;
			korekcija = 2 * (x2 - x1);
			// Crtaj.
			for (x = x1; x <= x2; x++) {
				slika.upaliTocku(yc, x);
				yf += a;
				if (yf <= 0) {
					yf += korekcija;
					yc -= 1;
				}
			}
		}
	}

	/**
	 * Pomoćni statički ugniježđeni razred koji predstavlja stvaratelja linije.
	 * Stvara liniju parsiranjem stringa parametara.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class LinijaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "LINIJA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] listaParametara = parametri.split("[\\s+]");
			if (listaParametara.length != 4) {
				throw new IllegalArgumentException(
						"Neispravni parametri za liniju: " + parametri);
			}
			int t1x, t1y, t2x, t2y;
			try {
				t1x = Integer.parseInt(listaParametara[0]);
				t1y = Integer.parseInt(listaParametara[1]);
				t2x = Integer.parseInt(listaParametara[2]);
				t2y = Integer.parseInt(listaParametara[3]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Neispravni parametri za liniju: " + parametri);
			}
			Linija linija = new Linija(new Tocka(t1x, t1y), new Tocka(t2x, t2y));
			return linija;
		}

	}

}
