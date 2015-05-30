package hr.fer.zemris.java.tecaj.hw3;

import hr.fer.zemris.java.tecaj.hw3.CString;

import org.junit.Assert;
import org.junit.Test;

public class CStringTest {

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void constructor_NegativeOffset_ExceptionThrown() {
		new CString(new char[] { 'b', 'b' }, -1, 2);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void constructor_NegativeCount_ExceptionThrown() {
		new CString(new char[] { 'b', 'b' }, 0, -2);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void constructor_ArrayOutOfDataLength_ExceptionThrown() {
		new CString(new char[] { 'b', 'b' }, 1, 3);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void constructor_OffsetGreaterThanDataLength_ExceptionThrown() {
		new CString(new char[] { 'b', 'b' }, 5, 2);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void charAt_NegativeIndex_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' }, 0, 4);
		string.charAt(-1);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void charAt_IndexGraterThanItsCount_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' }, 1, 2);
		string.charAt(3);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void charAt_IndexGraterThanArrayLength_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' }, 0, 4);
		string.charAt(7);
	}

	@Test
	public void toCharArray_FullLength() {
		char[] array = new char[] { 'a', 'b', 'c', 'd' };
		CString string = new CString(array, 0, 4);
		Assert.assertArrayEquals(array, string.toCharArray());
	}

	@Test
	public void toCharArray_PartOfGivenCharArray() {
		char[] array = new char[] { 'a', 'b', 'c', 'd', 'e' };
		CString string = new CString(array, 1, 3);
		Assert.assertArrayEquals(new char[] { 'b', 'c', 'd' },
				string.toCharArray());
	}

	@Test
	public void indexOf_True() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		Assert.assertTrue(string.indexOf('b') == 1);

	}

	@Test
	public void indexOf_False() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		Assert.assertTrue(string.indexOf('g') == -1);
	}

	@Test
	public void startsWith_PartOfTheArray_True() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'b' });
		Assert.assertTrue(string1.startsWith(string2));
	}

	@Test
	public void startsWith_PartOfTheArray_False() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'g' });
		Assert.assertFalse(string1.startsWith(string2));
	}

	@Test
	public void startsWith_WholeArray_True() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		Assert.assertTrue(string1.startsWith(string2));
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void startsWith_BiggerArray_ExceptionThrown() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'b', 'c', 'd', 'e' });
		string1.startsWith(string2);
	}

	@Test
	public void endsWith_PartOfTheArray_True() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'c', 'd' });
		Assert.assertTrue(string1.endsWith(string2));
	}

	@Test
	public void endsWith_PartOfTheArray_False() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'g' });
		Assert.assertFalse(string1.endsWith(string2));
	}

	@Test
	public void endsWith_WholeArray_True() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		Assert.assertTrue(string1.endsWith(string2));
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void endsWith_BiggerArray_ExceptionThrown() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'a', 'b', 'c', 'd', 'e' });
		string1.endsWith(string2);
	}

	@Test
	public void contains_True() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'b', 'c' });
		Assert.assertTrue(string1.contains(string2));
	}

	@Test
	public void contains_False() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'l', 'd' });
		Assert.assertFalse(string1.contains(string2));
	}

	@Test
	public void contains_BiggerArray_False() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = new CString(new char[] { 'c', 'd', 'e', 'f' });
		Assert.assertFalse(string1.contains(string2));
	}
	
	@Test
	public void substring_Equal() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString substring = string.substring(1, 3);
		Assert.assertArrayEquals(new char[] { 'b', 'c'}, substring.toCharArray());
	}

	@Test
	public void substring_StartEqualsEnd_EmptyString() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString substring = string.substring(3, 3);
		Assert.assertArrayEquals(new char[] {}, substring.toCharArray());
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void substring_StartGreaterThanEnd_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		string.substring(3, 2);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void left_NegativeArgument_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		string.left(-1);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void left_ArgumentGreaterThanCount_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		string.left(6);
	}

	@Test
	public void left_Equal() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = string1.left(2);
		Assert.assertArrayEquals(new char[] { 'a', 'b' }, string2.toCharArray());
	}

	@Test
	public void left_FullLength() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = string1.left(4);
		Assert.assertArrayEquals(new char[] { 'a', 'b', 'c', 'd' },
				string2.toCharArray());
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void right_NegativeArgument_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		string.right(-1);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void right_ArgumentGreaterThanCount_ExceptionThrown() {
		CString string = new CString(new char[] { 'a', 'b', 'c', 'd' });
		string.right(5);
	}

	@Test
	public void right_Equal() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = string1.right(3);
		Assert.assertArrayEquals(new char[] { 'b', 'c', 'd' },
				string2.toCharArray());
	}

	@Test
	public void right_FullLength() {
		CString string1 = new CString(new char[] { 'a', 'b', 'c', 'd' });
		CString string2 = string1.right(4);
		Assert.assertArrayEquals(new char[] { 'a', 'b', 'c', 'd' },
				string2.toCharArray());
	}

	@Test
	public void add_Equal() {
		CString string1 = new CString(new char[] { 'a', 'b' });
		CString string2 = new CString(new char[] { 'c', 'd' });
		Assert.assertArrayEquals(new char[] { 'a', 'b', 'c', 'd' }, string1
				.add(string2).toCharArray());
	}

	@Test
	public void replaceAll_Char_Equal() {
		char[] array1 = { '1', '2', '1', '1' };
		char[] array2 = { 'a', '2', 'a', 'a' };
		CString original = new CString(array1);
		Assert.assertArrayEquals(array2, original.replaceAll('1', 'a')
				.toCharArray());
	}
	
	@Test
	public void replaceAll_Sequence_Equal() {
		char[] array1 = { '1', '2', '1', '1' };
		char[] array2 = { 'a', 'b', '2', 'a', 'b', 'a', 'b' };
		char[] replace = {'1'};
		char[] seq = {'a', 'b'};
		CString original = new CString(array1);
		CString oldStr = new CString(replace);
		CString newStr = new CString(seq);
		Assert.assertArrayEquals(array2, original.replaceAll(oldStr, newStr)
				.toCharArray());
	}

}
