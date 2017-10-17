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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link ArrayList} that implements {@link ChronoCollection}.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
public class ChronoArrayList<E> extends ArrayList<E> implements ChronoList<E> {

	/**
	 * Contains the elements added to this list that were not originally (since the last reset) in this list.
	 */
	protected final ArrayList<E> added = new ArrayList<>();

	/**
	 * Contains the elements removed from the original (since the last reset) list.
	 */
	protected final ArrayList<E> removed = new ArrayList<>();

	/**
	 * Creates a new {@code ChronoArrayList} initialized with the given element.
	 *
	 * @param e Element to initialize this list with.
	 * @param <E> Type of element contained in the list.
	 * @return a new {@code ChronoArrayList} initialized with the given element.
	 */
	public static <E> ChronoArrayList<E> create(E e) {
		ChronoArrayList<E> list = new ChronoArrayList<>();
		list._add(e);
		return list;
	}

	/**
	 * Creates a new {@code ChronoArrayList} initialized with the given elements.
	 *
	 * @param e Element to initialize this list with.
	 * @param elements Other elements to initialize this list with.
	 * @param <E> Type of element contained in the list.
	 * @return a new {@code ChronoArrayList} initialized with the given elements.
	 */
	public static <E> ChronoArrayList<E> create(E e, E... elements) {
		ChronoArrayList<E> list = new ChronoArrayList<>();
		list._add(e);
		for (E element : elements) {
			list._add(element);
		}
		return list;
	}

	@Override
	public List<E> added() {
		return added;
	}

	@Override
	public List<E> removed() {
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
		ChronoHelper.updateAdded(added, removed, e);
		return super.add(e);
	}

	@Override
	public void add(int index, E element) {
		ChronoHelper.updateAdded(added, removed, element);
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		ChronoHelper.updateAdded(added, removed, c);
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		ChronoHelper.updateAdded(added, removed, c);
		return super.addAll(index, c);
	}

	@Override
	public E set(int index, E element) {
		E e = super.set(index, element);
		ChronoHelper.updateAdded(added, removed, element);
		ChronoHelper.updateRemoved(added, removed, e);
		return e;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		if (fromIndex < toIndex) {
			for (int i = fromIndex; i < toIndex; i++) {
				ChronoHelper.updateRemoved(added, removed, get(i));
			}
		}
		super.removeRange(fromIndex, toIndex);
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
	@SuppressWarnings("unchecked")
	public boolean removeAll(Collection<?> c) {
		final Collection<E> elements = ChronoHelper.elements(this, c, true);
		if (super.removeAll(c)) {
			ChronoHelper.updateRemoved(added, removed, elements);
			return true;
		} // else
		return false;
	}

	@Override
	public E remove(int index) {
		E element = super.remove(index);
		if (element != null) {
			ChronoHelper.updateRemoved(added, removed, element);
		}
		return element;
	}
}
