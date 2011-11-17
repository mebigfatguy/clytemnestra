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
package com.mebigfatguy.clytemnestra.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.ColumnOrSuperColumn;

import com.mebigfatguy.clytemnestra.Pair;

public class ColumnFamilyDataTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -1540764453055275897L;
	
	List<Pair<String, List<ColumnOrSuperColumn>>> columnData = new ArrayList<Pair<String, List<ColumnOrSuperColumn>>>();
	
    public void replaceContents(List<Pair<String, List<ColumnOrSuperColumn>>> newColumnData) {
    	columnData.clear();
    	columnData.addAll(newColumnData);
        fireTableStructureChanged();
    }
	@Override
	public int getRowCount() {
		return columnData.size();
	}

	@Override
	public int getColumnCount() {
		if (columnData.size() == 0) {
			return 0;
		}
		
		return columnData.get(0).getValue().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		List<ColumnOrSuperColumn> row = columnData.get(rowIndex).getValue();
		ColumnOrSuperColumn column = row.get(columnIndex);
		return new String(column.column.getValue());
	}
	
    @Override
    public String getColumnName(int column) {
    	try {
			if (columnData.size() == 0) {
				return "";
			}
			
			return new String(columnData.get(0).getValue().get(column).column.getName(), "UTF-8");
    	} catch (UnsupportedEncodingException uee) {
    		return "";
    	}
    }
    
    @Override
	public Class<?> getColumnClass(int columnIndex) {
    	return String.class;
    }

}
