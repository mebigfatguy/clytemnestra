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
import java.util.List;

public class StressTestData {

	private List<KeySpaceData> keySpaceData;
	
	public StressTestData() {
		keySpaceData = new ArrayList<KeySpaceData>();
		keySpaceData.add(new KeySpaceData());
	}

	public static class KeySpaceData {
		private List<ColumnFamilyData> columnFamilyData;
		
		public KeySpaceData() {
			columnFamilyData = new ArrayList<ColumnFamilyData>();
			columnFamilyData.add(new ColumnFamilyData());
		}
	}
	
	public static class ColumnFamilyData {
		private ColumnType key;
		private List<ColumnInfo> columnInfo;
		
		public ColumnFamilyData() {
			key = ColumnType.STRING;
			columnInfo = new ArrayList<ColumnInfo>();
			for (int i = 0; i < 30; i++) {
				columnInfo.add(new ColumnInfo());
			}
		}
	}
	
	public static class ColumnInfo {
		private String columnName;
		private ColumnType columnType;
		
		public ColumnInfo() {
		}
	}
}
