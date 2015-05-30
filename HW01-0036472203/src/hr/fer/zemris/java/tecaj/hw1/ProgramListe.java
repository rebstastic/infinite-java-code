package hr.fer.zemris.java.tecaj.hw1;

/**
 * This class represents a data structure formed as a list of strings. New
 * elements are inserted at the end of the list. The user can sort the list. The
 * list allows duplicate elements.
 * 
 * @author Petra
 *
 */
class ProgramListe {

	/**
	 * Defines a requirements for an object that can be used as a list node.
	 * Each node contains string data and a reference to the node that is next
	 * in the list.
	 * 
	 * Initially, both data and reference are null.
	 * 
	 * @author Petra
	 *
	 */
	static class CvorListe {
		CvorListe sljedeci;
		String podatak;
	}

	/**
	 * Demonstration of list usage. The list is created to store and manipulate
	 * names. This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		CvorListe cvor = null;
		// ubaci() mora vratiti prvi element da se ne bi izgubio pocetak liste
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Jasna");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		int vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
		cvor = ubaci(cvor, "Žana");
		cvor = ubaci(cvor, "Sofija");
		cvor = ubaci(cvor, "Lana");
		cvor = ubaci(cvor, "Petra");
		cvor = ubaci(cvor, "Agneza");
		System.out.println("Ispisujem listu uz originalni poredak:");
		ispisiListu(cvor);
		cvor = sortirajListu(cvor);
		System.out.println("Ispisujem listu nakon sortiranja:");
		ispisiListu(cvor);
		vel = velicinaListe(cvor);
		System.out.println("Lista sadrzi elemenata: " + vel);
	}

	/**
	 * Returns the number of elements in the list starting from given node.
	 * Usually, the given parameter is the reference to the first element in the
	 * list and therefore the result is a total list size.
	 * 
	 * @param cvor
	 *            The reference to the list.
	 * @return the size of the list starting from given node.
	 */
	static int velicinaListe(CvorListe cvor) {
		if (cvor == null)
			return 0;
		return 1 + velicinaListe(cvor.sljedeci);
	}

	/**
	 * Inserts a new element at the end of the list.
	 * 
	 * @param prvi
	 *            The reference to the first element of the list.
	 * @param podatak
	 *            The data of the element to insert.
	 * @return the reference to the list.
	 */
	static CvorListe ubaci(CvorListe prvi, String podatak) {
		CvorListe novi = new CvorListe();
		novi.podatak = podatak;
		novi.sljedeci = null;
		CvorListe cvor;
		if (prvi != null) {
			for (cvor = prvi; cvor.sljedeci != null; cvor = cvor.sljedeci);
			cvor.sljedeci = novi;
		} else {
			prvi = novi;
		}

		return prvi;
	}

	/**
	 * Prints the list on the standard output stream. First shown element is given
	 * as an argument.
	 * 
	 * @param cvor
	 *            The reference to the list.
	 */
	static void ispisiListu(CvorListe cvor) {
		for (CvorListe forCvor = cvor; forCvor != null; forCvor = forCvor.sljedeci) {
			System.out.print(forCvor.podatak + " ");
		}
		System.out.println();
	}

	/**
	 * Sorts the list in ascending order by data stored in each list element.
	 * This method is using insertion sort. Each iteration, insertion sort takes
	 * one element from the input data, finds the location it belongs within the
	 * sorted list, and inserts it there. It is not efficient for large data
	 * sets.
	 * 
	 * @param cvor
	 *            The reference to the list.
	 * @return the reference to the sorted list.
	 */
	static CvorListe sortirajListu(CvorListe cvor) {
		CvorListe glava = cvor;
		CvorListe minCvor = cvor;
		CvorListe pomPrethodni = cvor;
		CvorListe pomSljedeci = cvor;
		CvorListe zadnjiSortirani = null;
		CvorListe forCvor;

		for (int i = velicinaListe(cvor); i > 1; i--) {
			if (zadnjiSortirani == null)
				forCvor = glava;
			else
				forCvor = zadnjiSortirani;
			for (; forCvor.sljedeci != null; forCvor = forCvor.sljedeci) {
				if ((forCvor.sljedeci.podatak).compareTo(minCvor.podatak) < 0) {
					pomPrethodni = forCvor;
					minCvor = forCvor.sljedeci;
					pomSljedeci = forCvor.sljedeci.sljedeci;
				}
			}

			if (zadnjiSortirani == null) {
				zadnjiSortirani = minCvor;
				if (minCvor != glava) {
					pomPrethodni.sljedeci = pomSljedeci;
					minCvor.sljedeci = glava; // ako je prvi u listi minimum
												// onda pokaze na sam sebe ->
												// beskonacna petlja
					glava = minCvor;
				}
			} else {
				if (minCvor != zadnjiSortirani.sljedeci) {
					pomPrethodni.sljedeci = pomSljedeci;
					CvorListe pom = zadnjiSortirani.sljedeci;
					zadnjiSortirani.sljedeci = minCvor;
					minCvor.sljedeci = pom;
				}
				zadnjiSortirani = minCvor;
			}

			pomPrethodni = minCvor;
			minCvor = zadnjiSortirani.sljedeci;
			pomSljedeci = minCvor.sljedeci;
		}
		return glava;
	}

}