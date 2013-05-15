/*
 * Context.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.context;

import java.util.Map;

/**
 * <p>
 * An interface for a key based context
 * </p>
 * 
 * @param <K>
 *            the type of key
 * @param <V>
 *            the type of value
 * @author jramoyo
 */
public interface Context<K, V> {

	/**
	 * Saves a value identified by a key
	 * 
	 * @param key
	 *            a key
	 * @param value
	 *            a value
	 */
	void put(K key, V value);

	/**
	 * Returns a value given a key
	 * 
	 * @param key
	 *            a key
	 * @return a value given a key
	 */
	V get(K key);

	/**
	 * Loads a map to the context
	 * 
	 * @param map
	 *            a map
	 */
	void load(Map<K, V> map);

	/**
	 * Returns this context as a map
	 * 
	 * @return this context as a map
	 */
	Map<K, V> asMap();
}