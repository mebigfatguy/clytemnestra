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

import org.apache.cassandra.thrift.KsDef;

import com.mebigfatguy.clytemnestra.Bundle;

public class KeySpacesTableModel extends AbstractTableModel {

    enum Columns { Name, ReplicationFactor, StrategyClass};

    private final List<KsDef> keySpaces = new ArrayList<KsDef>();

    public void replaceContents(List<KsDef> newKeySpaces) {
        keySpaces.clear();
        keySpaces.addAll(newKeySpaces);
        fireTableDataChanged();
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

            case ReplicationFactor:
                return Integer.valueOf(keySpace.getReplication_factor());

            case StrategyClass:
                return keySpace.getStrategy_class();

            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (Columns.values()[column]) {
            case Name:
                return Bundle.getString(Bundle.Key.KeySpace);

            case ReplicationFactor:
                return Bundle.getString(Bundle.Key.ReplicationFactor);

            case StrategyClass:
                return Bundle.getString(Bundle.Key.StrategyClass);

            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (Columns.values()[columnIndex]) {
            case Name:
                return String.class;

            case ReplicationFactor:
                return Integer.class;

            case StrategyClass:
                return String.class;

            default:
                return String.class;
        }
    }
}
