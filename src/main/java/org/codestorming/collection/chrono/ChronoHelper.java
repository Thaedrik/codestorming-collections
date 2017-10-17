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
 * The {@link ChronoHelper} utility class gathers the methods used in the Chrono-collection API.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 * @since 1.1
 */
public class ChronoHelper {

	public static <E> void updateAdded(Collection<E> added, Collection<E> removed, Collection<? extends E> elements) {
		for (E element : elements) {
			updateAdded(added, removed, element);
		}
	}

	public static <E> void updateAdded(Collection<E> added, Collection<E> removed, E element) {
		if (removed.contains(element)) {
			removed.remove(element);
		} else {
			added.add(element);
		}
	}

	public static <E> void updateRemoved(Collection<E> added, Collection<E> removed, Collection<? extends E> elements) {
		for (E element : elements) {
			updateRemoved(added, removed, element);
		}
	}

	public static <E> void updateRemoved(Collection<E> added, Collection<E> removed, E element) {
		if (added.contains(element)) {
			added.remove(element);
		} else {
			removed.add(element);
		}
	}

	public static <E> void reset(Collection<E> added, Collection<E> removed) {
		added.clear();
		removed.clear();
	}

	@SuppressWarnings("unchecked")
	public static <E> Collection<E> elements(final Collection<E> collection, Collection<?> objects,
			boolean complement) {
		final List<E> elements = new ArrayList<>();
		for (Object o : objects) {
			if (collection.contains(o) == complement) {
				elements.add((E) o);
			}
		}
		return elements;
	}
}
