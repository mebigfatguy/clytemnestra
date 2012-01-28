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
package com.mebigfatguy.clytemnestra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Strings;

public class KeySpacesTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -1250326173521242924L;

    private enum Columns { 
    	Name(Bundle.Key.KeySpace, String.class), 
    	DurableWrites(Bundle.Key.DurableWrites, Boolean.class), 
    	StrategyClass(Bundle.Key.StrategyClass, String.class), 
    	StrategyOptions(Bundle.Key.StrategicOptions, String.class);
    
		private final Bundle.Key key;
		private final Class<?> cls;
		
		private Columns(Bundle.Key bundleKey, Class<?> columnClass) {
			key = bundleKey;
			cls = columnClass;
		}	
		
		public String getTitle() {
			return Bundle.getString(key);
		}
		
		public Class<?> getColumnClass() {
			return cls;
		}
	};

    private final List<KsDef> keySpaces = new ArrayList<KsDef>();

    public void replaceContents(List<KsDef> newKeySpaces) {
        keySpaces.clear();
        keySpaces.addAll(newKeySpaces);
        fireTableDataChanged();
    }

    public KsDef getKeySpaceAt(int i) {
        return keySpaces.get(i);
    }

    @Override
    public int getRowCount() {
        return keySpaces.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KsDef keySpace = keySpaces.get(rowIndex);
        switch (Columns.values()[columnIndex]) {
            case Name:
                return keySpace.getName();

            case DurableWrites:
            	return Boolean.valueOf(keySpace.durable_writes);
            	
            case StrategyClass:
                String name = keySpace.getStrategy_class();
                int dotPos = name.lastIndexOf('.');
                if (dotPos >= 0) {
                    name = name.substring(dotPos+1);
                }
                return name;
                
            case StrategyOptions:
            	Map<String, String> options = keySpace.getStrategy_options();
            	return Strings.mapToCSV(options);

            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return Columns.values()[column].getTitle();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Columns.values()[columnIndex].getColumnClass();
    }
}
