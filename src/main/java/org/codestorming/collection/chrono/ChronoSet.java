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

import java.util.Set;

/**
 * A {@code ChronoSet} is a {@link ChronoCollection} for {@link Set sets}.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
public interface ChronoSet<E> extends Set<E>, ChronoCollection<E> {

	@Override
	Set<E> added();

	@Override
	Set<E> removed();
}
