package hr.fer.zemris.java.student0036472203.hw06;

/**
 * Razred koji nudi elementarne metode za ispitivanje cijelih brojeva.
 */
public class CijeliBrojevi {

	/**
	 * Vraća <code>true</code> ako je broj neparan, inače
	 * <code>false</code>.
	 * @param broj Broj koji se ispituje.
	 * @return <code>true</code> ako je broj neparan, inače
	 * <code>false</code>.
	 */
    public boolean jeNeparan(int broj) {
		if (broj % 2 == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Vraća <code>true</code> ako je broj paran, inače
	 * <code>false</code>.
	 * @param broj Broj koji se ispituje.
	 * @return <code>true</code> ako je broj paran, inače
	 * <code>false</code>.
	 */
    public boolean jeParan(int broj) {
		if (broj % 2 != 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Vraća <code>true</code> ako je broj prost, inače
	 * <code>false</code>.
	 * @param broj Broj koji se ispituje.
	 * @return <code>true</code> ako je broj prost, inače
	 * <code>false</code>.
	 */
    public boolean jeProst(int broj) {
		for(int i = 2, kraj = (int) Math.sqrt(broj); i <= kraj; i++) {
			if (broj % i == 0) {
				return false;
			}
		}
		return true;
	}
}