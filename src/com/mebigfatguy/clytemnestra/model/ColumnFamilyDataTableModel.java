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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Pair;

public class ColumnFamilyDataTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -1540764453055275897L;
	
	private final List<Pair<String, List<ColumnOrSuperColumn>>> columnData = new ArrayList<Pair<String, List<ColumnOrSuperColumn>>>();
	private final List<String> columnNames = new ArrayList<String>();
	
	public void clear() {
    	columnData.clear();
    	columnNames.clear();
	}
    
    public void append(List<Pair<String, List<ColumnOrSuperColumn>>> newColumnData) {
    	columnData.addAll(newColumnData);
    	columnNames.add(Bundle.getString(Bundle.Key.Key));
    	
    	for (Pair<String, List<ColumnOrSuperColumn>> pair : columnData) {
    		for (ColumnOrSuperColumn column : pair.getValue()) {
    			
    			String name = new String(column.column.getName());
    			if (!columnNames.contains(name)) {
    				columnNames.add(name);
    			}
    		}
    	}
    	
        fireTableStructureChanged();
    }
    
	@Override
	public int getRowCount() {
		return columnData.size();
	}

	@Override
	public int getColumnCount() {
		if (columnData.isEmpty()) {
			return 0;
		}
		
		return columnData.get(0).getValue().size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return columnData.get(rowIndex).getKey();			
		} else {
			List<ColumnOrSuperColumn> row = columnData.get(rowIndex).getValue();
			ColumnOrSuperColumn column = row.get(columnIndex - 1);
			String name = new String(column.column.getName());
			if (name.equals(columnNames.get(columnIndex))) {
				return new String(column.column.getValue());	
			}
			
			Charset utfCS = Charset.forName("UTF-8");
			for (ColumnOrSuperColumn col : row) {
				if (name.equals(new String(col.column.getName(), utfCS))) {
					return new String(col.column.getValue());
				}
			}
		}
		
		return "";
	}
	
    @Override
    public String getColumnName(int column) {
		if (columnData.isEmpty()) {
			return "";
		}
		return columnNames.get(column);
    }
    
    @Override
	public Class<?> getColumnClass(int columnIndex) {
    	return String.class;
    }

}
