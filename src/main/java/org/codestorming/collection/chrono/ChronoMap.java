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

import java.util.Map;

/**
 * A {@code ChronoMap} keeps the changes applied to it.
 * <p>
 * When new elements are added or removed to/from this map, they are also inserted respectively into the {@code added}
 * or {@code removed} maps.
 * <p>
 * The added or removed maps collections contains only the elements added or removed since the last reset of this map.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
public interface ChronoMap<K, V> extends Map<K, V> {

	/**
	 * Returns the elements that were added to this map and that weren't in the original one.
	 * <p>
	 * That is, all elements that were added since the last reset.
	 *
	 * @return the elements that were added to this map and that weren't in the original one.
	 */
	Map<K, V> added();

	/**
	 * Returns the elements that were removed from this map and that weren't in the original one.
	 * <p>
	 * That is, all elements that were removed since the last reset.
	 *
	 * @return the elements that were removed from this map and that weren't in the original one.
	 */
	Map<K, V> removed();

	/**
	 * Reset the internal {@code added} and {@code removed} maps collections.
	 */
	void reset();
}
