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

import org.apache.cassandra.thrift.ColumnDef;

import com.mebigfatguy.clytemnestra.Bundle;


public class ColumnDefinitionsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7958071543733609681L;

	private enum Columns { Name };
    
	private final List<ColumnDef> columnDefinitions = new ArrayList<ColumnDef>();
	
    public void replaceContents(List<ColumnDef> newColumnDefinitions) {
    	columnDefinitions.clear();
    	columnDefinitions.addAll(newColumnDefinitions);
        fireTableDataChanged();
    }
    
	@Override
	public int getRowCount() {
		return columnDefinitions.size();
	}

	@Override
	public int getColumnCount() {
		return Columns.values().length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
    	ColumnDef columnDefinition = columnDefinitions.get(rowIndex);
        switch (Columns.values()[columnIndex]) {
            case Name:
                return new String(columnDefinition.getName());
            	
            default:
                return "";
        }
	}
	
    @Override
    public String getColumnName(int column) {
        switch (Columns.values()[column]) {
            case Name:
                return Bundle.getString(Bundle.Key.ColumnName);
            	
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (Columns.values()[columnIndex]) {
            case Name:
                return String.class;
            	
            default:
                return String.class;
        }
    }
}
