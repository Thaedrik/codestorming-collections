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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A {@code ChronoHashMap} is a {@link HashMap} implementing {@link ChronoMap}.
 *
 * @author Thaedrik [thaedrik@codestorming.org]
 * @since 1.1
 */
public class ChronoHashMap<K, V> extends HashMap<K, V> implements ChronoMap<K, V> {

	protected final Map<K, V> added = new HashMap<>();

	protected final Map<K, V> removed = new HashMap<>();

	public static <K, V> ChronoHashMap<K, V> create(K key, V value) {
		ChronoHashMap<K, V> map = new ChronoHashMap<>();
		map._put(key, value);
		return map;
	}

	public static <K, V> ChronoHashMap<K, V> create(K k1, V v1, K k2, V v2) {
		ChronoHashMap<K, V> map = new ChronoHashMap<>();
		map._put(k1, v1);
		map._put(k2, v2);
		return map;
	}

	public static <K, V> ChronoHashMap<K, V> create(K k1, V v1, K k2, V v2, K k3, V v3) {
		ChronoHashMap<K, V> map = new ChronoHashMap<>();
		map._put(k1, v1);
		map._put(k2, v2);
		map._put(k3, v3);
		return map;
	}

	public static <K, V> ChronoHashMap<K, V> create(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
		ChronoHashMap<K, V> map = new ChronoHashMap<>();
		map._put(k1, v1);
		map._put(k2, v2);
		map._put(k3, v3);
		map._put(k4, v4);
		return map;
	}

	private static boolean objectEqual(Object o1, Object o2) {
		return o1 == o2 || o1 != null && o1.equals(o2);
	}

	@Override
	public Map<K, V> added() {
		return added;
	}

	@Override
	public Map<K, V> removed() {
		return removed;
	}

	@Override
	public void reset() {
		added.clear();
		removed.clear();
	}

	protected void _put(K key, V value) {
		super.put(key, value);
	}

	@Override
	public V put(K key, V value) {
		final V old = super.put(key, value);
		if (objectEqual(removed.get(key), value)) {
			removed.remove(key);
		} else {
			added.put(key, value);
		}
		return old;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		final boolean contained = containsKey(key);
		final V value = super.remove(key);
		if (contained) {
			if (objectEqual(added.get(key), value)) {
				added.remove(key);
			} else {
				removed.put((K) key, value);
			}
		}
		return value;
	}

	@Override
	public void clear() {
		if (!isEmpty()) {
			for (K k : added.keySet()) {
				super.remove(k);
			}
			added.clear();
			removed.putAll(this);
		}
		super.clear();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return new EntrySet(super.entrySet());
	}

	@Override
	public Set<K> keySet() {
		return new KeySet(super.keySet());
	}

	@Override
	public Collection<V> values() {
		return new Values(super.values());
	}

	final class EntrySet extends DelegatedSet<Map.Entry<K, V>> {

		EntrySet(Set<Map.Entry<K, V>> delegate) {
			super(delegate);
		}

		@Override
		public Iterator<Map.Entry<K, V>> iterator() {
			final Iterator<Map.Entry<K, V>> iter = delegate.iterator();
			return new Iterator<Map.Entry<K, V>>() {
				Map.Entry<K, V> last;

				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public Map.Entry<K, V> next() {
					last = iter.next();
					return last;
				}

				@Override
				public void remove() {
					iter.remove();
					if (added.containsKey(last.getKey())) {
						added.remove(last.getKey());
					} else {
						removed.put(last.getKey(), last.getValue());
					}
				}
			};
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			final boolean remove = delegate.remove(o);
			if (remove) {
				final K key = ((Map.Entry<K, V>) o).getKey();
				if (added.containsKey(key)) {
					added.remove(key);
				} else {
					removed.put(key, ((Map.Entry<K, V>) o).getValue());
				}
			}
			return remove;
		}
	}

	final class KeySet extends DelegatedSet<K> {

		KeySet(Set<K> delegate) {
			super(delegate);
		}

		@Override
		public Iterator<K> iterator() {
			final Iterator<K> iter = delegate.iterator();
			return new Iterator<K>() {
				K last;

				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public K next() {
					last = iter.next();
					return last;
				}

				@Override
				public void remove() {
					final V value = get(last);
					iter.remove();
					if (added.containsKey(last)) {
						added.remove(last);
					} else {
						removed.put(last, value);
					}
				}
			};
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			final V value = get(o);
			if (super.remove(o)) {
				if (added.containsKey(o)) {
					added.remove(o);
				} else {
					removed.put((K) o, value);
				}
				return true;
			} // else
			return false;
		}
	}

	final class Values extends DelegatedCollection<V> {

		Values(Collection<V> delegate) {
			super(delegate);
		}

		@Override
		public Iterator<V> iterator() {
			final Iterator<V> iter = delegate.iterator();

			return new Iterator<V>() {
				private V last;

				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public V next() {
					last = iter.next();
					return last;
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}
}
