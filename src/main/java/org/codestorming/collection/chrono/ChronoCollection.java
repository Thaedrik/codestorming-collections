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

/**
 * A {@code ChronoCollection} keeps the changes applied to it.
 * <p>
 * When new elements are added or removed to/from this collection, they are also inserted respectively into the {@code
 * added} or {@code removed} collection.
 * <p>
 * The added or removed collection contains only the elements added or removed since the last reset of this collection.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 * @since 1.1
 */
public interface ChronoCollection<E> extends Collection<E> {

	/**
	 * Returns the elements that were added to this collection and that weren't in the original collection.
	 * <p>
	 * That is, all elements that were added since the last reset.
	 *
	 * @return the elements that were added to this collection and that weren't in the original collection.
	 */
	Collection<E> added();

	/**
	 * Returns the elements that were removed from this collection and that weren't in the original collection.
	 * <p>
	 * That is, all elements that were removed since the last reset.
	 *
	 * @return the elements that were removed from this collection and that weren't in the original collection.
	 */
	Collection<E> removed();

	/**
	 * Reset the internal {@code added} and {@code removed} collections.
	 */
	void reset();
}
