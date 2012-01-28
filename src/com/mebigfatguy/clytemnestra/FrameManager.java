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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;

public class FrameManager {

	private enum Key { KS, CF, DATA };
	
	private static Map<String, JFrame> frames = new HashMap<String, JFrame>();
	
	private FrameManager() {
	}
	
	public static void setKeySpaceFrame(KsDef keySpace, JFrame frame) {
		frames.put(buildKey(keySpace), frame);
	}
	
	public static JFrame getKeySpaceFrame(KsDef keySpace) {
		return frames.get(buildKey(keySpace));
	}
	
	public static void removeKeySpaceFrame(KsDef keySpace) {
		frames.remove(buildKey(keySpace));
	}
	
	public static void setColumnFamilyFrame(CfDef columnFamily, JFrame frame) {
		frames.put(buildKey(columnFamily, false), frame);
	}
	
	public static JFrame getColumnFamilyFrame(CfDef columnFamily) {
		return frames.get(buildKey(columnFamily, false));
	}
	
	public static void removeColumnFamilyFrame(CfDef columnFamily) {
		frames.remove(buildKey(columnFamily, false));
	}
	
	public static void setColumnFamilyDataFrame(CfDef columnFamily, JFrame frame) {
		frames.put(buildKey(columnFamily, true), frame);		
	}
	
	public static JFrame getColumnFamilyDataFrame(CfDef columnFamily) {
		return frames.get(buildKey(columnFamily, true));
	}
	
	public static void removeColumnFamilyDataFrame(CfDef columnFamily) {
		frames.remove(buildKey(columnFamily, true));
	}
	
	public static List<JFrame> findKeySpaceDependentFrames(KsDef keySpace) {
		List<JFrame> keySpaceFrames = new ArrayList<JFrame>();
		
		String ksPrefix = buildKey(keySpace);
		for (Map.Entry<String, JFrame> entry : frames.entrySet()) {
			if (entry.getKey().startsWith(ksPrefix)) {
				keySpaceFrames.add(entry.getValue());
			}
		}
		
		return keySpaceFrames;
	}
	
	public static List<JFrame> findColumnFamilyDependentFrames(CfDef cfDef) {
		List<JFrame> columnFamilyFrames = new ArrayList<JFrame>();
		
		String cfPrefix = buildKey(cfDef, false);
		for (Map.Entry<String, JFrame> entry : frames.entrySet()) {
			if (entry.getKey().startsWith(cfPrefix)) {
				columnFamilyFrames.add(entry.getValue());
			}
		}
		
		return columnFamilyFrames;
	}
	
	private static String buildKey(KsDef keySpace) {
		return Key.KS.name() + ":" + keySpace.getName();
	}
	
	private static String buildKey(CfDef columnFamily, boolean isData) {
		String key = Key.KS.name() + ":" + columnFamily.getKeyspace() + "::" + Key.CF.name() + ":" + columnFamily.getName();
		
		if (isData) {
			key += ":" + Key.DATA.name();
		}
		
		return key;
	}
}
