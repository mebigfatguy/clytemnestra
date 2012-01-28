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

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.ColumnDef;
import org.apache.cassandra.thrift.IndexType;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Strings;


public class ColumnIndexTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7958071543733609681L;

	private enum Columns { 
		Name(Bundle.Key.ColumnName, String.class), 
		Validation(Bundle.Key.ValidationClass, String.class), 
		IndexName(Bundle.Key.IndexName, String.class), 
		IndexType(Bundle.Key.IndexType, String.class);
		
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
    
	private final List<ColumnDef> columnIndexes = new ArrayList<ColumnDef>();
	
    public void replaceContents(List<ColumnDef> newColumnDefinitions) {
    	columnIndexes.clear();
    	columnIndexes.addAll(newColumnDefinitions);
        fireTableDataChanged();
    }
    
	@Override
	public int getRowCount() {
		return columnIndexes.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
    	ColumnDef columnDefinition = columnIndexes.get(rowIndex);
        switch (Columns.values()[columnIndex]) {
            case Name:
                return new String(columnDefinition.getName());
            	
            case Validation:
            	return Strings.getSimpleName(columnDefinition.getValidation_class());
            	
            case IndexName:
            	return columnDefinition.getIndex_name();
            	
            case IndexType:
            	IndexType type = columnDefinition.getIndex_type();
            	if (type == null)
            		return "";
            	return type.name();
            	
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
