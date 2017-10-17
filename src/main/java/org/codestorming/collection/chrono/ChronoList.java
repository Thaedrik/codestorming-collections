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

import java.util.List;

/**
 * A {@code ChronoList} is a {@link ChronoCollection} for {@link List lists}.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
public interface ChronoList<E> extends List<E>, ChronoCollection<E> {

	@Override
	List<E> added();

	@Override
	List<E> removed();
}
