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

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.cassandra.thrift.CfDef;
import org.apache.cassandra.thrift.KsDef;

public class FrameManager {

	private enum Key { KS, CF };
	
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
		frames.put(buildKey(columnFamily), frame);
	}
	
	public static JFrame getColumnFamilyFrame(CfDef columnFamily) {
		return frames.get(buildKey(columnFamily));
	}
	
	public static void removeColumnFamilyFrame(CfDef columnFamily) {
		frames.remove(buildKey(columnFamily));
	}
	
	private static String buildKey(KsDef keySpace) {
		return Key.KS.name() + ":" + keySpace.getName();
	}
	
	private static String buildKey(CfDef columnFamily) {
		return Key.KS.name() + ":" + columnFamily.getKeyspace() + "::" + Key.CF.name() + ":" + columnFamily.getName();
	}
}
