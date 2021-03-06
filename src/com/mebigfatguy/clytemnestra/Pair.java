/*
 * clytemnastra - a simple gui explorer and stresstool for cassandra
 * Copyright 2011-2012 MeBigFatGuy.com
 * Copyright 2011-2012 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.clytemnestra;

import java.io.Serializable;

public class Pair<K extends Comparable<K>, V> implements Serializable, Comparable<Pair<K, V>>, Cloneable {

	private static final long serialVersionUID = 2916944903550233455L;
	
	private final K key;
	private V value;
	
	public Pair(K k, V v) {
		key = k;
		value = v;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V v) {
		value = v;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode() ^ value.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pair) {
			@SuppressWarnings("unchecked")
			Pair<K, V> that = (Pair<K, V>) o;
			
			if (!getKey().equals(that.getKey())) {
				return false;
			}
		
			return getValue().equals(that.getValue());
		}
		
		return false;
	}
	
	@Override
	public int compareTo(Pair<K, V> that) {
		return getKey().compareTo(that.getKey());
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new Error("Clone not implemented for Pair", cnse);
		}
	}
	
	public String toString() {
	    return "Pair[" + getKey() + "=" + getValue() + "]";
	}
	
}
