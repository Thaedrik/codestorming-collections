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
 * XXX Write comment
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
public interface ChronoMap<K, V> extends Map<K, V> {

	Map<K, V> added();

	Map<K, V> removed();

	void reset();
}
