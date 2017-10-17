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
import java.util.Iterator;
import java.util.Set;

/**
 * {@link Set} that delegates operation to its internal set defined on creation.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 */
class DelegatedSet<T> extends DelegatedCollection<T> implements Set<T> {

	DelegatedSet(Set<T> delegate) {
		super(delegate);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;

		if (size() > c.size()) {
			for (Iterator<?> i = c.iterator(); i.hasNext(); ) {
				if (remove(i.next())) {
					modified = true;
				}
			}
		} else {
			for (Iterator<?> i = iterator(); i.hasNext(); ) {
				if (c.contains(i.next())) {
					i.remove();
					modified = true;
				}
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}
}

