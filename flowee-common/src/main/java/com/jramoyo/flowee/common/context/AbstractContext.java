/*
 * AbstractContext.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

/**
 * <p>
 * An abstract class for building a key based context backed by a
 * <code>ConcurrentMap</code>
 * </p>
 * 
 * @author jramoyo
 */
public abstract class AbstractContext<K, V> implements Context<K, V> {

	private final ConcurrentMap<K, V> map;

	public AbstractContext() {
		this.map = Maps.newConcurrentMap();
	}

	@Override
	public void put(K key, V value) {
		if (value != null) {
			map.put(key, value);
		}
	}

	@Override
	public V get(K key) {
		return map.get(key);
	}

	@Override
	public void load(Map<K, V> map) {
		if (map != null && !map.isEmpty()) {
			this.map.putAll(map);
		}
	}

	@Override
	public Map<K, V> asMap() {
		return Collections.unmodifiableMap(new HashMap<K, V>(map));
	}
}