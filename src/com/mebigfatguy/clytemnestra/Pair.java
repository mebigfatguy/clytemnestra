/*
 * clytemnastra - a simple gui explorer and stresstool for cassandra
 * Copyright 2011 MeBigFatGuy.com
 * Copyright 2011 Dave Brosius
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

public class Pair<K extends Comparable<K>, V extends Comparable<V>> implements Serializable, Comparable<Pair<K, V>>, Cloneable {

	private static final long serialVersionUID = 2916944903550233455L;
	
	private K key;
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
	
	public int hashCode() {
		return key.hashCode() ^ value.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o instanceof Pair) {
			Pair<K, V> that = (Pair<K, V>) o;
			
			if (!getKey().equals(that.getKey())) {
				return false;
			}
		
			return getValue().equals(that.getValue());
		}
		
		return false;
	}
	
	public int compareTo(Pair<K, V> that) {
		int cmp = getKey().compareTo(that.getKey());
		if (cmp != 0) {
			return cmp;
		}
		
		return getValue().compareTo(that.getValue());
	}
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new Error("Clone not implemented for Pair");
		}
	}
	
	
	
}
