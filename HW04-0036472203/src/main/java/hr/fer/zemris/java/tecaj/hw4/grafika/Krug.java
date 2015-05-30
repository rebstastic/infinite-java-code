package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Razred koji predstavlja krug u 2D prostoru. Sadrži vlastitog stvaratelja te
 * metode za provjeru sadrživosti točke i crtanje kruga na slici.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Krug extends Elipsa {

	/**
	 * Referenca na stvaratelja linije.
	 */
	public static final StvarateljLika STVARATELJ = new KruznicaStvaratelj();

	/**
	 * Konstruktor.
	 * 
	 * @param centar
	 *            Centar kruga.
	 * @param radijus
	 *            Radijus kruga.
	 */
	public Krug(Tocka centar, int radijus) {
		super(centar, radijus, radijus);
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
	 * Pomoćni statički ugniježđeni razred koji predstavlja stvaratelja kruga.
	 * Stvara krug parsiranjem stringa parametara.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class KruznicaStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KRUG";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] listaParametara = parametri.split("[\\s+]");
			if (listaParametara.length != 3) {
				throw new IllegalArgumentException(
						"Neispravni parametri za liniju: " + parametri);
			}
			int centarX, centarY, radijus;
			try {
				centarX = Integer.parseInt(listaParametara[0]);
				centarY = Integer.parseInt(listaParametara[1]);
				radijus = Integer.parseInt(listaParametara[2]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Neispravni parametri za kružnicu: " + parametri);
			}
			if (radijus <= 0) {
				throw new IllegalArgumentException(
						"Neispravni parametri za kružnicu: " + parametri);
			}
			Krug krug = new Krug(new Tocka(centarX, centarY), radijus);
			return krug;
		}

	}

}
