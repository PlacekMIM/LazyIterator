package pl.edu.mimuw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class LazyIteratorTest {

	@Test
	void testNoDups() {
		List<Integer> l;
		LazyIterator<Integer> li;

		l = java.util.Arrays.asList(new Integer[] {});
		li = LazyIterator.from(l.iterator());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
		li = LazyIterator.from(l.iterator());
		assertEquals(1, (int) li.next());
		assertEquals(2, (int) li.next());
		assertEquals(3, (int) li.next());
		assertEquals(4, (int) li.next());
		assertEquals(5, (int) li.next());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
		li = LazyIterator.from(l.iterator());
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(2, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(3, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(4, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(5, (int) li.next());
	}

	@Test
	void testDups() {
		List<Integer> l;
		LazyIterator<Integer> li;

		l = java.util.Arrays.asList(new Integer[] { 1, 1 });
		li = LazyIterator.from(l.iterator());
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 1 });
		li = LazyIterator.from(l.iterator());
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 1, 2, 1, 4 });
		li = LazyIterator.from(l.iterator());
		assertEquals(1, (int) li.next());
		assertEquals(2, (int) li.next());
		assertEquals(4, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 1, 2, 1, 4 });
		li = LazyIterator.from(l.iterator());
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(2, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(4, (int) li.next());
		assertFalse(li.hasNext());
	}

	@Test
	void testDupsWithMapper() {
		List<Integer> l;
		LazyIterator<Integer> li;

		//identity mapping
		l = java.util.Arrays.asList(new Integer[] { 1, 1 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> x);
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 1 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> x);
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 1, 2, 1, 4 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> x);
		assertEquals(1, (int) li.next());
		assertEquals(2, (int) li.next());
		assertEquals(4, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 1, 2, 1, 4 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> x);
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(2, (int) li.next());
		assertTrue(li.hasNext());
		assertEquals(4, (int) li.next());
		assertFalse(li.hasNext());

	
		//mapping everything to 1
		l = java.util.Arrays.asList(new Integer[] { 1, 2 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> 1);
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> 1);
		assertTrue(li.hasNext());
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());

		l = java.util.Arrays.asList(new Integer[] { 1, 2, 1, 4, 1, 6 });
		li = LazyIterator.from(l.iterator());
		li.map((Integer x) -> 1);
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());
	}
	
	public static void main(String[] args) {
		List<Integer> l;
		LazyIterator<Integer> li;

		l = java.util.Arrays.asList(new Integer[] { 1, 1 });
		li = LazyIterator.from(l.iterator());
		assertEquals(1, (int) li.next());
		assertFalse(li.hasNext());
	}
}
