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
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

public class StressTestData {

	public static final int MAX_KEYSPACE_NAME_LENGTH = 30;
	private File dataFile;
	private List<KeySpaceData> keySpaceData;
	
	public StressTestData() {
		keySpaceData = new ArrayList<KeySpaceData>();
	}
	
	public StressTestData(int numKeySpaces) {
		this();
		
		for (int i = 0; i < numKeySpaces; i++) {
			KeySpaceData ksData = new KeySpaceData();
			ksData.name = RandomStringUtils.randomAlphanumeric(StressTestData.MAX_KEYSPACE_NAME_LENGTH);
			keySpaceData.add(ksData);
		}
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
	
	public List<KeySpaceData> getKeySpaceData() {
		return Collections.unmodifiableList(keySpaceData);
	}
}

@JsonSerialize
class KeySpaceData {
	String name;
	List<ColumnFamilyData> columnFamilyData;
	
	public KeySpaceData() {
		columnFamilyData = new ArrayList<ColumnFamilyData>();
		columnFamilyData.add(new ColumnFamilyData());
	}
	
	public String toString() {
		return name;
	}
}

@JsonSerialize
class ColumnFamilyData {
	ColumnType key;
	List<ColumnInfo> columnInfo;
	
	public ColumnFamilyData() {
		key = ColumnType.STRING;
		columnInfo = new ArrayList<ColumnInfo>();
		for (int i = 0; i < 30; i++) {
			columnInfo.add(new ColumnInfo());
		}
	}
}

@JsonSerialize
class ColumnInfo {
	String columnName;
	ColumnType columnType;
	
	public ColumnInfo() {
	}
}
