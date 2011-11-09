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
import com.mebigfatguy.clytemnestra.controllers.Controller;

public class ColumnFamiliesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 7363520423000602880L;

	private enum Columns { Name };
    
	private final List<CfDef> columnFamilies = new ArrayList<CfDef>();
    
    public void replaceContents(List<CfDef> newColumnFamilies) {
    	columnFamilies.clear();
    	columnFamilies.addAll(newColumnFamilies);
        fireTableDataChanged();
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

            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (Columns.values()[column]) {
            case Name:
                return Bundle.getString(Bundle.Key.ColumnFamily);
            	
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