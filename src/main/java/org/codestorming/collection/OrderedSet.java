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
package org.codestorming.collection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * An {@code OrderedSet} is the union between a {@link List} and a {@link Set}. All elements contained in the {@code
 * OrderedSet} are unique and the insertion order is preserved.
 * <p>
 * The addition of an object already present in the set does nothing, that is, the index at which the object was first
 * inserted does not change.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 * @see Collections3
 */
public interface OrderedSet<E> extends List<E>, Set<E> {

	@Override
	default void replaceAll(UnaryOperator<E> operator) {
		Objects.requireNonNull(operator);
		final ListIterator<E> iter = listIterator();
		while (iter.hasNext()) {
			iter.set(operator.apply(iter.next()));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	default void sort(Comparator<? super E> c) {
		Object[] a = this.toArray();
		Arrays.sort(a, (Comparator) c);
		ListIterator<E> i = this.listIterator();
		for (Object e : a) {
			i.next();
			i.set((E) e);
		}
	}

	@Override
	default Spliterator<E> spliterator() {
		return new Spliterator<E>() {
			private final int CHARACTERISTICS = DISTINCT | ORDERED | SIZED;

			private final ListIterator<E> iterator = listIterator();

			private int index = 0;

			@Override
			public boolean tryAdvance(Consumer<? super E> action) {
				final boolean advanced = iterator.hasNext();
				if (advanced) {
					action.accept(iterator.next());
					index++;
				}
				return advanced;
			}

			@Override
			public Spliterator<E> trySplit() {
				return null;
			}

			@Override
			public long estimateSize() {
				return size() - index;
			}

			@Override
			public int characteristics() {
				return CHARACTERISTICS;
			}
		};
	}
}
