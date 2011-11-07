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
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;
import com.mebigfatguy.clytemnestra.Strings;

public class KeySpacesTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -1250326173521242924L;

    private enum Columns { Name, StrategyClass, StrategyOptions};

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
        switch (Columns.values()[column]) {
            case Name:
                return Bundle.getString(Bundle.Key.KeySpace);

            case StrategyClass:
                return Bundle.getString(Bundle.Key.StrategyClass);

            case StrategyOptions:
            	return Bundle.getString(Bundle.Key.StrategicOptions);
            	
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (Columns.values()[columnIndex]) {
            case Name:
                return String.class;

            case StrategyClass:
                return String.class;


            case StrategyOptions:
                return String.class;
                
            default:
                return String.class;
        }
    }
}
