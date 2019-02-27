package pl.edu.mimuw;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;


//Remembers its function and all elements seen so far. Will return only elements it has not seen before.
class UniqueMapper<E> {
	Set<E> ignoredElements = new HashSet<E>();
	Function<E, E> mapper;

	private UniqueMapper(Function<E, E> mapper) {
		this.mapper = mapper;
	}
	
	//factory method
	public static <E> UniqueMapper<E> from(Function<E, E> mapper) {
		return new UniqueMapper<>(mapper);
	}

	E processElement(E e) {
		E dummy = mapper.apply(e);
		if (ignoredElements.add(dummy))
			return dummy;
		else
			return null;
	}
}

//A lazy Iterator. Any moment if can be decorated with a map operation. The functions used
//in maps will be stored in a list and used to process elements. At each step duplicates
//will be eliminated. Storing the functions in a list has this advantage over functional
//implementation that we will not get stack overflow errors.
public class LazyIterator<E> {
	Iterator<E> backingIter;
	E next;

	ArrayList<UniqueMapper<E>> uniqueMappers = new ArrayList<UniqueMapper<E>>();

	private LazyIterator(Iterator<E> backingIter) {
		this.backingIter = backingIter;
		uniqueMappers.add(UniqueMapper.from((E x) -> x));
	}

	//factory method
	public static <T> LazyIterator<T> from(Iterator<T> backingIter) {
		return new LazyIterator<T>(backingIter);
	}

	public void map(Function<E, E> f) {
		uniqueMappers.add(UniqueMapper.from(f));
	}

	// sets next
	public boolean hasNext() {
		if (next != null)
			return true;

		labelWhile: while (backingIter.hasNext()) {
			next = backingIter.next();
			System.out.println(next);

			for (int j = 0; j < uniqueMappers.size(); j++) {
				next = uniqueMappers.get(j).processElement(next);
				if (next == null)
					continue labelWhile;
			}
			if (next != null) 
				return true;
		}

		return false;
	}

	public E next() {
		if (next == null && !hasNext())
			throw new NoSuchElementException();

		E dummy = next;
		next = null;
		return dummy;
	}
}
