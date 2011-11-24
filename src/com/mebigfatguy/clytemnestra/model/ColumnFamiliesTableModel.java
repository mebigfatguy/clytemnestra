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

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.CfDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Strings;

public class ColumnFamiliesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7363520423000602880L;

	private enum Columns { 
		Name(Bundle.Key.ColumnFamily, String.class), 
		Type(Bundle.Key.ColumnType, String.class), 
		CompactionStrategy(Bundle.Key.CompactionStrategy, String.class), 
		ComparatorType(Bundle.Key.ComparatorType, String.class), 
		Comment(Bundle.Key.Comment, String.class);
		
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
    
	private final List<CfDef> columnFamilies = new ArrayList<CfDef>();
    
    public void replaceContents(List<CfDef> newColumnFamilies) {
    	columnFamilies.clear();
    	columnFamilies.addAll(newColumnFamilies);
        fireTableDataChanged();
    }
    
    public CfDef getColumnFamilyAt(int i) {
        return columnFamilies.get(i);
    }
    
	@Override
	public int getRowCount() {
		return columnFamilies.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	CfDef columnFamily = columnFamilies.get(rowIndex);
        switch (Columns.values()[columnIndex]) {
            case Name:
                return columnFamily.getName();

            case Type:
            	return columnFamily.getColumn_type();
                
            case CompactionStrategy:
            	return Strings.getSimpleName(columnFamily.getCompaction_strategy());
            	
            case ComparatorType:
            	return Strings.getSimpleName(columnFamily.getComparator_type());
            	
            case Comment:
            	return columnFamily.getComment();
            	
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
