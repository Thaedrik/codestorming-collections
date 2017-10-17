/*
 * Copyright (c) 2012-2017 Codestorming.org
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Codestorming - initial API and implementation
 */
package org.codestorming.collection.chrono;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * {@link HashSet} implementing {@link ChronoCollection}.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 * @since 1.1
 */
public class ChronoHashSet<E> extends HashSet<E> implements ChronoSet<E> {

	/**
	 * Contains the elements added to this set that were not originally (since the last reset) in this set.
	 */
	protected final HashSet<E> added = new HashSet<>();

	/**
	 * Contains the elements removed from the original (since the last reset) set.
	 */
	protected final HashSet<E> removed = new HashSet<>();

	/**
	 * Creates a new {@code ChronoHashSet} initialized with the given element.
	 *
	 * @param e Element to initialize this set with.
	 * @param <E> Type of element contained in the set.
	 * @return a new {@code ChronoHashSet} initialized with the given element.
	 */
	public static <E> ChronoHashSet<E> create(E e) {
		ChronoHashSet<E> set = new ChronoHashSet<>();
		set._add(e);
		return set;
	}

	/**
	 * Creates a new {@code ChronoHashSet} initialized with the given elements.
	 *
	 * @param e Element to initialize this set with.
	 * @param elements Other elements to initialize this set with.
	 * @param <E> Type of element contained in the set.
	 * @return a new {@code ChronoHashSet} initialized with the given elements.
	 */
	public static <E> ChronoHashSet<E> create(E e, E... elements) {
		ChronoHashSet<E> set = new ChronoHashSet<>();
		set._add(e);
		for (E element : elements) {
			set._add(element);
		}
		return set;
	}

	@Override
	public Set<E> added() {
		return added;
	}

	@Override
	public Set<E> removed() {
		return removed;
	}

	@Override
	public void reset() {
		ChronoHelper.reset(added, removed);
	}

	/**
	 * Used internaly to add an element without modifying the added collection.
	 * <p>
	 * It is used only for initialization purposes.
	 */
	protected boolean _add(E e) {
		return super.add(e);
	}

	@Override
	public boolean add(E e) {
		if (super.add(e)) {
			ChronoHelper.updateAdded(added, removed, e);
			return true;
		} // else
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Collection<E> elements = ChronoHelper.elements(this, c, false);
		if (super.addAll(c)) {
			ChronoHelper.updateAdded(added, removed, elements);
			return true;
		} // else
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(Object o) {
		if (super.remove(o)) {
			ChronoHelper.updateRemoved(added, removed, (E) o);
			return true;
		} // else
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Collection<E> elements = ChronoHelper.elements(this, c, true);
		if (super.removeAll(c)) {
			ChronoHelper.updateRemoved(added, removed, elements);
			return true;
		} // else
		return false;
	}

	@Override
	public void clear() {
		if (!isEmpty()) {
			ChronoHelper.updateRemoved(added, removed, this);
		}
		super.clear();
	}

	@Override
	public Iterator<E> iterator() {
		return new ChronoHashSetIterator(super.iterator());
	}

	final class ChronoHashSetIterator implements Iterator<E> {

		private final Iterator<E> delegate;

		private E last;

		ChronoHashSetIterator(Iterator<E> delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}

		@Override
		public E next() {
			last = delegate.next();
			return last;
		}

		@Override
		public void remove() {
			if (last != null) {
				ChronoHelper.updateRemoved(added, removed, last);
			}
			delegate.remove();
		}
	}
}
