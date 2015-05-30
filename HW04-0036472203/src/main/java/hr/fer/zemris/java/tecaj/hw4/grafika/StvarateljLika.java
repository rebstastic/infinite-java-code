package hr.fer.zemris.java.tecaj.hw4.grafika;

/**
 * Sučelje koje predstavlja ugovor o stvaranju likova. Svaki razred koji
 * implementira ovo sučelje mora ponuditi implementaciju metoda
 * <code>nazivLika</code> i <code>stvoriIzStringa</code>.
 * 
 * @author Petra Rebernjak - 0036472203
 *
 */
public interface StvarateljLika {

	/**
	 * Vraća naziv geometrijskog lika.
	 * 
	 * @return Naziv geometrijskog lika.
	 */
	String nazivLika();

	/**
	 * Parsira predani string u parametre geometrijskog lika i vraća
	 * odgovarajući geometrijski lik. Ukoliko se predani parametri ne mogu
	 * parsirati na način zadovoljavajuć geometrijskom liku
	 * {@link IllegalArgumentException} iznimka je bačena.
	 * 
	 * @param parametri
	 *            Tekstualni zapis parametara geometrijskog lika.
	 * @return Odgovarajući geometrijski lik.
	 * @throws IllegalArgumentException
	 *             - ako parametri ne odgovaraju geometrijskom liku.
	 */
	GeometrijskiLik stvoriIzStringa(String parametri);

}
