package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Razred koji predstavlja kvadrat u 2D prostoru. Sadrži vlastitog
 * stvaratelja te metode za provjeru sadrživosti točke i crtanje kvadrata na
 * slici.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public class Kvadrat extends Pravokutnik {

	/**
	 * Referenca na stvaratelja linije.
	 */
	public static final StvarateljLika STVARATELJ = new KvadratStvaratelj();
	
	/**
	 * Konstruktor.
	 * @param vrh Gornji lijevi vrh kvadrata.
	 * @param duljina Duljina stranice kvadrata.
	 */
	public Kvadrat(Tocka vrh, int duljina) {
		super(vrh, duljina, duljina);
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
	 * Pomoćni statički ugniježđeni razred koji predstavlja stvaratelja
	 * kvadrata. Stvara kvadrat parsiranjem stringa parametara.
	 * 
	 * @author Petra Rebernjak - 0036472203
	 *
	 */
	private static class KvadratStvaratelj implements StvarateljLika {

		@Override
		public String nazivLika() {
			return "KVADRAT";
		}

		@Override
		public GeometrijskiLik stvoriIzStringa(String parametri) {
			String[] listaParametara = parametri.split("[\\s+]");
			if (listaParametara.length != 3) {
				throw new IllegalArgumentException(
						"Neispravni parametri za kvadrat: " + parametri);
			}
			int vrhX, vrhY, duljina;
			try {
				vrhX = Integer.parseInt(listaParametara[0]);
				vrhY = Integer.parseInt(listaParametara[1]);
				duljina = Integer.parseInt(listaParametara[2]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Neispravni parametri za kvadar: " + parametri);
			}
			if(duljina <= 0) {
				throw new IllegalArgumentException(
						"Neispravni parametri za kvadar: " + parametri);
			}
			Kvadrat kvadrat = new Kvadrat(new Tocka(vrhX, vrhY), duljina);
			return kvadrat;
		}
		
	}
}
