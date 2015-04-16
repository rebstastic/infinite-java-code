package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleHashtableTest {

	private SimpleHashtable t;
	private SimpleHashtable newT;

	@Before
	public void setUp() {
		t = new SimpleHashtable();
		t.put("GameOfThrones", 9.5);
		t.put("MadMen", 8.7);
		t.put("BetterCallSaul", 9.2);
		t.put("TrueDetective", 9.3);
		t.put("BreakingBad", 9.5);
		t.put("Community", 8.6);
		t.put("ModernFamily", 8.6);
	}

	@After
	public void tearDown() {
		t = null;
		newT = null;
	}

	/*
	 * hash("BetterCallSaul")) = 1 hash("Community")) = 1
	 * hash("GameOfThrones"))= 2 hash("MadMen")) = 2 hash("BreakingBad")) = 2
	 * hash("TrueDetective")) = 3 hash("ModernFamily")) = 3
	 * 
	 * [BetterCallSaul=9.2, Community=8.6, GameOfThrones=9.5, MadMen=8.7,
	 * BreakingBad=9.5, TrueDetective=9.3, ModernFamily=8.6]
	 */

	/* ==================== SIMPLE HASH TABLE CONSTRUCTOR ==================== */

	@Test(expected = IllegalArgumentException.class)
	public void constructor_NegativeCapacity_ExceptionThrown() {
		newT = new SimpleHashtable(-100);
	}

	/* ==================== PUT METHOD ==================== */

	@Test(expected = IllegalArgumentException.class)
	public void put_NullKey_ExceptionThrown() {
		t.put(null, 0);
	}

	@Test
	public void put_OnTheBeginning_ContainsKey() {
		newT = new SimpleHashtable();
		newT.put("Shameless", 8.7);
		Assert.assertTrue(newT.containsKey("Shameless"));
	}

	@Test
	public void put_RepeatKey_SameSize() {
		int sizeBefore = t.size();
		t.put("MadMen", 10);
		Assert.assertEquals(sizeBefore, t.size());
	}

	@Test
	public void put_RepeatKey_NewValue() {
		t.put("MadMen", 10);
		Assert.assertEquals(10, t.get("MadMen"));
	}

	@Test
	public void put_NewKey_SizeGreater() {
		int sizeBefore = t.size();
		t.put("Californication", 8.4);
		Assert.assertTrue(sizeBefore < t.size());
	}

	/* ==================== REHASH METHOD ==================== */

	@Test
	public void rehash() {
		newT = new SimpleHashtable(2);
		newT.put("HouseOfCards", 9.1);
		// Table will be rehashed.
		newT.put("GameOfThrones", 9.5);
		Assert.assertTrue(newT.containsKey("HouseOfCards")
				&& newT.containsKey("GameOfThrones"));
	}

	/* ==================== GET METHOD ==================== */

	@Test(expected = IllegalArgumentException.class)
	public void get_NullKey_ExceptionThrown() {
		t.get(null);
	}

	@Test
	public void get_NonExistingKeyInEmptySlot() {
		newT = new SimpleHashtable();
		newT.put("Vikings", 8.6);
		Assert.assertNull(newT.get("Community"));
	}

	@Test
	public void get_ExistingKey_EqualsValue() {
		Assert.assertEquals(8.7, t.get("MadMen"));
	}

	@Test
	public void get_NonExistingKey_EqualsNull() {
		Assert.assertEquals(null, t.get("PrettyLittleLiars"));
	}

	@Test
	public void get_FirstInTheSlot() {
		Assert.assertEquals(9.5, t.get("GameOfThrones"));
	}

	@Test
	public void get_InTheMiddle() {
		Assert.assertEquals(8.7, t.get("MadMen"));
	}

	@Test
	public void get_LastInTheSlot() {
		Assert.assertEquals(9.5, t.get("BreakingBad"));
	}

	/* ==================== SIZE METHOD ==================== */

	@Test
	public void size_ExpectedSeven() {
		Assert.assertTrue(7 == t.size());
	}

	/* ==================== CONTAINS VALUE METHOD ==================== */

	@Test
	public void containsValue_DoesContain_ExpectedTrue() {
		Assert.assertTrue(t.containsValue(9.5));
	}

	@Test
	public void containsValue_DoesNotContain_ExpectedFalse() {
		Assert.assertFalse(t.containsValue(100));
	}

	@Test
	public void containsValue_NullValue_ExpectedFalse() {
		Assert.assertFalse(t.containsValue(null));
	}

	/* ==================== CONTAINS KEY METHOD ==================== */

	@Test(expected = IllegalArgumentException.class)
	public void containsKey_NullKey_ExceptionThrown() {
		t.containsKey(null);
	}

	@Test
	public void containsKey_DoesContain_ExpectedTrue() {
		Assert.assertTrue(t.containsKey("ModernFamily"));
	}

	@Test
	public void containsKey_DoesNotContain_ExpectedFalse() {
		Assert.assertFalse(t.containsKey("HighSchoolMusical"));
	}

	/* ==================== REMOVE METHOD ==================== */

	@Test(expected = IllegalArgumentException.class)
	public void remove_NullKey_ExceptionThrown() {
		t.remove(null);
	}

	@Test
	public void remove_FirstInTheSlot() {
		t.remove("BetterCallSaul");
		Assert.assertNull(t.get("BetterCallSaul"));
	}

	@Test
	public void remove_InTheMiddle() {
		t.remove("MadMen");
		Assert.assertNull(t.get("MadMen"));
	}

	@Test
	public void remove_LastInTheSlot() {
		t.remove("ModernFamily");
		Assert.assertNull(t.get("ModernFamily"));
	}

	@Test
	public void remove_NonExisting() {
		int sizeBefore = t.size();
		t.remove("GosspiGirl");
		Assert.assertTrue(sizeBefore == t.size());
	}

	@Test
	public void remove_SizeLess() {
		t.remove("BetterCallSaul");
		t.remove("MadMen");
		t.remove("ModernFamily");
		Assert.assertTrue(t.size() == 4);
	}

	/* ==================== IS EMPTY METHOD ==================== */

	@Test
	public void isEmpty_AssertFalse() {
		Assert.assertFalse(t.isEmpty());
	}

	@Test
	public void isEmpty_AssertTrue() {
		t.remove("BetterCallSaul");
		t.remove("Community");
		t.remove("GameOfThrones");
		t.remove("MadMen");
		t.remove("BreakingBad");
		t.remove("TrueDetective");
		t.remove("ModernFamily");
		Assert.assertTrue(t.isEmpty());
	}
	
	/* ==================== CLEAR METHOD ==================== */

	@Test
	public void clear() {
		newT = new SimpleHashtable(2);
		newT.put("GameOfThrones", 9.5);
		newT.put("MadMen", 8.7);
		newT.put("BetterCallSaul", 9.2);
		newT.put("TrueDetective", 9.3);
		newT.put("BreakingBad", 9.5);
		newT.put("Community", 8.6);
		newT.put("ModernFamily", 8.6);
		newT.clear();
		Assert.assertTrue(newT.isEmpty());
	}

	/* ==================== TO STRING METHOD ==================== */

	@Test
	public void toString_Full_Equals() {
		newT = new SimpleHashtable(3);
		newT.put("BetterCallSaul", 9.2);
		newT.put("BreakingBad", 9.5);
		Assert.assertEquals("[BetterCallSaul=9.2, BreakingBad=9.5]",
				newT.toString());
	}

	@Test
	public void toString_Empty_Equals() {
		newT = new SimpleHashtable();
		Assert.assertEquals("[]", newT.toString());
	}
	
	/* ==================== TABLE ENTRY CONSTRUCTOR METHOD ==================== */

	@Test(expected=IllegalArgumentException.class)
	public void constructor_NullKey_ExceptionThrown() {
		new SimpleHashtable.TableEntry(null,null, null);
	}
	
	/* ==================== ITERATORS ==================== */

	@Test
	public void iterator_multipleIterators_ExpectDifferent() {
		Iterator<SimpleHashtable.TableEntry> it1 = t.iterator();
		Iterator<SimpleHashtable.TableEntry> it2 = t.iterator();
		it1.next(); 
		Assert.assertNotEquals(it1.next(), it2.next());
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void hasNext_ConcurrentPut_ExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		while(it.hasNext()) {
			it.next();
			t.put("HouseOfCards", 9.2);
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void hasNext_ConcurrentRemove_ExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		while(it.hasNext()) {
			it.next();
			t.remove("MadMen");
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void hasNext_ConcurrentClean_ExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		while(it.hasNext()) {
			it.next();
			t.clear();
		}
	}
	
	@Test
	public void hasNext_RemoveThroughIterator() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		while(it.hasNext()) {
			it.next();
			it.remove();
		}
		Assert.assertTrue(t.isEmpty());
	}
	
	@Test(expected=IllegalStateException.class)
	public void hasNext_RemoveTwoTimes_ExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		if(it.hasNext()) {
			it.next();
			it.remove();
			it.remove();

		}
	}
	
	@Test(expected=NoSuchElementException.class)
	public void next_DoesNotHaveNext_ExceptionThrown() {
		newT = new SimpleHashtable();
		Iterator<SimpleHashtable.TableEntry> it = newT.iterator();
		it.next();	
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void remove_ConcurrentModification_ExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry> it = t.iterator();
		while(it.hasNext()) {
			it.next();
			it.remove();
			it.next();
			t.put("HouseOfCards", 9.2);
			it.remove();
		}
	}
}