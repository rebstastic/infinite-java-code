package hr.fer.zemris.java.tecaj.hw4.grafika;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

/**
 * Razred koji predstavlja pravokutnik u 2D prostoru. Sadrži vlastitog
 * stvaratelja te metode za provjeru sadrživosti točke i crtanje pravokutnika na
 * slici.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Pravokutnik extends GeometrijskiLik {

	/**
	 * Referenca na stvaratelja linije.
	 */
	public static final StvarateljLika STVARATELJ = new PravokutnikStvaratelj();

	/**
	 * Gornji lijevi vrh pravokutnika.
	 */
	private Tocka vrh;

	/**
	 * Širina pravokutnika.
	 */
	private int sirina;

	/**
	 * Visina pravokutnika.
	 */
	private int visina;

	/**
	 * Konstruktor. Inicijalizira gornji lijevi vrh pravokutnika, širinu i
	 * visinu pravokutnika.
	 * 
	 * @param vrh
	 *            Gornji lijevi vrh pravokutnika.
	 * @param sirina
	 *            Širina pravokutnika.
	 * @param visina
	 *            Visina pravokutnika.
	 */
	public Pravokutnik(Tocka vrh, int sirina, int visina) {
		this.vrh = vrh;
		this.sirina = sirina;
		this.visina = visina;
	}

	/**
	 * Vraća referencu na stvaratelja pravokutnika.
	 * 
	 * @return Stvaratelj pravokutnika.
	 */
	public static StvarateljLika getStvaratelj() {
		return STVARATELJ;
	}

	/**
	 * Vraća gornji lijevi vrh pravokutnika.
	 * 
	 * @return Gornji lijevi vrh pravokutnika.
	 */
	public Tocka getVrh() {
		return vrh;
	}

	/**
	 * Vraća širinu pravokutnika.
	 * 
	 * @return Širina pravokutnika.
	 */
	public int getSirina() {
		return sirina;
	}

	/**
	 * Vraća visinu pravokutnika.
	 * 
	 * @return Visina pravokutnika.
	 */
	public int getVisina() {
		return visina;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		if (x < vrh.x || x >= vrh.x + sirina) {
			return false;
		}
		if (y < vrh.y || y >= vrh.y + visina) {
			return false;
		}
		return true;
	}

	@Override
	public void popuniLik(Slika slika) {
		int x = vrh.x < 0 ? 0 : vrh.x;
		int y = vrh.y < 0 ? 0 : vrh.y;
		int xGranica = vrh.x + sirina < slika.getSirina() ? vrh.x + sirina
				: slika.getSirina();
		int yGranica = vrh.y + visina < slika.getVisina() ? vrh.y + visina
				: slika.getVisina();
		for (int i = x; i < xGranica; i++) {
			for (int j = y; j < yGranica; j++) {
				slika.upaliTocku(i, j);
			}
		}
	}

	/**
	 * Pomoćni statički ugniježđeni razred koji predstavlja stvaratelja
	 * pravokutnika. Stvara pravokutnik parsiranjem stringa parametara.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class PravokutnikStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "PRAVOKUTNIK";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] listaParametara = parametri.split("[\\s+]");
			if (listaParametara.length != 4) {
				throw new IllegalArgumentException(
						"Neispravni parametri za liniju: " + parametri);
			}
			int vrhX, vrhY, sirina, visina;
			try {
				vrhX = Integer.parseInt(listaParametara[0]);
				vrhY = Integer.parseInt(listaParametara[1]);
				sirina = Integer.parseInt(listaParametara[2]);
				visina = Integer.parseInt(listaParametara[3]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Neispravni parametri za pravokutnik: " + parametri);
			}
			if (sirina <= 0 || visina <= 0) {
				throw new IllegalArgumentException(
						"Neispravni parametri za pravokutnik: " + parametri);
			}
			Pravokutnik pravokutnik = new Pravokutnik(new Tocka(vrhX, vrhY),
					sirina, visina);
			return pravokutnik;
		}

	}

}
