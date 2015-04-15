package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred koji predstavlja elipsu u 2D prostoru. Sadrži vlastitog stvaratelja te
 * metode za provjeru sadrživosti točke i crtanje elipse na slici.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Elipsa extends GeometrijskiLik {

	/**
	 * Referenca na stvaratelja linije.
	 */
	public static final StvarateljLika STVARATELJ = new ElipsaStvaratelj();

	/**
	 * Centar elipse.
	 */
	private Tocka centar;

	/**
	 * Vodoravni radijus elipse.
	 */
	private int rVodoravni;

	/**
	 * Okomiti radijus elipse.
	 */
	private int rOkomiti;

	/**
	 * Konstruktor. Inicijalizira centar elipse, vodoravni i okomiti radijus
	 * elipse.
	 * 
	 * @param centar Centar elipse.
	 * @param rVodoravni Vodoravni radijus elipse.
	 * @param rOkomiti Okomiti radijus elipse.
	 */
	public Elipsa(Tocka centar, int rVodoravni, int rOkomiti) {
		super();
		this.centar = centar;
		this.rVodoravni = rVodoravni;
		this.rOkomiti = rOkomiti;
	}

	/**
	 * Vraća referencu na stvaratelja kvadrata.
	 * 
	 * @return Stvaratelj kvadrata.
	 */
	public static StvarateljLika getStvaratelj() {
		return STVARATELJ;
	}

	/**
	 * Vraća centar elipse.
	 * @return Centar elipse.
	 */
	public Tocka getCentar() {
		return centar;
	}

	/**
	 * Vraća vodoravni radijus elipse.
	 * @return Vodoravni radijus elipse.
	 */
	public int getrVodoravni() {
		return rVodoravni;
	}

	/**
	 * Vraća okomiti radijus elipse.
	 * @return Okomiti radijus elipse.
	 */
	public int getrOkomiti() {
		return rOkomiti;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		int razlikaX = (int) Math.pow((x - centar.x), 2);
		int razlikaY = (int) Math.pow((y - centar.y), 2);
		return (rOkomiti * razlikaX + rVodoravni * razlikaY) <= rVodoravni
				* rOkomiti;
	}

	@Override
	public void popuniLik(Slika slika) {
		int x = centar.x - rVodoravni < 0 ? 0 : centar.x - rVodoravni;
		int y = centar.y - rOkomiti < 0 ? 0 : centar.y - rOkomiti;

		int xGranica = centar.x + rVodoravni < slika.getSirina() ? centar.x
				+ rVodoravni : slika.getSirina();
		int yGranica = centar.y + rOkomiti < slika.getVisina() ? centar.y
				+ rOkomiti : slika.getVisina();

		for (int i = x; i < xGranica; i++) {
			for (int j = y; j < yGranica; j++) {
				if (sadrziTocku(i, j)) {
					slika.upaliTocku(i, j);
				}
			}
		}
	}

	/**
	 * Pomoćni statički ugniježđeni razred koji predstavlja stvaratelja elipse.
	 * Stvara elipsuF parsiranjem stringa parametara.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class ElipsaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "ELIPSA";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] listaParametara = parametri.split("[\\s+]");
			if (listaParametara.length != 4) {
				throw new IllegalArgumentException(
						"Neispravni parametri za liniju: " + parametri);
			}
			int centarX, centarY, rVodoravno, rOkomito;
			try {
				centarX = Integer.parseInt(listaParametara[0]);
				centarY = Integer.parseInt(listaParametara[1]);
				rVodoravno = Integer.parseInt(listaParametara[2]);
				rOkomito = Integer.parseInt(listaParametara[3]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Neispravni parametri za elipsu: " + parametri);
			}
			if (rVodoravno <= 0 || rOkomito <= 0) {
				throw new IllegalArgumentException(
						"Neispravni parametri za elipsu: " + parametri);
			}
			Elipsa elipsa = new Elipsa(new Tocka(centarX, centarY), rVodoravno,
					rOkomito);
			return elipsa;
		}

	}

}
