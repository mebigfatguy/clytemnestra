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

	private enum Columns { Name, Type, CompactionStrategy, ComparatorType, Comment };
    
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
        switch (Columns.values()[column]) {
            case Name:
                return Bundle.getString(Bundle.Key.ColumnFamily);
            	
            case Type:
            	return Bundle.getString(Bundle.Key.ColumnType);
            	
            case CompactionStrategy:
            	return Bundle.getString(Bundle.Key.CompactionStrategy);
            	
            case ComparatorType:
            	return Bundle.getString(Bundle.Key.ComparatorType);
            	
            case Comment:
            	return Bundle.getString(Bundle.Key.Comment);
            	
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (Columns.values()[columnIndex]) {
            case Name:
                return String.class;
                
            case Type:
            	return String.class;
            	
            case CompactionStrategy:
            	return String.class;
            
            case ComparatorType:
            	return String.class;
            	
            case Comment:
            	return String.class;
            	
            default:
                return String.class;
        }
    }
}
