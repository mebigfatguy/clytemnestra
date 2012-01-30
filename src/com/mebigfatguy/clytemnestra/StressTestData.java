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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class StressTestData {

	private File dataFile;
	private List<KeySpaceData> keySpaceData;
	
	public StressTestData() {
		keySpaceData = new ArrayList<KeySpaceData>();
		keySpaceData.add(new KeySpaceData());
	}
	
	public StressTestData(File f) throws JsonMappingException, JsonParseException, IOException {
		dataFile = f;
		ObjectMapper mapper = new ObjectMapper();
		keySpaceData = mapper.readValue(dataFile, new TypeReference<List<KeySpaceData>>() { });
	}
	
	public void setFile(File f) {
		dataFile = f;
	}
	
	public void writeToFile() throws JsonMappingException, JsonGenerationException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(dataFile,  keySpaceData);
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
