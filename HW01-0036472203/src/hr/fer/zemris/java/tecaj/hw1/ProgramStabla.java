package hr.fer.zemris.java.tecaj.hw1;

/**
 * The class represents a data structure formed as a binary tree. The user can
 * both insert the elements in the tree and access the elements by their data.
 * The tree allows duplicate elements.
 * 
 * @author Petra
 *
 */
class ProgramStabla {

	/**
	 * Defines the requirements for an object that can be used as a tree node.
	 * Each node contains string data, a reference to the left child and a
	 * reference to the right child.
	 * 
	 * @author Petra
	 *
	 */
	static class CvorStabla {
		CvorStabla lijevi;
		CvorStabla desni;
		String podatak;
	}

	/**
	 * Demonstration of a binary tree usage. The tree is created to store names.
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		CvorStabla cvor = null;
		cvor = ubaci(cvor, "Jasna");
		cvor = ubaci(cvor, "Ana");
		cvor = ubaci(cvor, "Ivana");
		cvor = ubaci(cvor, "Anamarija");
		cvor = ubaci(cvor, "Vesna");
		cvor = ubaci(cvor, "Kristina");
		System.out.println("Ispisujem stablo inorder:");
		ispisiStablo(cvor);
		int vel = velicinaStabla(cvor);
		System.out.println("Stablo sadrzi elemenata: " + vel);
		boolean pronaden = sadrziPodatak(cvor, "Ivana");
		System.out.println("Trazeni podatak je pronaden: " + pronaden);
	}

	/**
	 * Returns true if the binary tree contains given element.
	 * 
	 * @param korijen
	 *            The reference to the tree.
	 * @param podatak
	 *            The data of the element to search.
	 * @return true if and only if this tree contains given element.
	 */
	static boolean sadrziPodatak(CvorStabla korijen, String podatak) {
		if (korijen == null) {
			return false;
		} else {
			return ((korijen.podatak).compareToIgnoreCase(podatak) == 0)
					|| sadrziPodatak(korijen.lijevi, podatak)
					|| sadrziPodatak(korijen.desni, podatak);
		}
	}

	/**
	 * Returns the number of elements in the tree starting from given node.
	 * Usually, the given parameter is the reference to the first element in the
	 * tree and therefore the result is a total tree size.
	 * 
	 * @param cvor
	 *            The reference to the tree.
	 * @return the size of the tree starting from given node.
	 */
	static int velicinaStabla(CvorStabla cvor) {
		if (cvor == null)
			return 0;
		return velicinaStabla(cvor.lijevi) + 1 + velicinaStabla(cvor.desni);
	}

	/**
	 * Inserts a new element in the tree. If the element data is less or equal
	 * than current node data, the insertion is applied on the left side of the
	 * tree. Right otherwise. This specification gives sorted inorder listing.
	 * 
	 * @param korijen
	 *            The reference to the list.
	 * @param podatak
	 *            The data of the element to insert.
	 * @return
	 */
	static CvorStabla ubaci(CvorStabla korijen, String podatak) {
		if (korijen == null) {
			CvorStabla novi = new CvorStabla();
			novi.podatak = podatak;
			return novi;
		}
		if (podatak.compareToIgnoreCase(korijen.podatak) <= 0) {
			korijen.lijevi = ubaci(korijen.lijevi, podatak);
		} else {
			korijen.desni = ubaci(korijen.desni, podatak);
		}
		return korijen;
	}

	/**
	 * Prints the tree on the standard output stream in inorder listing.
	 * 
	 * @param cvor
	 *            The reference to the list.
	 */
	static void ispisiStablo(CvorStabla cvor) {
		if (cvor.lijevi != null) {
			ispisiStablo(cvor.lijevi);
		}
		System.out.println(cvor.podatak);
		if (cvor.desni != null) {
			ispisiStablo(cvor.desni);
		}
	}
}