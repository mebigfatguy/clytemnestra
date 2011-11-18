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

import java.util.Map;

public class Strings {

    private Strings() {
    }

    public static boolean isEmpty(String s) {
        return (s == null) || (s.isEmpty());
    }

    public static String trim(String s) {
        if (s == null) {
            return "";
        }

        return s.trim();
    }
    
    public static String mapToCSV(Map<?, ?> m) {
    	StringBuilder sb = new StringBuilder();
    	String sep = "";
    	for (Map.Entry<?, ?> entry : m.entrySet()) {
    		sb.append(sep);
    		sep = ",";
    		sb.append(entry.getKey()).append("=").append(entry.getValue());
    	}
    	
    	return sb.toString();
    }
    
    
    public static String getSimpleName(String qualifiedName) {
    	int lastDotPos = qualifiedName.lastIndexOf('.');
    	if (lastDotPos >= 0)
    		qualifiedName = qualifiedName.substring(lastDotPos+1);
    	return qualifiedName;
    }
}
